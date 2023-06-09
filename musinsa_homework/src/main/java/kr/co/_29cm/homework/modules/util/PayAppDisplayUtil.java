package kr.co._29cm.homework.modules.util;

import java.util.List;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.util
* @fileName      : PayAppUtil.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ���� display util
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
public class PayAppDisplayUtil {
	
	/**
	 * ��ǰ ����Ʈ display
	 * @param list
	 */
	public static void productListDisplay(List<ProductDTO> list) {
		System.out.println("��ǰ��ȣ		��ǰ��								�ǸŰ���			����");
		for(ProductDTO tmp : list) {
			System.out.println(tmp.getProductNum()+"		"+tmp.getName()+"					"+tmp.getPrice()+"		"+tmp.getCnt());
		}
	}

	/***
	 * ���� ó�� �ý���
	 * @param productList
	 * @param itemList
	 */
	public static void productPayDisPlay(OrderApp app, List<OrderAppItemDTO> itemList) {

		int totalAmount = 0;
		int deliverAmount = 0;
		
		System.out.println("�ֹ�����:");
		System.out.println("-----------------------------------");
		for(OrderAppItemDTO tmp : itemList) {
			System.out.println(tmp.getName()+" - "+tmp.getCnt()+"��");
			totalAmount += tmp.getAmount();
		}			
		System.out.println("-----------------------------------");
		if(totalAmount < 50000) {
			deliverAmount = 2500;
		}
		System.out.println("�ֹ��ݾ�: "+ totalAmount);
		System.out.println("-----------------------------------");
		System.out.println("���ұݾ�: "+(totalAmount+deliverAmount));
		System.out.println("-----------------------------------");
		
		app.addDeliverAmount(deliverAmount);
		app.addTotalAmount((totalAmount+deliverAmount));
		
	}
	
}
