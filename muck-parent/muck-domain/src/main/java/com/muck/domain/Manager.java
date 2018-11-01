package com.muck.domain;

import java.util.ArrayList;
import java.util.List;

import com.muck.annotation.Table;

/**
* @Description: 系统用户
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午1:57:50
 */
@Table(name="t_manager")
public class Manager extends BaseEntity{
	
	// 管理姓名
	private String managerName;
	
	// 管理员密码
	private String managerPassword;
	
	// 管理员联系方式
	private String managerPhone;
	
	// 用户所属的企业
	private Company company;
	
	// 用户所属的工地
	private Site site;
	
	// 用户所属的处置场
	private Disposal disposal;
	
	// 用户所属区域
	private Area area;
	
	// 备注说明
	private String memo;
	
	// 证件号码
	private String managerCardNo;
	
	// 邮箱
	private String managerEmail;
	
	// 用户所属的权限组
	private List<PrivilegeGroup> privilegeGroups = new ArrayList<PrivilegeGroup>();

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

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<PrivilegeGroup> getPrivilegeGroups() {
		return privilegeGroups;
	}

	public void setPrivilegeGroups(List<PrivilegeGroup> privilegeGroups) {
		this.privilegeGroups = privilegeGroups;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Disposal getDisposal() {
		return disposal;
	}

	public void setDisposal(Disposal disposal) {
		this.disposal = disposal;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "[managerName=" + managerName + ", managerPhone=" + managerPhone + ", managerCardNo="
				+ managerCardNo + ", managerEmail=" + managerEmail + "]";
	}
}
