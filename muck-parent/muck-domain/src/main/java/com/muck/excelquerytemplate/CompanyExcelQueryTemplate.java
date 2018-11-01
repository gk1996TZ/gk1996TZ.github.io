package com.muck.excelquerytemplate;


import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 企业Excel模版参数
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月7日 下午5:27:47
 */
public class CompanyExcelQueryTemplate extends BaseExcelQueryTemplate{

	/**企业名称*/
	@ExcelTemplate(name="企业名称")
	private String has_companyName;
	/**企业注册地址*/
	@ExcelTemplate(name="企业注册地址")
	private String has_companyRegisteredAddress;
	/**企业所属区域名称*/
	@ExcelTemplate(name="企业所属区域")
	private String has_areaName;
	/**企业面积*/
	@ExcelTemplate(name="企业面积")
	private String has_companyAcreage;
	/**企业自有或租赁*/
	@ExcelTemplate(name="企业自有或租赁")
	private String has_companyOwnLease;
	/**企业停车场地址*/
	@ExcelTemplate(name="企业停车场地址")
	private String has_companyCarParkAddress;
	/**企业停车场面积*/
	@ExcelTemplate(name="停车场面积")
	private String has_companyCarParkAcreage;
	/**企业停车场自有或租赁*/
	@ExcelTemplate(name="停车场自有或租赁")
	private String has_companyCarParkOwnLease;
	/**营业执照注册号*/
	@ExcelTemplate(name="营业执照注册号")
	private String has_companyBusinessLicense;
	/**营业执照注册号截止日期*/
	@ExcelTemplate(name="营业执照注册号截止日期")
	private String has_companyBusinessLicenseCloseDate;
	/**道路普通货物运输经营许可证号*/
	@ExcelTemplate(name="道路普通货物运输经营许可证号")
	private String has_companyRoadLicense;
	/**道路普通货物运输经营许可证号截止日期*/
	@ExcelTemplate(name="道路普通货物运输经营许可证号截止日期")
	private String has_companyRoadLicenseCloseDate;
	/**企业类型*/
	@ExcelTemplate(name="企业类型")
	private String has_companyType;
	/**企业类别*/
	@ExcelTemplate(name="企业类别")
	private String has_companyCategory;
	/**企业创建时间*/
	@ExcelTemplate(name="企业建立时间")
	private String has_companyCreationTime;
	/**企业联系方式*/
	@ExcelTemplate(name="企业联系电话")
	private String has_companyContact;
	/**企业传真*/
	@ExcelTemplate(name="企业传真")
	private String has_companyFacsimile;
	/**企业url*/
	@ExcelTemplate(name="企业网址")
	private String has_companyUrl;
	/**企业电子信箱*/
	@ExcelTemplate(name="企业电子信箱")
	private String has_companyEmail;
	/**法定代表人*/
	@ExcelTemplate(name="法定代表人")
	private String has_companyLegalRepresentative;
	/**法定代表人联系方式*/
	@ExcelTemplate(name="法定代表人联系电话")
	private String has_companyLegalRepresentativePhone;
	/**企业经理*/
	@ExcelTemplate(name="企业经理")
	private String has_companyDirector;
	/**企业经理联系方式*/
	@ExcelTemplate(name="企业经理联系电话")
	private String has_companyDirectorPhone;
	/**车队负责人*/
	@ExcelTemplate(name="车队负责人")
	private String has_companyMotorcadePrincipal;
	/**车队负责人联系方式*/
	@ExcelTemplate(name="车队负责人联系电话")
	private String has_companyMotorcadePrincipalPhone;
	/**企业员工总人数*/
	@ExcelTemplate(name="职工总人数")
	private String has_companyEmployeeNumber;
	/**企业管理员总人数*/
	@ExcelTemplate(name="管理人员人数")
	private String has_companyAdministratorNumber;
	/**企业普通员工人数*/
	@ExcelTemplate(name="企业普通员工人数")
	private String has_companyGeneralNumber;
	/**企业下的驾驶员数*/
	@ExcelTemplate(name="企业下驾驶员数量")
	private String has_driverTotal;
	/**驾驶员人数*/
	@ExcelTemplate(name="驾驶员人数")
	private String has_companyDriverNumber;
	/**企业负责人名称*/
	@ExcelTemplate(name="企业负责人名称")
	private String has_companyPrincipalName;
	/**企业负责人联系方式*/
	@ExcelTemplate(name="企业负责人联系方式")
	private String has_companyPrincipalPhone;
	/**企业下的车辆数*/
	@ExcelTemplate(name="企业下车辆数量（废弃）")
	private String has_carTotal;
	/**企业下的车辆数*/
	@ExcelTemplate(name="车辆总数")
	private String has_companyCarNumber;
	/**企业下黄色车牌车辆总数*/
	@ExcelTemplate(name="黄牌车辆数")
	private String has_companyCarYellowCardNumber;
	/**企业下蓝色车牌车辆总数*/
	@ExcelTemplate(name="蓝牌车辆数")
	private String has_companyCarBlueCardNumber;
	/**企业备注信息*/
	@ExcelTemplate(name="企业备注信息")
	private String has_memo;
	@ExcelTemplate(name="列表_车辆")
	private String has_cars;
	@ExcelTemplate(name="列表_驾驶员")
	private String has_carDrivers;
	public CompanyExcelQueryTemplate() {
	}

