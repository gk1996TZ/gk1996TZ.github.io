package com.xumengba.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @Description: 校验字段唯一性Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月3日 下午5:18:49
 */
@Repository
public interface UniqueMapper {

	/**
	* @Description: 校验字段唯一性的值
	* @param:
	* 		 fields : 需要校验的字段
	* 		 tableName : 表名
	* @author: 展昭
	* @date: 2018年5月3日 下午5:19:57
	 */
	@Select("${sql}")
	public Long validateUnique(@Param("sql")String sql);
	
}
