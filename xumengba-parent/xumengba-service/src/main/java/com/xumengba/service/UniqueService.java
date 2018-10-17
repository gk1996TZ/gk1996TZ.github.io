package com.xumengba.service;

import com.xumengba.response.StatusCode;

/**
* @Description: 唯一性校验服务
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月3日 下午4:59:31
 */
public interface UniqueService {

	/**
	* @Description: 校验唯一性
	* @param:
	* 		    fields : 要校验的字段
	* 			values:字段的值
	* 			tableName : 要校验的表名
	* @author: 展昭
	* @date: 2018年5月3日 下午5:00:16
	 */
	public StatusCode validateUnique(String[] fields,Object[] values,String tableName);
	
}
