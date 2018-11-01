package com.muck.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 	简单的企业返回实体
*/
public class SimpleCompanyResponse {

	// 企业id
	private String companyId;
	
	// 企业名称
	private String companyName;
	
	// 企业下面的所有车辆
	private List<String> cars = new ArrayList<String>();
	
	// 正在运行的车辆（默认为0）
	private Integer runningCarCount = 0;
	// 企业下面的当前运行的车辆
	private List<String> runningCars = new ArrayList<String>();
	
	// 企业下面的一级车辆组
	private List<SimpleCarGroupResponse> carGroups = new ArrayList<SimpleCarGroupResponse>();

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

	public List<String> getCars() {
		return cars;
	}

	public void setCars(List<String> cars) {
		this.cars = cars;
	}

	public Integer getRunningCarCount() {
		return runningCarCount;
	}

	public void setRunningCarCount(Integer runningCarCount) {
		this.runningCarCount = runningCarCount;
	}
	
	public List<String> getRunningCars() {
		return runningCars;
	}

	public void setRunningCars(List<String> runningCars) {
		this.runningCars = runningCars;
	}
	
	public List<SimpleCarGroupResponse> getCarGroups() {
		return carGroups;
	}

	public void setCarGroups(List<SimpleCarGroupResponse> carGroups) {
		this.carGroups = carGroups;
	}

	@Override
	public String toString() {
		return "SimpleCompanyResponse [companyId=" + companyId + ", companyName=" + companyName + ", runningCarCount="
				+ runningCarCount + ", cars=" + cars + "]";
	}
}
