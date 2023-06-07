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

				System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
				type = bf.readLine();

				if(!"o".equals(type) && !"order".equals(type) && !"q".equals(type) && !"quit".equals(type)) {
					System.out.println("o[order] 또는 q[quit] 만 입력해주세요.");
					continue;
				}
				
				if("o".equals(type) || "order".equals(type)) {
					
					System.out.println("######");
					OrderAppDTO appDTO = new OrderAppDTO();
					List<OrderAppItemDTO> itemList = new ArrayList<>();
					List<ProductDTO> orderProductList = new ArrayList<ProductDTO>();
					
					//상품 리스트
					ProductDefaultDTO searchDTO = new ProductDefaultDTO();
					searchDTO.setSize(20);
					Page<ProductDTO> list = productService.selectProductList(searchDTO);
					PayAppDisplayUtil.productListDisplay(list.toList());
					
					while(true) {
						System.out.print("상품번호 : ");
						productNum = bf.readLine();
						
						if(productNum.isBlank() && itemList.size() > 0) {							
							try {
								//상품 재고량 체크
								for(ProductDTO tmp : orderProductList) {
									productService.validateProductCntCheck(tmp);
								}
								
								//주문 내역 display
								PayAppDisplayUtil.productPayDisPlay(appDTO,orderProductList,itemList);
								
								//주문 등록
								orderAppService.insertOrderApp(appDTO);	
							}catch (Exception e) {
								System.err.println(e.getMessage());
							}		
							break;
						}else if(productNum.isBlank()) {
							System.out.println("선택하신 상품번호가 없습니다. 초기화면으로 넘어갑니다.");
							break;
						}else {
							
							String temp = productNum;
							ProductDTO productDTO = list.stream().filter(x->x.getProductNum().equals(temp)).findFirst().orElse(null);
							if(productDTO == null) {
								System.out.println("해당 상품번호는 존재하지 않습니다.");
								continue;
							}
							
							//상품 수량 선택
							System.out.print("수량 : ");
							cnt = bf.readLine();
							
							//상품 아이템 담기
							OrderAppItemDTO itemDTO = new OrderAppItemDTO();
							itemDTO.setCnt(Integer.parseInt(cnt));
							itemDTO.setProductNum(temp);
							itemDTO.setName(productDTO.getName());
							itemDTO.setAmount(productDTO.getPrice()*Integer.parseInt(cnt));
							itemList.add(itemDTO);
							
							//수량 감소처리
							productDTO.minusProductCnt(itemDTO.getCnt());
							orderProductList.add(productDTO);
						}
					}
				}else {
					System.out.println("고객님의 주문 감사합니다.");
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
