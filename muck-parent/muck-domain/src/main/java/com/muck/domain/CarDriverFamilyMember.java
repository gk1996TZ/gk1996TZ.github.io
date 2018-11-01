package com.muck.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 车辆驾驶员的家庭关系表
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CarDriverFamilyMember extends BaseEntity {

	// 所属车辆驾驶员
	private CarDriver carDriver;
	
	// 姓名
	private String name;

	// 联系方式
	private String phone;

	// 家庭关系
	private String relation;

	// 工作单位
	private String workUnit;

	// 说明描述
	private String memo;

	// 操作模块
	private String operatorModel;

	public CarDriver getCarDriver() {
		return carDriver;
	}

	public void setCarDriver(CarDriver carDriver) {
		this.carDriver = carDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(String operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "CarDriverFamilyMember [carDriver=" + carDriver + ", name=" + name + ", phone=" + phone + ", relation="
				+ relation + ", workUnit=" + workUnit + ", memo=" + memo + ", operatorModel=" + operatorModel + "]";
	}
}