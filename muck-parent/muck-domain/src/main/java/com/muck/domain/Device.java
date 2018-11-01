package com.muck.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 设备实体
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月11日 下午1:48:21
 */
@Table(name = "t_device")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Device extends BaseEntity {

	// 设备号
	private String deviceCode;

	// 设备名称
	private String deviceName;

	// 设备安装日期,默认当前日期
	private Date deviceInstalledTime;

	// 设备安装人
	private String deviceInstalledUser;

	// 设备安装联系方式
	private String deviceInstalledUserPhone;

	// 所属设备类型(1:工地，2：车辆 ， 3: 处置场)
	private Integer deviceType;

	// 设备方位(如果是车辆设备的话，该属性存放设备所属的车辆部位，1:前置，2:内置，3:后置)
	private Integer deviceDirection;

	// 设备出厂日期
	private Date deviceMadeTime;

	// 设备所属区域
	private Area area;

	// 区域名称
	private String deviceAreaName;

	// 设备所属企业
	private Company company;

	// 设备厂家
	private String deviceFactoryName;

	// 所属工地
	private Site site;

	// 所属处置场
	private Disposal disposal;

	// 设备所属车辆
	private Car car;

	// 设备所属管理员
	private Manager manager;

	// 设备真实ip
	private String deviceIp;

	// 设备端口
	private String devicePort;

	// 设备登录用户名
	private String deviceLoginName;

	// 设备登录密码
	private String deviceLoginPassword;

	// 设备状态(大华那边提供的状态)
	private String deviceStatus;

	// 设备类型(大华提供)
	private String deviceTypeDaHua;

	// 注册ID
	private String deviceRegisterId;

	// 设备是否运行,默认是运行(1:运行,0:不运行)
	private Boolean isRunning = true;

	// 设备所属经度
	private BigDecimal deviceLongitude;

	// 设备所属纬度
	private BigDecimal deviceLatitude;

	// 设备下面的通道数
	private Integer deviceChannelNums;

	// 设备的视频服务器(1:中心服务器)
	private Integer deviceServerType;

	// 设备的物理类型(1:NVR)
	private Integer deviceRealType;

	// 设备的备注说明
	private String memo;

	// 设备下面所有的通道
	private List<Channel> channels = new ArrayList<Channel>();

	// 设备的地址
	private String deviceAddress;

	// 设备的注册码
	private String deviceRegisterCode;

	public Device() {

	}

	public String getDeviceRegisterCode() {
		return deviceRegisterCode;
	}

	public void setDeviceRegisterCode(String deviceRegisterCode) {
		this.deviceRegisterCode = deviceRegisterCode;
	}

	public Device(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Date getDeviceInstalledTime() {
		return deviceInstalledTime;
	}

	public void setDeviceInstalledTime(Date deviceInstalledTime) {
		this.deviceInstalledTime = deviceInstalledTime;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getDeviceDirection() {
		return deviceDirection;
	}

	public void setDeviceDirection(Integer deviceDirection) {
		this.deviceDirection = deviceDirection;
	}

	public Date getDeviceMadeTime() {
		return deviceMadeTime;
	}

	public void setDeviceMadeTime(Date deviceMadeTime) {
		this.deviceMadeTime = deviceMadeTime;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDeviceAreaName() {
		return deviceAreaName;
	}

	public void setDeviceAreaName(String deviceAreaName) {
		this.deviceAreaName = deviceAreaName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDeviceFactoryName() {
		return deviceFactoryName;
	}

	public void setDeviceFactoryName(String deviceFactoryName) {
		this.deviceFactoryName = deviceFactoryName;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Integer getDeviceChannelNums() {
		return deviceChannelNums;
	}

	public void setDeviceChannelNums(Integer deviceChannelNums) {
		this.deviceChannelNums = deviceChannelNums;
	}

	public Integer getDeviceServerType() {
		return deviceServerType;
	}

	public void setDeviceServerType(Integer deviceServerType) {
		this.deviceServerType = deviceServerType;
	}

	public Integer getDeviceRealType() {
		return deviceRealType;
	}

	public void setDeviceRealType(Integer deviceRealType) {
		this.deviceRealType = deviceRealType;
	}

	public BigDecimal getDeviceLongitude() {
		return deviceLongitude;
	}

	public void setDeviceLongitude(BigDecimal deviceLongitude) {
		this.deviceLongitude = deviceLongitude;
	}

	public BigDecimal getDeviceLatitude() {
		return deviceLatitude;
	}

	public void setDeviceLatitude(BigDecimal deviceLatitude) {
		this.deviceLatitude = deviceLatitude;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public String getDeviceLoginName() {
		return deviceLoginName;
	}

	public void setDeviceLoginName(String deviceLoginName) {
		this.deviceLoginName = deviceLoginName;
	}

	public String getDeviceLoginPassword() {
		return deviceLoginPassword;
	}

	public void setDeviceLoginPassword(String deviceLoginPassword) {
		this.deviceLoginPassword = deviceLoginPassword;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceTypeDaHua() {
		return deviceTypeDaHua;
	}

	public void setDeviceTypeDaHua(String deviceTypeDaHua) {
		this.deviceTypeDaHua = deviceTypeDaHua;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Disposal getDisposal() {
		return disposal;
	}

	public void setDisposal(Disposal disposal) {
		this.disposal = disposal;
	}

	public String getDeviceRegisterId() {
		return deviceRegisterId;
	}

	public void setDeviceRegisterId(String deviceRegisterId) {
		this.deviceRegisterId = deviceRegisterId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDeviceInstalledUser() {
		return deviceInstalledUser;
	}

	public void setDeviceInstalledUser(String deviceInstalledUser) {
		this.deviceInstalledUser = deviceInstalledUser;
	}

	public String getDeviceInstalledUserPhone() {
		return deviceInstalledUserPhone;
	}

	public void setDeviceInstalledUserPhone(String deviceInstalledUserPhone) {
		this.deviceInstalledUserPhone = deviceInstalledUserPhone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceCode == null) ? 0 : deviceCode.hashCode());
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
		Device other = (Device) obj;
		if (deviceCode == null) {
			if (other.deviceCode != null)
				return false;
		} else if (!deviceCode.equals(other.deviceCode))
			return false;
		return true;
	}
}
