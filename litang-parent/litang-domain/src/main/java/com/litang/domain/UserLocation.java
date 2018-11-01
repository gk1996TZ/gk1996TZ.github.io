package com.litang.domain;

import java.io.Serializable;

public class UserLocation extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -1728327931577144687L;

	private String userId;
	
	/** 经度 	*/
	private Long longitude;

	/** 纬度 */
	private Long latitude;

	/** 车辆经纬度对应的位置 */
	private String location;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
