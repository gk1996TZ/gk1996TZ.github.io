package com.muck.response;

/**
 * 	简单的设备类型
*/
public class SimpleDeviceTypeInfoResponse {

	// 设备id
	private String deviceId;
	
	// 设备注册id
	private String deviceRegisterId;
	
	// 设备类型
	private Integer deviceType;
	
	// 设备类型的名称
	private String deviceTypeName;
	
	// 工地id
	private String siteId;
	
	// 工地名称
	private String siteName;
	
	// 处置场id
	private String disposalId;
	
	// 处置场名称
	private String disposalName;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceRegisterId() {
		return deviceRegisterId;
	}

	public void setDeviceRegisterId(String deviceRegisterId) {
		this.deviceRegisterId = deviceRegisterId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}
}
