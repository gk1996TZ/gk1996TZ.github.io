package com.muck.domain;

import java.util.Date;
import java.util.List;

import com.muck.annotation.Table;

/**
* @Description: 车辆实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午1:26:58
*/
@Table(name = "t_car")
public class Car extends BaseEntity{
	
	// 车辆序号
	private String carId;
	
	// 车牌号
	private String carCode;
	
	// 车牌颜色
	private String carCardColor;
	
	// 驾驶人员姓名
	private String carDriverName;
	
	// 驾驶人员联系电话
	private String carDriverPhone;

	// 驾驶人员身份证号
	private String carDriverIdNumber;
	
	//车辆所属人
	private String carOwnerOfVehicle;
	
	//车辆品牌
	private String carTradeMark;
	
	
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
	
	// 企业联系方式
	private String companyContact;
	
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
	
	// 车辆类型
	private String carType;
	
	// 车辆图片路径
	private String carImagePath;
	
	// 车辆颜色
	private String carColor;
	
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
		
	// 卫星定位系统、行车记录仪
	private String cargps;
	
	// 安装顶灯和具有反光功能的放大号牌
	private String reflectLightNumber;
	
	// 喷印企业的名称、标志、编号
	private String jetPrintingCompanyName;
	
	// 粘贴反光标贴
	private String pasteReflectStickers;
	
	// 安装自喷淋系统
	private String installSpraySystem;
	
	// 车辆驾驶员列表
	private List<CarDriver> carDrivers;
	
	// 电子围栏id
	private String electricFenceId;
	
	//电子围栏名称
	private String electricFenceName;
	
	
	
	// 是否锁车,默认不锁车(1:不锁车, 0 : 锁车)
	private boolean isLock = true;
	
	// 是否运行,默认运行(1:运行,0:不运行)
	private boolean isRunning = true;

	// 说明
	private String memo;
	
	public String getElectricFenceId() {
		return electricFenceId;
	}

	public void setElectricFenceId(String electricFenceId) {
		this.electricFenceId = electricFenceId;
	}

	public String getElectricFenceName() {
		return electricFenceName;
	}

