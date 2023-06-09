package kr.co._29cm.homework.exception;

public class NotExistProductException  extends Exception{

	private static final long serialVersionUID = 1L;

	public NotExistProductException(String message) {
		super(message);
	}
	
}
