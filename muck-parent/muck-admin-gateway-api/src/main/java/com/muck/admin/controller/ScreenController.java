package com.muck.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.muck.domain.Car;
import com.muck.helper.QueryHelper;
import com.muck.request.CarForm;
import com.muck.response.ResponseResult;
import com.muck.service.AreaService;
import com.muck.service.CarService;
import com.muck.service.DeviceService;

/**
 * @Description: 首页筛选查询
 * @version: v1.0.0
 * @author 1
 * @date 2018年4月27日 下午5:06:36
 *
 */
@RestController("AdminScreenController")
@RequestMapping("/admin/screen")
public class ScreenController {
	
	@Autowired
	AreaService areaService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	CarService carService;
	/**
	 * @Description: 筛选查询车辆
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 下午5:09:09
	 * @param: @param areaId 传入地域id，根据地域查询设备
	 * @param: @param companyId 传入企业id，根据企业查询设备
	 * @param: carForm 传入车辆信息，根据车辆信息查询
	 * @return: ResponseResult 返回含有查询数据的结果集
	 */
	@RequestMapping("queryCarByScreen.action")
	public ResponseResult queryCarByScreen(String areaId,String companyId,CarForm carForm){
		List<Car> listCar = null;
		// 1、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		if("true".equals(carForm.getQuery())){
			
			// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
			QueryHelper queryHelper = new QueryHelper();
			//根据地域查询
			String areaIds = null;
			if(!StringUtil.isEmpty(areaId)){
				areaIds = areaService.querySubAreaIdsByParentId(areaId);
			}
			//根据车牌号查询
			String carCode = carForm.getCarCode();
			
			queryHelper.addCondition(areaIds, "area_id in (%s)",false)
					   .addCondition(companyId, "company_id=%d",true)
					   .addCondition(carCode, "car_code='%s'", false);
			listCar = carService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		}else{
			listCar = carService.queryData();
		}
		//获取车辆上的设备信息
		/*if(listCar != null){
			QueryHelper queryHelper = null;
			for(Car car :listCar){
				queryHelper = new QueryHelper();
				queryHelper.addCondition(car.getId(),"device_car_id=%d", true);
				car.setDevice(deviceService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
			}
		}*/
		return ResponseResult.ok(listCar);
	}
}
