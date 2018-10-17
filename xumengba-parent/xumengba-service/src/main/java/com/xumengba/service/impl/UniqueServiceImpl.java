package com.xumengba.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.mapper.UniqueMapper;
import com.xumengba.response.StatusCode;
import com.xumengba.service.UniqueService;
import com.xumengba.utils.ArrayUtils;

/**
* @Description: 唯一性校验服务实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月3日 下午5:01:30
 */
@Service
public class UniqueServiceImpl implements UniqueService {
	
	@Autowired
	private UniqueMapper uniqueMapper;
	
	/**
	 * 	校验字段值的唯一性
	 * 		返回值：
	 * 			true : 表示要校验的字段的值在表中存在
	 * 			false : 表示要校验的字段的值在表中不存在
	*/
	public StatusCode validateUnique(String[] fields,Object[] values, String tableName) {
		
		if(ArrayUtils.isNotEmpty(fields) && StringUtils.isNotBlank(tableName)){
			StringBuilder sb = new StringBuilder("select count(id) from ");
			sb.append(tableName).append(" ").append("where").append(" ");
			for(int i = 0 ;i < fields.length ; i++){
				String field = fields[i];	// 字段名称
				Object value = values[i];
				boolean flag = (value.getClass() == Long.class || value.getClass() == Integer.class);
				// 表示的是基础数据类型
				if(flag){
					if(i == (fields.length - 1)){
						sb.append(field).append("=").append(value).append(" ");
					}
					else{
						sb.append(field).append("=").append(value).append(" ").append("and").append(" ");
					}
				}else{
					if(i == (fields.length - 1)){
						sb.append(field).append("=").append("'").append(value).append("'").append(" ");
					}
					else{
						sb.append(field).append("=").append("'").append(value).append("'").append(" ").append("and").append(" ");
					}
				}
			}
			Long count = uniqueMapper.validateUnique(sb.toString());
			
			return (count == null || count == 0L) ? null : StatusCode.setMemo(tableName);
		}
		return StatusCode.UNKNOWN;
	}
}
