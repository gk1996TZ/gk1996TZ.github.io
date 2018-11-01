package com.muck.service;

import java.util.List;

import com.muck.domain.Privilege;

/**
* @Description: 权限service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月27日 下午2:24:53
 */
public interface PrivilegeService extends BaseService<Privilege>{

	/***
	* @Description: 查询顶级权限
	* @author: 展昭
	* @date: 2018年4月27日 下午4:42:25
	*/
	public List<Privilege> queryTopPrivileges();

	/****
	* @Description: 根据父权限id查询下面所有的子权限
	* @author: 展昭
	* @date: 2018年4月27日 下午5:28:43
	*/
	public List<Privilege> querySubPrivilegesByParentId(String parentId);
	
	

	/**
	 * 递归查新权限
	 * */
	public List<Privilege> queryPrivilegeByDeep();
	
}
