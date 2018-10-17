package com.xumengba.exception;

import com.xumengba.response.StatusCode;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private StatusCode statusCode;
	
	public BizException(StatusCode statusCode) {
		super(statusCode.getMessage());
		this.statusCode = statusCode;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}
}