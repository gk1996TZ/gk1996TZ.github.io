package com.muck.request;


import com.muck.response.StatusCode;

public class SnapshotImageForm extends BaseForm{

	// 工地id
	private String siteId;
	// 处置场id
	private String disposalId;
	// 停车场id
	private String carParkId;
	// 区域id
	private String areaId;
	// 企业id
	private String companyId;
	// 操作人的姓名
	private String operatorName;
	// 抓拍时间
	private String snapshotTime;
	//抓拍起始时间
	private String snapshotTimeStart;
	//抓拍结束时间
	private String snapshotTimeEnd;
	//抓拍类别 处置场，工地，停车场
	private String snapshotType;
	//车牌号
	private String carCode;
	//创建时间
	private String createdTime;
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getSnapshotTime() {
		return snapshotTime;
	}
	public void setSnapshotTime(String snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	public String getSnapshotTimeStart() {
		return snapshotTimeStart;
	}
	public void setSnapshotTimeStart(String snapshotTimeStart) {
		this.snapshotTimeStart = snapshotTimeStart;
	}
	public String getSnapshotTimeEnd() {
		return snapshotTimeEnd;
	}
	public void setSnapshotTimeEnd(String snapshotTimeEnd) {
		this.snapshotTimeEnd = snapshotTimeEnd;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getDisposalId() {
		return disposalId;
	}
	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}
	public String getCarParkId() {
		return carParkId;
	}
	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
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
	public String getSnapshotType() {
		return snapshotType;
	}
	public void setSnapshotType(String snapshotType) {
		this.snapshotType = snapshotType;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
}
