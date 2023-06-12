package kr.co._29cm.homework.modules;

import java.io.BufferedReader;
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

/**
 * 
* @packageName   : kr.co._29cm.homework.modules
* @fileName      : OrderInsertTest.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.12
* @description   : 주문 결제 Test
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.12        ghgo      		 최초 생성
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class OrderInsertTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderAppService orderAppService;

	@Test
	void test() {
		
		BufferedReader 	bf = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			//주문 DTO
			OrderAppDTO appDTO = new OrderAppDTO();
			//주문 상품 list
			List<OrderAppItemDTO> itemList = new ArrayList<>();
			//주문 상품번호 list
			List<String> productNumList = new ArrayList<String>();
			//상품목록 조회 목록 개수 20개
			ProductDefaultDTO searchDTO = new ProductDefaultDTO();
			searchDTO.setSize(20);
			List<ProductDTO> list = productService.selectProductList(searchDTO).toList();
			
			//상품 3개 담아서 테스트
			for(int i = 0; i<3; i++) {
				
				String productNum = "";
				String cnt = "";
				
				System.out.print("상품번호 : ");
				productNum = bf.readLine();
				
				System.out.print("수량 : ");
				cnt = bf.readLine();
				
				String temp = productNum;
				ProductDTO productDTO = list.stream().filter(x->x.getProductNum().equals(temp)).findFirst().orElse(null);
				
				//주문 상품 담기
				OrderAppItemDTO itemDTO = new OrderAppItemDTO();
				itemDTO.setCnt(Integer.parseInt(cnt));
				itemDTO.setProductNum(productNum);
				itemDTO.setName(productDTO.getName());
				itemDTO.setAmount(productDTO.getPrice()*Integer.parseInt(cnt));
				itemList.add(itemDTO);
				productNumList.add(productNum);
			}
			
			//주문 정보에 주문상품 list setter
			appDTO.setItemList(itemList);
			orderAppService.insertOrderApp(appDTO,productNumList);	
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}

}


