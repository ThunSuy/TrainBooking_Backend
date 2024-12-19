package com.doan.trainbooker.exception;

public class AppException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	private ErrorCode errorCode;

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}