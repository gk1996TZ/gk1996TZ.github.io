package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.PrivilegeGroupPrivilege;

@Repository
public interface PrivilegeGroupPrivilegeMapper extends BaseMapper<PrivilegeGroupPrivilege> {
	
	
	/**
	 *给权限权限组中间表添加数据(为权限组添加权限)
	 */
	public void insertBatch(@Param("privilegeGroupId")String privilegeGroupId,@Param("privilegeIds")List<String> privilegeIds);
	
	/**
	 * 根据权限组id清空权限组下的权限
	 * */
	public void deleteByPrivilegeGroupId(@Param("privilegeGroupId")String privilegeGroupId);

}
