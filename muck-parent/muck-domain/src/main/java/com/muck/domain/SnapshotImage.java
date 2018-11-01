package com.muck.domain;

import java.util.Date;

import com.muck.annotation.Table;

/**
* @Description: 抓拍图片图片实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午2:00:59
 */
@Table(name = "t_snapshot_image")
public class SnapshotImage extends BaseEntity{

	/**抓拍时间,默认当前时间*/
	private Date snapshotTime = new Date();
	
	/**抓拍图片保存路径*/
	private String snapshotImagePath;
	
	/**图片类型*/
	private String snapshotType;
	
	/**工地id*/
	private String siteId;
	
	/**工地名称*/
	private String siteName;
	
	/**处置场id*/
	private String disposalId;
	
	/**处置场名称*/
	private String disposalName;
	
	/**停车场id*/
	private String carParkId;
	
	/**停车场名称*/
	private String carParkName;
	
	/**区域id*/
	private String areaId;
	
	/**区域名称*/
	private String areaName;
	
	/**企业id*/
	private String companyId;
	
	/**企业名称*/
	private String companyName;
	
	/**备注信息*/
	private String memo;
	
	/** 操作人姓名*/
	private String operatorName;
	
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String getSnapshotImagePath() {
		return snapshotImagePath;
	}

	public void setSnapshotImagePath(String snapshotImagePath) {
		this.snapshotImagePath = snapshotImagePath;
	}

	public String getSnapshotType() {
		return snapshotType;
	}

	public void setSnapshotType(String snapshotType) {
		this.snapshotType = snapshotType;
	}

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

	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}

	public String getCarParkId() {
		return carParkId;
	}

	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
	}

	public String getCarParkName() {
		return carParkName;
	}

	public void setCarParkName(String carParkName) {
		this.carParkName = carParkName;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
