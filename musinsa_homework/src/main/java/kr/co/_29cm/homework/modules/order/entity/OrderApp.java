package kr.co._29cm.homework.modules.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.entity
* @fileName      : OrderApp.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.09
* @description   : �ֹ� Entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.09        ghgo       ���� ����
 */
@Entity
@Table(name="order_app")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderApp implements Persistable<String>{

	@Id
	@Comment("�ֹ���ȣ")
	@Column(length = 20)
	private String orderNum;
	
	@Comment("��ü�ݾ�")
	private int totalAmount;
	
	@Comment("��ۺ�")
	private int deliverAmount;
	
	@Comment("�������")
	@CreatedDate
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "orderApp", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
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
	
	public void addItemList(OrderAppItem orderAppItem) {
		this.itemList.add(orderAppItem);
	}
	
	public void addTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void addDeliverAmount(int deliverAmount) {
		this.deliverAmount = deliverAmount;
	}

	@Override
	public String getId() {
		return this.orderNum;
	}

	@Override
	public boolean isNew() {
		return this.createDate == null;
	}
	
}
