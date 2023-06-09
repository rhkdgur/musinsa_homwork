package kr.co._29cm.homework.modules.product.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Product{

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
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
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
	
	/**재고 수량 감소*/
	public void minusProductCnt(int cnt) {
		this.cnt -= cnt;
	}
	
	/**재고 수량 증가*/
	public void plusProductCnt(int cnt) {
		this.cnt += cnt;
	}
}
