package kr.co._29cm.homework.exception;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.service.OrderAppService;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.service.ProductService;

/**
 * 
* @packageName   : kr.co._29cm.homework.exception
* @fileName      : SoldOutExcetpionTest.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.12
* @description   : 중복체크 예외처리 테스트
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.12        ghgo       최초 생성
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class OverlapExcetpionTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderAppService orderAppService;
	
	@Test
	void SoldOutExceptionTest() {
		try {
			//주문 DTO
			OrderAppDTO appDTO = new OrderAppDTO();
			//주문 상품 list
			List<OrderAppItemDTO> itemList = new ArrayList<>();
			//주문 상품번호 list
			List<String> productNumList = new ArrayList<String>();
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductNum("768848");
			
			productDTO = productService.selectProduct(productDTO);
			
			//주문 상품 담기
			for(int i = 0; i<2; i++) {
				OrderAppItemDTO itemDTO = new OrderAppItemDTO();
				itemDTO.setCnt(5);
				itemDTO.setProductNum(productDTO.getProductNum());
				itemDTO.setName(productDTO.getName());
				itemDTO.setAmount(50000);
				itemList.add(itemDTO);	
				productNumList.add(productDTO.getProductNum());
			}
			
			//주문 정보에 주문상품 list setter
			appDTO.setItemList(itemList);
			orderAppService.insertOrderApp(appDTO,productNumList);	
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	
}


