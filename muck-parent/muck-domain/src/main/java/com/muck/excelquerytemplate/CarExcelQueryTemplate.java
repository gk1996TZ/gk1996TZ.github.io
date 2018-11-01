package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 车辆Excel请求模版参数
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月25日 下午7:27:57
 */
public class CarExcelQueryTemplate extends BaseExcelQueryTemplate {

	/** 序号 */
	@ExcelTemplate(name = "序号")
	private String has_carId;

	/** 车牌号 */
	@ExcelTemplate(name = "车辆号牌号码")
	private String has_carCode;

	@ExcelTemplate(name = "车牌颜色")
	private String has_carCardColor;

	/** 车主姓名 */
	@ExcelTemplate(name = "列表_驾驶员")
	private String has_carDrivers;

	/** 驾驶人员 */
	@ExcelTemplate(name = "驾驶人员")
	private String has_carDriverName;

	@ExcelTemplate(name = "联系电话")
	private String has_carDriverPhone;

	@ExcelTemplate(name = "身份证号")
	private String has_carDriverIdNumber;

	/** 车辆所属人 */
	@ExcelTemplate(name = "车辆所有人")
	private String has_carOwnerOfVehicle;

	/** 车辆品牌 */
	@ExcelTemplate(name = "品牌型号")
	private String has_carTradeMark;

	@ExcelTemplate(name = "所属区域ID")
	private String has_areaId;

	/** 车辆所属区域 */
	@ExcelTemplate(name = "所属区域")
	private String has_areaName;

	/** 车辆所属车辆组 */
	@ExcelTemplate(name = "所属车辆组")
	private String has_carGroupName;

	/** 车辆类别 */
	@ExcelTemplate(name = "车辆类型")
	private String has_carType;

	/** 车辆颜色 */
	@ExcelTemplate(name = "车辆颜色")
	private String has_carColor;

	/** 车辆图片 */
	@ExcelTemplate(name = "车辆图片")
	private String has_carImagePath;

	@ExcelTemplate(name = "所属企业ID")
	private String has_companyId;
	/** 车辆所属企业 */
	@ExcelTemplate(name = "所属企业")
	private String has_companyName;

	/** 车辆所属企业负责人 */
	@ExcelTemplate(name = "企业负责人")
	private String has_companyPrincipalName;

	/** 车辆所属企业负责人联系方式 */
	@ExcelTemplate(name = "企业负责人联系方式")
	private String has_companyPrincipalPhone;

	/** 企业联系方式 */
	@ExcelTemplate(name = "企业联系方式")
	private String has_companyContact;

	/** GPS机号 */
	@ExcelTemplate(name = "GPS机号")
	private String has_carGpsMachineNumber;

	/** 车型号 */
	@ExcelTemplate(name = "车辆型号")
	private String has_carVehicleModel;

	/** 车辆发动机号码 */
	@ExcelTemplate(name = "发动机号码")
	private String has_carEngineNumber;

	/** 车辆使用性质 */
	@ExcelTemplate(name = "使用性质")
	private String has_carUseNature;

	/** 车辆识别代码 */
	@ExcelTemplate(name = "车辆识别代号")
	private String has_carWpmi;

	/** 强制报废期止 */
	@ExcelTemplate(name = "强制报废期止")
	private String has_forceScrap;

	/** 运输车辆截至日期 */
	@ExcelTemplate(name = "运输车辆截至日期")
	private String has_carClosingDate;

	/*** 核定载质量 单位：吨 */
	@ExcelTemplate(name = "核定载质量")
	private String has_vouchPayload;

	/** 外廓尺寸 */
	@ExcelTemplate(name = "外廓尺寸")
	private String has_outlineSize;

	/** 品牌型号 */
	@ExcelTemplate(name = "品牌型号")
	private String has_brandModel;

	/** 注册登记日期 */
	@ExcelTemplate(name = "注册登记日期")
	private String has_registrationDate;

	/** 道路运输证号 */
	@ExcelTemplate(name = "道路运输证号")
	private String has_roadTransportLicenseNumber;

	/** 发证日期 */
	@ExcelTemplate(name = "发证日期")
	private String has_certificateDate;

	/** 密闭装置 */
	@ExcelTemplate(name = "密闭装置")
	private String has_obturator;

	/** 卫星定位系统 */
	@ExcelTemplate(name = "卫星定位系统、行车记录仪")
	private String has_cargps;

	/** 安装顶灯和具有反光功能的放大号牌 */
	@ExcelTemplate(name = "安装顶灯和具有反光功能的放大号牌")
	private String has_reflectLightNumber;

	/** 喷印企业的名称 */
	@ExcelTemplate(name = "喷印企业的名称、标志、编号")
	private String has_jetPrintingCompanyName;

	/** 粘贴反光标贴 */
	@ExcelTemplate(name = "粘贴反光标贴")
	private String has_pasteReflectStickers;

