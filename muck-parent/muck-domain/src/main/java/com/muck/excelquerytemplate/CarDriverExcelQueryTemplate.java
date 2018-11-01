package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 驾驶员模板类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月22日 下午12:36:49
 */
public class CarDriverExcelQueryTemplate extends BaseExcelQueryTemplate{

	// 驾驶员序号
			@ExcelTemplate(name="序号")
			private String has_driverId;
			
			// 驾驶员名称
			@ExcelTemplate(name = "姓名")
			private String has_driverName;

			// 驾驶员性别
			@ExcelTemplate(name = "性别")
			private String has_driverSex;

			@ExcelTemplate(name="年龄")
			private String has_driverAge;
			
			// 驾驶员出生年月
			@ExcelTemplate(name = "出生年月")
			private String has_driverBirth;

			// 驾驶员头像
			@ExcelTemplate(name="免冠近照")
			private String has_headPath;
			// 是否结婚
			@ExcelTemplate(name = "婚否")
			private String has_isMarry;

			// 驾驶员所属民族
			@ExcelTemplate(name = "民族")
			private String has_driverNation;

			// 驾驶员学历
			@ExcelTemplate(name = "学历")
			private String has_driverEducation;

			// 驾驶员身份证号
			@ExcelTemplate(name = "身份证号")
			private String has_idNumber;

			// 驾驶员身份证地址
			@ExcelTemplate(name = "身份证地址")
			private String has_idNumberAddress;

			// 道路运输从业人员从业资格证
			@ExcelTemplate(name = "道路运输从业人员从业资格证")
			private String has_qualificationCertificate;
			
			// 驾驶证档案编号
			@ExcelTemplate(name="驾驶证档案编号")
			private String has_driverLicenseFileNumber;
			
			// 驾驶员车牌号
			@ExcelTemplate(name = "驾驶车辆号牌")
			private String has_carCode;

			// 现居住地址
			@ExcelTemplate(name = "现住址")
			private String has_liveAddress;

			// 驾驶员联系方式
			@ExcelTemplate(name = "手机号码")
			private String has_driverPhone;

			// 工作单位id
			@ExcelTemplate(name="工作单位id")
			private String has_companyId;
			
			// 驾驶员工作单位
			@ExcelTemplate(name = "工作单位")
			private String has_companyName;

			// 个人简介
			@ExcelTemplate(name = "个人简介")
			private String has_resume;

			// 在职状态
			@ExcelTemplate(name="在职情况(1:在职/2:离职/3:已开除)")
			private String has_statusType;
			// 原因说明
			@ExcelTemplate(name = "离职/开除原因")
			private String has_reason;

			// 家庭成员表
			
			//姓名
			@ExcelTemplate(name="家庭成员姓名")
			private String has_name;
			//关系
			@ExcelTemplate(name="家庭成员关系")
			private String has_relation;
			//联系电话
			@ExcelTemplate(name="家庭成员联系电话")
			private String has_phone;
			//工作单位
			@ExcelTemplate(name="家庭成员工作单位")
			private String has_workUnit;
			
			// 姓名
			@ExcelTemplate(name = "家庭成员1姓名")
			private String has_familyName1;

			// 联系方式
			@ExcelTemplate(name = "家庭成员1联系方式")
			private String has_familyPhone1;

			// 家庭关系
			@ExcelTemplate(name = "与家庭成员1关系")
			private String has_familyRelation1;

			// 工作单位
			@ExcelTemplate(name = "家庭成员1工作单位")
			private String has_familyWorkUnit1;

			// 姓名
			@ExcelTemplate(name = "家庭成员2姓名")
			private String has_familyName2;
			
			// 联系方式
			@ExcelTemplate(name = "家庭成员2联系方式")
			private String has_familyPhone2;
			
			// 家庭关系
			@ExcelTemplate(name = "与家庭成员2关系")
			private String has_familyRelation2;
			
			// 工作单位
			@ExcelTemplate(name = "家庭成员2工作单位")
			private String has_familyWorkUnit2;
			
			// 姓名
			@ExcelTemplate(name = "家庭成员3姓名")
			private String has_familyName3;
			
			// 联系方式
			@ExcelTemplate(name = "家庭成员3联系方式")
			private String has_familyPhone3;
			
			// 家庭关系
			@ExcelTemplate(name = "与家庭成员3关系")
			private String has_familyRelation3;
			
			// 工作单位
			@ExcelTemplate(name = "家庭成员3工作单位")
			private String has_familyWorkUnit3;
			
			// 姓名
			@ExcelTemplate(name = "家庭成员4姓名")
			private String has_familyName4;
			
			// 联系方式
			@ExcelTemplate(name = "家庭成员4联系方式")
			private String has_familyPhone4;
			