	public CompanyExcelQueryTemplate(String has_companyName, String has_companyRegisteredAddress, String has_areaName,
			String has_companyAcreage, String has_companyOwnLease, String has_companyCarParkAddress,
			String has_companyCarParkAcreage, String has_companyCarParkOwnLease, String has_companyBusinessLicense,
			String has_companyBusinessLicenseCloseDate, String has_companyRoadLicense,
			String has_companyRoadLicenseCloseDate, String has_companyType, String has_companyCategory,
			String has_companyCreationTime, String has_companyContact, String has_companyFacsimile,
			String has_companyUrl, String has_companyEmail, String has_companyLegalRepresentative,
			String has_companyLegalRepresentativePhone, String has_companyDirector, String has_companyDirectorPhone,
			String has_companyMotorcadePrincipal, String has_companyMotorcadePrincipalPhone,
			String has_companyEmployeeNumber, String has_companyAdministratorNumber, String has_companyGeneralNumber,
			String has_driverTotal, String has_companyPrincipalName, String has_companyPrincipalPhone,
			String has_carTotal, String has_companyCarNumber, String has_companyCarYellowCardNumber,
			String has_companyCarBlueCardNumber, String has_memo, String has_cars, String has_carDrivers) {
		super();
		this.has_companyName = has_companyName;
		this.has_companyRegisteredAddress = has_companyRegisteredAddress;
		this.has_areaName = has_areaName;
		this.has_companyAcreage = has_companyAcreage;
		this.has_companyOwnLease = has_companyOwnLease;
		this.has_companyCarParkAddress = has_companyCarParkAddress;
		this.has_companyCarParkAcreage = has_companyCarParkAcreage;
		this.has_companyCarParkOwnLease = has_companyCarParkOwnLease;
		this.has_companyBusinessLicense = has_companyBusinessLicense;
		this.has_companyBusinessLicenseCloseDate = has_companyBusinessLicenseCloseDate;
		this.has_companyRoadLicense = has_companyRoadLicense;
		this.has_companyRoadLicenseCloseDate = has_companyRoadLicenseCloseDate;
		this.has_companyType = has_companyType;
		this.has_companyCategory = has_companyCategory;
		this.has_companyCreationTime = has_companyCreationTime;
		this.has_companyContact = has_companyContact;
		this.has_companyFacsimile = has_companyFacsimile;
		this.has_companyUrl = has_companyUrl;
		this.has_companyEmail = has_companyEmail;
		this.has_companyLegalRepresentative = has_companyLegalRepresentative;
		this.has_companyLegalRepresentativePhone = has_companyLegalRepresentativePhone;
		this.has_companyDirector = has_companyDirector;
		this.has_companyDirectorPhone = has_companyDirectorPhone;
		this.has_companyMotorcadePrincipal = has_companyMotorcadePrincipal;
		this.has_companyMotorcadePrincipalPhone = has_companyMotorcadePrincipalPhone;
		this.has_companyEmployeeNumber = has_companyEmployeeNumber;
		this.has_companyAdministratorNumber = has_companyAdministratorNumber;
		this.has_companyGeneralNumber = has_companyGeneralNumber;
		this.has_driverTotal = has_driverTotal;
		this.has_companyPrincipalName = has_companyPrincipalName;
		this.has_companyPrincipalPhone = has_companyPrincipalPhone;
		this.has_carTotal = has_carTotal;
		this.has_companyCarNumber = has_companyCarNumber;
		this.has_companyCarYellowCardNumber = has_companyCarYellowCardNumber;
		this.has_companyCarBlueCardNumber = has_companyCarBlueCardNumber;
		this.has_memo = has_memo;
		this.has_cars = has_cars;
		this.has_carDrivers = has_carDrivers;
	}





