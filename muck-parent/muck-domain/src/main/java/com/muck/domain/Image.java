package com.muck.domain;

/**
 * 	图片实体
*/
public class Image extends BaseEntity{
    
    /** 图片路径*/
    private String imagePath;

    /** 图片说明*/
    private String memo;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}