package com.muck.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
* @Description: 企业实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午1:44:10
 */
@Table(name = "t_company")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Company extends BaseEntity{

	/**企业名称*/
	private String companyName;
	/**企业注册地址*/
	private String companyRegisteredAddress;
	/**企业所属区域id*/
	private String areaId;
	/**企业所属区域名称*/
	private String areaName;
	/**企业面积*/
	private String companyAcreage;
	/**企业自有或租赁*/
	private String companyOwnLease;
	/**企业停车场地址*/
	private String companyCarParkAddress;
	/**企业停车场面积*/
	private String companyCarParkAcreage;
	/**企业停车场自有或租赁*/
	private String companyCarParkOwnLease;
	/**营业执照注册号*/
	private String companyBusinessLicense;
	/**营业执照注册号截止日期*/
	private Date companyBusinessLicenseCloseDate;
	/**道路普通货物运输经营许可证号*/
	private String companyRoadLicense;
	/**道路普通货物运输经营许可证号截止日期*/
	private Date companyRoadLicenseCloseDate;
	/**企业类型*/
	private String companyType;
	/**企业类别*/
	private String companyCategory;
	/**企业创建时间*/
	private Date companyCreationTime;
	/**企业联系方式*/
	private String companyContact;
	/**企业传真*/
	private String companyFacsimile;
	/**企业url*/
	private String companyUrl;
	/**企业电子信箱*/
	private String companyEmail;
	/**法定代表人*/
	private String companyLegalRepresentative;
	/**法定代表人联系方式*/
	private String companyLegalRepresentativePhone;
	/**企业经理*/
	private String companyDirector;
	/**企业经理联系方式*/
	private String companyDirectorPhone;
	/**车队负责人*/
	private String companyMotorcadePrincipal;
	/**车队负责人联系方式*/
	private String companyMotorcadePrincipalPhone;
	/**企业员工总数*/
	private Integer companyEmployeeNumber;
	/**企业管理员总人数*/
	private Integer companyAdministratorNumber;
	/**企业驾驶员总人数*/
	private Integer companyDriverNumber;
	/**企业普通员工人数*/
	private Integer companyGeneralNumber;
	/**企业车辆总数*/
	private Integer companyCarNumber;
	/**企业黄牌车辆总数*/
	private Integer companyCarYellowCardNumber;
	/**企业蓝牌车辆总数*/
	private Integer companyCarBlueCardNumber;
	/**企业负责人名称*/
	private String companyPrincipalName;
	/**企业负责人联系方式*/
	private String companyPrincipalPhone;
	//企业规模
	private String companyScale;
	//企业颜色id
	private String companyColorId;
	//企业颜色值
	private String companyColorValue;
	// 企业logo路径
	private String companyLogo;
	// 企业入驻时间
	private Date companyArrivalTime;
	// 企业入驻的区域
	private String[] areaCodes;
	// 企业下面的设备编号
	private String[] deviceCodes;
	// 企业下面的车辆组
	private List<CarGroup> carGroups;
	//private List<Map<String,Object>> carGroups;
//	public void setCarGroups(List<Map<String, Object>> carGroups) {
//		this.carGroups = carGroups;
//	}
//	
//	public List<Map<String, Object>> getCarGroups() {
//		return carGroups;
//	}
	/**企业下的车辆列表*/
	private List<Car> cars = new ArrayList<Car>();
	/**企业下的驾驶员列表*/
	private List<CarDriver> carDrivers ;
	private Integer total;
	// 企业下驾驶员数
	private Integer driverTotal;
	// 企业下车辆数
	private Integer carTotal;
	// 企业备注信息
	private String memo;
	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getCompanyColorId() {
		return companyColorId;
	}

	public void setCompanyColorId(String companyColorId) {
		this.companyColorId = companyColorId;
	}

	public String getCompanyColorValue() {
		return companyColorValue;
	}

	public void setCompanyColorValue(String companyColorValue) {
		this.companyColorValue = companyColorValue;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	

	public String getCompanyRegisteredAddress() {
		return companyRegisteredAddress;
	}

	public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
		this.companyRegisteredAddress = companyRegisteredAddress;
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
	
	public String getCompanyAcreage() {
		return companyAcreage;
	}

	public void setCompanyAcreage(String companyAcreage) {
		this.companyAcreage = companyAcreage;
	}

	public String getCompanyOwnLease() {
		return companyOwnLease;
	}

	public void setCompanyOwnLease(String companyOwnLease) {
		this.companyOwnLease = companyOwnLease;
	}

	public String getCompanyCarParkAddress() {
		return companyCarParkAddress;
	}

	public void setCompanyCarParkAddress(String companyCarParkAddress) {
		this.companyCarParkAddress = companyCarParkAddress;
	}

	public String getCompanyCarParkAcreage() {
		return companyCarParkAcreage;
	}

	public void setCompanyCarParkAcreage(String companyCarParkAcreage) {
		this.companyCarParkAcreage = companyCarParkAcreage;
	}

	public String getCompanyCarParkOwnLease() {
		return companyCarParkOwnLease;
	}

	public void setCompanyCarParkOwnLease(String companyCarParkOwnLease) {
		this.companyCarParkOwnLease = companyCarParkOwnLease;
	}

	public String getCompanyBusinessLicense() {
		return companyBusinessLicense;
	}

	public void setCompanyBusinessLicense(String companyBusinessLicense) {
		this.companyBusinessLicense = companyBusinessLicense;
	}

	public Date getCompanyBusinessLicenseCloseDate() {
		return companyBusinessLicenseCloseDate;
	}

	public void setCompanyBusinessLicenseCloseDate(Date companyBusinessLicenseCloseDate) {
		this.companyBusinessLicenseCloseDate = companyBusinessLicenseCloseDate;
	}

	public String getCompanyRoadLicense() {
		return companyRoadLicense;
	}

	public void setCompanyRoadLicense(String companyRoadLicense) {
		this.companyRoadLicense = companyRoadLicense;
	}

	public Date getCompanyRoadLicenseCloseDate() {
		return companyRoadLicenseCloseDate;
	}

	public void setCompanyRoadLicenseCloseDate(Date companyRoadLicenseCloseDate) {
		this.companyRoadLicenseCloseDate = companyRoadLicenseCloseDate;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}

	public Date getCompanyCreationTime() {
		return companyCreationTime;
	}

	public void setCompanyCreationTime(Date companyCreationTime) {
		this.companyCreationTime = companyCreationTime;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getCompanyFacsimile() {
		return companyFacsimile;
	}

	public void setCompanyFacsimile(String companyFacsimile) {
		this.companyFacsimile = companyFacsimile;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyLegalRepresentative() {
		return companyLegalRepresentative;
	}

	public void setCompanyLegalRepresentative(String companyLegalRepresentative) {
		this.companyLegalRepresentative = companyLegalRepresentative;
	}

	public String getCompanyLegalRepresentativePhone() {
		return companyLegalRepresentativePhone;
	}

	public void setCompanyLegalRepresentativePhone(String companyLegalRepresentativePhone) {
		this.companyLegalRepresentativePhone = companyLegalRepresentativePhone;
	}

	public String getCompanyDirector() {
		return companyDirector;
	}

	public void setCompanyDirector(String companyDirector) {
		this.companyDirector = companyDirector;
	}

	public String getCompanyDirectorPhone() {
		return companyDirectorPhone;
	}

	public void setCompanyDirectorPhone(String companyDirectorPhone) {
		this.companyDirectorPhone = companyDirectorPhone;
	}

	public String getCompanyMotorcadePrincipal() {
		return companyMotorcadePrincipal;
	}

	public void setCompanyMotorcadePrincipal(String companyMotorcadePrincipal) {
		this.companyMotorcadePrincipal = companyMotorcadePrincipal;
	}

	public String getCompanyMotorcadePrincipalPhone() {
		return companyMotorcadePrincipalPhone;
	}

	public void setCompanyMotorcadePrincipalPhone(String companyMotorcadePrincipalPhone) {
		this.companyMotorcadePrincipalPhone = companyMotorcadePrincipalPhone;
	}

	public Integer getCompanyEmployeeNumber() {
		return companyEmployeeNumber;
	}

	public void setCompanyEmployeeNumber(Integer companyEmployeeNumber) {
		this.companyEmployeeNumber = companyEmployeeNumber;
	}

	public Integer getCompanyAdministratorNumber() {
		return companyAdministratorNumber;
	}

	public void setCompanyAdministratorNumber(Integer companyAdministratorNumber) {
		this.companyAdministratorNumber = companyAdministratorNumber;
	}

	public Integer getCompanyDriverNumber() {
		return companyDriverNumber;
	}

	public void setCompanyDriverNumber(Integer companyDriverNumber) {
		this.companyDriverNumber = companyDriverNumber;
	}

	public Integer getCompanyGeneralNumber() {
		return companyGeneralNumber;
	}

	public void setCompanyGeneralNumber(Integer companyGeneralNumber) {
		this.companyGeneralNumber = companyGeneralNumber;
	}

	public Integer getCompanyCarNumber() {
		return companyCarNumber;
	}

	public void setCompanyCarNumber(Integer companyCarNumber) {
		this.companyCarNumber = companyCarNumber;
	}

	public Integer getCompanyCarYellowCardNumber() {
		return companyCarYellowCardNumber;
	}

	public void setCompanyCarYellowCardNumber(Integer companyCarYellowCardNumber) {
		this.companyCarYellowCardNumber = companyCarYellowCardNumber;
	}

	public Integer getCompanyCarBlueCardNumber() {
		return companyCarBlueCardNumber;
	}

	public void setCompanyCarBlueCardNumber(Integer companyCarBlueCardNumber) {
		this.companyCarBlueCardNumber = companyCarBlueCardNumber;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
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

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public Date getCompanyArrivalTime() {
		return companyArrivalTime;
	}

	public void setCompanyArrivalTime(Date companyArrivalTime) {
		this.companyArrivalTime = companyArrivalTime;
	}

	public String[] getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String[] areaCodes) {
		this.areaCodes = areaCodes;
	}

	public String[] getDeviceCodes() {
		return deviceCodes;
	}

	public void setDeviceCodes(String[] deviceCodes) {
		this.deviceCodes = deviceCodes;
	}

	public List<CarGroup> getCarGroups() {
		return carGroups;
	}

	public void setCarGroups(List<CarGroup> carGroups) {
		this.carGroups = carGroups;
	}
	
	public List<CarDriver> getCarDrivers() {
		return carDrivers;
	}

	public void setCarDrivers(List<CarDriver> carDrivers) {
		this.carDrivers = carDrivers;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getDriverTotal() {
		return driverTotal;
	}

	public void setDriverTotal(Integer driverTotal) {
		this.driverTotal = driverTotal;
	}

	public Integer getCarTotal() {
		return carTotal;
	}

	public void setCarTotal(Integer carTotal) {
		this.carTotal = carTotal;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "Company [companyName=" + companyName + ", companyRegisteredAddress=" + companyRegisteredAddress
				+ ", areaId=" + areaId + ", areaName=" + areaName + ", companyAcreage=" + companyAcreage
				+ ", companyOwnLease=" + companyOwnLease + ", companyCarParkAddress=" + companyCarParkAddress
				+ ", companyCarParkAcreage=" + companyCarParkAcreage + ", companyCarParkOwnLease="
				+ companyCarParkOwnLease + ", companyBusinessLicense=" + companyBusinessLicense
				+ ", companyBusinessLicenseCloseDate=" + companyBusinessLicenseCloseDate + ", companyRoadLicense="
				+ companyRoadLicense + ", companyRoadLicenseCloseDate=" + companyRoadLicenseCloseDate + ", companyType="
				+ companyType + ", companyCategory=" + companyCategory + ", companyCreationTime=" + companyCreationTime
				+ ", companyContact=" + companyContact + ", companyFacsimile=" + companyFacsimile + ", companyUrl="
				+ companyUrl + ", companyEmail=" + companyEmail + ", companyLegalRepresentative="
				+ companyLegalRepresentative + ", companyLegalRepresentativePhone=" + companyLegalRepresentativePhone
				+ ", companyDirector=" + companyDirector + ", companyDirectorPhone=" + companyDirectorPhone
				+ ", companyMotorcadePrincipal=" + companyMotorcadePrincipal + ", companyMotorcadePrincipalPhone="
				+ companyMotorcadePrincipalPhone + ", companyEmployeeNumber=" + companyEmployeeNumber
				+ ", companyAdministratorNumber=" + companyAdministratorNumber + ", companyDriverNumber="
				+ companyDriverNumber + ", companyGeneralNumber=" + companyGeneralNumber + ", companyPrincipalName="
				+ companyPrincipalName + ", companyPrincipalPhone=" + companyPrincipalPhone + ", companyScale="
				+ companyScale + ", companyColorId=" + companyColorId + ", companyColorValue=" + companyColorValue
				+ ", companyLogo=" + companyLogo + ", companyArrivalTime=" + companyArrivalTime + ", areaCodes="
				+ Arrays.toString(areaCodes) + ", deviceCodes=" + Arrays.toString(deviceCodes) + ", carGroups="
				+ carGroups + ", cars=" + cars + ", carDrivers=" + carDrivers + ", total=" + total + ", driverTotal="
				+ driverTotal + ", carTotal=" + carTotal + ", memo=" + memo + "]";
	}

}
