package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/**
 * 
* @Description: 用户登录请求Form
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月26日 下午3:11:17
 */
public class ManagerLoginForm extends BaseForm {

	// 用户登录密码
	private String managerPassword;

	// 用户登录账号
	private String managerPhone;

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode = null;
		if(StringUtils.isBlank(managerPhone)){
			statusCode = StatusCode.MANAGER_NAME_BLANK;
		}else if(StringUtils.isBlank(managerPassword)){
			statusCode = StatusCode.MANAGER_PASSWORD_BLANK;
		}
		return statusCode;
	}
}
