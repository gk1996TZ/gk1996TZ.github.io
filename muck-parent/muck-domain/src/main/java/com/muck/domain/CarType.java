package com.muck.domain;

/**
 * 	车辆的类型
 * 		1、长宝公司
 * 		2、北斗公司
*/
public enum CarType {

	CHANG_BAO(1,"长宝"),
	BEI_DOU(2,"北斗");
	
	//  类型
	private Integer type;
	
	// 类型名称
	private String name;
	
	private CarType(Integer type,String name){
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
