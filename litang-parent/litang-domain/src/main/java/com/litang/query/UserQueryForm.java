package com.litang.query;

public class UserQueryForm extends BaseQueryForm {
	
	//用户id
	private String userId;
	
	//用户名
	private String userName;
	
	//密码
	private String userPwd;
	
	//用户电话
	private String userPhone;

	//用户token
	private String userTokenCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserTokenCode() {
		return userTokenCode;
	}

	public void setUserTokenCode(String userTokenCode) {
		this.userTokenCode = userTokenCode;
	}
}
