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
* @description   : ��ǰ ��ƼƼ
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
@Entity
@Table(name="product")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product{

	@Id
	@Comment("��ǰ��ȣ")
	private String productNum;
	
	@Comment("��ǰ��")
	@Column(length = 200)
	private String name;
	
	@Comment("��ǰ����")
	private int price;
	
	@Comment("��ǰ���")
	private int cnt;
	
	@Comment("�������")
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
	
	/**��� ���� ����*/
	public void minusProductCnt(int cnt) {
		this.cnt -= cnt;
	}
	
	/**��� ���� ����*/
	public void plusProductCnt(int cnt) {
		this.cnt += cnt;
	}
}
