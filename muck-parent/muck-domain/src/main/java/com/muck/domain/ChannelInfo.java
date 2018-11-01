package com.muck.domain;

public class ChannelInfo extends BaseEntity {

	// 工地或处置场名称
    private String Name;

    // 项目或工地地址
    private String projectAddress;

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
    
    // 视频监控
    private String videoSurveillance;

    // 视频监控负责人
    private String videoSurveillanceManager;

    // 视频监控负责人联系电话
    private String videoSurveillanceManagerPhone;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
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


}
