package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

public class DeviceExcelQueryTemplate extends BaseExcelQueryTemplate {

	/* 设备端口号 **/
	@ExcelTemplate(name = "设备端口号")
	private String has_devicePort;
	/* 设备ip **/
	@ExcelTemplate(name = "设备IP/域名")
	private String has_deviceIp;
	/* 设备通道数 **/
	@ExcelTemplate(name = "设备通道数")
	private Integer has_deviceChannelNums;
	/* 设备安装人联系方式 **/
	@ExcelTemplate(name = "设备安装人联系方式")
	private String has_deviceInstalledUserPhone;
	/* 设备安装人 **/
	@ExcelTemplate(name = "设备安装人")
	private String has_deviceInstalledUser;
	/* 设备登录密码 **/
	@ExcelTemplate(name = "设备登录密码")
	private String has_deviceLoginPassword;
	/* 设备用户名 **/
	@ExcelTemplate(name = "设备用户名")
	private String has_deviceLoginName;
	/* 设备所属工地 **/
	@ExcelTemplate(name = "所属工地")
	private String has_site;
	/* 设备所属处置场 **/
	@ExcelTemplate(name = "所属处置场")
	private String has_disposal;
	/* 设备类型 **/
	@ExcelTemplate(name = "设备类型")
	private String has_deviceType;
	/* 设备名称 **/
	@ExcelTemplate(name = "设备名称")
	private String has_deviceName;
	/* 所属区域 **/
	@ExcelTemplate(name = "所属区域")
	private String has_area;
	/* 所属企业 **/
	@ExcelTemplate(name = "所属企业")
	private String has_company;
	/* 备注 **/
	@ExcelTemplate(name = "备注")
	private String has_memo;

	public DeviceExcelQueryTemplate() {
		super();
	}

	public String getHas_devicePort() {
		return has_devicePort;
	}

	public void setHas_devicePort(String has_devicePort) {
		this.has_devicePort = has_devicePort;
	}

	public String getHas_deviceIp() {
		return has_deviceIp;
	}

	public void setHas_deviceIp(String has_deviceIp) {
		this.has_deviceIp = has_deviceIp;
	}

	public Integer getHas_deviceChannelNums() {
		return has_deviceChannelNums;
	}

	public void setHas_deviceChannelNums(Integer has_deviceChannelNums) {
		this.has_deviceChannelNums = has_deviceChannelNums;
	}

	public String getHas_deviceInstalledUserPhone() {
		return has_deviceInstalledUserPhone;
	}

	public void setHas_deviceInstalledUserPhone(String has_deviceInstalledUserPhone) {
		this.has_deviceInstalledUserPhone = has_deviceInstalledUserPhone;
	}

	public String getHas_deviceInstalledUser() {
		return has_deviceInstalledUser;
	}

	public void setHas_deviceInstalledUser(String has_deviceInstalledUser) {
		this.has_deviceInstalledUser = has_deviceInstalledUser;
	}

	public String getHas_deviceLoginPassword() {
		return has_deviceLoginPassword;
	}

	public void setHas_deviceLoginPassword(String has_deviceLoginPassword) {
		this.has_deviceLoginPassword = has_deviceLoginPassword;
	}

	public String getHas_deviceLoginName() {
		return has_deviceLoginName;
	}

	public void setHas_deviceLoginName(String has_deviceLoginName) {
		this.has_deviceLoginName = has_deviceLoginName;
	}

	public String getHas_site() {
		return has_site;
	}

	public void setHas_site(String has_site) {
		this.has_site = has_site;
	}

	public String getHas_disposal() {
		return has_disposal;
	}

	public void setHas_disposal(String has_disposal) {
		this.has_disposal = has_disposal;
	}

	public String getHas_deviceType() {
		return has_deviceType;
	}

	public void setHas_deviceType(String has_deviceType) {
		this.has_deviceType = has_deviceType;
	}

	public String getHas_deviceName() {
		return has_deviceName;
	}

	public void setHas_deviceName(String has_deviceName) {
		this.has_deviceName = has_deviceName;
	}

	public String getHas_area() {
		return has_area;
	}

	public void setHas_area(String has_area) {
		this.has_area = has_area;
	}

	public String getHas_company() {
		return has_company;
	}

	public void setHas_company(String has_company) {
		this.has_company = has_company;
	}

	public String getHas_memo() {
		return has_memo;
	}

	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}

}
