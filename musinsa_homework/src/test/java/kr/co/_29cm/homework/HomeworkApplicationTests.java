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
		BufferedReader 	bf = new BufferedReader(new InputStreamReader(System.in));
		try {			
			while(true) {
				String type = "";
				System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
				type = bf.readLine();
				
				//입력 유효성 체크
				if(!"o".equals(type) && !"order".equals(type) && !"q".equals(type) && !"quit".equals(type)) {
					System.out.println("o[order] 또는 q[quit] 만 입력해주세요.");
					continue;
				}
				
				if("o".equals(type) || "order".equals(type)) {
					
					//주문 번호
					OrderAppDTO appDTO = new OrderAppDTO();
					
					//주문 상품 목록
					List<OrderAppItemDTO> itemList = new ArrayList<>();
					//주문 상품 확인 목록
					List<String> productNumList = new ArrayList<String>();
					
					//상품 리스트
					ProductDefaultDTO searchDTO = new ProductDefaultDTO();
					searchDTO.setSize(20);
					Page<ProductDTO> list = productService.selectProductList(searchDTO);
					PayAppDisplayUtil.productListDisplay(list.toList());
					
					while(true) {
						String productNum = "";
						String cnt = "";
						
						System.out.print("상품번호 : ");
						productNum = bf.readLine();
						
						//결제 처리
						if(productNum.isBlank() && itemList.size() > 0) {							
							try {
								//주문 등록
								appDTO.setItemList(itemList);
								orderAppService.insertOrderApp(appDTO,productNumList);	
							}catch (Exception e) {
								System.err.println(e.getMessage());
							}		
							break;
						//초기 상품 선택 없을 경우 초기화면 이동
						}else if(productNum.isBlank()) {
							System.out.println("선택하신 상품번호가 없습니다. 초기화면으로 넘어갑니다.");
							break;
						//상품 담기 처리
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
							if(!isInteger(cnt)) {
								System.out.println("숫자를 입력해주시기 바랍니다. 다시 입력해주시기 바랍니다.");
								continue;
							}
							
							//주문 상품 아이템 담기
							OrderAppItemDTO itemDTO = new OrderAppItemDTO();
							itemDTO.setCnt(Integer.parseInt(cnt));
							itemDTO.setProductNum(productNum);
							itemDTO.setName(productDTO.getName());
							itemDTO.setAmount(productDTO.getPrice()*Integer.parseInt(cnt));
							itemList.add(itemDTO);
							productNumList.add(productNum);
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
	
	/**
	 * 숫자여부 확인
	 * @param cnt
	 * @return
	 */
	public boolean isInteger(String cnt) {
		try {
			Integer.parseInt(cnt);
		}catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
}
