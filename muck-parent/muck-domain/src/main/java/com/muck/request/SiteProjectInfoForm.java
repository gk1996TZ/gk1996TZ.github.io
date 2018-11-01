package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

@Unique(uniqueFields = { "project_name" }, tableName = "t_biz_site_project_info")
public class SiteProjectInfoForm extends BaseForm {

	// 项目id
	private String id;

	// 工地id
	private String siteId;

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

	// 土方单位
	private String earthworkUnit;

	// 土方单位负责人
	private String earthworkUnitManager;

	// 土方单位负责人联系电话
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

	// 设计单位
	private String designUnit;

	// 设计单位负责人
	private String designUnitManager;

	// 设计单位负责人联系电话
	private String designUnitManagerPhone;

	// 备注说明
	private String memo;

	// 工地设备注册码
	private String siteDeviceRegisterCode;

	// 项目所属区域
	private String areaName;

	// 项目进度描述
	private String projectInfoMemo;

	public String getProjectName() {
		return projectName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getDesignUnitManager() {
		return designUnitManager;
	}

	public void setDesignUnitManager(String designUnitManager) {
		this.designUnitManager = designUnitManager;
	}

	public String getDesignUnitManagerPhone() {
		return designUnitManagerPhone;
	}

	public void setDesignUnitManagerPhone(String designUnitManagerPhone) {
		this.designUnitManagerPhone = designUnitManagerPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSiteDeviceRegisterCode() {
		return siteDeviceRegisterCode;
	}

	public void setSiteDeviceRegisterCode(String siteDeviceRegisterCode) {
		this.siteDeviceRegisterCode = siteDeviceRegisterCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProjectInfoMemo() {
		return projectInfoMemo;
	}

	public void setProjectInfoMemo(String projectInfoMemo) {
		this.projectInfoMemo = projectInfoMemo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public StatusCode validate() {
		StatusCode statusCode = null;
		if (StringUtils.isBlank(projectName)) {
			statusCode = StatusCode.COMPANY_NAME_BLANK;
		}
		return statusCode;
	}

	@Override
	public Object[] validateUnique() {
		return new Object[] { projectName };
	}

}
