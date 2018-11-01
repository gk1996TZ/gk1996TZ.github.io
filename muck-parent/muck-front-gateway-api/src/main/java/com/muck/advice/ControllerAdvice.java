package com.muck.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.muck.exception.base.BizException;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;

/**
* @Description: 异常拦截器控制
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月13日 下午2:02:10
 */
@RestController("FrontControllerAdvice")
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseResult exception(Exception e) {
		e.printStackTrace();
		BizException bizExp = getBizException(e);
		ResponseResult responseObject = new ResponseResult();
		if(bizExp != null){
			responseObject.setCode(bizExp.getStatusCode().getCode());
			responseObject.setMsg(bizExp.getStatusCode().getMessage());
		}else{
			responseObject.setCode(StatusCode.UNKNOWN.getCode());
			responseObject.setMsg(StatusCode.UNKNOWN.getMessage());
		}
		return responseObject;
	}
	
	private BizException getBizException(Throwable e1) {
		Throwable e2 = e1;
		do {
			if (e2 instanceof BizException)
				return (BizException) e2;
			e1 = e2;
			e2 = e1.getCause();
		} while (e2 != null && e2 != e1);
		return null;
	}
}
