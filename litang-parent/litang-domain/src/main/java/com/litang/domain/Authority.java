package com.litang.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.litang.annotation.Table;

/**
 * 权限实体类
 */
@Table(name="t_authority")
public class Authority extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -358140425200496277L;
	/**权限名称*/
	private String authorityName;
	/**权限url*/
	private String authorityUrl;
	/**是否是公共权限*/
	private Boolean isCommon;
	/**权限描述*/
	
	//权限的所有子权限
	private List<Authority>authorities=new ArrayList<Authority>();
	
	private String authorityDescribe;
	
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getAuthorityUrl() {
		return authorityUrl;
	}
	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}
	
	public Boolean getIsCommon() {
		return isCommon;
	}
	public void setIsCommon(Boolean isCommon) {
		this.isCommon = isCommon;
	}
	public String getAuthorityDescribe() {
		return authorityDescribe;
	}
	public void setAuthorityDescribe(String authorityDescribe) {
		this.authorityDescribe = authorityDescribe;
	}
}