	public void setElectricFenceName(String electricFenceName) {
		this.electricFenceName = electricFenceName;
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

	public String getCarDriverName() {
		return carDriverName;
	}

	public void setCarDriverName(String carDriverName) {
		this.carDriverName = carDriverName;
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

	public String getCarTradeMark() {
		return carTradeMark;
	}

	public void setCarTradeMark(String carTradeMark) {
		this.carTradeMark = carTradeMark;
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

	public Date getCarClosingDate() {
		return carClosingDate;
	}

	public void setCarClosingDate(Date carClosingDate) {
		this.carClosingDate = carClosingDate;
	}

	public List<CarDriver> getCarDrivers() {
		return carDrivers;
	}

	public void setCarDrivers(List<CarDriver> carDrivers) {
		this.carDrivers = carDrivers;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
	
	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCarVehicleModel(String carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarImagePath() {
		return carImagePath;
	}

	public void setCarImagePath(String carImagePath) {
		this.carImagePath = carImagePath;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
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

	public String getCarCardColor() {
		return carCardColor;
	}

	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}

	public String getCarDriverIdNumber() {
		return carDriverIdNumber;
	}

	public void setCarDriverIdNumber(String carDriverIdNumber) {
		this.carDriverIdNumber = carDriverIdNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result + ((brandModel == null) ? 0 : brandModel.hashCode());
		result = prime * result + ((carCardColor == null) ? 0 : carCardColor.hashCode());
		result = prime * result + ((carClosingDate == null) ? 0 : carClosingDate.hashCode());
		result = prime * result + ((carCode == null) ? 0 : carCode.hashCode());
		result = prime * result + ((carColor == null) ? 0 : carColor.hashCode());
		result = prime * result + ((carDriverIdNumber == null) ? 0 : carDriverIdNumber.hashCode());
		result = prime * result + ((carDriverName == null) ? 0 : carDriverName.hashCode());
		result = prime * result + ((carDriverPhone == null) ? 0 : carDriverPhone.hashCode());
		result = prime * result + ((carDrivers == null) ? 0 : carDrivers.hashCode());
		result = prime * result + ((carEngineNumber == null) ? 0 : carEngineNumber.hashCode());
		result = prime * result + ((carGpsMachineNumber == null) ? 0 : carGpsMachineNumber.hashCode());
		result = prime * result + ((carGroupId == null) ? 0 : carGroupId.hashCode());
		result = prime * result + ((carGroupName == null) ? 0 : carGroupName.hashCode());
		result = prime * result + ((carId == null) ? 0 : carId.hashCode());
		result = prime * result + ((carImagePath == null) ? 0 : carImagePath.hashCode());
		result = prime * result + ((carOwnerOfVehicle == null) ? 0 : carOwnerOfVehicle.hashCode());
		result = prime * result + ((carTradeMark == null) ? 0 : carTradeMark.hashCode());
		result = prime * result + ((carType == null) ? 0 : carType.hashCode());
		result = prime * result + ((carUseNature == null) ? 0 : carUseNature.hashCode());
		result = prime * result + ((carVehicleModel == null) ? 0 : carVehicleModel.hashCode());
		result = prime * result + ((carWpmi == null) ? 0 : carWpmi.hashCode());
		result = prime * result + ((cargps == null) ? 0 : cargps.hashCode());
		result = prime * result + ((certificateDate == null) ? 0 : certificateDate.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((companyContact == null) ? 0 : companyContact.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((companyPrincipalName == null) ? 0 : companyPrincipalName.hashCode());
		result = prime * result + ((companyPrincipalPhone == null) ? 0 : companyPrincipalPhone.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((electricFenceId == null) ? 0 : electricFenceId.hashCode());
		result = prime * result + ((electricFenceName == null) ? 0 : electricFenceName.hashCode());
		result = prime * result + ((forceScrap == null) ? 0 : forceScrap.hashCode());
		result = prime * result + ((installSpraySystem == null) ? 0 : installSpraySystem.hashCode());
		result = prime * result + (isLock ? 1231 : 1237);
		result = prime * result + (isRunning ? 1231 : 1237);
		result = prime * result + ((jetPrintingCompanyName == null) ? 0 : jetPrintingCompanyName.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((obturator == null) ? 0 : obturator.hashCode());
		result = prime * result + ((outlineSize == null) ? 0 : outlineSize.hashCode());
		result = prime * result + ((pasteReflectStickers == null) ? 0 : pasteReflectStickers.hashCode());
		result = prime * result + ((reflectLightNumber == null) ? 0 : reflectLightNumber.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((roadTransportLicenseNumber == null) ? 0 : roadTransportLicenseNumber.hashCode());
		result = prime * result + ((vouchPayload == null) ? 0 : vouchPayload.hashCode());
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
		Car other = (Car) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (brandModel == null) {
			if (other.brandModel != null)
				return false;
		} else if (!brandModel.equals(other.brandModel))
			return false;
		if (carCardColor == null) {
			if (other.carCardColor != null)
				return false;
		} else if (!carCardColor.equals(other.carCardColor))
			return false;
		if (carClosingDate == null) {
			if (other.carClosingDate != null)
				return false;
		} else if (!carClosingDate.equals(other.carClosingDate))
			return false;
		if (carCode == null) {
			if (other.carCode != null)
				return false;
		} else if (!carCode.equals(other.carCode))
			return false;
		if (carColor == null) {
			if (other.carColor != null)
				return false;
		} else if (!carColor.equals(other.carColor))
			return false;
		if (carDriverIdNumber == null) {
			if (other.carDriverIdNumber != null)
				return false;
		} else if (!carDriverIdNumber.equals(other.carDriverIdNumber))
			return false;
		if (carDriverName == null) {
			if (other.carDriverName != null)
				return false;
		} else if (!carDriverName.equals(other.carDriverName))
			return false;
		if (carDriverPhone == null) {
			if (other.carDriverPhone != null)
				return false;
		} else if (!carDriverPhone.equals(other.carDriverPhone))
			return false;
		if (carDrivers == null) {
			if (other.carDrivers != null)
				return false;
		} else if (!carDrivers.equals(other.carDrivers))
			return false;
		if (carEngineNumber == null) {
			if (other.carEngineNumber != null)
				return false;
		} else if (!carEngineNumber.equals(other.carEngineNumber))
			return false;
		if (carGpsMachineNumber == null) {
			if (other.carGpsMachineNumber != null)
				return false;
		} else if (!carGpsMachineNumber.equals(other.carGpsMachineNumber))
			return false;
		if (carGroupId == null) {
			if (other.carGroupId != null)
				return false;
		} else if (!carGroupId.equals(other.carGroupId))
			return false;
		if (carGroupName == null) {
			if (other.carGroupName != null)
				return false;
		} else if (!carGroupName.equals(other.carGroupName))
			return false;
		if (carId == null) {
			if (other.carId != null)
				return false;
		} else if (!carId.equals(other.carId))
			return false;
		if (carImagePath == null) {
			if (other.carImagePath != null)
				return false;
		} else if (!carImagePath.equals(other.carImagePath))
			return false;
		if (carOwnerOfVehicle == null) {
			if (other.carOwnerOfVehicle != null)
				return false;
		} else if (!carOwnerOfVehicle.equals(other.carOwnerOfVehicle))
			return false;
		if (carTradeMark == null) {
			if (other.carTradeMark != null)
				return false;
		} else if (!carTradeMark.equals(other.carTradeMark))
			return false;
		if (carType == null) {
			if (other.carType != null)
				return false;
		} else if (!carType.equals(other.carType))
			return false;
		if (carUseNature == null) {
			if (other.carUseNature != null)
				return false;
		} else if (!carUseNature.equals(other.carUseNature))
			return false;
		if (carVehicleModel == null) {
			if (other.carVehicleModel != null)
				return false;
		} else if (!carVehicleModel.equals(other.carVehicleModel))
			return false;
		if (carWpmi == null) {
			if (other.carWpmi != null)
				return false;
		} else if (!carWpmi.equals(other.carWpmi))
			return false;
		if (cargps == null) {
			if (other.cargps != null)
				return false;
		} else if (!cargps.equals(other.cargps))
			return false;
		if (certificateDate == null) {
			if (other.certificateDate != null)
				return false;
		} else if (!certificateDate.equals(other.certificateDate))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (companyContact == null) {
			if (other.companyContact != null)
				return false;
		} else if (!companyContact.equals(other.companyContact))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (companyPrincipalName == null) {
			if (other.companyPrincipalName != null)
				return false;
		} else if (!companyPrincipalName.equals(other.companyPrincipalName))
			return false;
		if (companyPrincipalPhone == null) {
			if (other.companyPrincipalPhone != null)
				return false;
		} else if (!companyPrincipalPhone.equals(other.companyPrincipalPhone))
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (electricFenceId == null) {
			if (other.electricFenceId != null)
				return false;
		} else if (!electricFenceId.equals(other.electricFenceId))
			return false;
		if (electricFenceName == null) {
			if (other.electricFenceName != null)
				return false;
		} else if (!electricFenceName.equals(other.electricFenceName))
			return false;
		if (forceScrap == null) {
			if (other.forceScrap != null)
				return false;
		} else if (!forceScrap.equals(other.forceScrap))
			return false;
		if (installSpraySystem == null) {
			if (other.installSpraySystem != null)
				return false;
		} else if (!installSpraySystem.equals(other.installSpraySystem))
			return false;
		if (isLock != other.isLock)
			return false;
		if (isRunning != other.isRunning)
			return false;
		if (jetPrintingCompanyName == null) {
			if (other.jetPrintingCompanyName != null)
				return false;
		} else if (!jetPrintingCompanyName.equals(other.jetPrintingCompanyName))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (obturator == null) {
			if (other.obturator != null)
				return false;
		} else if (!obturator.equals(other.obturator))
			return false;
		if (outlineSize == null) {
			if (other.outlineSize != null)
				return false;
		} else if (!outlineSize.equals(other.outlineSize))
			return false;
		if (pasteReflectStickers == null) {
			if (other.pasteReflectStickers != null)
				return false;
		} else if (!pasteReflectStickers.equals(other.pasteReflectStickers))
			return false;
		if (reflectLightNumber == null) {
			if (other.reflectLightNumber != null)
				return false;
		} else if (!reflectLightNumber.equals(other.reflectLightNumber))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (roadTransportLicenseNumber == null) {
			if (other.roadTransportLicenseNumber != null)
				return false;
		} else if (!roadTransportLicenseNumber.equals(other.roadTransportLicenseNumber))
			return false;
		if (vouchPayload == null) {
			if (other.vouchPayload != null)
				return false;
		} else if (!vouchPayload.equals(other.vouchPayload))
			return false;
		return true;
	}
}
