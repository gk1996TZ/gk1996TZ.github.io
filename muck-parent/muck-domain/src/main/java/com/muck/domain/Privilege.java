package com.muck.domain;

import java.util.ArrayList;
import java.util.List;

import com.muck.annotation.Table;

/**
* @Description: 权限实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月27日 上午11:33:43
 */
@Table(name = "t_privilege")
public class Privilege extends BaseEntity{

	// 权限名称
	private String privilegeName;
	
	// 权限的url
	private String privilegeUrl;
	
	// 此权限是否是公共的权限(true就是公共权限表示此权限不需要授权，false表示此权限需要授权来访问)
	private boolean isCommon = false;
	
	// 权限所属的模块
	private String privilegeModel;
	
	// 权限说明
	private String memo;
	
	// 上级权限
	private Privilege parent;
	
	// 下级权限
	private List<Privilege> childPrivileges = new ArrayList<Privilege>();

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeUrl() {
		return privilegeUrl;
	}

	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

	public boolean isCommon() {
		return isCommon;
	}

	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
	}

	public String getPrivilegeModel() {
		return privilegeModel;
	}

	public void setPrivilegeModel(String privilegeModel) {
		this.privilegeModel = privilegeModel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public List<Privilege> getChildPrivileges() {
		return childPrivileges;
	}

	public void setChildPrivileges(List<Privilege> childPrivileges) {
		this.childPrivileges = childPrivileges;
	}
}
