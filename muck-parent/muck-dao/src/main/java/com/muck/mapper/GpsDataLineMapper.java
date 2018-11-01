package com.muck.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.GpsDataLine;
import com.muck.response.CarGPSDataInfoResponse;

/**
 *		GPSMapper
 */
@Repository
public interface GpsDataLineMapper extends BaseMapper<GpsDataLine>{

	/**
	 * 删除指定的月份之外的数据
	*/
	public void deleteBatchGPSData(Integer month);

	/***
	 * 根据车牌号查询车辆详情(是查询最后一次车辆的状态)
	 * @param carCode
	 * @return
	 * 	根据车牌号查询车辆详情(是查询最后一次车辆的状态)
	*/
	public CarGPSDataInfoResponse selectCarGPSDataInfo(@Param("carCode")String carCode,@Param("carColor")String carColor);

}
