package com.muck.domain;

/**
 * 	设备类型
*/
public enum DeviceType {

	SITE(1,"工地"),
	CAR(2,"车辆"),
	DISPOSAL(3,"处置场");
	
	//  类型
	private Integer type;
	
	// 类型名称
	private String name;
	
	private DeviceType(Integer type,String name){
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
