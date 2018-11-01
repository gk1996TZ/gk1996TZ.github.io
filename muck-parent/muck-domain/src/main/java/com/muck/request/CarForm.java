package com.muck.request;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.domain.Area;
import com.muck.domain.Company;
import com.muck.domain.Device;
import com.muck.response.StatusCode;

/**
 * @Description: 车辆Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月17日 下午5:49:28
 */
@Unique(uniqueFields={"car_code"},tableName="t_car")
public class CarForm extends BaseForm {

	private String carId;
	//车辆所属人 
	private String carOwnerOfVehicle;
	//车辆驾驶员姓名
	private String carDriverName;
	//驾驶员联系电话
	private String carDriverPhone;
	//驾驶员身份证号
	private String carDriverIdNumber;
	//车牌号
	private String carCode;
	//车牌颜色
	private String carCardColor;
	// 车辆类型
	private String carType;
	// 车辆颜色
	private String carColor;
	// 图片路径
	private String carImagePath;
	// 车辆所属区域
	private Area area;
	// 区域id
	private String areaId;
	// 车辆所属区域名称
	private String areaName;
	// 车辆所属企业
	private Company company ;
	// 企业id
	private String companyId;
	// 车辆所属企业名称
	private String companyName;
	// 企业负责人姓名
	private String companyPrincipalName;
	// 企业负责人联系方式
	private String companyPrincipalPhone;
	//车辆组id
	private String carGroupId;
	// 车辆组名称
	private String carGroupName;
	// 车辆gps机号
	private String carGpsMachineNumber;
	// 车辆型号
	private String carVehicleModel;
	// 车辆所包含的设备
	private List<Device> device;
	// 是否锁车,默认不锁车(1:不锁车, 0 : 锁车)
	private boolean isLock = true;
	// 是否运行,默认运行(1:运行,0:不运行)
	private boolean isRunning = true;
	// 车辆发动机号码
	private String carEngineNumber;
	// 车辆使用性质
	private String carUseNature;
	// 车辆识别代码
	private String carWpmi;
	// 强制报废期止
	private Date forceScrap;
	// 运输车辆截至日期
	private Date carClosingDate;
	// 核定载质量 单位：吨
	private String vouchPayload;
	// 外廓尺寸
	private String outlineSize;
	// 品牌型号
	private String brandModel;
	// 注册登记日期
	private Date registrationDate;
	// 道路运输证号
	private String roadTransportLicenseNumber;
	// 发证日期
	private Date certificateDate;
	// 密闭装置
	private String obturator;
	// 卫星定位系统，行车记录仪
	private String cargps;
	// 安装顶灯和具有反光功能的放大号牌
	private String reflectLightNumber;
	// 喷印企业的名称，标志，编号
	private String jetPrintingCompanyName;
	// 粘贴反光标贴
	private String pasteReflectStickers;
	// 安装自喷淋系统
	private String installSpraySystem;
	// 说明
	private String memo;
	// 参数校验
	public StatusCode validate() {
		StatusCode statusCode = null;
		if(StringUtils.isBlank(carCode)){
			statusCode = StatusCode.CAR_CODE_BLANK;
		}else if(StringUtils.isBlank(areaId)){
			statusCode = StatusCode.CAR_AREA_ID_BLANK;
		}else if(StringUtils.isBlank(companyId)){
			statusCode = StatusCode.CAR_COMPANY_ID_BLANK;
		}
		return statusCode;
	}
	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getCarImagePath() {
		return carImagePath;
	}
	public void setCarImagePath(String carImagePath) {
		this.carImagePath = carImagePath;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getCarDriverPhone() {
		return carDriverPhone;
	}
	public void setCarDriverPhone(String carDriverPhone) {
		this.carDriverPhone = carDriverPhone;
	}
	public String getCarOwnerOfVehicle() {
		return carOwnerOfVehicle;
	}
	public void setCarOwnerOfVehicle(String carOwnerOfVehicle) {
		this.carOwnerOfVehicle = carOwnerOfVehicle;
	}
	
	public String getCarDriverName() {
		return carDriverName;
	}
	public void setCarDriverName(String carDriverName) {
		this.carDriverName = carDriverName;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public String getCompanyPrincipalName() {
		return companyPrincipalName;
	}
	public void setCompanyPrincipalName(String companyPrincipalName) {
		this.companyPrincipalName = companyPrincipalName;
	}
	public String getCompanyPrincipalPhone() {
		return companyPrincipalPhone;
	}
	public void setCompanyPrincipalPhone(String companyPrincipalPhone) {
		this.companyPrincipalPhone = companyPrincipalPhone;
	}
	public String getCarGroupId() {
		return carGroupId;
	}
	public void setCarGroupId(String carGroupId) {
		this.carGroupId = carGroupId;
	}
	public String getCarGroupName() {
		return carGroupName;
	}
	public void setCarGroupName(String carGroupName) {
		this.carGroupName = carGroupName;
	}
	public String getCarGpsMachineNumber() {
		return carGpsMachineNumber;
	}
	public void setCarGpsMachineNumber(String carGpsMachineNumber) {
		this.carGpsMachineNumber = carGpsMachineNumber;
	}
	public String getCarVehicleModel() {
		return carVehicleModel;
	}
	public void setCarVehicleModel(String carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}
	public List<Device> getDevice() {
		return device;
	}
	public void setDevice(List<Device> device) {
		this.device = device;
	}
	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public String getCarEngineNumber() {
		return carEngineNumber;
	}
	public void setCarEngineNumber(String carEngineNumber) {
		this.carEngineNumber = carEngineNumber;
	}
	public String getCarUseNature() {
		return carUseNature;
	}
	public void setCarUseNature(String carUseNature) {
		this.carUseNature = carUseNature;
	}
	public String getCarWpmi() {
		return carWpmi;
	}
	public void setCarWpmi(String carWpmi) {
		this.carWpmi = carWpmi;
	}
	public Date getForceScrap() {
		return forceScrap;
	}
	public void setForceScrap(Date forceScrap) {
		this.forceScrap = forceScrap;
	}
	public String getVouchPayload() {
		return vouchPayload;
	}
	public void setVouchPayload(String vouchPayload) {
		this.vouchPayload = vouchPayload;
	}
	public String getOutlineSize() {
		return outlineSize;
	}
	public void setOutlineSize(String outlineSize) {
		this.outlineSize = outlineSize;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getCarClosingDate() {
		return carClosingDate;
	}
	public void setCarClosingDate(Date carClosingDate) {
		this.carClosingDate = carClosingDate;
	}
	public String getRoadTransportLicenseNumber() {
		return roadTransportLicenseNumber;
	}
	public void setRoadTransportLicenseNumber(String roadTransportLicenseNumber) {
		this.roadTransportLicenseNumber = roadTransportLicenseNumber;
	}
	public Date getCertificateDate() {
		return certificateDate;
	}
	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}
	public String getObturator() {
		return obturator;
	}
	public void setObturator(String obturator) {
		this.obturator = obturator;
	}
	public String getCargps() {
		return cargps;
	}
	public void setCargps(String cargps) {
		this.cargps = cargps;
	}
	public String getReflectLightNumber() {
		return reflectLightNumber;
	}
	public void setReflectLightNumber(String reflectLightNumber) {
		this.reflectLightNumber = reflectLightNumber;
	}
	public String getJetPrintingCompanyName() {
		return jetPrintingCompanyName;
	}
	public void setJetPrintingCompanyName(String jetPrintingCompanyName) {
		this.jetPrintingCompanyName = jetPrintingCompanyName;
	}
	public String getPasteReflectStickers() {
		return pasteReflectStickers;
	}
	public void setPasteReflectStickers(String pasteReflectStickers) {
		this.pasteReflectStickers = pasteReflectStickers;
	}
	public String getInstallSpraySystem() {
		return installSpraySystem;
	}
	public void setInstallSpraySystem(String installSpraySystem) {
		this.installSpraySystem = installSpraySystem;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCarDriverIdNumber() {
		return carDriverIdNumber;
	}
	public void setCarDriverIdNumber(String carDriverIdNumber) {
		this.carDriverIdNumber = carDriverIdNumber;
	}
	public String getCarCardColor() {
		return carCardColor;
	}
	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}
	@Override
	public Object[] validateUnique() {
		return new Object[]{carCode};
	}
}
