package com.muck.query;

import java.util.Date;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 告警Form 
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月4日 下午12:24:02
 */
public class WarningQueryForm extends BaseForm {

	// 告警id
	private String id;
	// 设备id
	private String deviceId;

	// 设备编号
	private String deviceCode;

	// 通道号
	private String channelCode;
	
	// 处置场id
	private String disposalId;

	// 工地id
	private String siteId;

	// 停车场id
	private String carParkId;
	
	// 车牌号
	private String carCode;

	// 告警类型
	private Integer warningType;

	// 告警状态
	private Integer warningStatus;

	// 告警信息
	private String warningContent;

	// 是否处理(默认是未处理)
	private Integer isHandle;

	// 时间起止
	private String timeStartEnd;
	
	// 开始时间
	private Date beginDate;

	// 结束时间
	private Date endDate;

	// 备注信息
	private String memo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDisposalId() {
		return disposalId;
	}

	public void setDisposalId(String disposalId) {
		this.disposalId = disposalId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getCarParkId() {
		return carParkId;
	}

	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getWarningType() {
		return warningType;
	}

	public void setWarningType(Integer warningType) {
		this.warningType = warningType;
	}

	public Integer getWarningStatus() {
		return warningStatus;
	}

	public void setWarningStatus(Integer warningStatus) {
		this.warningStatus = warningStatus;
	}

	public String getWarningContent() {
		return warningContent;
	}

	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}

	public Integer getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
	}

	public String getTimeStartEnd() {
		return timeStartEnd;
	}

	public void setTimeStartEnd(String timeStartEnd) {
		this.timeStartEnd = timeStartEnd;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public StatusCode validate() {
		return null;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
