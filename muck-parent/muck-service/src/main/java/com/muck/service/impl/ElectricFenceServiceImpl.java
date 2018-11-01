package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.ElectricFence;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ElectricFenceMapper;
import com.muck.service.ElectricFenceService;
import com.muck.utils.CollectionUtils;

@Service
public class ElectricFenceServiceImpl extends BaseServiceImpl<ElectricFence> implements ElectricFenceService {

	@Autowired
	private ElectricFenceMapper electricFenceMapper;
	
	
	
	//获取所有的电子围栏
	@Override
	public List<ElectricFence> queryAllElectricFence() {
		List<ElectricFence> electricFences=electricFenceMapper.selectAll();
		return CollectionUtils.toList(electricFences);
	}
	
	@Override
	public ElectricFence queryElectricFenceByName(String electricFenceName) {
		
		return electricFenceMapper.selectByName(electricFenceName);
		
	}
	

	@Override
	protected BaseMapper<ElectricFence> getMapper() {
		
		return electricFenceMapper;
	}

	@Override
	protected String getFields() {
		return "*";
	}

	

}
