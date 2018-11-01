package com.muck.domain;

import com.muck.annotation.Table;

@Table(name="t_electric_fence")
public class ElectricFence extends BaseEntity {
	
	//电子围栏名称
	private String electricFenceName;
	
	//是否禁用
	private  boolean electricFenceIsbanning=true;
	
	//电子围栏坐标点
	private String  electricFenceCoordinate;
	
	//备注
	private String  memo;

	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getElectricFenceName() {
		return electricFenceName;
	}

	public void setElectricFenceName(String electricFenceName) {
		this.electricFenceName = electricFenceName;
	}

	public boolean isElectricFenceIsbanning() {
		return electricFenceIsbanning;
	}

	public void setElectricFenceIsbanning(boolean electricFenceIsbanning) {
		this.electricFenceIsbanning = electricFenceIsbanning;
	}

	public String getElectricFenceCoordinate() {
		return electricFenceCoordinate;
	}

	public void setElectricFenceCoordinate(String electricFenceCoordinate) {
		this.electricFenceCoordinate = electricFenceCoordinate;
	}
	
	

	
	
	
}