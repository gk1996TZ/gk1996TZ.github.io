package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/***
 * @Description: 权限Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月27日 下午2:29:51
 */
public class PrivilegeForm extends BaseForm {

	// 权限id
	private String privilegeId;
	
	// 权限名称
	private String privilegeName;

	// 权限的url
	private String privilegeUrl;

	// 此权限是否是公共的权限(true就是公共权限表示此权限不需要授权，false表示此权限需要授权来访问)
	private boolean isCommon = false;

	// 权限所属的模块
	private String privilegeModel;

	// 权限说明
	private String memo;
	
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public boolean isCommon() {
		return isCommon;
	}

	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}

	public String getPrivilegeModel() {
		return privilegeModel;
	}

	public void setPrivilegeModel(String privilegeModel) {
		this.privilegeModel = privilegeModel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;
		if(StringUtils.isBlank(privilegeName)){
			statusCode = StatusCode.PRIVILEGE_NAME_BLANK;
		}else if(StringUtils.isBlank(privilegeUrl)){
			statusCode = StatusCode.PRIVILEGE_URL_BLANK;
		}
		return statusCode;
	}
}
