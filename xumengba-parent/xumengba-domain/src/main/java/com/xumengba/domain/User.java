package com.xumengba.domain;

import java.io.Serializable;
import java.util.Date;

import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;

/**
 * 用户实体类
 */
@Table(name="t_user")
public class User extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 7818231660385316148L;
	/**用户名*/
	@Field(name="user_name")
	private String userName;
	/**用户真实姓名*/
	@Field(name="user_real_name")
	private String userRealName;
	/**用户昵称*/
	@Field(name="user_nick_name")
	private String userNickName;
	/**用户密码*/
	@Field(name="user_pwd")
	private String userPwd;
	/**用户联系方式*/
	@Field(name="user_phone")
	private String userPhone;
	/**用户邮箱*/
	@Field(name="user_mail")
	private String userMail;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
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
