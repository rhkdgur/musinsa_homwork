package kr.co._29cm.homework.exception;

/**
 * 
* @packageName   : kr.co._29cm.homework.exception
* @fileName      : SoldOutException.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : 상품 재고량 체크 exception
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       최초 생성
 */
public class SoldOutException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public SoldOutException(String message) {
		super(message);
	}
}
