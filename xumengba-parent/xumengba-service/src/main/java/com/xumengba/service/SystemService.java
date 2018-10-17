package com.xumengba.service;

import java.util.List;

import com.xumengba.domain.System;

public interface SystemService extends BaseService<System> {

	//获取系统设置信息
	public List<System>getSystem();
	
	//修改系统设置信息
	public void updateSystem(System system);
}
