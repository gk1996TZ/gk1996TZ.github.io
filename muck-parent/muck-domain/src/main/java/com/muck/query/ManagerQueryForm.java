package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * 	用户查询实体
*/
public class ManagerQueryForm extends BaseForm{

	// 用户联系方式
	private String managerPhone;
	
	// 用户姓名
	private String managerName;
	
	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	@Override
	public StatusCode validate() {
		return null;
	}
}
