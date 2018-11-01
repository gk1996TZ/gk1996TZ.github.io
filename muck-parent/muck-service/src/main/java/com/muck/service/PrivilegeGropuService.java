package com.muck.service;

import com.muck.domain.PrivilegeGroup;

/**
* @Description: 权限组service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月24日 下午4:31:08
 */
public interface PrivilegeGropuService extends BaseService<PrivilegeGroup>{

	/**
	 * 	添加权限
	*/
	public void save(PrivilegeGroup privilegeGroup);
	
	/**
	 * 	修改权限
	*/
	public void editById(PrivilegeGroup privilegeGroup);
}
