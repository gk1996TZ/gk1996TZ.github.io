package com.muck.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Table;

/**
 * @Description: 车辆GPS实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月24日 下午4:31:06
 */
@Table(name="t_biz_gps_data")
public class GpsData extends BaseEntity{

	/**	Code	**/
	private Integer code;
	
	/*** 经纬度 **/
	private double[] lnglat = new double[2];
	
	/** 车牌号 */
	private String vehicleNo;

	/** 车牌颜色(1:蓝色,2:黄色,3:白色,4:黑色,9:其他) */
	private Integer vehicleColor;

	/** 经度 	*/
	private Long longitude;

	/** 纬度 */
	private Long latitude;

	/** 车辆经纬度对应的位置 */
	private String location;

	/** 车辆速度 */
	private Double speed;

	/** 车辆总里程 */
	private String mileage;

	/** GPS时间 */
	private Date gpsTime;
	
	/**	车辆当前的方向 	**/
	private String direction;
	
	/**	样式,这个是高德地图所要求的字段,目的是可以定制化图片的样式	**/
	private Integer style = CarType.CHANG_BAO.getType();
	
	/**名称**/
	private String name;
	/**车辆状态*/
	private byte status;
	/**企业id*/
	private String companyId;
	/**车辆组id*/
	private String carGroupId;
	/**企业名称*/
	private String companyName;
	/**车辆驾驶员列表*/ 
	private List<CarDriver> carDrivers;
	
	public List<CarDriver> getCarDrivers() {
		return carDrivers;
	}

	public void setCarDrivers(List<CarDriver> carDrivers) {
		this.carDrivers = carDrivers;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCarGroupId() {
		return carGroupId;
	}

	public void setCarGroupId(String carGroupId) {
		this.carGroupId = carGroupId;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public GpsData() {
		
	}

	public GpsData(String vehicleNo, Integer vehicleColor, Long longitude, Long latitude, String location,
			Double speed, String mileage, Date gpsTime) {
		super();
		this.vehicleNo = vehicleNo;
		this.vehicleColor = vehicleColor;
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = location;
		this.speed = speed;
		this.mileage = mileage;
		this.gpsTime = gpsTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo == null ? vehicleNo : vehicleNo.trim();
	}

	public Integer getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(Integer vehicleColor) {
		this.vehicleColor = vehicleColor;
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
	
	public double[] getLnglat() {
		return lnglat;
	}
	
	

	public void setLnglat(double[] lnglat) {
		this.lnglat = lnglat;
	}

	public String getLocation() {
		if(StringUtils.isNotBlank(this.location)){
			return this.location.trim();
		}
		return location;
	}

	public void setLocation(String location) {
		this.location = ((location == null) ? location : location.trim());
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public String getMileage() {
		return mileage;
	}

	public String getName() {
		if(StringUtils.isNotBlank(this.location)){
			this.name = this.location;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? name : name.trim();
	}

	/** 时间	**/
	private String time;

	public String getTime() {
		return time;
	}

	/*
	public Integer getStyle() {
		if(this.vehicleColor != null){
			if(this.vehicleColor == 1){
				this.style = CarType.CHANG_BAO.getType();
			}else if(this.vehicleColor == 2){
				this.style = CarType.BEI_DOU.getType();
			}
		}
		return this.style;
	}**/
	
	public void setMileage(String mileage) {
		if(StringUtils.isNotBlank(mileage)){
			if(mileage.contains(".")){
				this.mileage = ((Double.parseDouble(mileage)) * 1000) + "";
			}
		}
		this.mileage = mileage;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public void setTime(String time) {
		this.time = time;
		if(StringUtils.isNotBlank(this.time)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				this.gpsTime = sdf.parse(this.time);
				return;
			} catch (ParseException e) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					this.gpsTime = sdf.parse(this.time);
				} catch (ParseException e1) {
					return;
				}
			}
		}
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicleNo == null) ? 0 : vehicleNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GpsData other = (GpsData) obj;
		if (vehicleNo == null) {
			if (other.vehicleNo != null)
				return false;
		} else if (!vehicleNo.equals(other.vehicleNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GpsData [code=" + code + ", lnglat=" + Arrays.toString(lnglat) + ", vehicleNo=" + vehicleNo
				+ ", vehicleColor=" + vehicleColor + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", location=" + location + ", speed=" + speed + ", mileage=" + mileage + ", gpsTime=" + gpsTime
				+ ", direction=" + direction + ", style=" + style + ", name=" + name + ", status=" + status
				+ ", companyId=" + companyId + ", carGroupId=" + carGroupId + ", companyName=" + companyName
				+ ", carDrivers=" + carDrivers + ", time=" + time + "]";
	}

	

	
}
