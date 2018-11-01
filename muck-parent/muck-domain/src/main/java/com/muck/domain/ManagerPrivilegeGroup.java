package com.muck.domain;

import com.muck.annotation.Table;

/**
 * @Description: 用户权限组实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @Date: 2018年7月16日 下午4:39:08
 */
@Table(name="t_manager_privilegegroup")
public class ManagerPrivilegeGroup extends BaseEntity{

	/**用户id*/
	private String managerId;
	/**权限组id*/
	private String privilegeGroupId;
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getPrivilegeGroupId() {
		return privilegeGroupId;
	}
	public void setPrivilegeGroupId(String privilegeGroupId) {
		this.privilegeGroupId = privilegeGroupId;
	}
}
