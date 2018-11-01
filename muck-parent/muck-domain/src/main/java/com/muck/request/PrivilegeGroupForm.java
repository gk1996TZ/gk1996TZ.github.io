package com.muck.request;


import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/**
* @Description: 权限组管理
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月24日 下午3:23:57
 */
public class PrivilegeGroupForm extends BaseForm{

	
	
	// 权限组id
	private String privilegeGroupId;
	
	//权限组等级
	private String privilegeGrouDegree;
	
	// 权限组名称
	private String privilegeGroupName;
	
	// 权限描述
	private String memo;

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
	
	public String getPrivilegeGroupId() {
		return privilegeGroupId;
	}

	public void setPrivilegeGroupId(String privilegeGroupId) {
		this.privilegeGroupId = privilegeGroupId;
	}
	

	public String getPrivilegeGrouDegree() {
		return privilegeGrouDegree;
	}

	public void setPrivilegeGrouDegree(String privilegeGrouDegree) {
		this.privilegeGrouDegree = privilegeGrouDegree;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode = null;
		if(StringUtils.isBlank(this.privilegeGroupName)){
			statusCode = StatusCode.PRIVILEGE_GROUP_NAME_BLANK;
		}
		
		return statusCode;
	}
}
