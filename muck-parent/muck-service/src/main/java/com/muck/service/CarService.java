package com.muck.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.Car;
import com.muck.domain.CarAndOrCarDriver;
import com.muck.domain.Statistics;
import com.muck.query.CarQueryForm;
import com.muck.response.SimpleCarListResponse;

/**
* @Description: 车辆Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:19:47
 */
public interface CarService extends BaseService<Car>{

	
	/**
	 * @Description: 查询条件查询车辆列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午10:17:57
	 * @param:whereSQL 传入一个where条件
	 * @param:whereParams 传入一个where参数
	 * @return:List<Car> 返回一个车辆列表
	 */
	public List<Car> queryDataForElectricFence(String whereSQL, List<Object> whereParams);
	
	/**
	 * @Description: 根据车牌号查询车辆
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午5:06:41
	 * @param: carCode 传入一个车牌号
	 * @return:Car 返回车辆信息
	 */
	public Car queryByCarCode(String carCode);
	/**
	 * @Description: 根据身份证号查询车辆
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午6:05:20
	 * @Param: idNumber 传入一个身份证号
	 * @Return: Car 返回一个车辆
	 */
	public Car queryByIdNumber(String idNumber);
	
	/**
	 * @Description: 根据车牌号修改车辆信息
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月3日  下午2:48:04
	 * @Param:car 传入一个需要被修改的车辆信息
	 */
	public void editByCarCode(Car car);

	// -----------------	前台功能 ---------------------------
	
	/**
	 * 	 根据企业id,车牌号查询车辆列表
	*/
	public List<SimpleCarListResponse> queryCarsByCondition(CarQueryForm queryForm);

	/**
	 * 	查询车辆的车牌号和颜色的车辆集合
	*/
	public List<Map<String, Object>> queryCarNoAndColorList(Integer type);
	/**
	 * @Description: 根据车牌号列表查询车辆
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月8日  下午9:17:39
	 * @param: carCodes 传入一个车牌号列表，用逗号分隔
	 * @return:List<Car> 返回车辆列表
	 */
	public List<Car> queryCarByCarCodes(String carCodes);
	
	/**
	 * @Description: 批量修改车辆isRunning状态
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月9日  上午12:39:17
	 * @param: isRunning 传入一个状态
	 * @param: carCodes 传入多个车牌号，用逗号分隔
	 * @return:Integer 返回受影响行数
	 */
	public Integer updateCarsIsRunning(Integer isRunning,String carCodes);
	
	/**
	 * @Description: 导出车辆驾驶员汇总
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月19日 下午3:31:01
	 * @Param: carAndOrCarDrivers 传入车辆驾驶员汇总列表数据
	 * @Param: fileName 传入一个模版文件名
	 * @Param: water 传入水印
	 * @Return: String 返回导出时在本地的excel的绝对路径
	 */
	public String createExcelPOI(List<CarAndOrCarDriver> carAndOrCarDrivers,String fileName,String [] water);

	List<Car> queryAll();

	List<Statistics> statisticsCarGroupCout();

	List<Statistics> statisticsCompCarCout();

	void updateRunning(boolean b);

	//根据车牌号和颜色查询车辆
	public Car queryByCarCodeAndColor(String carCode, String carColor);
	/**
	 * @Description: 根据车牌号和车牌颜色查询车辆
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月24日 下午10:47:35
	 * @Param: carCode 传入一个车牌号
	 * @Param: carCardColor 传入一个车牌颜色
	 * @Return: Car 返回一个车辆信息
	 */
	public Car selectCarByCarCodeAndCarCardColor(String carCode,String carCardColor);
	/**
	 * @Description: 维护企业和车辆的关系
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月20日 下午12:12:59
	 */
	public void setCompanyAndCarRelation();
	
	
	public List<Car>queryBySql(String sql);
}
