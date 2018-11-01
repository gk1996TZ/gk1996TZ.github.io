package com.muck.service;

import java.util.List;

import com.muck.domain.Car;
import com.muck.domain.CarGroup;
import com.muck.domain.Company;

/**
 * @Description: 车辆组Service
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月30日 上午10:37:48
 */
public interface CarGroupService extends BaseService<CarGroup>{

	/**
	 * @Description:查询根据车辆组树(区域>企业>车辆组)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日  上午11:01:25
	 * @return:List<Company> 返回车辆组树(企业>车辆组)
	 */
	public List<Company> queryCarGroupTree(String companyId);
	/**
	 * @Description: 根据车辆组id获取车辆组里的车辆列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日  上午11:46:44
	 * @param:@param carGroupId 传入一个车辆组id
	 * @return:List<Car> 返回当前车辆组里的车辆列表
	 */
	public List<Car> queryCarByGroupId(String carGroupId);
	
	/**
	 * @Description: 根据车辆组名称查询车辆组
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月12日  上午12:07:39
	 * @param: carGroupName 传入一个车辆组名称
	 * @return:CarGroup
	 */
	public CarGroup queryByCarGroupName(String carGroupName);
}
