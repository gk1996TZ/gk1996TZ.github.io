package com.muck.domain;

/**
 * 	区域的类型(1:工地 2:处置场)
*/
public enum AreaType {

	SITE(1,"工地"),
	DISPOSAL(3,"处置场");
	
	//  类型
	private Integer type;
	
	// 类型名称
	private String name;
	
	private AreaType(Integer type,String name){
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
