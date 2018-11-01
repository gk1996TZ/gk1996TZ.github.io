package com.litang.bean;

public class ImagePath {
	
	// 图片uuid
	private String uuid;
	
	// 图片原图路径
	private String srcPath;
	
	// 图片的缩略图路径
	private String thumPath;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getThumPath() {
		return thumPath;
	}

	public void setThumPath(String thumPath) {
		this.thumPath = thumPath;
	}

	@Override
	public String toString() {
		return "ImagePath [uuid=" + uuid + ", srcPath=" + srcPath + ", thumPath=" + thumPath + "]";
	}
}
