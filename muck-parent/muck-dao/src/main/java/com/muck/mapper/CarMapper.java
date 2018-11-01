package com.muck.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Car;
import com.muck.domain.Statistics;
import com.muck.query.CarQueryForm;
import com.muck.response.SimpleCarListResponse;

/**
 * @Description: 车辆Mapper
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月11日 下午2:13:46
 */
@Repository
public interface CarMapper extends BaseMapper<Car>{

	/**
	 * @Description: 根据车牌号查询车辆
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午5:06:41
	 * @param: carCode 传入一个车牌号
	 * @return:Car 返回车辆信息
	 */
	public Car queryByCarCode(@Param("carCode")String carCode);
	
	/**
	 * @Description: 根据身份证号查询车辆
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午6:05:20
	 * @Param: idNumber 传入一个身份证号
	 * @Return: Car 返回一个车辆
	 */
	public Car queryByIdNumber(@Param("idNumber")String idNumber);

	/**
	 * @Description: 根据车牌号修改车辆信息
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月3日  下午2:48:04
	 * @Param:car 传入一个需要被修改的车辆信息
	 */
	public void editByCarCode(Car car);

	/**
	 * 根据车牌号和颜色查询车辆
	 * */
	public Car selectCarByCarCodeAndColor(@Param("carCode")String carCode,@Param("carColor")String carColor);
	/**
	 * @Description: 根据车牌号和车牌颜色查询车辆
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月24日 下午10:47:35
	 * @Param: carCode 传入一个车牌号
	 * @Param: carCardColor 传入一个车牌颜色
	 * @Return: Car 返回一个车辆信息
	 */
	public Car selectCarByCarCodeAndCarCardColor(@Param("carCode")String carCode,@Param("carCardColor")String carCardColor);
	
	// --------------- 前台 -------------------
	
	/**
	 * 	根据企业id,车牌号查询车辆列表(不带分页)
	*/
	public List<SimpleCarListResponse> selectCarsByCondition(CarQueryForm queryForm);

	/***
	 * 	获取车辆的车牌号和车辆颜色的车辆List集合,主要是组装获取车辆GPS数据的请求参数
	*/
	public List<Map<String, Object>> selectCarNoAndColorList(@Param("typey")Integer type);

	
	/**
	 * 查询所有的车辆
	 * */
	
	public List<Car> queryAll();
	
	/**根据企业统计车的总数*/
	public List<Statistics> statisticsCompCarCout();
	/**根据车辆组统计车的总数*/
	public List<Statistics> statisticsCarGroupCout();
	/**更新运行状态*/
	public void updateRunning(@Param("isRunning")boolean b);


	/**
	 * @Description: 根据车牌号列表查询车辆
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午9:17:39
	 * @param: carCodes 传入一个车牌号列表，用逗号分隔
	 * @return:List<Car> 返回车辆列表
	 */
	public List<Car> queryCarByCarCodes(@Param("carCodes")String carCodes);
	
	
	/**
	 * @Description: 查询条件查询车辆列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午10:17:57
	 * @param: sql 传入一个sql语句
	 * @return:List<Car> 返回一个车辆列表
	 */
	public List<Car> queryDataForElectricFence(@Param("sql")String sql);
	
	
	/**
	 * @Description: 批量修改车辆isRunning状态
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月9日  上午12:39:17
	 * @param: isRunning 传入一个状态
	 * @param: carCodes 传入多个车牌号，用逗号分隔
	 * @return:Integer 返回受影响行数
	 */
	public Integer updateCarsIsRunning(@Param("isRunning")Integer isRunning,@Param("carCodes")String carCodes);

	
	
	
	/**
	 * @Description: 维护企业和车辆的关系
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午12:12:59
	 */
	public void setCompanyAndCarRelation();
}