	/** 安装自喷淋系统 */
	@ExcelTemplate(name = "安装自喷淋降尘系统")
	private String has_installSpraySystem;

	/** 车辆组ID */
	@ExcelTemplate(name = "车辆组ID")
	private String has_carGroupId;

	/** 车辆是否锁定 */
	@ExcelTemplate(name = "车辆是否锁定")
	private String has_isLock;

	/** 车辆是否运行 */
	@ExcelTemplate(name = "车辆是否运行")
	private String has_isRunning;

	/** 备注 */
	@ExcelTemplate(name = "备注")
	private String has_memo;

	/** 电子围栏id */
	@ExcelTemplate(name = "电子围栏id")
	private String has_electricFenceId;

	/** 电子围栏名称 */
	@ExcelTemplate(name = "电子围栏名称")
	private String has_electricFenceName;

	public String getHas_electricFenceName() {
		return has_electricFenceName;
	}

	public void setHas_electricFenceName(String has_electricFenceName) {
		this.has_electricFenceName = has_electricFenceName;
	}

	public String getHas_electricFenceId() {
		return has_electricFenceId;
	}

	public void setHas_electricFenceId(String has_electricFenceId) {
		this.has_electricFenceId = has_electricFenceId;
	}

	public String getHas_isLock() {
		return has_isLock;
	}

	public void setHas_isLock(String has_isLock) {
		this.has_isLock = has_isLock;
	}

	public String getHas_isRunning() {
		return has_isRunning;
	}

	public void setHas_isRunning(String has_isRunning) {
		this.has_isRunning = has_isRunning;
	}

	public String getHas_carCode() {
		return has_carCode;
	}

	public String getHas_areaName() {
		return has_areaName;
	}

	public String getHas_carDriverName() {
		return has_carDriverName;
	}

	public void setHas_carDriverName(String has_carDriverName) {
		this.has_carDriverName = has_carDriverName;
	}

	public String getHas_carOwnerOfVehicle() {
		return has_carOwnerOfVehicle;
	}

	public void setHas_carOwnerOfVehicle(String has_carOwnerOfVehicle) {
		this.has_carOwnerOfVehicle = has_carOwnerOfVehicle;
	}

	public String getHas_carGroupId() {
		return has_carGroupId;
	}

	public void setHas_carGroupId(String has_carGroupId) {
		this.has_carGroupId = has_carGroupId;
	}

	public String getHas_carGroupName() {
		return has_carGroupName;
	}

	public String getHas_carType() {
		return has_carType;
	}

	public void setHas_carType(String has_carType) {
		this.has_carType = has_carType;
	}

	public String getHas_areaId() {
		return has_areaId;
	}

	public void setHas_areaId(String has_areaId) {
		this.has_areaId = has_areaId;
	}

	public String getHas_carColor() {
		return has_carColor;
	}

	public void setHas_carColor(String has_carColor) {
		this.has_carColor = has_carColor;
	}

	public String getHas_companyContact() {
		return has_companyContact;
	}

	public void setHas_companyContact(String has_companyContact) {
		this.has_companyContact = has_companyContact;
	}

	public String getHas_companyId() {
		return has_companyId;
	}

	public void setHas_companyId(String has_companyId) {
		this.has_companyId = has_companyId;
	}

	public String getHas_carImagePath() {
		return has_carImagePath;
	}

	public void setHas_carImagePath(String has_carImagePath) {
		this.has_carImagePath = has_carImagePath;
	}

	public String getHas_carId() {
		return has_carId;
	}

	public void setHas_carId(String has_carId) {
		this.has_carId = has_carId;
	}

	public String getHas_carDriverPhone() {
		return has_carDriverPhone;
	}

	public void setHas_carDriverPhone(String has_carDriverPhone) {
		this.has_carDriverPhone = has_carDriverPhone;
	}

	public String getHas_carTradeMark() {
		return has_carTradeMark;
	}

	public void setHas_carTradeMark(String has_carTradeMark) {
		this.has_carTradeMark = has_carTradeMark;
	}

	public String getHas_companyName() {
		return has_companyName;
	}

	public String getHas_companyPrincipalName() {
		return has_companyPrincipalName;
	}

	public String getHas_companyPrincipalPhone() {
		return has_companyPrincipalPhone;
	}

	public String getHas_carGpsMachineNumber() {
		return has_carGpsMachineNumber;
	}

	public String getHas_carVehicleModel() {
		return has_carVehicleModel;
	}

	public String getHas_carEngineNumber() {
		return has_carEngineNumber;
	}

	public String getHas_carUseNature() {
		return has_carUseNature;
	}

	public String getHas_carWpmi() {
		return has_carWpmi;
	}

	public String getHas_vouchPayload() {
		return has_vouchPayload;
	}

	public String getHas_outlineSize() {
		return has_outlineSize;
	}

	public String getHas_brandModel() {
		return has_brandModel;
	}

