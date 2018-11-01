package com.muck.domain;

import com.muck.annotation.Table;

/**
 * 电子围栏与车辆的关联实体类
 * @Description:
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月8日 下午12:29:29
 */
@Table(name="t_electric_fence_car")
public class ElectricFenceCar extends BaseEntity{

	/**车辆id*/
	private String carId;
	/**车牌号*/
	private String carCode;
	/**车辆所属人*/
	private String carOwnerOfVehicle;
	/**企业id*/
	private String companyId;
	/**企业名称*/
	private String companyName;
	/**企业联系方式*/
	private String companyContact;
	/**电子围栏id*/
	private String electricFenceId;
	/**电子围栏名称*/
	private String electricFenceName;
	/**电子围栏坐标点*/
	private String electricFenceCoordinate;
	/**电子围栏是否启用1：启用0：禁用*/
	private Boolean electricFenceIsbanning;
	/**备注信息*/
	private String memo;
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarOwnerOfVehicle() {
		return carOwnerOfVehicle;
	}
	public void setCarOwnerOfVehicle(String carOwnerOfVehicle) {
		this.carOwnerOfVehicle = carOwnerOfVehicle;
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
	public String getCompanyContact() {
		return companyContact;
	}
	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}
	public String getElectricFenceId() {
		return electricFenceId;
	}
	public void setElectricFenceId(String electricFenceId) {
		this.electricFenceId = electricFenceId;
	}
	public String getElectricFenceName() {
		return electricFenceName;
	}
	public void setElectricFenceName(String electricFenceName) {
		this.electricFenceName = electricFenceName;
	}
	public String getElectricFenceCoordinate() {
		return electricFenceCoordinate;
	}
	public void setElectricFenceCoordinate(String electricFenceCoordinate) {
		this.electricFenceCoordinate = electricFenceCoordinate;
	}
	public Boolean getElectricFenceIsbanning() {
		return electricFenceIsbanning;
	}
	public void setElectricFenceIsbanning(Boolean electricFenceIsbanning) {
		this.electricFenceIsbanning = electricFenceIsbanning;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
