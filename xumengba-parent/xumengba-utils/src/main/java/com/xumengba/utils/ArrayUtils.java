package com.xumengba.utils;

/**
* @Description: 数组工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月25日 下午4:29:39
 */
public final class ArrayUtils {

	private ArrayUtils(){}
	
	/**
	* @Description: 将一个数组转换成一个字符串
	* @param:描述1描述
	* @author: 展昭
	* @date: 2018年4月25日 下午4:30:25
	 */
	public static String array2str(Object[] arrs){
		StringBuilder sb = new StringBuilder("");
		if(arrs != null && arrs.length > 0){
			for(Object arr : arrs){
				if(arr != null){
					sb.append(arr).append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	* @Description: 校验一个字符串数组为空
	* @param:描述1描述
	* @author: 展昭
	* @date: 2018年5月3日 下午5:55:12
	 */
	public static boolean isEmpty(String[] values){
		if(values == null || values.length <= 0){
			return true;
		}
		return false;
	}
	
	/**
	* @Description: 校验一个字符串数组不为空
	* @param:描述1描述
	* @author: 展昭
	* @date: 2018年5月3日 下午5:59:43
	 */
	public static boolean isNotEmpty(String[] values){
		return !isEmpty(values);
	}
	
	
}
