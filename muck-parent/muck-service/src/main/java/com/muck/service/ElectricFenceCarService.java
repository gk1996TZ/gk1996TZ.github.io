package com.muck.service;

import java.util.List;
import java.util.Map;

import com.muck.domain.ElectricFenceCar;

/**
 * @Description: 电子围栏车辆信息Service
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月8日 下午4:08:48
 */
public interface ElectricFenceCarService extends BaseService<ElectricFenceCar>{

	/**
	 * @Description: 查询电子围栏车辆信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午4:20:04
	 * @param: carCode 传入一个车牌号
	 * @return:ElectricFenceCar 返回电子围栏车辆信息
	 */
	public ElectricFenceCar queryByCarCode(String carCode);
	/**
	 * @Description: 根据电子围栏id查询电子围栏车辆信息列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午4:10:06
	 * @param: electricFenceId 传入一个电子围栏id
	 * @return:List<ElectricFenceCar> 返回电子围栏车辆信息列表
	 */
	public List<ElectricFenceCar> queryElectricFenceCarsByElectricFenceId(String electricFenceId);
	
	
	/**
	 * @Description: 根据车牌号查询电子围栏车辆信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月9日  下午7:55:33
	 * @param: carCodes 传入一个车牌号列表
	 * @return:List<ElectricFenceCar> 返回电子围栏车辆信息
	 */
	public List<ElectricFenceCar> queryElectricFenceCarSimpleByCarCodes(String carCodes);
	
	
	/**
	 * 	根据车牌号查询电子围栏
	*/
	public List<Map<String,Object>> queryElectricFenceCarByCarCode(String carCode);
}
