package com.muck.init;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.muck.domain.CarType;
import com.muck.response.SimpleCompanyResponse;
import com.muck.service.CarService;
import com.muck.service.CompanyService;

/**
 * 	系统启动完毕之后初始化各种厂商车辆数据
*/
@Component
public class InitCarsDataService implements ApplicationRunner{
	
	@Autowired
	private CarService carService;	//  车辆service
	
	@Autowired
	private CompanyService companyService;	//  企业service

	// 长宝车辆数据
	private List<Map<String,Object>> changBaoCarList = null;
	
	// 北斗车辆数据
	private List<Map<String,Object>> beidouCarList = null;
	
	// 企业
	private List<SimpleCompanyResponse> companys;
	
	public void run(ApplicationArguments args) throws Exception {
		
		// 获取长宝的车辆数据
		changBaoCarList = carService.queryCarNoAndColorList(CarType.CHANG_BAO.getType());
		
		// 获取北斗的车辆数据
		beidouCarList = carService.queryCarNoAndColorList(CarType.BEI_DOU.getType());
		
		// 根据企业分组统计每个企业下面的车辆
		companys = companyService.statisticsGroupCars();
	}

	public List<Map<String, Object>> getChangBaoCarList() {
		return changBaoCarList;
	}

	public List<Map<String, Object>> getBeidouCarList() {
		return beidouCarList;
	}

	public List<SimpleCompanyResponse> getCompanys() {
		return companys;
	}
}
