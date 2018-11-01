package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/***
 * 	设备查询Form
*/
public class DeviceQueryForm extends BaseForm{

	/**工地id*/
	private String siteId;
	
	/**地域id*/
	private String areaId;
	
	/** 设备名称	**/
	private String deviceName;
	
	/*** 处置场id **/
	private String disposalId;
	
	/**	设备类型	**/
	private Integer deviceType;
	
	/**	设备id	**/
	private String deviceId;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	@Override
	public StatusCode validate() {
		return null;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
}
