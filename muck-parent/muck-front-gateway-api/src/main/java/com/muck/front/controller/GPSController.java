package com.muck.front.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.GpsData;
import com.muck.helper.QueryHelper;
import com.muck.response.ResponseResult;
import com.muck.service.GpsDataService;

/**
 * @Description: gps数据访问控制层
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月24日 下午4:08:16
 */
@RestController("FrontGPS")
@RequestMapping("/front/gps")
public class GPSController extends BaseController{

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private GpsDataService gpsDataService;
	/**
	 * @Description: 条件查询车辆gps列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月24日  下午4:12:28
	 * @param: startTime 根据时间段查询的开始时间
	 * @param: endTime 根据时间段查询的结束时间
	 * @param: carNumber 根据车牌号查询
	 * @return:ResponseResult 返回含有车辆数据的结果集
	 */
	@RequestMapping("carGps.action")
	public ResponseResult getCarGPS(String startTime,String endTime,String carNumber){
		List<GpsData> listDevice = null;
		QueryHelper queryHelper = new QueryHelper();
		// 拼接查询条件
		queryHelper.addCondition(startTime, "gps_time > '%s'", false)
		.addCondition(endTime, "gps_time < '%s'", false)
		.addCondition(carNumber, "vehicle_no = '%s'", false);
		// 查询到设备的列表
		listDevice = gpsDataService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		return ResponseResult.ok(listDevice);
	}
	
}
