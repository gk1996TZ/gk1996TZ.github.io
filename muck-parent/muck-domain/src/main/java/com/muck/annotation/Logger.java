package com.muck.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 日志注解
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月28日 下午3:38:16
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
	
	// 操作模块
	String operatorModel();
	
	// 操作说明
	String operatorDesc();
}
