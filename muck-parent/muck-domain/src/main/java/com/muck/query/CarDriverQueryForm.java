package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * 	车辆驾驶员查询实体
*/
public class CarDriverQueryForm extends BaseForm{

	// 驾驶员名称
	private String driverName;
	
	// 驾驶员车牌号
	private String carCode;
	
	// 驾驶员工作单位id
	private String companyId;
	
	// 工作单位
	private String companyName;
	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public StatusCode validate() {
		return null;
	}
}
