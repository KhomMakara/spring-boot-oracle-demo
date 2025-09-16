package com.example.demo.exception;

public class MRegisterErrException extends MException {
	
	public MRegisterErrException() {
		super();
	}
	
	public MRegisterErrException(String message) {
		super(message);
	}
	
	public MRegisterErrException(String code, String message) {
		super(code, message);
	}
	
	public MRegisterErrException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MRegisterErrException(Throwable cause) {
		super(cause);
	}
	
	public MRegisterErrException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}
}
