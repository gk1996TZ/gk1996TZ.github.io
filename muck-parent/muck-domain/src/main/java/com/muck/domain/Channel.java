package com.muck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
* @Description: 通道实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月11日 下午3:31:11
 */
@Table(name="t_channel")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Channel extends BaseEntity{

	// 通道名称
    private String channelName;

    // 通道编码
    private String channelCode;

    // 通道状态
    private String channelStatus;

    // 通道类型
    private String channelType;

    // 通道摄像头类型
    private String channelCameraType;

    // 通道纬度
    private Double channelLatitude;

    // 通道经度
    private Double channelLongitude;

    // 通道说明
    private String memo;

    // 通道报警类别
    private String channelAlarmType;

    // 通道报警级别
    private String channelAlarmLevel;
    
    // 通道所属的设备
    @JsonIgnore
    private Device device;
    
    public Channel() {

    }

	public Channel(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(String channelStatus) {
		this.channelStatus = channelStatus;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelCameraType() {
		return channelCameraType;
	}

	public void setChannelCameraType(String channelCameraType) {
		this.channelCameraType = channelCameraType;
	}

	public Double getChannelLatitude() {
		return channelLatitude;
	}

	public void setChannelLatitude(Double channelLatitude) {
		this.channelLatitude = channelLatitude;
	}

	public Double getChannelLongitude() {
		return channelLongitude;
	}

	public void setChannelLongitude(Double channelLongitude) {
		this.channelLongitude = channelLongitude;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getChannelAlarmType() {
		return channelAlarmType;
	}

	public void setChannelAlarmType(String channelAlarmType) {
		this.channelAlarmType = channelAlarmType;
	}

	public String getChannelAlarmLevel() {
		return channelAlarmLevel;
	}

	public void setChannelAlarmLevel(String channelAlarmLevel) {
		this.channelAlarmLevel = channelAlarmLevel;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelCode == null) ? 0 : channelCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (channelCode == null) {
			if (other.channelCode != null)
				return false;
		} else if (!channelCode.equals(other.channelCode))
			return false;
		return true;
	}
}