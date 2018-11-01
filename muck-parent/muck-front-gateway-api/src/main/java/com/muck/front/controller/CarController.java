package com.muck.front.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Car;
import com.muck.domain.CarDriver;
import com.muck.domain.Company;
import com.muck.query.CarQueryForm;
import com.muck.response.CarGPSDataInfoResponse;
import com.muck.response.CarGroupTree;
import com.muck.response.ResponseResult;
import com.muck.response.SimpleCarListResponse;
import com.muck.service.CarDriverService;
import com.muck.service.CarGroupService;
import com.muck.service.CarService;
import com.muck.service.GpsDataService;

/***
 * 车辆Controller
 */
@RestController("FrontCarController")
@RequestMapping("/front/car")
public class CarController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CarGroupService carGroupService; // 车辆组Service

	@Autowired
	private CarService carService; // 车辆Service

	@Autowired
	private CarDriverService carDriverService;

	@Autowired
	private GpsDataService gpsDataService; // 车辆gps数据

	/***
	 * 初始化车辆树
	 */
	@RequestMapping(value = "initTreeCars.action", method = RequestMethod.GET)
	public ResponseResult queryCarGroupTree() {

		List<Company> list = carGroupService.queryCarGroupTree(null);

		return ResponseResult.ok(list);
	}

	/***
	 * 根据企业id,车牌号查询车辆列表
	 */
	@RequestMapping(value = "queryCarsByCondition.action", method = RequestMethod.POST)
	public ResponseResult queryCarsByCondition(CarQueryForm queryForm) {

		List<SimpleCarListResponse> cars = carService.queryCarsByCondition(queryForm);

		return ResponseResult.ok(new ArrayList<SimpleCarListResponse>(new HashSet<SimpleCarListResponse>(cars)));
	}

	/**
	 * 根据车牌号查询车辆详情 车辆列表单击鼠标右键根据车牌号查询车辆详情。
	
	@RequestMapping(value = "queryCarByCarCode.action", method = RequestMethod.POST)
	public ResponseResult queryCarByCarCode(String carCode, String carCardColor) {

		if (StringUtils.isBlank(carCode)) {
			logger.error("调用queryCarByCarCode接口时carCode为空");
		}

		CarGPSDataInfoResponse carGPSDataInfoResponse = gpsDataService.queryCarGPSDataInfo(carCode, carCardColor);
		if (carGPSDataInfoResponse == null) {
			logger.error("调用queryCarByCarCode接口时查询的CarGPSDataInfoResponse对象为空");
		}
		// 根据车牌号和车辆颜色查询
		Car car = carService.queryByCarCodeAndColor(carCode, carCardColor);
		if (car == null) {
			logger.error("调用queryCarByCarCode接口时查询的Car对象为空");
		}else{
			if (car.isRunning()) {
				carGPSDataInfoResponse.setCarIsRunning("在线");
			} else {
				carGPSDataInfoResponse.setCarIsRunning("离线");
			}
			carGPSDataInfoResponse.setCarOwnerOfVehicle(car.getCarOwnerOfVehicle());
			carGPSDataInfoResponse.setMemo(car.getMemo());
		}
		List<CarDriver> carDrivers = carDriverService.queryByCarCode(carCode);
		carGPSDataInfoResponse.setCarDrivers(carDrivers);
		return ResponseResult.ok(carGPSDataInfoResponse);
	} */
	
	/**
	 * 根据车牌号查询车辆详情 车辆列表单击鼠标右键根据车牌号查询车辆详情。
	 */
	@RequestMapping(value = "queryCarByCarCode.action", method = RequestMethod.POST)
	public ResponseResult queryCarByCarCode(String carCode, String carCardColor) {
		CarGPSDataInfoResponse carGPSDataInfoResponse = null;
		try {
			if (StringUtils.isBlank(carCode)) {
				logger.error("调用queryCarByCarCode接口时carCode为空");
			}
			carGPSDataInfoResponse = gpsDataService.queryCarGPSDataInfo(carCode, carCardColor);
			if (carGPSDataInfoResponse == null) {
				logger.error("调用queryCarByCarCode接口时查询的CarGPSDataInfoResponse对象为空");
				carGPSDataInfoResponse = new CarGPSDataInfoResponse();
			}
			// 根据车牌号和车辆颜色查询
			Car car = carService.queryByCarCodeAndColor(carCode, carCardColor);
			if (car == null) {
				logger.error("调用queryCarByCarCode接口时查询的Car对象为空");
			}else{
				boolean isRunning = car.isRunning();
				if (isRunning) {
					carGPSDataInfoResponse.setCarIsRunning("在线");
				} else {
					carGPSDataInfoResponse.setCarIsRunning("离线");
				}
				carGPSDataInfoResponse.setCarOwnerOfVehicle(car.getCarOwnerOfVehicle());
				carGPSDataInfoResponse.setMemo(car.getMemo());
				carGPSDataInfoResponse.setCarCode(carCode);
				if(carGPSDataInfoResponse.getCompanyName() == null){
					carGPSDataInfoResponse.setCompanyName(car.getCompanyName());
				}
			}
			List<CarDriver> carDrivers = carDriverService.queryByCarCode(carCode);
			carGPSDataInfoResponse.setCarDrivers(carDrivers);
		} catch (Exception e) {
			logger.error("查询车辆报错啦" + e.getMessage());
		}
		return ResponseResult.ok(carGPSDataInfoResponse);
	}

	/**
	 * @Description: 获取车辆组树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月29日 下午11:17:04
	 * @return:ResponseResult 返回车辆组树
	 */
	@RequestMapping(value = "queryCarGroupTree.action", method = RequestMethod.GET)
	public ResponseResult queryCarGroupTree(String companyId) {
		List<CarGroupTree> listCarGroupTree = new ArrayList<CarGroupTree>();
		CarGroupTree carGroupTree = new CarGroupTree();
		List<Company> list = carGroupService.queryCarGroupTree(companyId);
		carGroupTree.setRootName("根节点");
		carGroupTree.setListCompany(list);
		listCarGroupTree.add(carGroupTree);
		// for(Company cp:list){
		// List<CarGroup> carGrop=cp.getCarGroups();
		// if(carGrop.size()==1&&carGrop.get(0).getId()==null){
		// cp.setCarGroups(null);
		// }
		// }
		// System.out.println(list);
		return ResponseResult.ok(listCarGroupTree);
	}

	/**
	 * 根据车牌号查询车辆
	 **/
	@RequestMapping(value = "queryByCodeAndColor.action", method = RequestMethod.POST)
	public ResponseResult queryCarInfoByCarCode(String carCode,String carCardColor) {

		if (StringUtils.isBlank(carCode)) {
			logger.error("调用queryCarInfoByCarCode接口时carCode为空");
		}
		try {
			Car car = carService.selectCarByCarCodeAndCarCardColor(carCode, carCardColor);
			return ResponseResult.ok(car);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}
}