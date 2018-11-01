package com.muck.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
* @Description: 日期工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月2日 下午12:10:49
 */
public final class DateUtils {

	private DateUtils(){
		
	}
	
	/**
	* @Description: 根据指定的格式格式化一个日期
	* @param:描述1描述
	* @author: 展昭
	* @date: 2018年5月2日 下午12:11:45
	 */
	public static String formatDate(Date date , String pattern){
		
		if(date == null){
			return null;
		}
		
		if(StringUtils.isBlank(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		return sdf.format(date);
	}
}
