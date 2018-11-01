package com.muck.admin.controller.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.muck.exception.base.ParameterIllegalException;
import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
* @Description: 统一参数拦截验证
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月13日 下午4:00:57
 */
@Aspect
@Component("AdminMethodParameterAspect")
public class MethodParameterAspect {
	
	private final String VALIDATE_METHOD_NAME = "validate";
	
	@Pointcut("execution(* com.muck.admin.controller.*Controller.*(..))")
	private void setCurrentPotincut(){}

	@Around("setCurrentPotincut()")
	public Object verifyMethodParameter(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		if(null == args) {
			return point.proceed();
		}
		for (Object arg : args) {
			if(null == arg || !(arg instanceof BaseForm)) {
				continue;
			}
			Method verifyMethod = getVerifyMethod(arg.getClass());
			Object resultVal = verifyMethod.invoke(arg, new Object[] {});
			if(null != resultVal && (resultVal instanceof StatusCode)) {
				throw new ParameterIllegalException((StatusCode) resultVal);
			}
		}
		return point.proceed();
	}
	
	private Method getVerifyMethod(Class<?> clazz) throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(VALIDATE_METHOD_NAME, new Class[] {});
	}
}
