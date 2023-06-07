package kr.co._29cm.homework.modules.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.entity
* @fileName      : Product.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 엔티티
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
@Entity
@Table(name="product")
@NoArgsConstructor
public class Product {

	@Id
	@Comment("상품번호")
	private String productNum;
	
	@Comment("상품명")
	@Column(length = 200)
	private String name;
	
	@Comment("상품가격")
	private int price;
	
	@Comment("상품재고량")
	private int cnt;
	
	@Builder
	public Product(ProductDTO dto) {
		this.productNum = dto.getProductNum();
		this.name = dto.getName();
		this.price = dto.getPrice();
		this.cnt = dto.getCnt();
	}

	public String getProductNum() {
		return productNum;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getCnt() {
		return cnt;
	}
	
}
