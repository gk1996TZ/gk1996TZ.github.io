package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.PrivilegeGroup;

/**
* @Description: 权限组Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月24日 下午4:29:07
 */
@Repository
public interface PrivilegeGroupMapper extends BaseMapper<PrivilegeGroup>{

	/**
	* @Description: 根据权限组名称校验权限组是否存在
	* @param: 权限组名称
	* @author: 展昭
	* @date: 2018年4月25日 上午10:03:50
	 */
	public Long validateExistPrivilegeGroup(@Param("pgName")String privilegeGropuName);

	/**
	* @Description: 根据用户id查询权限组
	* @param: 用户id
	* @author: 展昭
	* @date: 2018年4月26日 下午6:04:10
	*/
	public List<PrivilegeGroup> selectByManagerId(@Param("managerId")String managerId);
}
