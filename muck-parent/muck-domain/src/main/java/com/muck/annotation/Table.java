package com.muck.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 此注解是用来定义一个实体上面对应的表的名称
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 下午4:06:02
*/
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Table {

	// 表的名称
	String name();
}
