package com.xumengba.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.System;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.mapper.SystemMapper;
import com.xumengba.service.SystemService;
import com.xumengba.utils.CollectionUtils;
@Service
public class SystemServiceImpl extends BaseServiceImpl<System> implements SystemService {

	

	@Autowired
	private SystemMapper systemMapper;
	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	protected BaseMapper<System> getMapper() {
		return null;
	}

	@Override
	public List<System> getSystem() {
		return CollectionUtils.toList(systemMapper.getSystem());
	}

	@Override
	public void updateSystem(System system) {
		
		if(system==null){
			throw new RuntimeException("系统提示为空");
		}
		system.setUpdatedTime(new Date());
		systemMapper.updateSystem(system);
		
	}
	
	

}
