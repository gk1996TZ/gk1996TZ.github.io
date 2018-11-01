package com.muck.response;

/**
 * 返回车辆结果
 */
public class SimpleCarListResponse {

	// 车辆id
	private String id;

	// 车牌号
	private String carCode;
	// 车牌号
	private String carCardColor;

	// 企业id
	private String companyId;

	// 企业名称
	private String companyName;

	// 驾驶员名称
	private String carDriver;

	// 是否运行,默认运行(1:运行,0:不运行)
	private boolean isRunning = false;

	
	public String getCarCardColor() {
		return carCardColor;
	}

	public void setCarCardColor(String carCardColor) {
		this.carCardColor = carCardColor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
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

	public String getCarDriver() {
		return carDriver;
	}

	public void setCarDriver(String carDriver) {
		this.carDriver = carDriver;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}