			// 家庭关系
			@ExcelTemplate(name = "与家庭成员4关系")
			private String has_familyRelation4;
			
			// 工作单位
			@ExcelTemplate(name = "家庭成员4工作单位")
			private String has_familyWorkUnit4;

			// 是否包含家庭成员
			@ExcelTemplate(name="列表_家庭成员")
			private String has_carDriverFamilyMembers;
			
			public CarDriverExcelQueryTemplate() {
			}

			public CarDriverExcelQueryTemplate(String has_driverId, String has_driverName, String has_driverSex,
					String has_driverAge, String has_driverBirth, String has_headPath, String has_isMarry,
					String has_driverNation, String has_driverEducation, String has_idNumber,
					String has_idNumberAddress, String has_qualificationCertificate, String has_driverLicenseFileNumber,
					String has_carCode, String has_liveAddress, String has_driverPhone, String has_companyId,
					String has_companyName, String has_resume, String has_statusType, String has_reason,
					String has_name, String has_relation, String has_phone, String has_workUnit, String has_familyName1,
					String has_familyPhone1, String has_familyRelation1, String has_familyWorkUnit1,
					String has_familyName2, String has_familyPhone2, String has_familyRelation2,
					String has_familyWorkUnit2, String has_familyName3, String has_familyPhone3,
					String has_familyRelation3, String has_familyWorkUnit3, String has_familyName4,
					String has_familyPhone4, String has_familyRelation4, String has_familyWorkUnit4,
					String has_carDriverFamilyMembers) {
				super();
				this.has_driverId = has_driverId;
				this.has_driverName = has_driverName;
				this.has_driverSex = has_driverSex;
				this.has_driverAge = has_driverAge;
				this.has_driverBirth = has_driverBirth;
				this.has_headPath = has_headPath;
				this.has_isMarry = has_isMarry;
				this.has_driverNation = has_driverNation;
				this.has_driverEducation = has_driverEducation;
				this.has_idNumber = has_idNumber;
				this.has_idNumberAddress = has_idNumberAddress;
				this.has_qualificationCertificate = has_qualificationCertificate;
				this.has_driverLicenseFileNumber = has_driverLicenseFileNumber;
				this.has_carCode = has_carCode;
				this.has_liveAddress = has_liveAddress;
				this.has_driverPhone = has_driverPhone;
				this.has_companyId = has_companyId;
				this.has_companyName = has_companyName;
				this.has_resume = has_resume;
				this.has_statusType = has_statusType;
				this.has_reason = has_reason;
				this.has_name = has_name;
				this.has_relation = has_relation;
				this.has_phone = has_phone;
				this.has_workUnit = has_workUnit;
				this.has_familyName1 = has_familyName1;
				this.has_familyPhone1 = has_familyPhone1;
				this.has_familyRelation1 = has_familyRelation1;
				this.has_familyWorkUnit1 = has_familyWorkUnit1;
				this.has_familyName2 = has_familyName2;
				this.has_familyPhone2 = has_familyPhone2;
				this.has_familyRelation2 = has_familyRelation2;
				this.has_familyWorkUnit2 = has_familyWorkUnit2;
				this.has_familyName3 = has_familyName3;
				this.has_familyPhone3 = has_familyPhone3;
				this.has_familyRelation3 = has_familyRelation3;
				this.has_familyWorkUnit3 = has_familyWorkUnit3;
				this.has_familyName4 = has_familyName4;
				this.has_familyPhone4 = has_familyPhone4;
				this.has_familyRelation4 = has_familyRelation4;
				this.has_familyWorkUnit4 = has_familyWorkUnit4;
				this.has_carDriverFamilyMembers = has_carDriverFamilyMembers;
			}


			public String getHas_driverId() {
				return has_driverId;
			}

			public void setHas_driverId(String has_driverId) {
				this.has_driverId = has_driverId;
			}

			public String getHas_driverName() {
				return has_driverName;
			}

			public void setHas_driverName(String has_driverName) {
				this.has_driverName = has_driverName;
			}

			public String getHas_driverSex() {
				return has_driverSex;
			}

			public void setHas_driverSex(String has_driverSex) {
				this.has_driverSex = has_driverSex;
			}

			public String getHas_driverAge() {
				return has_driverAge;
			}

			public void setHas_driverAge(String has_driverAge) {
				this.has_driverAge = has_driverAge;
			}

			public String getHas_driverBirth() {
				return has_driverBirth;
			}

			public void setHas_driverBirth(String has_driverBirth) {
				this.has_driverBirth = has_driverBirth;
			}

			public String getHas_headPath() {
				return has_headPath;
			}

