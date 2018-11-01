package com.litang.request;

import org.apache.commons.lang3.StringUtils;

import com.litang.response.StatusCode;

/**
 * 更新用户操作请求实体类
 */
public class UserInfoForm extends BaseForm {

	/** 用户id */
	private String userId;
	/** 用户头像 */
	private String userHead;
	/** 用户姓名 */
	private String userRealName;
	/** 用户职位 */
	private String userJob;
	/** 用户描述 */
	private String userDescribe;
	/** 用户联系方式 */
	private String userPhone;
	
	/**为用户分配的角色id*/
	private String  roleId;
	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserJob() {
		return userJob;
	}

	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

	public String getUserDescribe() {
		return userDescribe;
	}

	public void setUserDescribe(String userDescribe) {
		this.userDescribe = userDescribe;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;

		if (StringUtils.isBlank(userId)) {
			statusCode = StatusCode.USER_ID_NULL;
		}
		return statusCode;
	}
}
