package com.muck.response;

import java.util.Date;
import java.util.List;

import com.muck.domain.CarDriver;
import com.muck.utils.map.CoordinateConverter;

/**
 * 	车辆gps返回车辆最后一次车辆详情
*/
public class CarGPSDataInfoResponse {

	/** 企业 **/
	private String companyName;
	
	/** 车牌号 **/
	private String carCode;
	
	/**车主*/
	private String carOwnerOfVehicle;
	
	/**驾驶员*/
	private List<CarDriver> carDrivers;
	
	/**车辆运行状态*/
	private String carIsRunning; 
	
	/**车辆备注*/
	private String memo;
	
	/** 速度  **/
	private Integer speed;
	
	/** 经度 	*/
	private Double longitude;

	/** 纬度 */
	private Double latitude;
	
	/** 车辆经纬度对应的位置 */
	private String location;
	
	/**	车辆当前的方向 	**/
	private String direction;
	
	/** GPS时间 */
	private Date gpsTime;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
		if (this.longitude != null) {
			this.lnglat[0] = ((double)this.longitude) / 1000000;
		}
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;

		if (this.latitude != null) {
			this.lnglat[1] = ((double)this.latitude) / 1000000;
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
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
					lnglat[0] = gcjs[0];
					lnglat[1] = gcjs[1];
					
					// 坐标转换
					this.latitude = this.lnglat[1];
					this.longitude = this.lnglat[0];
				}
			}
		}
		return lnglat;
	}

	public String getCarOwnerOfVehicle() {
		return carOwnerOfVehicle;
	}

	public void setCarOwnerOfVehicle(String carOwnerOfVehicle) {
		this.carOwnerOfVehicle = carOwnerOfVehicle;
	}

	public String getCarIsRunning() {
		return carIsRunning;
	}

	public void setCarIsRunning(String carIsRunning) {
		this.carIsRunning = carIsRunning;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setLnglat(Double[] lnglat) {
		this.lnglat = lnglat;
	}

	public List<CarDriver> getCarDrivers() {
		return carDrivers;
	}

	public void setCarDrivers(List<CarDriver> carDrivers) {
		this.carDrivers = carDrivers;
	}
}
