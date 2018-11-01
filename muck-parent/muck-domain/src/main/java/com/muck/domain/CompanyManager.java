package com.muck.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 企业管理人员实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月20日 下午10:28:44
 */
@Table(name="t_company_manager")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CompanyManager extends BaseEntity{

	/**企业管理人员自定义序号*/
	private String companyManagerId;
	/**企业管理人员姓名*/
	private String companyManagerName;
	/**企业管理人员性别*/
	private String companyManagerSex;
	/**企业管理人员岗位*/
	private String companyManagerPost;
	/**企业管理人员联系电话*/
	private String companyManagerPhone;
	/**企业管理人员身份证号码*/
	private String companyManagerIdNumber;
	/**企业id*/
	private String companyId;
	/**企业名称*/
	private String companyName;
	/**企业管理人员备注信息*/
	private String memo;
	
	public String getCompanyManagerId() {
		return companyManagerId;
	}
	public void setCompanyManagerId(String companyManagerId) {
		this.companyManagerId = companyManagerId;
	}
	public String getCompanyManagerName() {
		return companyManagerName;
	}
	public void setCompanyManagerName(String companyManagerName) {
		this.companyManagerName = companyManagerName;
	}
	public String getCompanyManagerSex() {
		return companyManagerSex;
	}
	public void setCompanyManagerSex(String companyManagerSex) {
		this.companyManagerSex = companyManagerSex;
	}
	public String getCompanyManagerPost() {
		return companyManagerPost;
	}
	public void setCompanyManagerPost(String companyManagerPost) {
		this.companyManagerPost = companyManagerPost;
	}
	public String getCompanyManagerPhone() {
		return companyManagerPhone;
	}
	public void setCompanyManagerPhone(String companyManagerPhone) {
		this.companyManagerPhone = companyManagerPhone;
	}
	public String getCompanyManagerIdNumber() {
		return companyManagerIdNumber;
	}
	public void setCompanyManagerIdNumber(String companyManagerIdNumber) {
		this.companyManagerIdNumber = companyManagerIdNumber;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
}
