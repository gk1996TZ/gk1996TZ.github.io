package com.muck.response;

/**
 *	返回前台的简单的设备详情
 *		包括设备信息、处置场信息
*/
public class SimpleDeviceAndDisposalInfoResponse {

	// 处置场名称
	private String disposalName;
	
	// 所属区域
	private String areaName;
	
	// 企业
	private String companyName;
	
	//  项目负责人一
	private String disposalProjectManagerOne;
	
	// 项目负责人一联系电话
	private String disposalProjectManagerPhoneOne;
	
	//  项目负责人二
	private String disposalProjectManagerTwo;
	
	// 项目负责人二联系电话
	private String disposalProjectManagerPhoneTwo;
	
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
	
	// 处置场备注
	private String memo;

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDisposalProjectManagerOne() {
		return disposalProjectManagerOne;
	}

	public void setDisposalProjectManagerOne(String disposalProjectManagerOne) {
		this.disposalProjectManagerOne = disposalProjectManagerOne;
	}

	public String getDisposalProjectManagerPhoneOne() {
		return disposalProjectManagerPhoneOne;
	}

	public void setDisposalProjectManagerPhoneOne(String disposalProjectManagerPhoneOne) {
		this.disposalProjectManagerPhoneOne = disposalProjectManagerPhoneOne;
	}

	public String getDisposalProjectManagerTwo() {
		return disposalProjectManagerTwo;
	}

	public void setDisposalProjectManagerTwo(String disposalProjectManagerTwo) {
		this.disposalProjectManagerTwo = disposalProjectManagerTwo;
	}

	public String getDisposalProjectManagerPhoneTwo() {
		return disposalProjectManagerPhoneTwo;
	}

	public void setDisposalProjectManagerPhoneTwo(String disposalProjectManagerPhoneTwo) {
		this.disposalProjectManagerPhoneTwo = disposalProjectManagerPhoneTwo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
