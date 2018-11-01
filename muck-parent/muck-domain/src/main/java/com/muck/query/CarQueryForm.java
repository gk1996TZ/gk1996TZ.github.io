package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 区域查询实体
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月23日 上午10:40:33
 */
public class CarQueryForm extends BaseForm {

	// 车牌号
	private String carCode;
	
	// 车牌颜色
	private String carCardColor;

	// 车主姓名
	private String carDriverName;

	// 车主联系电话
	private String carDriverPhone;

	// 车辆所属区域id
	private String areaId;
	
	// 车辆所属区域编号
	private String areaCode;

	// 车辆所属企业
	private String companyId;

	// 车辆所属企业名称
	private String companyName;
	
	//车辆所属企业负责人名称
	private String companyPrincipalName;
	
	//车辆所属企业负责人联系方式
	private String companyPrincipalPhone;
	
	// 车辆组id
	private String carGroupId;
	
	//车辆组名称
	private String carGroupName;
	
	//车辆GPS机号
	private String carGpsMachineNumber;
	
	//车辆型号
	private String carVehicleModel;
	
	//车辆锁定状态（1：表示没有锁定，0：表示锁定状态）
	private String isLock;
	
	//车辆运行状态
	private String isRunning;
	
	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getCarCardColor() {
		return carCardColor;
	}

	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}

	public String getCarDriverName() {
		return carDriverName;
	}

	public void setCarDriverName(String carDriverName) {
		this.carDriverName = carDriverName;
	}

	public String getCarDriverPhone() {
		return carDriverPhone;
	}

	public void setCarDriverPhone(String carDriverPhone) {
		this.carDriverPhone = carDriverPhone;
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
	
	public String getCompanyPrincipalName() {
		return companyPrincipalName;
	}

	public void setCompanyPrincipalName(String companyPrincipalName) {
		this.companyPrincipalName = companyPrincipalName;
	}

	public String getCompanyPrincipalPhone() {
		return companyPrincipalPhone;
	}

	public void setCompanyPrincipalPhone(String companyPrincipalPhone) {
		this.companyPrincipalPhone = companyPrincipalPhone;
	}
	
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
	
	public String getCarGpsMachineNumber() {
		return carGpsMachineNumber;
	}

	public void setCarGpsMachineNumber(String carGpsMachineNumber) {
		this.carGpsMachineNumber = carGpsMachineNumber;
	}
	
	public String getCarVehicleModel() {
		return carVehicleModel;
	}

	public void setCarVehicleModel(String carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}
	
	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public StatusCode validate() {
		return null;
	}
}
