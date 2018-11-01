package com.litang.request;

import org.apache.commons.lang3.StringUtils;

import com.litang.response.StatusCode;


/**
 * 角色请求实体类
 */
public class RoleForm extends BaseForm{

	/**角色id*/
	private String roleId;
	/**角色名称*/
	private String roleName;
	/**权限id列表*/
	private String [] authorityIds;
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String[] getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(String[] authorityIds) {
		this.authorityIds = authorityIds;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode=null;
		if(StringUtils.isBlank(roleName)){
			statusCode=StatusCode.CODE_IS_NULL;
		}else if(authorityIds==null){
			statusCode=StatusCode.AUTHORITY_ROLE_NULL;
		}
		return statusCode;
	}

	
}
