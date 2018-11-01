package com.muck.response;

/**
 * 	简单的工地树返回结果
*/
public class SimpleSiteTreeDataResponse {

	// 工地id
	private String id;
	
	// 工地名称
	private String siteName;
	
	// 通道号(这里的通道号采用的是site_id)
	private String channelCode;
	
	// 设备号
	private String deviceCode;
	
	//设备注册码
	private String siteRegisterCode;
	

	public String getSiteRegisterCode() {
		return siteRegisterCode;
	}

	public void setSiteRegisterCode(String siteRegisterCode) {
		this.siteRegisterCode = siteRegisterCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
}
