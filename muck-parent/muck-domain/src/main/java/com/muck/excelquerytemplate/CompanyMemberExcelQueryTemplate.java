package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

public class CompanyMemberExcelQueryTemplate extends BaseExcelQueryTemplate{
	
	/**人员姓名*/
	@ExcelTemplate(name="姓名")
	private String has_memberName;
	/**人员性别*/
	@ExcelTemplate(name="性别")
	private String has_memberSex;
	/**人员生日*/
	@ExcelTemplate(name="出生年月")
	private String has_memberBirth;
	/**人员职称*/
	@ExcelTemplate(name="职称") 
	private String has_jobName;
	/**毕业信息*/
	@ExcelTemplate(name="何时何校何专业毕业")
	private String has_graduateInfo;
	/**文化程度*/
	@ExcelTemplate(name="文化程度")
	private String has_cultureDegree;
	/**企业管理资历*/
	@ExcelTemplate(name="企业管理资历")
	private String has_companyManageYear;
	/**联系方式*/
	@ExcelTemplate(name="联系电话")
	private String has_phoneNumber;
	/***/
	@ExcelTemplate(name="由何年月至何年月")
	private String has_fromWhenToWhen;
	/***/
	@ExcelTemplate(name="在何单位从事何工作，任何职")
	private String has_atWhereAndJob;
	public String getHas_memberName() {
		return has_memberName;
	}
	public void setHas_memberName(String has_memberName) {
		this.has_memberName = has_memberName;
	}
	public String getHas_memberSex() {
		return has_memberSex;
	}
	public void setHas_memberSex(String has_memberSex) {
		this.has_memberSex = has_memberSex;
	}
	public String getHas_memberBirth() {
		return has_memberBirth;
	}
	public void setHas_memberBirth(String has_memberBirth) {
		this.has_memberBirth = has_memberBirth;
	}
	public String getHas_jobName() {
		return has_jobName;
	}
	public void setHas_jobName(String has_jobName) {
		this.has_jobName = has_jobName;
	}
	public String getHas_graduateInfo() {
		return has_graduateInfo;
	}
	public void setHas_graduateInfo(String has_graduateInfo) {
		this.has_graduateInfo = has_graduateInfo;
	}
	public String getHas_cultureDegree() {
		return has_cultureDegree;
	}
	public void setHas_cultureDegree(String has_cultureDegree) {
		this.has_cultureDegree = has_cultureDegree;
	}
	public String getHas_companyManageYear() {
		return has_companyManageYear;
	}
	public void setHas_companyManageYear(String has_companyManageYear) {
		this.has_companyManageYear = has_companyManageYear;
	}
	public String getHas_phoneNumber() {
		return has_phoneNumber;
	}
	public void setHas_phoneNumber(String has_phoneNumber) {
		this.has_phoneNumber = has_phoneNumber;
	}
	public String getHas_fromWhenToWhen() {
		return has_fromWhenToWhen;
	}
	public void setHas_fromWhenToWhen(String has_fromWhenToWhen) {
		this.has_fromWhenToWhen = has_fromWhenToWhen;
	}
	public String getHas_atWhereAndJob() {
		return has_atWhereAndJob;
	}
	public void setHas_atWhereAndJob(String has_atWhereAndJob) {
		this.has_atWhereAndJob = has_atWhereAndJob;
	}
	
	
	
	
	

}
