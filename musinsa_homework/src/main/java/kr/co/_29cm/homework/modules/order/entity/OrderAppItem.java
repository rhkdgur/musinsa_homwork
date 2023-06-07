package kr.co._29cm.homework.modules.order.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.entity.Product;
import lombok.Builder;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.entity
* @fileName      : OrderAppItem.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ��ǰ ������ entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
@Entity
@Table(name="order_app_item")
public class OrderAppItem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("�Ϸù�ȣ")
	private int idx;
	
	@Comment("�ݾ�")
	private int amount;
	
	@Comment("����")
	private int cnt;
	
	@Comment("��ǰ��")
	private String name;
	
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
		OrderAppDTO appDTO = new OrderAppDTO();
		appDTO.setOrderNum(dto.getOrderNum());
		orderApp = new OrderApp(appDTO);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductNum(dto.getProductNum());
		product = new Product(productDTO);
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