			public void setHas_headPath(String has_headPath) {
				this.has_headPath = has_headPath;
			}

			public String getHas_isMarry() {
				return has_isMarry;
			}

			public void setHas_isMarry(String has_isMarry) {
				this.has_isMarry = has_isMarry;
			}

			public String getHas_driverNation() {
				return has_driverNation;
			}

			public void setHas_driverNation(String has_driverNation) {
				this.has_driverNation = has_driverNation;
			}

			public String getHas_driverEducation() {
				return has_driverEducation;
			}

			public void setHas_driverEducation(String has_driverEducation) {
				this.has_driverEducation = has_driverEducation;
			}

			public String getHas_idNumber() {
				return has_idNumber;
			}

			public void setHas_idNumber(String has_idNumber) {
				this.has_idNumber = has_idNumber;
			}

			public String getHas_idNumberAddress() {
				return has_idNumberAddress;
			}

			public void setHas_idNumberAddress(String has_idNumberAddress) {
				this.has_idNumberAddress = has_idNumberAddress;
			}

			public String getHas_driverLicenseFileNumber() {
				return has_driverLicenseFileNumber;
			}

			public void setHas_driverLicenseFileNumber(String has_driverLicenseFileNumber) {
				this.has_driverLicenseFileNumber = has_driverLicenseFileNumber;
			}

			public String getHas_carCode() {
				return has_carCode;
			}

			public void setHas_carCode(String has_carCode) {
				this.has_carCode = has_carCode;
			}

			public String getHas_liveAddress() {
				return has_liveAddress;
			}

			public void setHas_liveAddress(String has_liveAddress) {
				this.has_liveAddress = has_liveAddress;
			}

			public String getHas_driverPhone() {
				return has_driverPhone;
			}

			public void setHas_driverPhone(String has_driverPhone) {
				this.has_driverPhone = has_driverPhone;
			}

			public String getHas_companyName() {
				return has_companyName;
			}

			public void setHas_companyName(String has_companyName) {
				this.has_companyName = has_companyName;
			}

			public String getHas_resume() {
				return has_resume;
			}

			public void setHas_resume(String has_resume) {
				this.has_resume = has_resume;
			}

			public String getHas_statusType() {
				return has_statusType;
			}

			public void setHas_statusType(String has_statusType) {
				this.has_statusType = has_statusType;
			}

			public String getHas_reason() {
				return has_reason;
			}

			public void setHas_reason(String has_reason) {
				this.has_reason = has_reason;
			}

			public String getHas_name() {
				return has_name;
			}

			public void setHas_name(String has_name) {
				this.has_name = has_name;
			}

			public String getHas_relation() {
				return has_relation;
			}

			public void setHas_relation(String has_relation) {
				this.has_relation = has_relation;
			}

			public String getHas_phone() {
				return has_phone;
			}

			public void setHas_phone(String has_phone) {
				this.has_phone = has_phone;
			}

			public String getHas_workUnit() {
				return has_workUnit;
			}

			public void setHas_workUnit(String has_workUnit) {
				this.has_workUnit = has_workUnit;
			}

			public String getHas_familyName1() {
				return has_familyName1;
			}

			public void setHas_familyName1(String has_familyName1) {
				this.has_familyName1 = has_familyName1;
			}

			public String getHas_familyPhone1() {
				return has_familyPhone1;
			}

			public void setHas_familyPhone1(String has_familyPhone1) {
				this.has_familyPhone1 = has_familyPhone1;
			}

			public String getHas_familyRelation1() {
				return has_familyRelation1;
			}

			public void setHas_familyRelation1(String has_familyRelation1) {
				this.has_familyRelation1 = has_familyRelation1;
			}

			public String getHas_familyWorkUnit1() {
				return has_familyWorkUnit1;
			}

			public void setHas_familyWorkUnit1(String has_familyWorkUnit1) {
				this.has_familyWorkUnit1 = has_familyWorkUnit1;
			}

			public String getHas_familyName2() {
				return has_familyName2;
			}

			public void setHas_familyName2(String has_familyName2) {
				this.has_familyName2 = has_familyName2;
			}

			public String getHas_familyPhone2() {
				return has_familyPhone2;
			}

			public void setHas_familyPhone2(String has_familyPhone2) {
				this.has_familyPhone2 = has_familyPhone2;
			}

			public String getHas_familyRelation2() {
				return has_familyRelation2;
			}

			public void setHas_familyRelation2(String has_familyRelation2) {
				this.has_familyRelation2 = has_familyRelation2;
			}

			public String getHas_familyWorkUnit2() {
				return has_familyWorkUnit2;
			}

