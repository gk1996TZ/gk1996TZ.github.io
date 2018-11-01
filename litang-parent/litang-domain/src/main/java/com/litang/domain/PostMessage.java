package com.litang.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.litang.annotation.Table;

/**
 * 发布信息实体类
 * */
@Table(name="t_post_message")
public class PostMessage extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -6228486161894053660L;
	
	// 用户
	private User user;
	
	//发布信息内容
	private String messageContent;
	
	//发布地址信息
	private String messageAddress;
	
	//经度
	private Double messageLongitude;
	
	//维度
	private Double messageLatitude;
	
	
	public String getMessageAddress() {
		return messageAddress;
	}

	public void setMessageAddress(String messageAddress) {
		this.messageAddress = messageAddress;
	}

	public Double getMessageLongitude() {
		return messageLongitude;
	}

	public void setMessageLongitude(Double messageLongitude) {
		this.messageLongitude = messageLongitude;
	}

	public Double getMessageLatitude() {
		return messageLatitude;
	}

	public void setMessageLatitude(Double messageLatitude) {
		this.messageLatitude = messageLatitude;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//发布的图片
	private List<Image>images=new ArrayList<Image>();
	
	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

}
