package com.litang.service;

import com.litang.domain.Role;


/**
 * 角色Service
 */
public interface RoleService extends BaseService<Role>{
	
	/**
	 * 添加角色并携带该角色所拥有的权限
	 * @param role 传入一个角色
	 * @param authorityIds 传入一个权限id列表
	 * @return 返回是否添加成功
	 */
	public boolean insertRoleWithAuthority(Role role,String [] authorityIds);
	
	/**
	 * 修改角色
	 * 并维护他所对应的权限
	 * */
	public void updateRole(Role role,String[] authorityIds);
	
	//根据权限id查询该用户是否被用户使用
	public int queryUserRoleByRoleId(String roleId);
	
	//根据角色id简单查询
	public Role queryRoleBySimple(String roleId);
	
	//获取普通用户角色
	public Role getOrdinaryUserRole();
}
