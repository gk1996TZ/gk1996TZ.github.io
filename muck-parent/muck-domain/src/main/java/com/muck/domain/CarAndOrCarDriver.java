package com.muck.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description: 车辆驾驶员信息
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年8月16日 上午11:52:51
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CarAndOrCarDriver extends BaseEntity{

	/**车辆所有人*/
	private String carOwnerOfVehicle;
	/**车牌号*/
	private String carCode;
	/**发动机号码*/
	private String carEngineNumber;
	/**车辆类型*/
	private String carType;
	/**车辆识别代号*/
	private String carWpmi;
	/**使用性质*/
	private String carUseNature;
	/**核定载质量*/
	private String vouchPayload;
	/**外廓尺寸*/
	private String outlineSize;
	/**品牌型号*/
	private String carTradeMark;
	/**注册登记日期*/
	private Date registrationDate;
	/**强制报废期止*/
	private Date forceScrap;
	/**道路运输证号*/
	private String roadTransportLicenseNumber;
	/**车牌颜色*/
	private String carCardColor;
	/**所属车辆组*/
	private String carGroupName;
	/**驾驶人员*/
	private String driverName;
	/**驾驶员联系电话*/
	private String driverPhone;
	/**性别*/
	private String driverSex;
	/**驾驶员出生年月*/
	private Date driverBirth;
	/**民族*/
	private String driverNation;
	/**婚否*/
	private String isMarry;
	/**学历*/
	private String driverEducation;
	/**身份证号*/
	private String idNumber;
	/**身份证地址*/
	private String idNumberAddress;
	/**现住址*/
	private String liveAddress;
	/**道路运输从业人员从业资格证*/
	private String qualificationCertificate;
	/**驾驶证档案编号*/
	private String driverLicenseFileNumber;
	/**家庭成员*/
	private List<CarDriverFamilyMember> carDriverFamilyMembers;
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarTradeMark() {
		return carTradeMark;
	}
	public void setCarTradeMark(String carTradeMark) {
		this.carTradeMark = carTradeMark;
	}
	public String getCarCardColor() {
		return carCardColor;
	}
	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}
	public String getCarWpmi() {
		return carWpmi;
	}
	public void setCarWpmi(String carWpmi) {
		this.carWpmi = carWpmi;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarGroupName() {
		return carGroupName;
	}
	public void setCarGroupName(String carGroupName) {
		this.carGroupName = carGroupName;
	}
	public String getCarUseNature() {
		return carUseNature;
	}
	public void setCarUseNature(String carUseNature) {
		this.carUseNature = carUseNature;
	}
	public String getCarEngineNumber() {
		return carEngineNumber;
	}
	public void setCarEngineNumber(String carEngineNumber) {
		this.carEngineNumber = carEngineNumber;
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
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getForceScrap() {
		return forceScrap;
	}
	public void setForceScrap(Date forceScrap) {
		this.forceScrap = forceScrap;
	}
	public String getDriverSex() {
		return driverSex;
	}
	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}
	public Date getDriverBirth() {
		return driverBirth;
	}
	public void setDriverBirth(Date driverBirth) {
		this.driverBirth = driverBirth;
	}
	public String getDriverNation() {
		return driverNation;
	}
	public void setDriverNation(String driverNation) {
		this.driverNation = driverNation;
	}
	public String getIsMarry() {
		return isMarry;
	}
	public void setIsMarry(String isMarry) {
		this.isMarry = isMarry;
	}
	public String getRoadTransportLicenseNumber() {
		return roadTransportLicenseNumber;
	}
	public void setRoadTransportLicenseNumber(String roadTransportLicenseNumber) {
		this.roadTransportLicenseNumber = roadTransportLicenseNumber;
	}
	public String getCarOwnerOfVehicle() {
		return carOwnerOfVehicle;
	}
	public void setCarOwnerOfVehicle(String carOwnerOfVehicle) {
		this.carOwnerOfVehicle = carOwnerOfVehicle;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIdNumberAddress() {
		return idNumberAddress;
	}
	public void setIdNumberAddress(String idNumberAddress) {
		this.idNumberAddress = idNumberAddress;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public String getQualificationCertificate() {
		return qualificationCertificate;
	}
	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}
	public String getDriverLicenseFileNumber() {
		return driverLicenseFileNumber;
	}
	public void setDriverLicenseFileNumber(String driverLicenseFileNumber) {
		this.driverLicenseFileNumber = driverLicenseFileNumber;
	}
	public String getDriverEducation() {
		return driverEducation;
	}
	public void setDriverEducation(String driverEducation) {
		this.driverEducation = driverEducation;
	}
	public List<CarDriverFamilyMember> getCarDriverFamilyMembers() {
		return carDriverFamilyMembers;
	}
	public void setCarDriverFamilyMembers(List<CarDriverFamilyMember> carDriverFamilyMembers) {
		this.carDriverFamilyMembers = carDriverFamilyMembers;
	}
	@Override
	public String toString() {
		return "CarAndOrCarDriver [carOwnerOfVehicle=" + carOwnerOfVehicle + ", carCode=" + carCode
				+ ", carEngineNumber=" + carEngineNumber + ", carType=" + carType + ", carWpmi=" + carWpmi
				+ ", carUseNature=" + carUseNature + ", vouchPayload=" + vouchPayload + ", outlineSize=" + outlineSize
				+ ", carTradeMark=" + carTradeMark + ", registrationDate=" + registrationDate + ", forceScrap="
				+ forceScrap + ", roadTransportLicenseNumber=" + roadTransportLicenseNumber + ", carCardColor="
				+ carCardColor + ", carGroupName=" + carGroupName + ", driverName=" + driverName + ", driverPhone="
				+ driverPhone + ", driverSex=" + driverSex + ", driverBirth=" + driverBirth + ", driverNation="
				+ driverNation + ", isMarry=" + isMarry + ", driverEducation=" + driverEducation + ", idNumber="
				+ idNumber + ", idNumberAddress=" + idNumberAddress + ", liveAddress=" + liveAddress
				+ ", qualificationCertificate=" + qualificationCertificate + ", driverLicenseFileNumber="
				+ driverLicenseFileNumber + ", carDriverFamilyMembers=" + carDriverFamilyMembers + "]";
	}
}
