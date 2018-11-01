package com.muck.response;

/**
 *	返回前台的简单的设备详情
 *		包括设备信息、工地信息
*/
public class SimpleDeviceAndSiteInfoResponse {

	// 工地名称
	private String siteName;
	
	// 所属区域
	private String areaName;
	
	// 工地地址
	private String siteAddress;
	
	// 企业
	private String companyName;
	
	//  项目负责人一
	private String siteProjectManagerOne;
	
	// 项目负责人一联系电话
	private String siteProjectManagerPhoneOne;
	
	//  项目负责人二
	private String siteProjectManagerTwo;
	
	// 项目负责人二联系电话
	private String siteProjectManagerPhoneTwo;
	
	// 保洁人员
	private String siteCleaner;
	
	// 保洁员联系电话
	private String siteCleanerPhone;
	
	// 设备名称
	private String deviceName;
	
	// 设备ip域名
	private String deviceIP;
	
	// 在线状态
	private String statusName;
	
	// 通道数
	private Integer channelNums;
	
	// 设备类型
	private String deviceTypeName;
	
	// 厂商类型
	private String deviceFactoryName;
	
	// 工地备注
	private String siteProcessMemo;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getSiteCleaner() {
		return siteCleaner;
	}

	public void setSiteCleaner(String siteCleaner) {
		this.siteCleaner = siteCleaner;
	}

	public String getSiteCleanerPhone() {
		return siteCleanerPhone;
	}

	public void setSiteCleanerPhone(String siteCleanerPhone) {
		this.siteCleanerPhone = siteCleanerPhone;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getChannelNums() {
		return channelNums;
	}

	public void setChannelNums(Integer channelNums) {
		this.channelNums = channelNums;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getDeviceFactoryName() {
		return deviceFactoryName;
	}

	public void setDeviceFactoryName(String deviceFactoryName) {
		this.deviceFactoryName = deviceFactoryName;
	}

	public String getSiteProcessMemo() {
		return siteProcessMemo;
	}

	public void setSiteProcessMemo(String siteProcessMemo) {
		this.siteProcessMemo = siteProcessMemo;
	}
}
