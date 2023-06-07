package kr.co._29cm.homework.modules.order.dto;

import kr.co._29cm.homework.modules.order.entity.OrderAppItem;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderAppItemDTO {

	/**일련번호*/
	private int idx = 0;

	/**주문번호*/
	private String orderNum = "";
	
	/**상품번호*/
	private String productNum = "";
	
	/**금액*/
	private int amount = 0;
	
	/**재고량*/
	private int cnt = 0;
	
	/**상품명*/
	private String name = "";
	
	public OrderAppItem entity() {
		return OrderAppItem.builder().dto(this).build();
	}
	
	public OrderAppItemDTO(OrderAppItem entity) {
		this.idx = entity.getIdx();
		this.productNum = entity.getProduct().getProductNum();
		this.orderNum = entity.getOrderApp().getOrderNum();
		this.amount = entity.getAmount();
		this.cnt = entity.getCnt();
		this.name = entity.getName();
	}

	public int getIdx() {
		return idx;
	}

	public int getAmount() {
		return amount;
	}

	public int getCnt() {
		return cnt;
	}

	public String getName() {
		return name;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
