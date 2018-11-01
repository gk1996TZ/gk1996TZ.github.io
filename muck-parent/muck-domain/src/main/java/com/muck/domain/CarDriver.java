package com.muck.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * 	车辆驾驶员表
*/
@Table(name = "t_car_driver")
public class CarDriver extends BaseEntity{

	// 驾驶员序号
	private String driverId;
	// 驾驶员名称
	private String driverName;

	// 驾驶员性别
	private String driverSex;

	// 驾驶员出生年月
	private String driverBirth;

	// 是否结婚
	private String isMarry;

	// 驾驶员所属民族
	private String driverNation;

	// 驾驶员学历
	private String driverEducation;

	// 驾驶员身份证号
	private String idNumber;

	// 驾驶员身份证地址
	private String idNumberAddress;

	// 道路运输从业人员从业资格证
	private String qualificationCertificate;
	
	// 驾驶证档案编号
	private String driverLicenseFileNumber;

	// 车辆id
	private String carId;
	
	// 驾驶员车牌号
	private String carCode;

	// 头像
	private String headPath;

	// 现居住地址
	private String liveAddress;

	// 驾驶员联系方式
	private String driverPhone;

	// 驾驶员工作单位(企业)id
	private String companyId;

	// 驾驶员工作单位
	private String companyName;

	// 个人简介
	private String resume;

	// 状态类型(1:在职 2:辞职 3:被辞退)
	private Integer statusType;

	// 原因说明
	private String reason;

	// 说明描述
	private String memo;

	// 所属模块
	private String operatorModel;
	// 年龄
	private String driverAge;
	// 姓名
	private String name;
	// 关系
	private String relation;
	// 联系电话
	private String phone;
	// 工作单位
	private String workUnit;
	// 名称
	private String familyName1;
	// 联系方式
	private String familyPhone1;
	// 家庭关系
	private String familyRelation1;
	// 工作单位
	private String familyWorkUnit1;

	// 姓名
	private String familyName2;

	// 联系方式
	private String familyPhone2;

	// 家庭关系
	private String familyRelation2;

	// 工作单位
	private String familyWorkUnit2;

	// 姓名
	private String familyName3;

	// 联系方式
	private String familyPhone3;

	// 家庭关系
	private String familyRelation3;

	// 工作单位
	private String familyWorkUnit3;

	// 姓名
	private String familyName4;

	// 联系方式
	private String familyPhone4;

	// 家庭关系
	private String familyRelation4;

	// 工作单位
	private String familyWorkUnit4;
	
    
    // 关联的家庭成员
    private List<CarDriverFamilyMember> carDriverFamilyMembers;

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverSex() {
		return driverSex;
	}

	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}

	public String getDriverBirth() {
		return driverBirth;
	}

	public void setDriverBirth(String driverBirth) {
		this.driverBirth = driverBirth;
	}

	public String getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(String isMarry) {
		this.isMarry = isMarry;
	}

	public String getDriverNation() {
		return driverNation;
	}

	public void setDriverNation(String driverNation) {
		this.driverNation = driverNation;
	}

	public String getDriverEducation() {
		return driverEducation;
	}

	public void setDriverEducation(String driverEducation) {
		this.driverEducation = driverEducation;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getDriverLicenseFileNumber() {
		return driverLicenseFileNumber;
	}

	public void setDriverLicenseFileNumber(String driverLicenseFileNumber) {
		this.driverLicenseFileNumber = driverLicenseFileNumber;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getStatusType() {
		return statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<CarDriverFamilyMember> getCarDriverFamilyMembers() {
		return carDriverFamilyMembers;
	}

	public void setCarDriverFamilyMembers(List<CarDriverFamilyMember> carDriverFamilyMembers) {
		this.carDriverFamilyMembers = carDriverFamilyMembers;
	}

	public String getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(String operatorModel) {
		this.operatorModel = operatorModel;
	}

	public String getIdNumberAddress() {
		return idNumberAddress;
	}

	public void setIdNumberAddress(String idNumberAddress) {
		this.idNumberAddress = idNumberAddress;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDriverAge() {
		return driverAge;
	}

	public void setDriverAge(String driverAge) {
		this.driverAge = driverAge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getFamilyName1() {
		return familyName1;
	}

	public void setFamilyName1(String familyName1) {
		this.familyName1 = familyName1;
	}

	public String getFamilyPhone1() {
		return familyPhone1;
	}

	public void setFamilyPhone1(String familyPhone1) {
		this.familyPhone1 = familyPhone1;
	}

	public String getFamilyRelation1() {
		return familyRelation1;
	}

	public void setFamilyRelation1(String familyRelation1) {
		this.familyRelation1 = familyRelation1;
	}

	public String getFamilyWorkUnit1() {
		return familyWorkUnit1;
	}

	public void setFamilyWorkUnit1(String familyWorkUnit1) {
		this.familyWorkUnit1 = familyWorkUnit1;
	}

	public String getFamilyName2() {
		return familyName2;
	}

	public void setFamilyName2(String familyName2) {
		this.familyName2 = familyName2;
	}

	public String getFamilyPhone2() {
		return familyPhone2;
	}

	public void setFamilyPhone2(String familyPhone2) {
		this.familyPhone2 = familyPhone2;
	}

	public String getFamilyRelation2() {
		return familyRelation2;
	}

	public void setFamilyRelation2(String familyRelation2) {
		this.familyRelation2 = familyRelation2;
	}

	public String getFamilyWorkUnit2() {
		return familyWorkUnit2;
	}

	public void setFamilyWorkUnit2(String familyWorkUnit2) {
		this.familyWorkUnit2 = familyWorkUnit2;
	}

	public String getFamilyName3() {
		return familyName3;
	}

	public void setFamilyName3(String familyName3) {
		this.familyName3 = familyName3;
	}

	public String getFamilyPhone3() {
		return familyPhone3;
	}

	public void setFamilyPhone3(String familyPhone3) {
		this.familyPhone3 = familyPhone3;
	}

	public String getFamilyRelation3() {
		return familyRelation3;
	}

	public void setFamilyRelation3(String familyRelation3) {
		this.familyRelation3 = familyRelation3;
	}

	public String getFamilyWorkUnit3() {
		return familyWorkUnit3;
	}

	public void setFamilyWorkUnit3(String familyWorkUnit3) {
		this.familyWorkUnit3 = familyWorkUnit3;
	}

	public String getFamilyName4() {
		return familyName4;
	}

	public void setFamilyName4(String familyName4) {
		this.familyName4 = familyName4;
	}

	public String getFamilyPhone4() {
		return familyPhone4;
	}

	public void setFamilyPhone4(String familyPhone4) {
		this.familyPhone4 = familyPhone4;
	}

	public String getFamilyRelation4() {
		return familyRelation4;
	}

	public void setFamilyRelation4(String familyRelation4) {
		this.familyRelation4 = familyRelation4;
	}

	public String getFamilyWorkUnit4() {
		return familyWorkUnit4;
	}

	public void setFamilyWorkUnit4(String familyWorkUnit4) {
		this.familyWorkUnit4 = familyWorkUnit4;
	}
	public String getQualificationCertificate() {
		return qualificationCertificate;
	}

	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}

}