package com.muck.request;

import com.muck.annotation.Unique;
import com.muck.response.StatusCode;

/**
 * @Description: 车辆组请求数据Form
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月31日 下午2:38:21
 */
@Unique(uniqueFields = { "group_name" }, tableName = "t_car_group")
public class CarGroupForm extends BaseForm{

	/**父级id*/
	private String parentId;
	
	/**车辆组id*/
	private String carGroupId;
	
	/**车辆组编号*/
	private String groupCode;
	
	/**车辆组名称*/
	private String groupName;

	/**车辆组所属企业id*/
	private String companyId;
	
	/**车辆组所属企业名称*/
	private String companyName;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCarGroupId() {
		return carGroupId;
	}

	public void setCarGroupId(String carGroupId) {
		this.carGroupId = carGroupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public StatusCode validate() {
		return null;
	}

	@Override
	public Object[] validateUnique() {
		return new String[]{groupName};
	}
	
}
