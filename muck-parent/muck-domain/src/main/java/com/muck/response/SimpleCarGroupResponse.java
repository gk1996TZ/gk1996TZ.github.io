package com.muck.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 	
*/
public class SimpleCarGroupResponse {

	// 车辆组id
	private String carGroupId;

	// 车辆组名称
	private String carGroupName; 
	
	// 车辆组下面所有的车辆,保存的车牌号
	private List<String> cars = new ArrayList<String>();
	
	// 车辆组下面运行的车辆
	private Integer runningCarCount;

	public String getCarGroupId() {
		return carGroupId;
	}

	public void setCarGroupId(String carGroupId) {
		this.carGroupId = carGroupId;
	}

	public String getCarGroupName() {
		return carGroupName;
	}

	public void setCarGroupName(String carGroupName) {
		this.carGroupName = carGroupName;
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
}
