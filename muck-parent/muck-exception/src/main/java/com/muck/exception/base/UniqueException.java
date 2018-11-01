package com.muck.exception.base;

import com.muck.response.StatusCode;

public class UniqueException extends BizException {

	public UniqueException(StatusCode statusCode) {
		super(statusCode);
	}

	private static final long serialVersionUID = 1L;
}