	public String getHas_roadTransportLicenseNumber() {
		return has_roadTransportLicenseNumber;
	}

	public String getHas_obturator() {
		return has_obturator;
	}

	public String getHas_cargps() {
		return has_cargps;
	}

	public String getHas_reflectLightNumber() {
		return has_reflectLightNumber;
	}

	public String getHas_jetPrintingCompanyName() {
		return has_jetPrintingCompanyName;
	}

	public String getHas_pasteReflectStickers() {
		return has_pasteReflectStickers;
	}

	public String getHas_installSpraySystem() {
		return has_installSpraySystem;
	}

	public String getHas_memo() {
		return has_memo;
	}

	public void setHas_carCode(String has_carCode) {
		this.has_carCode = has_carCode;
	}

	public String getHas_carDrivers() {
		return has_carDrivers;
	}

	public void setHas_carDrivers(String has_carDrivers) {
		this.has_carDrivers = has_carDrivers;
	}

	public void setHas_areaName(String has_areaName) {
		this.has_areaName = has_areaName;
	}

	public void setHas_carGroupName(String has_carGroupName) {
		this.has_carGroupName = has_carGroupName;
	}

	public void setHas_companyName(String has_companyName) {
		this.has_companyName = has_companyName;
	}

	public void setHas_companyPrincipalName(String has_companyPrincipalName) {
		this.has_companyPrincipalName = has_companyPrincipalName;
	}

	public void setHas_companyPrincipalPhone(String has_companyPrincipalPhone) {
		this.has_companyPrincipalPhone = has_companyPrincipalPhone;
	}

	public void setHas_carGpsMachineNumber(String has_carGpsMachineNumber) {
		this.has_carGpsMachineNumber = has_carGpsMachineNumber;
	}

	public void setHas_carVehicleModel(String has_carVehicleModel) {
		this.has_carVehicleModel = has_carVehicleModel;
	}

	public void setHas_carEngineNumber(String has_carEngineNumber) {
		this.has_carEngineNumber = has_carEngineNumber;
	}

	public void setHas_carUseNature(String has_carUseNature) {
		this.has_carUseNature = has_carUseNature;
	}

	public void setHas_carWpmi(String has_carWpmi) {
		this.has_carWpmi = has_carWpmi;
	}

	public void setHas_vouchPayload(String has_vouchPayload) {
		this.has_vouchPayload = has_vouchPayload;
	}

	public void setHas_outlineSize(String has_outlineSize) {
		this.has_outlineSize = has_outlineSize;
	}

	public void setHas_brandModel(String has_brandModel) {
		this.has_brandModel = has_brandModel;
	}

	public void setHas_roadTransportLicenseNumber(String has_roadTransportLicenseNumber) {
		this.has_roadTransportLicenseNumber = has_roadTransportLicenseNumber;
	}

	public void setHas_obturator(String has_obturator) {
		this.has_obturator = has_obturator;
	}

	public void setHas_cargps(String has_cargps) {
		this.has_cargps = has_cargps;
	}

	public void setHas_reflectLightNumber(String has_reflectLightNumber) {
		this.has_reflectLightNumber = has_reflectLightNumber;
	}

	public void setHas_jetPrintingCompanyName(String has_jetPrintingCompanyName) {
		this.has_jetPrintingCompanyName = has_jetPrintingCompanyName;
	}

	public void setHas_pasteReflectStickers(String has_pasteReflectStickers) {
		this.has_pasteReflectStickers = has_pasteReflectStickers;
	}

	public void setHas_installSpraySystem(String has_installSpraySystem) {
		this.has_installSpraySystem = has_installSpraySystem;
	}

	public String getHas_forceScrap() {
		return has_forceScrap;
	}

	public void setHas_forceScrap(String has_forceScrap) {
		this.has_forceScrap = has_forceScrap;
	}

	public String getHas_registrationDate() {
		return has_registrationDate;
	}

	public void setHas_registrationDate(String has_registrationDate) {
		this.has_registrationDate = has_registrationDate;
	}

	public String getHas_certificateDate() {
		return has_certificateDate;
	}

	public void setHas_certificateDate(String has_certificateDate) {
		this.has_certificateDate = has_certificateDate;
	}

	public String getHas_carClosingDate() {
		return has_carClosingDate;
	}

	public void setHas_carClosingDate(String has_carClosingDate) {
		this.has_carClosingDate = has_carClosingDate;
	}

	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}

	public String getHas_carCardColor() {
		return has_carCardColor;
	}

	public void setHas_carCardColor(String has_carCardColor) {
		this.has_carCardColor = has_carCardColor;
	}

	public String getHas_carDriverIdNumber() {
		return has_carDriverIdNumber;
	}

	public void setHas_carDriverIdNumber(String has_carDriverIdNumber) {
		this.has_carDriverIdNumber = has_carDriverIdNumber;
	}
}
