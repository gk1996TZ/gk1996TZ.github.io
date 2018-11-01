package com.muck.domain;

import java.util.Date;

import com.muck.handler.IdTypeHandler;

/**
* @Description: 实体基类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午12:08:56
*/
/**   
* @Description: 该类的功能描述
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 下午12:18:55 
*/
public class BaseEntity {
	
	// 对象主键id
	private String id;

	// 是否删除,默认是不删除(1:不删除,0:删除)
	private boolean deleted = true;
	
	// 操作人id
	private Long operatorId;
	
	// 操作人的姓名
	private String operatorName;
	
	// 操作人联系方式
	private String operatorPhone;
	
	// 创建时间,默认是当前时间
	private Date createdTime;

	// 修改时间,默认也是当前时间
	private Date updatedTime;
	
	// 定义一个idRaw的字段
	private long idRaw = -1L;
	
	// 是否是由大华数据同步过来
	private Boolean isSynDaHua;
	
	public String getId() {
		if(id == null && idRaw > 0){
			id = IdTypeHandler.encode(idRaw);
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorPhone() {
		return operatorPhone;
	}

	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
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

	public long getIdRaw() {
		return idRaw;
	}

	public void setIdRaw(long idRaw) {
		this.idRaw = idRaw;
	}

	public Boolean isSynDaHua() {
		return isSynDaHua;
	}

	public void setSynDaHua(Boolean isSynDaHua) {
		this.isSynDaHua = isSynDaHua;
	}
}
