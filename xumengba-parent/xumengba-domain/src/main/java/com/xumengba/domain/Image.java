package com.xumengba.domain;

import java.io.Serializable;
import java.util.Date;

import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;

/**
 * 图片实体类
 */
@Table(name="t_image")
public class Image extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -9163903274141857L;
	/**图片标题*/
	@Field(name="image_title")
	private String imageTitle;
	/**图片路径*/
	@Field(name="image_url")
	private String imageUrl;
	/**图片类别*/
	@Field(name="category_id")
	private String categoryId;
	/**类别名称*/
	private String categoryName;
	/**图片序号*/
	@Field(name="image_seq")
	private Integer imageSeq;
	/**备注信息*/
	@Field(name="memo")
	private String memo;
	/**自增id*/
	@Field(name="id")
	private String id;
	/**创建时间*/
	@Field(name="created_time")
	private Date createdTime;
	/**修改时间*/
	@Field(name="updated_time")
	private Date updatedTime;
	/**是否删除true：不删除，false删除*/
	@Field(name="deleted")
	private Boolean deleted;
	/**定义一个idRaw字段*/
	private Long idRaw;
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public Integer getImageSeq() {
		return imageSeq;
	}
	public void setImageSeq(Integer imageSeq) {
		this.imageSeq = imageSeq;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getId() {
		if(id == null && idRaw > 0){
			id = IdTypeHandler.encode(idRaw);
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Long getIdRaw() {
		return idRaw;
	}
	public void setIdRaw(Long idRaw) {
		this.idRaw = idRaw;
	}
}
