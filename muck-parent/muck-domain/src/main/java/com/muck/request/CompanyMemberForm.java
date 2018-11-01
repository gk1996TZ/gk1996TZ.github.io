package com.muck.request;

import java.util.Date;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

@Unique(uniqueFields = { "phone_number" }, tableName = "t_company_member")
public class CompanyMemberForm extends BaseForm {
	// 人员id
	private String companyMemberId;
	// 人员姓名
	private String memberName;
	// 人员生日
	private Date memberBirth;
	// 人员性别
	private String memberSex;
	// 职称
	private String jobName;
	// 毕业信息
	private String graduateInfo;
	// 文化程度
	private String cultureDegree;
	// 管理资历
	private String companyManageYear;
	// 联系方式
	private String phoneNumber;
	// 工作信息
	private String fromWhenToWhen;
	// 工作单位及职位
	private String atWhereAndJob;
	// 照片路径
	private String imageUrl;
	// 人员类型
	private Integer type;

	

	public String getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}

	

	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyMemberId() {
		return companyMemberId;
	}

	public void setCompanyMemberId(String companyMemberId) {
		this.companyMemberId = companyMemberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(Date memberBirth) {
		this.memberBirth = memberBirth;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGraduateInfo() {
		return graduateInfo;
	}

	public void setGraduateInfo(String graduateInfo) {
		this.graduateInfo = graduateInfo;
	}

	public String getCultureDegree() {
		return cultureDegree;
	}

	public void setCultureDegree(String cultureDegree) {
		this.cultureDegree = cultureDegree;
	}

	public String getCompanyManageYear() {
		return companyManageYear;
	}

	public void setCompanyManageYear(String companyManageYear) {
		this.companyManageYear = companyManageYear;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFromWhenToWhen() {
		return fromWhenToWhen;
	}

	public void setFromWhenToWhen(String fromWhenToWhen) {
		this.fromWhenToWhen = fromWhenToWhen;
	}

	public String getAtWhereAndJob() {
		return atWhereAndJob;
	}

	public void setAtWhereAndJob(String atWhereAndJob) {
		this.atWhereAndJob = atWhereAndJob;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public StatusCode validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
