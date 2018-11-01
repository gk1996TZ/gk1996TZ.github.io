package com.muck.utils;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
* @Description: 日志工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月2日 下午2:57:20
 */
public final class LogUtils {

	private LogUtils(){
		
	}
	
	/**
	* @Description: 生成表的名称 格式是这样子：t_log_2018_9
	* 									 表名_年份_月份
	* @param: offset 偏移量
	* @author: 展昭
	* @date: 2018年5月2日 下午2:57:44
	*/
	public static String generateLogTableName(int offset){
		
		// 第一步、获取日期
		Calendar c = Calendar.getInstance();
		
		// 第二步、获取年份
		int year = c.get(Calendar.YEAR);
		
		// 第三步、获取月份
		int month = c.get(Calendar.MONTH) + 1 + offset;
		
		if(month > 12){
			year ++ ;
			month = month - 12 ;
		}
		if(month < 1){
			year -- ;
			month = month + 12 ;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00");
		
		// 生成的表格式为:  表名_年份_月份(eg : t_log_2018_05)
		return "t_log" + "_" + year + "_" + df.format(month) ;
	}
}
