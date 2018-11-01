package com.muck.service;

import com.muck.domain.GpsData;
import com.muck.response.CarGPSDataInfoResponse;

public interface GpsDataService extends BaseService<GpsData>{

	/**
	 * 	删除指定的月份之外的数据
	*/
	public void deleteBatchGPSData(Integer month);

	// ---------- 前台 -------------
	
	/***
	 * 	根据车牌号查询车辆详情(是查询最后一次车辆的状态)
	*/
	public CarGPSDataInfoResponse queryCarGPSDataInfo(String carCode,String carColor);

}
