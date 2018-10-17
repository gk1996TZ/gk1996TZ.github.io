package com.xumengba.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;

@Table(name="t_system")
public class System extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -4919659534810059851L;
	/**版权信息*/
	@Field(name="copyright")
	private String copyright;
	/**推荐图片的宽度*/
	@Field(name="rec_image_width")
	private String recImageWidth;
	/**推荐图片的高度*/
	@Field(name="rec_image_height")
	private String recImageHeight;
	/**自增id*/
	@JsonIgnore
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
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getRecImageWidth() {
		return recImageWidth;
	}
	public void setRecImageWidth(String recImageWidth) {
		this.recImageWidth = recImageWidth;
	}
	public String getRecImageHeight() {
		return recImageHeight;
	}
	public void setRecImageHeight(String recImageHeight) {
		this.recImageHeight = recImageHeight;
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
