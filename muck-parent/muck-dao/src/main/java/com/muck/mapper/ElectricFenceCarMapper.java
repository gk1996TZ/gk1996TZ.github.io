package com.muck.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.ElectricFenceCar;

/**
 * @Description: 电子围栏车辆关联的 Mapper
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月8日 下午12:54:42
 */
@Repository
public interface ElectricFenceCarMapper extends BaseMapper<ElectricFenceCar>{

	
	/**
	 * @Description: 查询电子围栏车辆信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午4:20:04
	 * @param: carCode 传入一个车牌号
	 * @return:ElectricFenceCar 返回电子围栏车辆信息
	 */
	public ElectricFenceCar queryByCarCode(@Param("carCode")String carCode);
	/**
	 * @Description: 根据电子围栏id查询电子围栏车辆信息列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午4:10:06
	 * @param: electricFenceId 传入一个电子围栏id
	 * @return:List<ElectricFenceCar> 返回电子围栏车辆信息列表
	 */
	public List<ElectricFenceCar> queryElectricFenceCarsByElectricFenceId(@Param("electricFenceId")String electricFenceId);


	/**
	 * @Description: 根据车牌号查询电子围栏车辆信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月9日  下午7:55:33
	 * @param: carCodes 传入一个车牌号列表
	 * @return:List<ElectricFenceCar> 返回电子围栏车辆信息
	 */
	public List<ElectricFenceCar> queryElectricFenceCarSimpleByCarCodes(@Param("carCodes")String carCodes);
	
	/***
	 * 	根据车牌号查询电子围栏
	*/
	public List<Map<String,Object>> queryElectricFenceCarByCarCode(@Param("carCode")String carCode);
}
