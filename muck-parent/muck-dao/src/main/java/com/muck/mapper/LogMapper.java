package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Log;

/**
* @Description: 日志Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月28日 下午3:27:55
 */
@Repository
public interface LogMapper extends BaseMapper<Log>{

	/**
	* @Description: 执行sql语句
	* @author: 展昭
	* @date: 2018年5月2日 下午3:40:57
	 */
	public void executeSQL(@Param("sql")String sql);

	/**
	* @Description: 查询table名称
	* @author: 展昭
	* @date: 2018年5月3日 上午10:38:12
	*/
	public List<String> selectTableName(@Param("sql")String sql);

}
