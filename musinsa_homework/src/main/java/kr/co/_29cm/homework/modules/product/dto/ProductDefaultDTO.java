package kr.co._29cm.homework.modules.product.dto;

import kr.co._29cm.homework.common.dto.BaseVO;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.dto
* @fileName      : ProductDefaultDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 정보 검색 DefaultDTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       		최초생성
 */
public class ProductDefaultDTO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	/**상품번호*/
	private String productNum = "";

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

}
