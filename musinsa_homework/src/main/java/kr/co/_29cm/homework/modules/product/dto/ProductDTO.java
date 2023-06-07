package kr.co._29cm.homework.modules.product.dto;

import kr.co._29cm.homework.modules.product.entity.Product;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.dto
* @fileName      : ProductDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ��ǰ DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
@NoArgsConstructor
public class ProductDTO {
	
	/**��ǰ��ȣ*/
	private String productNum;
	
	/**��ǰ�̸�*/
	private String name;
	
	/**����*/
	private int price;
	
	/**��ǰ ���*/
	private int cnt;
	
	//DTO -> Entity ��ȯ
	public Product entity() {
		return Product.builder().dto(this).build();
	}
	
	public ProductDTO(Product entity) {
		this.productNum = entity.getProductNum();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.cnt = entity.getCnt();
	}
	
	/**��� ���� ����*/
	public void minusProductCnt(int cnt) {
		this.cnt -= cnt;
	}
	
	/**��� ���� ����*/
	public void plusProductCnt(int cnt) {
		this.cnt += cnt;
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

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
