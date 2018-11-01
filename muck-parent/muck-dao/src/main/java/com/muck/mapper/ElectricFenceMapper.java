package com.muck.mapper;

import java.util.List;

import com.muck.domain.ElectricFence;

public interface ElectricFenceMapper extends BaseMapper<ElectricFence> {
	

	
	//查询所有数据
	public List<ElectricFence>  selectAll();
	
	//根据名称查询数据
	public ElectricFence selectByName(String electricFenceName);
}
