package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Area;
import com.muck.domain.CarPark;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CarParkMapper;
import com.muck.service.CarParkService;

/**
 * @Description: 停车场Service实现
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:17:01
 */
@Service
public class CarParkServiceImpl extends BaseServiceImpl<CarPark> implements CarParkService{

	@Autowired
	CarParkMapper carParkMapper;
	
	@Override
	public List<Area> queryCarParkTree() {
		return carParkMapper.queryCarParkTree();
	}
	// ---------------------------------------------------------------
	@Override
	protected BaseMapper<CarPark> getMapper() {
		return carParkMapper;
	}
	@Override
	protected String getFields() {
		return "*";
	}
}
