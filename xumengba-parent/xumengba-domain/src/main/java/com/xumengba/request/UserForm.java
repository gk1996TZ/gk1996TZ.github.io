package com.xumengba.request;

import org.apache.commons.lang3.StringUtils;

import com.xumengba.response.StatusCode;

public class UserForm extends BaseForm {

	// 用户名
	private String userName;

	// 姓名
	private String userRealName;

	private String userPwd;

	private String userPhone;

	private String userMail;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	@Override
	public StatusCode validate() {
		StatusCode statusCode=null;
		if(StringUtils.isBlank(userName)){
			statusCode=StatusCode.USER_NAME_NULL;
		}
		else if(StringUtils.isBlank(userPwd)){
			statusCode=StatusCode.USER_CODE_IS_NULL;
		}
		return statusCode;
	}

}
