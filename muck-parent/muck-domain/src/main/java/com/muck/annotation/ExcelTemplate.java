package com.muck.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 生成Excel表格表头信息
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月26日 上午11:14:58
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelTemplate {

	// 表头的字段名称
	String name();
}
