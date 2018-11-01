package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.GpsDataLine;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.GpsDataLineMapper;
import com.muck.response.CarGPSDataInfoResponse;
import com.muck.service.GpsDataLineService;

@Service
public class GpsDataLineServiceImpl extends BaseServiceImpl<GpsDataLine> implements GpsDataLineService{
	
	@Autowired
	GpsDataLineMapper gpsDataLineMapper;

	/**
	 * 	删除指定的月份之外的数据
	*/
	public void deleteBatchGPSData(Integer month) {
		gpsDataLineMapper.deleteBatchGPSData(month);
	}
	
	/***
	 * 	根据车牌号查询车辆详情(是查询最后一次车辆的状态)
	*/
	public CarGPSDataInfoResponse queryCarGPSDataInfo(String carCode,String carColor) {
		return gpsDataLineMapper.selectCarGPSDataInfo(carCode,carColor);
	}
	
	@Override
	public List<GpsDataLine> queryData(String whereSQL,List<Object> params) {
		
		String sql = "select DISTINCT vehicle_no,vehicle_color,latitude,longitude from t_biz_gps_data  where " + whereSQL;
		
		sql = setQueryParams(sql , params,null);

		return getMapper().queryData(sql);
	}
	

	// ----------------------------------------------------------
	@Override
	protected BaseMapper<GpsDataLine> getMapper() {
		return gpsDataLineMapper;
	}
	@Override
	protected String getFields() {
		return "vehicle_no,vehicle_color,longitude,latitude,location,speed,mileage,gps_time";
	}
}
