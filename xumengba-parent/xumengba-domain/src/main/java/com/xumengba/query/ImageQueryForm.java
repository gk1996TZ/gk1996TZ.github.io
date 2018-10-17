package com.xumengba.query;

import com.xumengba.response.StatusCode;

public class ImageQueryForm extends BaseForm{

	/**图片id*/
	private String id;
	/**类别id*/
	private String categoryId;
	/**类别名称*/
	private String categoryName;
	/**图片标题*/
	private String imageTitle;
	/**图片排序序号*/
	private Integer imageSeq;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public Integer getImageSeq() {
		return imageSeq;
	}
	public void setImageSeq(Integer imageSeq) {
		this.imageSeq = imageSeq;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
}
