package com.muck.service;

import java.util.List;

import com.muck.domain.PrivilegeGroupPrivilege;

/**
 * 权限权限组service
 * */
public interface PrivilegeGroupPrivilegeService extends BaseService<PrivilegeGroupPrivilege> {
	
	/**
	 * 批量为权限组添加权限
	 * */
	
	public void addBatch(String privilegeGroupId,List<String>privilegeIds);
	
	/**
	 * 根据权限组id清空中间表
	 * */
	public void deletedByPrivielgeGroupId(String privilegeGroupId);

}
