package com.xumengba.request;

import org.apache.commons.lang3.StringUtils;

import com.xumengba.response.StatusCode;

public class UserPwdForm extends BaseForm {

	
	private String userName;

	private String oldPwd;

	private String newPwd;
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getOldPwd() {
		return oldPwd;
	}


	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}


	public String getNewPwd() {
		return newPwd;
	}


	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}


	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;
		if(StringUtils.isBlank(userName)){
			statusCode=StatusCode.USER_NAME_NULL;
		}else if(StringUtils.isBlank(oldPwd)){
			statusCode=StatusCode.OLD_CODE_IS_NULL;
		}else if(StringUtils.isBlank(newPwd)){
			statusCode=StatusCode.USER_NEW_PWD_NULL;
		}
		return statusCode;
	}

}
