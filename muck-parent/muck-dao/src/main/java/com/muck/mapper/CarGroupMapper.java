package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Car;
import com.muck.domain.CarGroup;
import com.muck.domain.Company;

/**
 * @Description: 车辆组Mapper
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月30日 上午10:39:23
 */
@Repository
public interface CarGroupMapper extends BaseMapper<CarGroup>{

	/**
	 * @Description:查询根据车辆组树(企业>车辆组)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日  上午11:01:25
	 * @return:List<Area> 返回车辆组树(区域>企业>车辆组)
	 */
	public List<Company> queryCarGroupTree(@Param("companyId")String companyId);
	
	/**
	 * @Description: 根据车辆组id查询第二级的车辆组
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月4日  上午11:01:20
	 * @param:carGroupIds 传入一个用逗号分割的多个车辆组id
	 * @return:List<CarGroup> 返回车辆组列表
	 */
	public List<CarGroup> queryCarGroupAccess(@Param("carGroupIds")String carGroupIds);

	
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
	public CarGroup queryByCarGroupName(@Param("carGroupName")String carGroupName);

	/**
	 * 	根据车辆组名称和企业id查询车辆组
	*/
	public CarGroup selectByGroupNameAndCompany(
				@Param("carGroupName")String carGroupName, 
				@Param("companyId")String companyId);
}
