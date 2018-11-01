package com.litang.domain;

import java.io.Serializable;
import java.util.Date;

import com.litang.handler.IdTypeHandler;

/**
 * 实体类基类
 */
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = -5232796509807692050L;
	/**主键id*/
	private String id;
	/**是否删除(true:不删除，false:删除)*/
	private Boolean deleted;
	/**创建时间*/
	private Date createdTime;
	/**修改时间*/
	private Date updatedTime;
	/**更新操作返回的主键id*/
	private Long idRow = -1L;
	public String getId() {
		if(id == null && idRow > 0){
			id = IdTypeHandler.encode(idRow);
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
	public Long getIdRow() {
		return idRow;
	}
	public void setIdRow(Long idRow) {
		this.idRow = idRow;
	}
}
