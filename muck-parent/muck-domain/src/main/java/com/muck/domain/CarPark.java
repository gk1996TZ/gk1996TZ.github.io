package com.muck.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
 * @Description: 停车场实体类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:06:13
 */
@Table(name="t_car_park")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CarPark extends BaseEntity{

	/**停车场编号*/
	private String carParkCode;
	/**停车场名称*/
	private String carParkName;
	/**区域id*/
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
	public CarPark() {
	}
	
	public CarPark(String carParkCode, String carParkName, String areaId, String areaCode, String areaName,
			Company company, String companyId, String companyName, String memo) {
		super();
		this.carParkCode = carParkCode;
		this.carParkName = carParkName;
		this.areaId = areaId;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.company = company;
		this.companyId = companyId;
		this.companyName = companyName;
		this.memo = memo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
