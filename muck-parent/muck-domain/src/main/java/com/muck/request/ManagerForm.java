package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

/**
 * @Description: 系统用户请求Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 下午3:39:51
 */
@Unique(tableName = "t_manager",uniqueFields = {"manager_phone"})
public class ManagerForm extends BaseForm {

	// 管理员id
	private String managerId;
	
	// 管理用户名
	private String managerName;

	// 管理员密码
	private String managerPassword;

	// 管理员联系方式
	private String managerPhone;

	// 说明
	private String memo;
	
	// 所属企业
	private String companyId;
	
	// 所属企业名称
	private String companyName;
	
	// 所属区域
	private String areaId;
	
	//  所属工地
	private String siteId;
	
	// 所属处置场
	private String disposalId;
	
	// 管理员id的数组
	private String[] managerIds;
	
	// 权限组id数组
	private String[] privilegeGroupIds;
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

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

	public String getMemo() {
		return memo;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String[] getManagerIds() {
		return managerIds;
	}

	public void setManagerIds(String[] managerIds) {
		this.managerIds = managerIds;
	}

	public String[] getPrivilegeGroupIds() {
		return privilegeGroupIds;
	}

	public void setPrivilegeGroupIds(String[] privilegeGroupIds) {
		this.privilegeGroupIds = privilegeGroupIds;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode = null;
		if(StringUtils.isBlank(managerName)){
			statusCode = StatusCode.MANAGER_NAME_BLANK;
		}else if(StringUtils.isBlank(managerPhone)){
			statusCode = StatusCode.MANAGER_PHONE_BLANK;
		}
		return statusCode;
	}
	
	@Override
	public Object[] validateUnique() {
		return new Object[]{managerPhone}; 
	}
}
