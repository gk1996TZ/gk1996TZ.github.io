package com.muck.service;

import java.util.List;

import com.muck.domain.GpsDataLine;
import com.muck.response.CarGPSDataInfoResponse;

public interface GpsDataLineService extends BaseService<GpsDataLine>{

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
