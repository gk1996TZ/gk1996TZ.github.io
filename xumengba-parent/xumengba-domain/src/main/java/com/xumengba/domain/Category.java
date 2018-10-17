package com.xumengba.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;
@Table(name="t_category")
public class Category extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -154087980971229484L;
	/**分类名称*/
	@Field(name="category_name")
	private String categoryName;
	/**分类描述*/
	@Field(name="memo")
	private String memo;
	
//	private List<Category>subCategories=new ArrayList<Category>();
	

//	@Field(name="parent_id")
//	private String parentId;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
