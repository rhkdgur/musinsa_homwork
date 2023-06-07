package kr.co._29cm.homework.exception;

/**
 * 
* @packageName   : kr.co._29cm.homework.exception
* @fileName      : SoldOutException.java
* @author        : Gwang hyeok Go
* @date          : 2023.06.07
* @description   : ��ǰ ��� üũ exception
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.06.07        ghgo       ���� ����
 */
public class SoldOutException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public SoldOutException(String message) {
		super(message);
	}
}
