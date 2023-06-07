package kr.co._29cm.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.service.OrderAppService;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import kr.co._29cm.homework.modules.product.service.ProductService;
import kr.co._29cm.homework.modules.util.PayAppDisplayUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
class HomeworkApplicationTests {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderAppService orderAppService;

	@Test
	void contextLoads() {
		BufferedReader bf = null;
		try {			
			String type = "";
			String productNum = "";
			String cnt = "";
			while(true) {
				
				bf = new BufferedReader(new InputStreamReader(System.in));

				System.out.print("�Է�(o[order]: �ֹ�, q[quit]: ����) : ");
				type = bf.readLine();

				if(!"o".equals(type) && !"order".equals(type) && !"q".equals(type) && !"quit".equals(type)) {
					System.out.println("o[order] �Ǵ� q[quit] �� �Է����ּ���.");
					continue;
				}
				
				if("o".equals(type) || "order".equals(type)) {
					
					System.out.println("######");
					OrderAppDTO appDTO = new OrderAppDTO();
					List<OrderAppItemDTO> itemList = new ArrayList<>();
					List<ProductDTO> orderProductList = new ArrayList<ProductDTO>();
					
					//��ǰ ����Ʈ
					ProductDefaultDTO searchDTO = new ProductDefaultDTO();
					searchDTO.setSize(20);
					Page<ProductDTO> list = productService.selectProductList(searchDTO);
					PayAppDisplayUtil.productListDisplay(list.toList());
					
					while(true) {
						System.out.print("��ǰ��ȣ : ");
						productNum = bf.readLine();
						
						if(productNum.isBlank() && itemList.size() > 0) {							
							try {
								//��ǰ ����� üũ
								for(ProductDTO tmp : orderProductList) {
									productService.validateProductCntCheck(tmp);
								}
								
								//�ֹ� ���� display
								PayAppDisplayUtil.productPayDisPlay(appDTO,orderProductList,itemList);
								
								//�ֹ� ���
								orderAppService.insertOrderApp(appDTO);	
							}catch (Exception e) {
								System.err.println(e.getMessage());
							}		
							break;
						}else if(productNum.isBlank()) {
							System.out.println("�����Ͻ� ��ǰ��ȣ�� �����ϴ�. �ʱ�ȭ������ �Ѿ�ϴ�.");
							break;
						}else {
							
							String temp = productNum;
							ProductDTO productDTO = list.stream().filter(x->x.getProductNum().equals(temp)).findFirst().orElse(null);
							if(productDTO == null) {
								System.out.println("�ش� ��ǰ��ȣ�� �������� �ʽ��ϴ�.");
								continue;
							}
							
							//��ǰ ���� ����
							System.out.print("���� : ");
							cnt = bf.readLine();
							
							//��ǰ ������ ���
							OrderAppItemDTO itemDTO = new OrderAppItemDTO();
							itemDTO.setCnt(Integer.parseInt(cnt));
							itemDTO.setProductNum(temp);
							itemDTO.setName(productDTO.getName());
							itemDTO.setAmount(productDTO.getPrice()*Integer.parseInt(cnt));
							itemList.add(itemDTO);
							
							//���� ����ó��
							productDTO.minusProductCnt(itemDTO.getCnt());
							orderProductList.add(productDTO);
						}
					}
				}else {
					System.out.println("�������� �ֹ� �����մϴ�.");
					break;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bf.close();
			} catch (IOException e) {
				System.err.println("BufferedReader close error");
			}
		}
		
	}
}