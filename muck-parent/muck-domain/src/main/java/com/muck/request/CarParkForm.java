package com.muck.request;

import com.muck.annotation.Unique;
import com.muck.domain.Company;
import com.muck.handler.IdTypeHandler;
import com.muck.response.StatusCode;

/**
 * @Description: 提交停车场数据的请求类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月28日 上午10:46:31
 */
@Unique(uniqueFields = { "car_park_name","company_id" }, tableName = "t_car_park")
public class CarParkForm extends BaseForm{

	/**停车场id*/
	private String carParkId;
	/**停车场编号*/
	private String carParkCode;
	/**停车场名称*/
	private String carParkName;
	/**停车场id*/
	private String areaId;
	/**停车场所属区域编号*/
	private String areaCode;
	/**停车场所属区域名称*/
	private String areaName;
	/**停车场所属企业*/
	private Company company;
	/**企业id*/
	private String companyId;
	/**企业名称*/
	private String companyName;
	/**停车场备注信息*/
	private String memo;
	

	public String getCarParkId() {
		return carParkId;
	}

	public void setCarParkId(String carParkId) {
		this.carParkId = carParkId;
	}

	public String getCarParkCode() {
		return carParkCode;
	}

	public void setCarParkCode(String carParkCode) {
		this.carParkCode = carParkCode;
	}

	public String getCarParkName() {
		return carParkName;
	}

	public void setCarParkName(String carParkName) {
		this.carParkName = carParkName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public Object[] validateUnique() {
		return new Object[]{this.carParkName,IdTypeHandler.decode(this.companyId)};
	}
}
