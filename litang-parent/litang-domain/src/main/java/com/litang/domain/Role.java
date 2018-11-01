package com.litang.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.litang.annotation.Table;

/**
 * 角色实体类
 */
@Table(name = "t_role")
public class Role extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1177089647193061672L;

	/**角色名称*/
	private String roleName;

	/**角色所拥有的权限*/
	private List<Authority> listAuthority = new ArrayList<Authority>();
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Authority> getListAuthority() {
		return listAuthority;
	}
	public void setListAuthority(List<Authority> listAuthority) {
		this.listAuthority = listAuthority;
	}
}
