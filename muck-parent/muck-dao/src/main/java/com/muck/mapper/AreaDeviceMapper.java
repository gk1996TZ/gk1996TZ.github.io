package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Device;

@SuppressWarnings("rawtypes")
@Repository
public interface AreaDeviceMapper extends BaseMapper{

	/**
	* @Description: 批量添加设备和区域的关系
	* @author: 展昭
	* @date: 2018年5月10日 下午7:07:33
	 */
	public void insertBatch(@Param("devices")List<Device> devices);

}