			public void setHas_familyWorkUnit2(String has_familyWorkUnit2) {
				this.has_familyWorkUnit2 = has_familyWorkUnit2;
			}

			public String getHas_familyName3() {
				return has_familyName3;
			}

			public void setHas_familyName3(String has_familyName3) {
				this.has_familyName3 = has_familyName3;
			}

			public String getHas_familyPhone3() {
				return has_familyPhone3;
			}

			public void setHas_familyPhone3(String has_familyPhone3) {
				this.has_familyPhone3 = has_familyPhone3;
			}

			public String getHas_familyRelation3() {
				return has_familyRelation3;
			}

			public void setHas_familyRelation3(String has_familyRelation3) {
				this.has_familyRelation3 = has_familyRelation3;
			}

			public String getHas_familyWorkUnit3() {
				return has_familyWorkUnit3;
			}

			public void setHas_familyWorkUnit3(String has_familyWorkUnit3) {
				this.has_familyWorkUnit3 = has_familyWorkUnit3;
			}

			public String getHas_familyName4() {
				return has_familyName4;
			}

			public void setHas_familyName4(String has_familyName4) {
				this.has_familyName4 = has_familyName4;
			}

			public String getHas_familyPhone4() {
				return has_familyPhone4;
			}

			public void setHas_familyPhone4(String has_familyPhone4) {
				this.has_familyPhone4 = has_familyPhone4;
			}

			public String getHas_familyRelation4() {
				return has_familyRelation4;
			}

			public void setHas_familyRelation4(String has_familyRelation4) {
				this.has_familyRelation4 = has_familyRelation4;
			}

			public String getHas_familyWorkUnit4() {
				return has_familyWorkUnit4;
			}

			public void setHas_familyWorkUnit4(String has_familyWorkUnit4) {
				this.has_familyWorkUnit4 = has_familyWorkUnit4;
			}

			public String getHas_carDriverFamilyMembers() {
				return has_carDriverFamilyMembers;
			}

			public void setHas_carDriverFamilyMembers(String has_carDriverFamilyMembers) {
				this.has_carDriverFamilyMembers = has_carDriverFamilyMembers;
			}

			public String getHas_qualificationCertificate() {
				return has_qualificationCertificate;
			}

			public void setHas_qualificationCertificate(String has_qualificationCertificate) {
				this.has_qualificationCertificate = has_qualificationCertificate;
			}

			@Override
			public String toString() {
				return "CarDriverExcelQueryTemplate [has_driverId=" + has_driverId + ", has_driverName="
						+ has_driverName + ", has_driverSex=" + has_driverSex + ", has_driverAge=" + has_driverAge
						+ ", has_driverBirth=" + has_driverBirth + ", has_headPath=" + has_headPath + ", has_isMarry="
						+ has_isMarry + ", has_driverNation=" + has_driverNation + ", has_driverEducation="
						+ has_driverEducation + ", has_idNumber=" + has_idNumber + ", has_idNumberAddress="
						+ has_idNumberAddress + ", has_qualificationCertificate=" + has_qualificationCertificate
						+ ", has_driverLicenseFileNumber=" + has_driverLicenseFileNumber + ", has_carCode="
						+ has_carCode + ", has_liveAddress=" + has_liveAddress + ", has_driverPhone=" + has_driverPhone
						+ ", has_companyId=" + has_companyId + ", has_companyName=" + has_companyName + ", has_resume="
						+ has_resume + ", has_statusType=" + has_statusType + ", has_reason=" + has_reason
						+ ", has_name=" + has_name + ", has_relation=" + has_relation + ", has_phone=" + has_phone
						+ ", has_workUnit=" + has_workUnit + ", has_familyName1=" + has_familyName1
						+ ", has_familyPhone1=" + has_familyPhone1 + ", has_familyRelation1=" + has_familyRelation1
						+ ", has_familyWorkUnit1=" + has_familyWorkUnit1 + ", has_familyName2=" + has_familyName2
						+ ", has_familyPhone2=" + has_familyPhone2 + ", has_familyRelation2=" + has_familyRelation2
						+ ", has_familyWorkUnit2=" + has_familyWorkUnit2 + ", has_familyName3=" + has_familyName3
						+ ", has_familyPhone3=" + has_familyPhone3 + ", has_familyRelation3=" + has_familyRelation3
						+ ", has_familyWorkUnit3=" + has_familyWorkUnit3 + ", has_familyName4=" + has_familyName4
						+ ", has_familyPhone4=" + has_familyPhone4 + ", has_familyRelation4=" + has_familyRelation4
						+ ", has_familyWorkUnit4=" + has_familyWorkUnit4 + ", has_carDriverFamilyMembers="
						+ has_carDriverFamilyMembers + "]";
			}

}
