package com.xumengba.utils;

import java.util.Collections;
import java.util.List;

/**
* @Description: 集合工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月17日 下午4:00:33
 */
public final class CollectionUtils {

	private CollectionUtils(){
		
	}
	
	/**
	* @Description: 判断传入的List集合是否为空,如果为空则返回一个空集合而不是null
	* @author: 展昭
	* @date: 2018年4月17日 下午4:03:09
	*/
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(List<T> list){
		if(list == null || list.isEmpty()){
			return Collections.EMPTY_LIST;
		}
		return list;
	}
}
