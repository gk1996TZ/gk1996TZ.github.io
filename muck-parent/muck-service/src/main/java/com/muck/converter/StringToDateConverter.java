package com.muck.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @Description: 字符串转日期的全局转换器
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 下午3:11:17
 */
public class StringToDateConverter implements Converter<String, Date> {

	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

	private static final String shortDateFormat = "yyyy-MM-dd";

	// 实现转换
	public Date convert(String value) {

		if (StringUtils.isBlank(value)) {
			return null;
		}

		value = value.trim();

		try {
			if (value.contains("-")) {
				SimpleDateFormat formatter;
				if (value.contains(":")) {
					formatter = new SimpleDateFormat(dateFormat);
				} else {
					formatter = new SimpleDateFormat(shortDateFormat);
				}
				Date dtDate = formatter.parse(value);
				return dtDate;
			} else if (value.matches("^\\d+$")) {
				Long lDate = new Long(value);
				return new Date(lDate);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("parser %s to Date fail", value));
		}
		throw new RuntimeException(String.format("parser %s to Date fail", value));
	}
}
