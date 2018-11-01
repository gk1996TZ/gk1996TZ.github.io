package com.muck.request;

import com.muck.response.StatusCode;

public class CompanyManagerForm extends BaseForm{

	/**企业管理人员id*/
	private String id;
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
	/**企业管理人员备注信息*/
	private String memo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@Override
	public StatusCode validate() {
		return null;
	}
}
