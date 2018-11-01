package com.litang.request;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.litang.bean.ImagePath;
import com.litang.domain.Image;
import com.litang.domain.UserLocation;
import com.litang.response.StatusCode;

public class PostMessageForm extends BaseForm{

	// 用户id
	private String userId;
	
	// 发布信息
	private String messageContent;
	
	// 经度
	private Double longitude;
	
	//纬度
	private Double latitude;
	
	//地址
	private String location;
	
//	private List<ImagePath>imagePaths;
//	
//	public List<ImagePath> getImagePaths() {
//		return imagePaths;
//	}
//
//	public void setImagePaths(List<ImagePath> imagePaths) {
//		this.imagePaths = imagePaths;
//	}

	//发布地址信息
	private UserLocation userLocation;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserLocation getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(UserLocation userLocation) {
		this.userLocation = userLocation;
	}

	
	@Override
	public StatusCode validate() {

		StatusCode statusCode = null;
		
		if(StringUtils.isBlank(messageContent)){
			statusCode = StatusCode.POST_MESSAGE_CONTENT_NULL;
		}
		return statusCode;
	}
}
