package com.muck.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 工地信息
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月2日 下午5:11:41
 */
@Table(name = "t_site")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Site extends BaseEntity {

	// 工地名称
	private String siteName;

	// 工地地址
	private String siteAddress;

	// 工地编码
	private String siteId;

	// 所属区域
	private Area area;

	// 保洁员
	private String siteCleanerName;

	// 保洁员联系方式
	private String siteCleanerPhone;

	// 备注
	private String memo;

	// 企业
	private Company company;
	
	//工地设备注册码
	private String siteRegisterCode;

	// 项目负责人1姓名(工地负责人)
	private String siteProjectManagerOne;

	// 项目负责人1联系电话(工地负责人联系电话)
	private String siteProjectManagerPhoneOne;

	// 项目负责人2姓名(工地负责人)
	private String siteProjectManagerTwo;

	// 项目负责人2联系电话(工地负责人联系电话)
	private String siteProjectManagerPhoneTwo;
	
	// 工程项目进度描述
	private String siteProcessMemo;
	
	// 工地所管理的工地项目情况表
	private SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
	
	// 工地所属的类别组
	private String siteGroupName;
	
	public String getSiteRegisterCode() {
		return siteRegisterCode;
	}

	public void setSiteRegisterCode(String siteRegisterCode) {
		this.siteRegisterCode = siteRegisterCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getSiteCleanerName() {
		return siteCleanerName;
	}

	public void setSiteCleanerName(String siteCleanerName) {
		this.siteCleanerName = siteCleanerName;
	}

	public String getSiteCleanerPhone() {
		return siteCleanerPhone;
	}

	public void setSiteCleanerPhone(String siteCleanerPhone) {
		this.siteCleanerPhone = siteCleanerPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSiteProjectManagerOne() {
		return siteProjectManagerOne;
	}

	public void setSiteProjectManagerOne(String siteProjectManagerOne) {
		this.siteProjectManagerOne = siteProjectManagerOne;
	}

	public String getSiteProjectManagerPhoneOne() {
		return siteProjectManagerPhoneOne;
	}

	public void setSiteProjectManagerPhoneOne(String siteProjectManagerPhoneOne) {
		this.siteProjectManagerPhoneOne = siteProjectManagerPhoneOne;
	}

	public String getSiteProjectManagerTwo() {
		return siteProjectManagerTwo;
	}

	public void setSiteProjectManagerTwo(String siteProjectManagerTwo) {
		this.siteProjectManagerTwo = siteProjectManagerTwo;
	}

	public String getSiteProjectManagerPhoneTwo() {
		return siteProjectManagerPhoneTwo;
	}

	public void setSiteProjectManagerPhoneTwo(String siteProjectManagerPhoneTwo) {
		this.siteProjectManagerPhoneTwo = siteProjectManagerPhoneTwo;
	}

	public SiteProjectInfo getSiteProjectInfo() {
		return siteProjectInfo;
	}

	public void setSiteProjectInfo(SiteProjectInfo siteProjectInfo) {
		this.siteProjectInfo = siteProjectInfo;
	}

	public String getSiteProcessMemo() {
		return siteProcessMemo;
	}

	public void setSiteProcessMemo(String siteProcessMemo) {
		this.siteProcessMemo = siteProcessMemo;
	}

	public String getSiteGroupName() {
		return siteGroupName;
	}

	public void setSiteGroupName(String siteGroupName) {
		this.siteGroupName = siteGroupName;
	}
	
	// 工地的设备
	private String deviceName;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
