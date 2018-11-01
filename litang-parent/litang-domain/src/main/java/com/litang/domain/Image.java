package com.litang.domain;

import java.io.Serializable;

import com.litang.annotation.Table;

@Table(name="t_image")
public class Image extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -1354092263021854999L;

	//图片的保存路径
	private String imageUrl;
	
	//图片类型
	private Integer imageType;
	
	//图片缩略图保存路径
	private String imageUrl_SL;
	
	//uuid
	private String fileName;
	

	public String getImageUrl_SL() {
		return imageUrl_SL;
	}

	public void setImageUrl_SL(String imageUrl_SL) {
		this.imageUrl_SL = imageUrl_SL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	} 
	
	
	
}
