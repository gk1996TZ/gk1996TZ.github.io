package com.muck.domain;

import java.util.ArrayList;
import java.util.List;

import com.muck.annotation.Table;

/**
* @Description: 权限组管理
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月24日 下午3:23:57
 */
@Table(name = "t_privilege_group")
public class PrivilegeGroup extends BaseEntity{

	// 权限组名称
	private String privilegeGroupName;
	
	//权限组等级
	private String privilegeGroupDegree;
	// 权限描述
	private String memo;
	
	// 权限组下面有多个权限
	private List<Privilege> privileges = new ArrayList<Privilege>();

	
	public String getPrivilegeGroupDegree() {
		return privilegeGroupDegree;
	}

	public void setPrivilegeGroupDegree(String privilegeGroupDegree) {
		this.privilegeGroupDegree = privilegeGroupDegree;
	}

	public String getPrivilegeGroupName() {
		return privilegeGroupName;
	}

	public void setPrivilegeGroupName(String privilegeGroupName) {
		this.privilegeGroupName = privilegeGroupName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
}
