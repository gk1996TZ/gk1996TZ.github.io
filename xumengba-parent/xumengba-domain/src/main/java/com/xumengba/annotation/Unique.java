package com.xumengba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 唯一性注解
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月3日 下午4:52:22
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Unique {

	// 表示唯一性字段
	String[] uniqueFields();

	// 表示哪个表
	String tableName();
}
