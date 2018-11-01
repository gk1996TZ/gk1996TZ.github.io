package com.muck.exception.base;

import com.muck.response.StatusCode;

public class ParameterIllegalException extends BizException {

	private static final long serialVersionUID = 1L;
	
	public ParameterIllegalException(StatusCode statusCode) {
		super(statusCode);
	}
	
	public ParameterIllegalException() {
		this(StatusCode.PARAMETER_ILLEGAL);
	}
}