	public String getHas_companyBusinessLicense() {
		return has_companyBusinessLicense;
	}
	public void setHas_companyBusinessLicense(String has_companyBusinessLicense) {
		this.has_companyBusinessLicense = has_companyBusinessLicense;
	}
	public String getHas_companyBusinessLicenseCloseDate() {
		return has_companyBusinessLicenseCloseDate;
	}
	public void setHas_companyBusinessLicenseCloseDate(String has_companyBusinessLicenseCloseDate) {
		this.has_companyBusinessLicenseCloseDate = has_companyBusinessLicenseCloseDate;
	}
	public String getHas_companyRoadLicense() {
		return has_companyRoadLicense;
	}
	public void setHas_companyRoadLicense(String has_companyRoadLicense) {
		this.has_companyRoadLicense = has_companyRoadLicense;
	}
	public String getHas_companyRoadLicenseCloseDate() {
		return has_companyRoadLicenseCloseDate;
	}
	public void setHas_companyRoadLicenseCloseDate(String has_companyRoadLicenseCloseDate) {
		this.has_companyRoadLicenseCloseDate = has_companyRoadLicenseCloseDate;
	}
	public String getHas_companyName() {
		return has_companyName;
	}
	public String getHas_companyRegisteredAddress() {
		return has_companyRegisteredAddress;
	}
	public String getHas_areaName() {
		return has_areaName;
	}
	public String getHas_companyAcreage() {
		return has_companyAcreage;
	}
	public String getHas_companyOwnLease() {
		return has_companyOwnLease;
	}
	public String getHas_companyCarParkAddress() {
		return has_companyCarParkAddress;
	}
	public String getHas_companyCarParkAcreage() {
		return has_companyCarParkAcreage;
	}
	public String getHas_companyCarParkOwnLease() {
		return has_companyCarParkOwnLease;
	}
	public String getHas_companyType() {
		return has_companyType;
	}
	public String getHas_companyCategory() {
		return has_companyCategory;
	}
	public String getHas_companyCreationTime() {
		return has_companyCreationTime;
	}
	public String getHas_companyContact() {
		return has_companyContact;
	}
	public String getHas_companyFacsimile() {
		return has_companyFacsimile;
	}
	public String getHas_companyUrl() {
		return has_companyUrl;
	}
	public String getHas_companyEmail() {
		return has_companyEmail;
	}
	public String getHas_companyLegalRepresentative() {
		return has_companyLegalRepresentative;
	}
	public String getHas_companyLegalRepresentativePhone() {
		return has_companyLegalRepresentativePhone;
	}
	public String getHas_companyDirector() {
		return has_companyDirector;
	}
	public String getHas_companyDirectorPhone() {
		return has_companyDirectorPhone;
	}
	public String getHas_companyMotorcadePrincipal() {
		return has_companyMotorcadePrincipal;
	}
	public String getHas_companyMotorcadePrincipalPhone() {
		return has_companyMotorcadePrincipalPhone;
	}
	public String getHas_companyEmployeeNumber() {
		return has_companyEmployeeNumber;
	}
	public String getHas_companyAdministratorNumber() {
		return has_companyAdministratorNumber;
	}
	public String getHas_companyGeneralNumber() {
		return has_companyGeneralNumber;
	}
	public String getHas_companyPrincipalName() {
		return has_companyPrincipalName;
	}
	public String getHas_companyPrincipalPhone() {
		return has_companyPrincipalPhone;
	}
	public String getHas_carTotal() {
		return has_carTotal;
	}
	public String getHas_driverTotal() {
		return has_driverTotal;
	}
	public String getHas_memo() {
		return has_memo;
	}
	public void setHas_companyName(String has_companyName) {
		this.has_companyName = has_companyName;
	}
	public void setHas_companyRegisteredAddress(String has_companyRegisteredAddress) {
		this.has_companyRegisteredAddress = has_companyRegisteredAddress;
	}
	public void setHas_areaName(String has_areaName) {
		this.has_areaName = has_areaName;
	}
	public void setHas_companyAcreage(String has_companyAcreage) {
		this.has_companyAcreage = has_companyAcreage;
	}
	public void setHas_companyOwnLease(String has_companyOwnLease) {
		this.has_companyOwnLease = has_companyOwnLease;
	}
	public void setHas_companyCarParkAddress(String has_companyCarParkAddress) {
		this.has_companyCarParkAddress = has_companyCarParkAddress;
	}
	public void setHas_companyCarParkAcreage(String has_companyCarParkAcreage) {
		this.has_companyCarParkAcreage = has_companyCarParkAcreage;
	}
	public void setHas_companyCarParkOwnLease(String has_companyCarParkOwnLease) {
		this.has_companyCarParkOwnLease = has_companyCarParkOwnLease;
	}
	public void setHas_companyType(String has_companyType) {
		this.has_companyType = has_companyType;
	}
	public void setHas_companyCategory(String has_companyCategory) {
		this.has_companyCategory = has_companyCategory;
	}
	public void setHas_companyCreationTime(String has_companyCreationTime) {
		this.has_companyCreationTime = has_companyCreationTime;
	}
	public void setHas_companyContact(String has_companyContact) {
		this.has_companyContact = has_companyContact;
	}
	public void setHas_companyFacsimile(String has_companyFacsimile) {
		this.has_companyFacsimile = has_companyFacsimile;
	}
	public void setHas_companyUrl(String has_companyUrl) {
		this.has_companyUrl = has_companyUrl;
	}
	public void setHas_companyEmail(String has_companyEmail) {
		this.has_companyEmail = has_companyEmail;
	}
	public void setHas_companyLegalRepresentative(String has_companyLegalRepresentative) {
		this.has_companyLegalRepresentative = has_companyLegalRepresentative;
	}
	public void setHas_companyLegalRepresentativePhone(String has_companyLegalRepresentativePhone) {
		this.has_companyLegalRepresentativePhone = has_companyLegalRepresentativePhone;
	}
	public void setHas_companyDirector(String has_companyDirector) {
		this.has_companyDirector = has_companyDirector;
	}
	public void setHas_companyDirectorPhone(String has_companyDirectorPhone) {
		this.has_companyDirectorPhone = has_companyDirectorPhone;
	}
	public void setHas_companyMotorcadePrincipal(String has_companyMotorcadePrincipal) {
		this.has_companyMotorcadePrincipal = has_companyMotorcadePrincipal;
	}
	public void setHas_companyMotorcadePrincipalPhone(String has_companyMotorcadePrincipalPhone) {
		this.has_companyMotorcadePrincipalPhone = has_companyMotorcadePrincipalPhone;
	}
	public void setHas_companyEmployeeNumber(String has_companyEmployeeNumber) {
		this.has_companyEmployeeNumber = has_companyEmployeeNumber;
	}
	public void setHas_companyAdministratorNumber(String has_companyAdministratorNumber) {
		this.has_companyAdministratorNumber = has_companyAdministratorNumber;
	}
	public void setHas_companyGeneralNumber(String has_companyGeneralNumber) {
		this.has_companyGeneralNumber = has_companyGeneralNumber;
	}
	public void setHas_companyPrincipalName(String has_companyPrincipalName) {
		this.has_companyPrincipalName = has_companyPrincipalName;
	}
	public void setHas_companyPrincipalPhone(String has_companyPrincipalPhone) {
		this.has_companyPrincipalPhone = has_companyPrincipalPhone;
	}
	public void setHas_carTotal(String has_carTotal) {
		this.has_carTotal = has_carTotal;
	}
	public void setHas_driverTotal(String has_driverTotal) {
		this.has_driverTotal = has_driverTotal;
	}
	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}
	public String getHas_cars() {
		return has_cars;
	}
	public void setHas_cars(String has_cars) {
		this.has_cars = has_cars;
	}
	public String getHas_carDrivers() {
		return has_carDrivers;
	}
	public void setHas_carDrivers(String has_carDrivers) {
		this.has_carDrivers = has_carDrivers;
	}
}
