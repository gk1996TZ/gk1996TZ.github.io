package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

/**
 * @Description: 工地Form
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月15日 上午10:18:11
 */
@Unique(tableName = "t_site" , uniqueFields = {"site_name"})
public class SiteForm extends BaseForm {

	// 工地id
	private String siteId;

	// 工地名称
	private String siteName;

	// 工地地址
	private String siteAddress;

	// 工地所属区域
	private String areaId;

	// 工地所属企业
	private String companyId;

	// 工地所属企业名称
	private String companyName;
	
	// 保洁员
	private String siteCleanerName;

	// 保洁员联系方式
	private String siteCleanerPhone;

	// 备注
	private String memo;

	// 项目负责人1姓名(工地负责人)
	private String siteProjectManagerOne;

	// 项目负责人1联系电话(工地负责人联系电话)
	private String siteProjectManagerPhoneOne;

	// 项目负责人2姓名(工地负责人)
	private String siteProjectManagerTwo;

	// 项目负责人2联系电话(工地负责人联系电话)
	private String siteProjectManagerPhoneTwo;
	
	// 项目情况id
	private String siteProjectInfoId;
	
	// 项目名称
    private String projectName;

    // 项目地址
    private String projectAddress;

    // 项目概况
    private String projectIntroduce;

    // 项目规模
    private String projectScale;

    // 项目周期
    private String projectPeriod;

    // 项目建设单位
    private String buildUnit;

    // 项目建设单位负责人
    private String buildUnitManager;

    // 项目建设单位负责人联系电话
    private String buildUnitManagerPhone;

    // 施工单位
    private String constructionUnit;

    // 施工单位负责人
    private String constructionUnitManager;

    // 施工单位负责人联系电话
    private String constructionUnitManagerPhone;

    // 监理单位
    private String supervisionUnit;

    // 监理单位负责人
    private String supervisionUnitManager;

    // 监理单位负责人联系电话
    private String supervisionUnitManagerPhone;

    // 施工单位
    private String earthworkUnit;

    // 施工单位负责人
    private String earthworkUnitManager;

    // 施工单位负责人联系电话
    private String earthworkUnitManagerPhone;

    // 运输单位
    private String transportUnit;

    // 运输单位负责人
    private String transportUnitManager;

    // 运输单位负责人联系电话
    private String transportUnitManagerPhone;

    // 项目进入口清洁员
    private String entranceCleaners;

    // 项目进入口清洁员负责人
    private String entranceCleanersManager;

    // 项目进入口清洁员负责人联系电话
    private String entranceCleanersManagerPhone;

    // 视频监控
    private String videoSurveillance;

    // 视频监控负责人
    private String videoSurveillanceManager;

    // 视频监控负责人联系电话
    private String videoSurveillanceManagerPhone;

    // 备注说明
    private String projectInfoMemo;
    
    // 工程项目进度
    private String siteProcessMemo;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
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
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSiteCleanerName() {
		return siteCleanerName;
	}

	public void setSiteCleanerName(String siteCleanerName) {
		this.siteCleanerName = siteCleanerName;
	}

	public String getSiteCleanerPhone() {
		return siteCleanerPhone;
	}

