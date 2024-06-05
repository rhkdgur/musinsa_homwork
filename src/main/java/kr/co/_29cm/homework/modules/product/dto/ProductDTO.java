package kr.co._29cm.homework.modules.product.dto;

import kr.co._29cm.homework.modules.product.entity.Product;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.dto
* @fileName      : ProductDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 정보 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo     		최초생성
 */
@NoArgsConstructor
public class ProductDTO {
	
	/**상품번호*/
	private String productNum;
	
	/**상품명*/
	private String name;
	
	/**상품 금액*/
	private int price;
	
	/**상품 수량*/
	private int cnt;
	
	//DTO -> Entity
	public Product entity() {
		return Product.builder().dto(this).build();
	}
	
	public ProductDTO(Product entity) {
		this.productNum = entity.getProductNum();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.cnt = entity.getCnt();
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
