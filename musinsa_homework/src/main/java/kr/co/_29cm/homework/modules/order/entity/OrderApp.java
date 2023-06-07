package kr.co._29cm.homework.modules.order.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_app")
@NoArgsConstructor
public class OrderApp {

	@Id
	@Comment("주문번호")
	@Column(length = 20)
	private String orderNum;
	
	@Comment("전체금액")
	private int totalAmount;
	
	@Comment("배송비")
	private int deliverAmount;
	
	@OneToMany(mappedBy = "orderApp",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderAppItem> itemList = new ArrayList<OrderAppItem>();
	
	@Builder
	public OrderApp(OrderAppDTO dto) {
		this.orderNum = dto.getOrderNum();
		this.totalAmount = dto.getTotalAmount();
		this.deliverAmount = dto.getDeliverAmount();
	}

	public String getOrderNum() {
		return orderNum;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public int getDeliverAmount() {
		return deliverAmount;
	}

	public List<OrderAppItem> getItemList() {
		return itemList;
	}
	
}
