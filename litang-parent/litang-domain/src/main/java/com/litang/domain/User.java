package com.litang.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.litang.annotation.Table;

/**
 * 用户实体类
 */
@Table(name="t_user")
public class User extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -9004701258047588282L;
	/**用户名（非真实姓名）*/
	private String userName;
	/**用户手机号*/
	private String userPhone;
	/**用户密码*/
	private String userPwd;
	/**用户TOKEN*/
	private String userTokenCode;
	/**用户详情*/
	private UserInfo userInfo;
	/**用户的状态
	 * 0.审核不通过 1.审核通过 2.待审核
	 * */
	private Integer userState;
	
	
	/**用户所拥有的角色*/
	private List<Role> listRole = new ArrayList<Role>();
	public User(){
		
	}
	
	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserTokenCode() {
		return userTokenCode;
	}
	public void setUserTokenCode(String userTokenCode) {
		this.userTokenCode = userTokenCode;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<Role> getListRole() {
		return listRole;
	}
	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}
}
