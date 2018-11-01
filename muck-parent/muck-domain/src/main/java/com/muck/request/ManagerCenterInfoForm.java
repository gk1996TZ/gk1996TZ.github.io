package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/**
 * @Description: 个人中心ManagerCenterInfoForm
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月27日 上午10:11:54
 */
public class ManagerCenterInfoForm extends BaseForm {

	// 管理用户名
	private String managerName;

	// 管理员联系方式
	private String managerPhone;

	// 所属企业
	private String companyId;

	// 权限组
	private String[] privilegeGropuIds;

	// 说明
	private String memo;

	// 证件号码
	private String managerCardNo;

	// 邮箱
	private String managerEmail;

	@Override
	public StatusCode validate() {

		StatusCode statusCode = null;

		if (StringUtils.isBlank(managerPhone)) {
			statusCode = StatusCode.MANAGER_NAME_BLANK;
		} else if (StringUtils.isBlank(managerPhone)) {
			statusCode = StatusCode.MANAGER_PHONE_BLANK;
		}
		return statusCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String[] getPrivilegeGropuIds() {
		return privilegeGropuIds;
	}

	public void setPrivilegeGropuIds(String[] privilegeGropuIds) {
		this.privilegeGropuIds = privilegeGropuIds;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getManagerCardNo() {
		return managerCardNo;
	}

	public void setManagerCardNo(String managerCardNo) {
		this.managerCardNo = managerCardNo;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
}
