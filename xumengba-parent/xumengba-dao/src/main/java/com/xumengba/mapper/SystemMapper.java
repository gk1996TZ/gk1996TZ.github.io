package com.xumengba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.xumengba.domain.System;
import com.xumengba.mapper.provider.UpdateMapperProvider;
@Mapper
public interface SystemMapper extends BaseMapper<System> {
	
	
	
	@Select("select copyright,created_time,updated_time from t_system where deleted=1")
	@Results(id="systemResultMap",value={
			@Result(property="copyright",column="copyright",jdbcType=JdbcType.VARCHAR),
			@Result(property="createdTime",column="created_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="updatedTime",column="updated_time",jdbcType=JdbcType.TIMESTAMP),
			@Result(property="deleted",column="deleted",jdbcType=JdbcType.TINYINT)
	})
	public List<System> getSystem();
	
	
	@UpdateProvider(type=UpdateMapperProvider.class,method="update")
	public void updateSystem(System system);

}
