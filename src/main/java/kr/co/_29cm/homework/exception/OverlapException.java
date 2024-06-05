package kr.co._29cm.homework.exception;

/**
 * 
* @packageName   : kr.co._29cm.homework.exception
* @fileName      : NotExistProductException.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.10
* @description   : 상품 중복 유무 exception
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.10        ghgo       최초 생성
 */
public class OverlapException  extends Exception{

	private static final long serialVersionUID = 1L;

	public OverlapException(String message) {
		super(message);
	}
	
}
