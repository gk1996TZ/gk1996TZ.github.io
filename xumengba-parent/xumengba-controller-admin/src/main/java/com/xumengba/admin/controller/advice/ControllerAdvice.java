package com.xumengba.admin.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.exception.BizException;
import com.xumengba.response.ResponseResult;
import com.xumengba.response.StatusCode;

/**
* @Description: 异常拦截器控制
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月13日 下午2:02:10
 */
@RestController("AdminControllerAdvice")
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseResult exception(Exception e) {
		BizException bizExp = getBizException(e);
		ResponseResult responseObject = new ResponseResult();
		if(bizExp != null){
			logger.error("业务异常",e);
			responseObject.setCode(bizExp.getStatusCode().getCode());
			responseObject.setMsg(bizExp.getStatusCode().getMessage());
		}else{
			logger.error("未知异常",e);
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
