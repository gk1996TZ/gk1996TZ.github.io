package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

public class WarningExcelQueryTemplate extends BaseExcelQueryTemplate{

	// 类别
	@ExcelTemplate(name="类别")
	private String has_warningType;
	// 设备名称
	@ExcelTemplate(name="设备名称")
	private String has_deviceName;
	// 车牌号
	@ExcelTemplate(name="车牌号")
	private String has_carCode;
	// 告警信息
	@ExcelTemplate(name="告警信息")
	private String has_warningContent;
	// 告警时间
	@ExcelTemplate(name="告警时间")
	private String has_warningTime;
	// 告警地址
	@ExcelTemplate(name="告警地址")
	private String has_warningAddress;
	// 处理状态
	@ExcelTemplate(name="处理状态")
	private String has_isHandle;
	public WarningExcelQueryTemplate(String has_warningType, String has_deviceName, String has_carCode,
			String has_warningContent, String has_warningTime, String has_warningAddress, String has_isHandle) {
		super();
		this.has_warningType = has_warningType;
		this.has_deviceName = has_deviceName;
		this.has_carCode = has_carCode;
		this.has_warningContent = has_warningContent;
		this.has_warningTime = has_warningTime;
		this.has_warningAddress = has_warningAddress;
		this.has_isHandle = has_isHandle;
	}
	public String getHas_warningType() {
		return has_warningType;
	}
	public void setHas_warningType(String has_warningType) {
		this.has_warningType = has_warningType;
	}
	public String getHas_deviceName() {
		return has_deviceName;
	}
	public void setHas_deviceName(String has_deviceName) {
		this.has_deviceName = has_deviceName;
	}
	public String getHas_carCode() {
		return has_carCode;
	}
	public void setHas_carCode(String has_carCode) {
		this.has_carCode = has_carCode;
	}
	public String getHas_warningContent() {
		return has_warningContent;
	}
	public void setHas_warningContent(String has_warningContent) {
		this.has_warningContent = has_warningContent;
	}
	public String getHas_warningTime() {
		return has_warningTime;
	}
	public void setHas_warningTime(String has_warningTime) {
		this.has_warningTime = has_warningTime;
	}
	public String getHas_warningAddress() {
		return has_warningAddress;
	}
	public void setHas_warningAddress(String has_warningAddress) {
		this.has_warningAddress = has_warningAddress;
	}
	public String getHas_isHandle() {
		return has_isHandle;
	}
	public void setHas_isHandle(String has_isHandle) {
		this.has_isHandle = has_isHandle;
	}
	
}
