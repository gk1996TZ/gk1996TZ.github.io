package com.litang.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.litang.domain.Role;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
	
	//向权限角色中间表添加数据
	public Integer insertRoleAuthority(@Param("roleId")String roleId,@Param("authorityIds")String[] authorityIds);
	
	//根据角色id删除角色权限
	public void deleteRoleAuthorityByRoleId(@Param("roleId")String roleId);
	
	//根据角色id查询是否有用户使用该角色
	public int selectUserRoleByRoleId(@Param("roleId")String roleId);
	
	//根据id查询是否有这个角色(不封装权限)
	public Role queryByIdSimple(String id);
	
	//
	public Role getOrdinaryUserRole();
	
	

}
