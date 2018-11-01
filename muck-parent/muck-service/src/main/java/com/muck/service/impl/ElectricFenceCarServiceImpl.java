package com.muck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.ElectricFenceCar;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ElectricFenceCarMapper;
import com.muck.service.ElectricFenceCarService;

/**
 * @Description: 电子围栏车辆Service实现
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月8日 下午12:52:42
 */
@Service
public class ElectricFenceCarServiceImpl extends BaseServiceImpl<ElectricFenceCar> implements ElectricFenceCarService{

	@Autowired
	private ElectricFenceCarMapper electricFenceCarMapper;
	
	@Override
	public ElectricFenceCar queryByCarCode(String carCode) {
		return electricFenceCarMapper.queryByCarCode(carCode);
	}
	@Override
	public List<ElectricFenceCar> queryElectricFenceCarsByElectricFenceId(String electricFenceId) {
		return electricFenceCarMapper.queryElectricFenceCarsByElectricFenceId(electricFenceId);
	}
	@Override
	public List<ElectricFenceCar> queryElectricFenceCarSimpleByCarCodes(String carCodes) {
		return electricFenceCarMapper.queryElectricFenceCarSimpleByCarCodes(carCodes);
	}
	@Override
	public List<Map<String,Object>> queryElectricFenceCarByCarCode(String carCode) {
		return electricFenceCarMapper.queryElectricFenceCarByCarCode(carCode);
	}
	// =====================================
	@Override
	protected BaseMapper<ElectricFenceCar> getMapper() {
		return electricFenceCarMapper;
	}
	@Override
	protected String getFields() {
		return "*";
	}
}
