package com.muck.utils;

import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;

/**
 * @Description: 校验Excel单元格数据是否是时间的工具类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月11日 下午3:23:36
 */
public class XSSFDateUtil extends DateUtil {
	protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
		return DateUtil.absoluteDay(cal, use1904windowing);
	}
}
