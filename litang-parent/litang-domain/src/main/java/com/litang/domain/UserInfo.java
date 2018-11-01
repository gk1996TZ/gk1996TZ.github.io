package com.litang.domain;

import java.io.Serializable;

/**
 * 用户详情实体类
 */
public class UserInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 6576279912294647423L;
	/**用户主键id*/
	private String userId;
	/**用户性别*/
	private String userSex;
	/**用户出生年月*/
	private String userBrith;
	/**用户真实姓名*/
	private String userRealName;
	/**用户头像*/
	private String userHead;
	/**用户职务*/
	private String userJob;
	/**用户描述*/
	private String userDescribe;
	/**用户联系方式*/
	private String userPhone;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserBrith() {
		return userBrith;
	}
	public void setUserBrith(String userBrith) {
		this.userBrith = userBrith;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getUserJob() {
		return userJob;
	}
	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}
	public String getUserDescribe() {
		return userDescribe;
	}
	public void setUserDescribe(String userDescribe) {
		this.userDescribe = userDescribe;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
}
