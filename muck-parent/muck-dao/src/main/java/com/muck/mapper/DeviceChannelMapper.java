package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Channel;

/**
* @Description: 设备和通道关系Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月11日 下午5:34:49
 */
@SuppressWarnings("rawtypes")
@Repository
public interface DeviceChannelMapper extends BaseMapper{

	/**
	* @Description: 批量插入
	* @author: 展昭
	* @date: 2018年5月10日 下午7:07:33
	 */
	public void insertBatch(@Param("channels")List<Channel> channels);
	
	//根据设备号查询通道
	public List<String>selectChannelByDeviceCode(@Param("deviceCode")String deviceCode);

	public void updateIsRunningByDeviceCode(@Param("deviceCode")String deviceCode,@Param("isRunning")Integer isRunning);
}
