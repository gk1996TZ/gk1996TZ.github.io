package com.muck.request;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.domain.CarDriverFamilyMember;
import com.muck.response.StatusCode;

/**
 * 	车辆驾驶员表
*/
@Unique(uniqueFields={"driver_phone"},tableName="t_car_driver")
public class CarDriverForm extends BaseForm{

	// 驾驶员id
	private String carDriverId;
	
	// 驾驶员名称
    private String driverName;

    // 驾驶员性别
    private String driverSex;

    //驾驶员出生年月
    private Date driverBirth;

    // 是否结婚
    private Boolean isMarry;

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
    
    // 状态类型(1:在职 2:辞职  3:被辞退)
    private Integer statusType;
    
    // 原因说明
    private String reason;

    // 说明描述
    private String memo;
    
    // 家庭成员
    private List<CarDriverFamilyMember> carDriverFamilyMembers;
 
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

	public Date getDriverBirth() {
		return driverBirth;
	}

	public void setDriverBirth(Date driverBirth) {
		this.driverBirth = driverBirth;
	}

	public Boolean getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(Boolean isMarry) {
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
	
	public String getIdNumberAddress() {
		return idNumberAddress;
	}

	public void setIdNumberAddress(String idNumberAddress) {
		this.idNumberAddress = idNumberAddress;
	}
	
	public String getCarDriverId() {
		return carDriverId;
	}

	public void setCarDriverId(String carDriverId) {
		this.carDriverId = carDriverId;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode = null;
		if(StringUtils.isBlank(driverName)){
			statusCode = StatusCode.SITE_PROJECT_INFO_NAME_BLANK;
		}
		return statusCode;
	}

	@Override
	public Object[] validateUnique() {
		return new Object[]{driverPhone};
	}
	
	
}