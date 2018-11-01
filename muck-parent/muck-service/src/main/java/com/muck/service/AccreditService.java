package com.muck.service;

import java.util.List;

import com.muck.domain.ManagerPrivilegeGroup;


/**
 * @Description: 用户权限组Service
 * @version: v1.0.0
 * @author: 朱俊亮
 * @Date: 2018年7月16日 下午4:39:57
 */
public interface AccreditService extends BaseService<ManagerPrivilegeGroup>{

	/**
	 * @Description: 给单个用户添加多个权限组
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月16日 下午5:07:31
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组id列表
	 */
	public void insertBatch(String managerId,List<String> privilegeGroupIds);
	/**
	 * @Description: 给单个用户删除多个权限组
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月16日 下午5:57:34
	 * @Param: managerId 传入一个用户id
	 * @Param: privilegeGroupIds 传入一个权限组id列表
	 */
	public void deleteBatch(String managerId,List<String> privilegeGroupIds);
	
	
	/**
	 * 根据权限组id查询是否被授权给了用户
	 * 
	 * 
	 * */
	public int queryByPrivilegeGroupId(String privilegeGroupId);
}
