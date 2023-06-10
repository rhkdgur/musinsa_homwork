package kr.co._29cm.homework.modules.order.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.product.entity.Product;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.entity
* @fileName      : OrderAppItem.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 주문 상품 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo    		최초생성
 */
@Entity
@Table(name="order_app_item")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderAppItem{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("일련번호")
	private int idx;
	
	@Comment("금액")
	private int amount;
	
	@Comment("수량")
	private int cnt;
	
	@Comment("상품명")
	private String name;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_num")
	private OrderApp orderApp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_num")
	private Product product;
	
	@Builder
	public OrderAppItem(OrderAppItemDTO dto) {
		this.idx = dto.getIdx();
		this.amount = dto.getAmount();
		this.cnt = dto.getCnt();
		this.name = dto.getName();
		this.orderApp = dto.getOrderApp().entity();
		this.product = dto.getProduct().entity();
	}
	
	public OrderAppItem(OrderAppItemDTO dto, OrderApp orderApp,Product product) {
		this.idx = dto.getIdx();
		this.amount = dto.getAmount();
		this.cnt = dto.getCnt();
		this.name = dto.getName();
		this.orderApp = orderApp;
		this.product = product;
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

	public OrderApp getOrderApp() {
		return orderApp;
	}

	public Product getProduct() {
		return product;
	}

}
