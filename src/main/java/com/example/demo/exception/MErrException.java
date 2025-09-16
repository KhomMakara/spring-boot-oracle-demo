package com.example.demo.exception;

public class MErrException extends MException{
	
	public MErrException() {
		super();
	}
	
	public MErrException(String message) {
		super(message);
	}
	
	public MErrException(String code, String message) {
		super(code, message);
	}
	
	public MErrException(Throwable cause) {
		super(cause);
	}
	
	public MErrException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MErrException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}
}
