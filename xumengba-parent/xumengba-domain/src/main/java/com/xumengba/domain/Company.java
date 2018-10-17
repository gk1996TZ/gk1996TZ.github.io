package com.xumengba.domain;

import java.io.Serializable;
import java.util.Date;

import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;
@Table(name="t_company")
public class Company extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -1020597292385428902L;
	/**企业名称*/
	@Field(name="company_name")
	private String companyName;
	/**企业邮箱*/
	@Field(name="company_mail")
	private String companyMail;
	/**企业法人代表*/
	@Field(name="company_legal_person")
	private String companyLegalPerson;
	/**企业联系方式*/
	@Field(name="company_phone")
	private String companyPhone;
	/**企业logo*/
	@Field(name="company_logo")
	private String companyLogo;
	/***企业微信公众号*/
	@Field(name="company_wx_official")
	private String companyWxOfficial;
	/**企业地址*/
	@Field(name="company_address")
	private String companyAddress;
	/**企业描述*/
	@Field(name="memo")
	private String memo;
	/**自增id*/
	@Field(name="id")
	private String id;
	/**创建时间*/
	@Field(name="created_time")
	private Date createdTime;
	/**修改时间*/
	@Field(name="updated_time")
	private Date updatedTime;
	/**是否删除true：不删除，false删除*/
	@Field(name="deleted")
	private Boolean deleted;
	/**定义一个idRaw字段*/
	private Long idRaw;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyMail() {
		return companyMail;
	}
	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}
	public String getCompanyLegalPerson() {
		return companyLegalPerson;
	}
	public void setCompanyLegalPerson(String companyLegalPerson) {
		this.companyLegalPerson = companyLegalPerson;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyWxOfficial() {
		return companyWxOfficial;
	}
	public void setCompanyWxOfficial(String companyWxOfficial) {
		this.companyWxOfficial = companyWxOfficial;
	}
	public String getCompanAddress() {
		return companyAddress;
	}
	public void setCompanAddress(String companAddress) {
		this.companyAddress = companAddress;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getId() {
		if(id == null && idRaw > 0){
			id = IdTypeHandler.encode(idRaw);
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Long getIdRaw() {
		return idRaw;
	}
	public void setIdRaw(Long idRaw) {
		this.idRaw = idRaw;
	}
}
