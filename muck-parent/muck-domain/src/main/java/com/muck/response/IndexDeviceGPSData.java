package com.muck.response;

/***
 * 首页的设备的gps的数据格式
 */
public class IndexDeviceGPSData {

	/** 经度 */
	private Double longitude;

	/** 纬度 */
	private Double latitude;

	/** 通道编号 **/
	private String channelCode;

	/** 通道状态 **/
	private String deviceStatus;

	/** 设备类型(1:工地，2：车辆 ， 3: 处置场) **/
	private Integer deviceType;

	/*** 经纬度 **/
	private Double[] lnglat = new Double[2];

	public void setLongitude(Double longitude) {
		this.longitude = longitude;

		if (this.longitude != null) {
			this.lnglat[0] = this.longitude;
		}
	}

	public void setLatitude(Double latitude) {

		this.latitude = latitude;

		if (this.latitude != null) {
			this.lnglat[1] = this.latitude;
		}
	}

	public Double[] getLnglat() {

		if (lnglat.length == 2) {

			if (this.latitude != null && this.longitude != null) {
				// 坐标转换
				this.lnglat[1] = this.latitude;
				this.lnglat[0] = this.longitude;
			}
		}
		return lnglat;
	}

	public void setLnglat(Double[] lnglat) {
		this.lnglat = lnglat;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
}
