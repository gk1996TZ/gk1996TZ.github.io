package com.muck.request;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/**
 * @Description: 设备Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 下午4:45:18
 */
public class DeviceForm extends BaseForm {

	// 设备名称
	private String deviceName;

	// 设备所属工地
	private String siteId;

	// 设备所属处置场
	private String disposalId;

	// 设备所属车辆
	private String carId;

	// 设备所属类型
	private Integer deviceType;

	// 设备ip
	private String deviceIp;

	// 注册ID
	private String deviceRegisterId;

	// 设备登录用户名
	private String deviceLoginName;

	// 设备登录密码
	private String deviceLoginPassword;

	// 设备端口
	private String devicePort;

	// 设备id
	private String deviceId;

	// 设备号
	private String deviceCode;

	// 设备安装日期,默认当前日期
	private Date deviceInstalledTime;

	// 设备安装人
	private String deviceInstalledUser;

	// 设备安装联系方式
	private String deviceInstalledUserPhone;

	// 设备出厂日期
	private Date deviceMadeTime;

	// 设备所属区域
	private String areaId;

	// 区域名称
	private String deviceAreaName;

	// 设备所属企业
	private String companyId;

	// 企业名称
	private String companyName;

	// 设备所属管理员
	private String managerId;

	// 设备所属经度
	private BigDecimal deviceLongitude;

	// 设备所属纬度
	private BigDecimal deviceLatitude;

	// 设备地址
	private String deviceAddress;

	// 设备是否运行
	private Boolean isRunning = true;

	// 设备下面的通道数
	private Integer deviceChannelNums;

	// 设备的视频服务器(1:中心服务器)
	private Integer deviceServerType;

	// 设备厂家(1:大华 2:海康)
	private String deviceFactoryName;

	// 备注说明
	private String memo;

	// 设备的物理类型(1:NVR)
	private Integer deviceRealType;

	// 设备注册码
	private String deviceRegisterCode;

	public String getDeviceRegisterCode() {
		return deviceRegisterCode;
	}

	public void setDeviceRegisterCode(String deviceRegisterCode) {
		this.deviceRegisterCode = deviceRegisterCode;
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

	public Date getDeviceMadeTime() {
		return deviceMadeTime;
	}

	public void setDeviceMadeTime(Date deviceMadeTime) {
		this.deviceMadeTime = deviceMadeTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDeviceAreaName() {
		return deviceAreaName;
	}

	public void setDeviceAreaName(String deviceAreaName) {
		this.deviceAreaName = deviceAreaName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDeviceFactoryName() {
		return deviceFactoryName;
	}

	public void setDeviceFactoryName(String deviceFactoryName) {
		this.deviceFactoryName = deviceFactoryName;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
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

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceRegisterId() {
		return deviceRegisterId;
	}

	public void setDeviceRegisterId(String deviceRegisterId) {
		this.deviceRegisterId = deviceRegisterId;
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

	public String getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	public StatusCode validate() {

		StatusCode statusCode = null;
		if (StringUtils.isBlank(deviceName)) {
			statusCode = StatusCode.DEVICE_NAME_BLANK;
		} else if (StringUtils.isBlank(deviceRegisterId)) {
			statusCode = StatusCode.DEVICE_REGISITER_ID_BLANK;
		}
		return statusCode;
	}
}
