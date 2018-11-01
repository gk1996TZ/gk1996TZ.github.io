package com.muck.service;

import java.util.List;

import com.muck.domain.Channel;
import com.muck.domain.ChannelInfo;
import com.muck.response.IndexDeviceGPSData;

/**
 * 	通道Service
*/
public interface ChannelService  extends BaseService<Channel>{

	/**
	 * 	查询所有的通道
	*/
	public List<IndexDeviceGPSData> queryAllChannels();

	/*
	 * 根据通道号查询数据
	 * */
	public int  selectChaneelType(String channelCode);

	//通过通道号查询通道信息
	public ChannelInfo getChannelInfo(String channelCode);
	
	/**
	 * @Description: 根据通道号修改运行状态
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年9月29日 下午7:04:26
	 * @Param: channelCode 传入一个通道号
	 * @Param: channelStatus 传入一个运行状态 0：不运行，1：正常运行
	 */
	public void updateChannelStatusByChannelCode(String channelCode,Integer channelStatus);
}
