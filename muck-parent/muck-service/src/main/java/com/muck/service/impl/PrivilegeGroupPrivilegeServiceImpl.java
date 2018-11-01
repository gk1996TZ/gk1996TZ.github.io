package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.PrivilegeGroupPrivilege;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.PrivilegeGroupPrivilegeMapper;
import com.muck.service.PrivilegeGroupPrivilegeService;

@Service
public class PrivilegeGroupPrivilegeServiceImpl extends BaseServiceImpl<PrivilegeGroupPrivilege> implements PrivilegeGroupPrivilegeService {

	
	@Autowired
	private PrivilegeGroupPrivilegeMapper privilegeGroupPrivilegeMapper;
	
	@Override
	public void addBatch(String privilegeGroupId, List<String> privilegeIds) {
		privilegeGroupPrivilegeMapper.insertBatch(privilegeGroupId, privilegeIds);
	}
	
	
	/**
	 * 根据权限组id清空权限组所拥有的权限
	 * */
	public void deletedByPrivielgeGroupId(String privilegeGroupId) {

		privilegeGroupPrivilegeMapper.deleteByPrivilegeGroupId(privilegeGroupId);
	}

	@Override
	protected BaseMapper<PrivilegeGroupPrivilege> getMapper() {
		return privilegeGroupPrivilegeMapper;
	}

	@Override
	protected String getFields() {
		return "id,privilege_id,privilege_group_id";
	}

	

}
