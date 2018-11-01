package com.muck.domain;

public enum CompanyMemberType {
	CORPORATEREPRESENTATIVE(1,"4.企业法人代表简况"),
	COMPANYMANAGE(2,"5.企业经理简况"),
	VEHICLERESPONSIBLEPERSON(3,"6.企业车队负责人简况");
	
	//类型
	private Integer type;
	//类型名称
	private String name;
	
	private CompanyMemberType(Integer type,String name){
		this.type=type;
		this.name=name;
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
