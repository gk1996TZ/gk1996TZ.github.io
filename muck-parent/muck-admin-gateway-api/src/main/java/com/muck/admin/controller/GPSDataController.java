package com.muck.admin.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.GpsDataLine;
import com.muck.helper.QueryHelper;
import com.muck.response.ResponseResult;
import com.muck.service.GpsDataLineService;

/**
 * 	车辆GPS信息Controller
*/
@RestController("AdminGPSDataController")
@RequestMapping("/admin/gps/")
public class GPSDataController extends BaseController {
	
	@Autowired
	private GpsDataLineService gpsDataLineService;
	
	/*
	 * @param: points 多边形选框的各个点坐标
	 * @param: startTime 根据时间段查询的开始时间
	 * @param: endTime 根据时间段查询的结束时间
	 * @param: carNumber 根据车牌号查询
	 * @return:ResponseResult 返回含有车辆GPS数据的结果集
	 * @Description: 轨迹回放
	 * 
	 * */
	@RequestMapping(value="getCarGPSInfoReturn.action",method=RequestMethod.POST)
	public ResponseResult getCarGPSInfoReturn(String startTime,String endTime,String carCode,String carCardColor ){
		List<GpsDataLine> listDevice = null;
		QueryHelper queryHelper = new QueryHelper();
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			queryHelper.addCondition(startTime, "gps_time between '%s'", false);
			queryHelper.addCondition(endTime, " '%s' ", false);
		}
		if(StringUtils.isNotBlank(carCode)){
			queryHelper.addCondition(carCode, "vehicle_no = '%s'", false);
		}
		if(StringUtils.isNotBlank(carCardColor)){
			queryHelper.addCondition(carCardColor, "vehicle_color='%s'", false);
		}
		queryHelper.addCondition(30000000, "latitude <= %d", false).addCondition(29000000, "latitude >= %d", false);
		
		// 查询到设备的列表
		listDevice = gpsDataLineService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		return ResponseResult.ok(listDevice);
		
	}
}
