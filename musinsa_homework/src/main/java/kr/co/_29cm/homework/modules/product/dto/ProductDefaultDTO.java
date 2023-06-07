package kr.co._29cm.homework.modules.product.dto;

import kr.co._29cm.homework.common.dto.BaseVO;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.product.dto
* @fileName      : ProductDefaultDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ��ǰ �˻� DefaultDTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
public class ProductDefaultDTO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	/**��ǰ��ȣ*/
	private String productNum = "";

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

}
