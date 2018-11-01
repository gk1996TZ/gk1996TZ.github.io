package com.muck.domain;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;
@Table(name = "t_company_member")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL )
public class CompanyMember extends BaseEntity{
	
	/**
	 * @Description: 企业相关人员实体类
	 * @version: v1.0.0
	 * @author: 甘坤
	 */
	//姓名
	private String memberName;
	//性别
	private String memberSex;
	//生日
	private Date memberBirth;
	//职称
	private String jobName;
	//毕业信息
	private String graduateInfo;
	//文化程度
	private String cultureDegree;
	//管理资历
	private String companyManageYear;
	//联系方式
	private String phoneNumber;
	//工作时间
	private String fromWhenToWhen;
	//工作单位及职位
	private String atWhereAndJob;
	//照片路径
	private String imageUrl;
	//人员类别
	private Integer memberType;
	
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberSex() {
		return memberSex;
	}
	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
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
}



