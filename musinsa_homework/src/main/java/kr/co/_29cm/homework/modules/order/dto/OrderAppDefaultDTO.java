package kr.co._29cm.homework.modules.order.dto;

import kr.co._29cm.homework.common.dto.BaseVO;

/**
 * 
* @packageName   : kr.co._29cm.homework.modules.order.dto
* @fileName      : OrderAppDefaultDTO.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : �ֹ� DefaultDTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
public class OrderAppDefaultDTO extends BaseVO{

	private static final long serialVersionUID = 1L;

	/**�ֹ���ȣ*/
	private String orderNum = "";
	
	/**��ǰ��ȣ*/
	private String productNum = "";

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	
	
	
}
