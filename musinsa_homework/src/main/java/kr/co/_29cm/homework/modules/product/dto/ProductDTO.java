package kr.co._29cm.homework.modules.product.dto;

import kr.co._29cm.homework.modules.product.entity.Product;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.dto
* @fileName      : ProductDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
@NoArgsConstructor
public class ProductDTO {
	
	/**상품번호*/
	private String productNum;
	
	/**상품이름*/
	private String name;
	
	/**가격*/
	private int price;
	
	/**상품 재고량*/
	private int cnt;
	
	//DTO -> Entity 변환
	public Product entity() {
		return Product.builder().dto(this).build();
	}
	
	public ProductDTO(Product entity) {
		this.productNum = entity.getProductNum();
		this.name = entity.getName();
		this.price = entity.getPrice();
		this.cnt = entity.getCnt();
	}
	
	/**재고 수량 감소*/
	public void minusProductCnt(int cnt) {
		this.cnt -= cnt;
	}
	
	/**재고 수량 증가*/
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
