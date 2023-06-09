package kr.co._29cm.homework.modules.order.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kr.co._29cm.homework.modules.order.entity.OrderApp;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.dto
* @fileName      : OrderAppDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 주문 정보 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
@NoArgsConstructor
public class OrderAppDTO {

	/**주문번호*/
	private String orderNum = "";
	
	/**총 금액*/
	private int totalAmount = 0;

	/**배송비*/
	private int deliverAmount = 0;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**주문 상품 목록*/
	private List<OrderAppItemDTO> itemList = new ArrayList<OrderAppItemDTO>();

	public OrderApp entity() {
		return OrderApp.builder().dto(this).build();
	}
	
	public OrderAppDTO(OrderApp entity) {
		this.orderNum = entity.getOrderNum();
		this.totalAmount = entity.getTotalAmount();
		this.deliverAmount = entity.getDeliverAmount();
		if(entity.getItemList().size() > 0) {
			itemList = entity.getItemList().stream().map(x-> new OrderAppItemDTO(x)).collect(Collectors.toList());
		}
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getDeliverAmount() {
		return deliverAmount;
	}

	public void setDeliverAmount(int deliverAmount) {
		this.deliverAmount = deliverAmount;
	}

	public List<OrderAppItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderAppItemDTO> itemList) {
		this.itemList = itemList;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
}
