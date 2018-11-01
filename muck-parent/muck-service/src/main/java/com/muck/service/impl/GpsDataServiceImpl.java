package com.muck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.GpsData;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.GpsDataMapper;
import com.muck.response.CarGPSDataInfoResponse;
import com.muck.service.GpsDataService;

@Service
public class GpsDataServiceImpl extends BaseServiceImpl<GpsData> implements GpsDataService{
	
	@Autowired
	GpsDataMapper gpsDataMapper;

	/**
	 * 	删除指定的月份之外的数据
	*/
	public void deleteBatchGPSData(Integer month) {
		gpsDataMapper.deleteBatchGPSData(month);
	}
	
	/***
	 * 	根据车牌号查询车辆详情(是查询最后一次车辆的状态)
	*/
	public CarGPSDataInfoResponse queryCarGPSDataInfo(String carCode,String carColor) {
		return gpsDataMapper.selectCarGPSDataInfo(carCode,carColor);
	}

	// ----------------------------------------------------------
	@Override
	protected BaseMapper<GpsData> getMapper() {
		return gpsDataMapper;
	}
	@Override
	protected String getFields() {
		return "vehicle_no,vehicle_color,longitude,latitude,location,speed,mileage,gps_time";
	}
}
