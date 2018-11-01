package com.muck.query;

import com.muck.domain.BaseEntity;
import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

public class ChannelQueryForm extends BaseForm{

	/**区域id*/
	private String areaId;
	
	/**通道名称*/ 
    private String channelName;

    /**通道编码*/ 
    private String channelCode;

    /**通道状态*/ 
    private String channelStatus;

    /**通道类型*/ 
    private String channelType;

    /**通道摄像头类型*/ 
    private String channelCameraType;
    

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	@Override
	public StatusCode validate() {
		return null;
	}
}
