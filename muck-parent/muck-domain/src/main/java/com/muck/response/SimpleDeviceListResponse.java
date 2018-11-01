package com.muck.response;

import com.muck.utils.map.CoordinateConverter;

/**
 * 返回设备结果
 */
public class SimpleDeviceListResponse {

	// 设备id
	private String id;

	// 设备名称
	private String deviceName;

	// 所属工地id
	private String siteId;
	
	// 所属工地名称
	private String siteName;

	// 所属处置场id
	private String disposalId;
	
	// 所属处置场名称
	private String disposalName;
	
	// 设备是否运行,默认是运行(1:运行,0:不运行)
	private Boolean isRunning;
	
	// 通道号
	private String channelCode;
	
	// 纬度
	private Double latitude;
	
	// 经度
	private Double longitude;
	
	//项目负责人
	private String constructionManager;
	
	//项目负责人电话
	private String constructionPhone;

	
	public String getConstructionManager() {
		return constructionManager;
	}

	public void setConstructionManager(String constructionManager) {
		this.constructionManager = constructionManager;
	}

	public String getConstructionPhone() {
		return constructionPhone;
	}

	public void setConstructionPhone(String constructionPhone) {
		this.constructionPhone = constructionPhone;
	}

	public void setLnglat(Double[] lnglat) {
		this.lnglat = lnglat;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
		if (this.latitude != null) {
			this.lnglat[1] = this.latitude;
		}
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
		if (this.longitude != null) {
			this.lnglat[0] = this.longitude;
		}
	}
	
	/*** 经纬度 **/
	private Double[] lnglat = new Double[2];

	
	public Double[] getLnglat() {
		
		if(lnglat.length == 2){
			
			Double lat = lnglat[1];
			Double lon = lnglat[0];
			
			if(lat != null && lon != null){
				// 表示的是经度和纬度是获取到了 lnglat[0]-->经度  lnglat[1]---> 纬度
				double[] gcjs = CoordinateConverter.wgs2GCJ(lnglat[1], lnglat[0]);
				if(gcjs != null && gcjs.length == 2){
					lnglat[0] = gcjs[1];
					lnglat[1] = gcjs[0];
					
					// 坐标转换
					this.latitude = this.lnglat[1];
					this.longitude = this.lnglat[0];
				}
			}
		}
		return lnglat;
	}
	
	
}
