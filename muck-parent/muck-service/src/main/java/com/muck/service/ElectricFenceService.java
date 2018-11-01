package com.muck.service;

import java.util.List;

import com.muck.domain.ElectricFence;

public interface ElectricFenceService extends BaseService<ElectricFence> {

	
	//查询所有电子围栏
	public List<ElectricFence> queryAllElectricFence();
	
	//根据电子围栏名称查询
	public ElectricFence queryElectricFenceByName(String electricFenceName);
}
