package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

public class ElectricFenceQueryForm extends BaseForm {

	// 电子围栏id
	private String electricFenceId;

	// 电子围栏名称
	private String electricFenceName;

	// 电子围栏坐标点
	private String[] electricFenceCoordinateArr;

	// 备注
	private String memo;

	// 是否警用
	private boolean electricFenceIsbanning;

	public boolean isElectricFenceIsbanning() {
		return electricFenceIsbanning;
	}

	public void setElectricFenceIsbanning(boolean electricFenceIsbanning) {
		this.electricFenceIsbanning = electricFenceIsbanning;
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

	public String[] getElectricFenceCoordinateArr() {
		return electricFenceCoordinateArr;
	}

	public void setElectricFenceCoordinateArr(String[] electricFenceCoordinateArr) {
		this.electricFenceCoordinateArr = electricFenceCoordinateArr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public StatusCode validate() {
		return null;
	}

}
