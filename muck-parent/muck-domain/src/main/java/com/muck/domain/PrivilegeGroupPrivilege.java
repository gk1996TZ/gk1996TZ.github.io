package com.muck.domain;

import com.muck.annotation.Table;

/**
 * 权限权限组实体类
 * 
 * */
@Table(name="t_privilegegroup_privilege")
public class PrivilegeGroupPrivilege extends BaseEntity {

	
	/**权限组id*/
	private String privilegeGroupId;
	
	/**权限id*/
	private String privilegeId;

	public String getPrivilegeGroupId() {
		return privilegeGroupId;
	}

	public void setPrivilegeGroupId(String privilegeGroupId) {
		this.privilegeGroupId = privilegeGroupId;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	
}