	public void setSiteCleanerPhone(String siteCleanerPhone) {
		this.siteCleanerPhone = siteCleanerPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSiteProjectManagerOne() {
		return siteProjectManagerOne;
	}

	public void setSiteProjectManagerOne(String siteProjectManagerOne) {
		this.siteProjectManagerOne = siteProjectManagerOne;
	}

	public String getSiteProjectManagerPhoneOne() {
		return siteProjectManagerPhoneOne;
	}

	public void setSiteProjectManagerPhoneOne(String siteProjectManagerPhoneOne) {
		this.siteProjectManagerPhoneOne = siteProjectManagerPhoneOne;
	}

	public String getSiteProjectManagerTwo() {
		return siteProjectManagerTwo;
	}

	public void setSiteProjectManagerTwo(String siteProjectManagerTwo) {
		this.siteProjectManagerTwo = siteProjectManagerTwo;
	}

	public String getSiteProjectManagerPhoneTwo() {
		return siteProjectManagerPhoneTwo;
	}

	public void setSiteProjectManagerPhoneTwo(String siteProjectManagerPhoneTwo) {
		this.siteProjectManagerPhoneTwo = siteProjectManagerPhoneTwo;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getProjectIntroduce() {
		return projectIntroduce;
	}

	public void setProjectIntroduce(String projectIntroduce) {
		this.projectIntroduce = projectIntroduce;
	}

	public String getProjectScale() {
		return projectScale;
	}

	public void setProjectScale(String projectScale) {
		this.projectScale = projectScale;
	}

	public String getProjectPeriod() {
		return projectPeriod;
	}

	public void setProjectPeriod(String projectPeriod) {
		this.projectPeriod = projectPeriod;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getBuildUnitManager() {
		return buildUnitManager;
	}

	public void setBuildUnitManager(String buildUnitManager) {
		this.buildUnitManager = buildUnitManager;
	}

	public String getBuildUnitManagerPhone() {
		return buildUnitManagerPhone;
	}

	public void setBuildUnitManagerPhone(String buildUnitManagerPhone) {
		this.buildUnitManagerPhone = buildUnitManagerPhone;
	}

	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}

	public String getConstructionUnitManager() {
		return constructionUnitManager;
	}

	public void setConstructionUnitManager(String constructionUnitManager) {
		this.constructionUnitManager = constructionUnitManager;
	}

	public String getConstructionUnitManagerPhone() {
		return constructionUnitManagerPhone;
	}

	public void setConstructionUnitManagerPhone(String constructionUnitManagerPhone) {
		this.constructionUnitManagerPhone = constructionUnitManagerPhone;
	}

	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}

	public String getSupervisionUnitManager() {
		return supervisionUnitManager;
	}

	public void setSupervisionUnitManager(String supervisionUnitManager) {
		this.supervisionUnitManager = supervisionUnitManager;
	}

	public String getSupervisionUnitManagerPhone() {
		return supervisionUnitManagerPhone;
	}

	public void setSupervisionUnitManagerPhone(String supervisionUnitManagerPhone) {
		this.supervisionUnitManagerPhone = supervisionUnitManagerPhone;
	}

	public String getEarthworkUnit() {
		return earthworkUnit;
	}

	public void setEarthworkUnit(String earthworkUnit) {
		this.earthworkUnit = earthworkUnit;
	}

	public String getEarthworkUnitManager() {
		return earthworkUnitManager;
	}

	public void setEarthworkUnitManager(String earthworkUnitManager) {
		this.earthworkUnitManager = earthworkUnitManager;
	}

	public String getEarthworkUnitManagerPhone() {
		return earthworkUnitManagerPhone;
	}

	public void setEarthworkUnitManagerPhone(String earthworkUnitManagerPhone) {
		this.earthworkUnitManagerPhone = earthworkUnitManagerPhone;
	}

	public String getTransportUnit() {
		return transportUnit;
	}

	public void setTransportUnit(String transportUnit) {
		this.transportUnit = transportUnit;
	}

	public String getTransportUnitManager() {
		return transportUnitManager;
	}

	public void setTransportUnitManager(String transportUnitManager) {
		this.transportUnitManager = transportUnitManager;
	}

	public String getTransportUnitManagerPhone() {
		return transportUnitManagerPhone;
	}

	public void setTransportUnitManagerPhone(String transportUnitManagerPhone) {
		this.transportUnitManagerPhone = transportUnitManagerPhone;
	}

	public String getEntranceCleaners() {
		return entranceCleaners;
	}

	public void setEntranceCleaners(String entranceCleaners) {
		this.entranceCleaners = entranceCleaners;
	}

	public String getEntranceCleanersManager() {
		return entranceCleanersManager;
	}

	public void setEntranceCleanersManager(String entranceCleanersManager) {
		this.entranceCleanersManager = entranceCleanersManager;
	}

	public String getEntranceCleanersManagerPhone() {
		return entranceCleanersManagerPhone;
	}

	public void setEntranceCleanersManagerPhone(String entranceCleanersManagerPhone) {
		this.entranceCleanersManagerPhone = entranceCleanersManagerPhone;
	}

	public String getVideoSurveillance() {
		return videoSurveillance;
	}

	public void setVideoSurveillance(String videoSurveillance) {
		this.videoSurveillance = videoSurveillance;
	}

	public String getVideoSurveillanceManager() {
		return videoSurveillanceManager;
	}

	public void setVideoSurveillanceManager(String videoSurveillanceManager) {
		this.videoSurveillanceManager = videoSurveillanceManager;
	}

	public String getVideoSurveillanceManagerPhone() {
		return videoSurveillanceManagerPhone;
	}

	public void setVideoSurveillanceManagerPhone(String videoSurveillanceManagerPhone) {
		this.videoSurveillanceManagerPhone = videoSurveillanceManagerPhone;
	}

	public String getProjectInfoMemo() {
		return projectInfoMemo;
	}

	public void setProjectInfoMemo(String projectInfoMemo) {
		this.projectInfoMemo = projectInfoMemo;
	}
	
	public String getSiteProjectInfoId() {
		return siteProjectInfoId;
	}

	public void setSiteProjectInfoId(String siteProjectInfoId) {
		this.siteProjectInfoId = siteProjectInfoId;
	}

	public String getSiteProcessMemo() {
		return siteProcessMemo;
	}

	public void setSiteProcessMemo(String siteProcessMemo) {
		this.siteProcessMemo = siteProcessMemo;
	}

	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;
		if(StringUtils.isBlank(siteName)){
			return StatusCode.SITE_NAME_BLANK;
		}
		return statusCode;
	}
	
	@Override
	public Object[] validateUnique() {
		return new Object[]{this.siteName};
	}
}
