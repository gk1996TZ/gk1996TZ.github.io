package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.ManagerPrivilegeGroup;
import com.muck.mapper.AccreditMapper;
import com.muck.mapper.BaseMapper;
import com.muck.service.AccreditService;


/**
 * @Description: 授权	Service实现
 * @Version: v1.0.0
 * @Author: 朱俊亮
 * @Date: 2018年7月16日 下午4:48:47
 */
@Service
public class AccreditServiceImpl extends BaseServiceImpl<ManagerPrivilegeGroup> implements AccreditService{

	@Autowired
	private AccreditMapper accreditMapper;
	@Override
	public void insertBatch(String managerId, List<String> privilegeGroupIds) {
		accreditMapper.insertBatch(managerId, privilegeGroupIds);
	}
	@Override
	public void deleteBatch(String managerId, List<String> privilegeGroupIds) {
		accreditMapper.deleteBatch(managerId, privilegeGroupIds);
	}
	
	/**
	 * 根据权限组id查询是否有用户使用这个权限组
	 * */
	@Override
	public int queryByPrivilegeGroupId(String privilegeGroupId) {
		return accreditMapper.selectByPrivilegeGroupId(privilegeGroupId);
	}
	// ========================================================
	@Override
	protected BaseMapper<ManagerPrivilegeGroup> getMapper() {
		return accreditMapper;
	}
	@Override
	protected String getFields() {
		return "id,manager_id,privilege_group_id";
	}
	
}
