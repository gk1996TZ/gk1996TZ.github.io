package com.muck.response;


/**
 * @Description: 企业统计信息返回实体
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月2日 下午5:57:21
 */
public class StatisticsCompany {

	// 企业id
	private String id;

	// 企业名称
	private String companyName;
	
	// 企业法人名称
	private String companyLegalRepresentative;
	
	//企业负法人联系方式
	private String companyLegalRepresentativePhone;

	// 区域名称
	private String areaName;
	
	// 备注信息
	private String memo;
	// 工程中正在使用的车辆数
	private Integer companyCarNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLegalRepresentative() {
		return companyLegalRepresentative;
	}
	public void setCompanyLegalRepresentative(String companyLegalRepresentative) {
		this.companyLegalRepresentative = companyLegalRepresentative;
	}
	public String getCompanyLegalRepresentativePhone() {
		return companyLegalRepresentativePhone;
	}
	public void setCompanyLegalRepresentativePhone(String companyLegalRepresentativePhone) {
		this.companyLegalRepresentativePhone = companyLegalRepresentativePhone;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getCompanyCarNumber() {
		return companyCarNumber;
	}
	public void setCompanyCarNumber(Integer companyCarNumber) {
		this.companyCarNumber = companyCarNumber;
	}

}
