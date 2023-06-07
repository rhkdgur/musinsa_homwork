package kr.co._29cm.homework.modules.util;

import java.util.List;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.util
* @fileName      : PayAppUtil.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 결제 display util
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
public class PayAppDisplayUtil {
	
	/**
	 * 상품 리스트 display
	 * @param list
	 */
	public static void productListDisplay(List<ProductDTO> list) {
		System.out.println("상품번호		상품명								판매가격			재고수");
		for(ProductDTO tmp : list) {
			System.out.println(tmp.getProductNum()+"		"+tmp.getName()+"					"+tmp.getPrice()+"		"+tmp.getCnt());
		}
	}

	/***
	 * 결제 처리 시스템
	 * @param productList
	 * @param itemList
	 */
	public static OrderAppDTO productPayDisPlay(OrderAppDTO appDTO, List<ProductDTO> productList, List<OrderAppItemDTO> itemList) {

		int totalAmount = 0;
		int deliverAmount = 0;
		
		System.out.println("주문내역:");
		System.out.println("-----------------------------------");
		for(OrderAppItemDTO tmp : itemList) {
			System.out.println(tmp.getName()+" - "+tmp.getCnt()+"개");
			totalAmount += tmp.getAmount();
		}			
		System.out.println("-----------------------------------");
		if(totalAmount < 50000) {
			deliverAmount = 2500;
		}
		System.out.println("주문금액: "+ totalAmount);
		System.out.println("-----------------------------------");
		System.out.println("지불금액: "+(totalAmount+deliverAmount));
		System.out.println("-----------------------------------");
		
		appDTO.setDeliverAmount(deliverAmount);
		appDTO.setTotalAmount((totalAmount+deliverAmount));
		appDTO.setItemList(itemList);
		appDTO.setCheckProductList(productList);
		
		return appDTO;
		
	}
	
}
