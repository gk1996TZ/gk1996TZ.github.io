package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.Channel;
import com.muck.domain.ChannelInfo;
import com.muck.response.IndexDeviceGPSData;


/**
* @Description: 通道Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月11日 下午3:43:55
 */
public interface ChannelMapper extends BaseMapper<Channel>{

	/**
	* @Description: 批量添加通道
	* @author: 展昭
	* @date: 2018年5月10日 下午7:07:33
	 */
	public void insertBatch(@Param("channels")List<Channel> channels);

	/**
	 * 	查询所有通道
	*/
	public List<IndexDeviceGPSData> selectAllChannels();

	//根据通道号查询通道l类型
	public  int  selectChaneelType(String channelcode);
	
	public ChannelInfo selectSiteInfoByChannelCode(String channelcode);
	
	public ChannelInfo selectDisposalInfoByChannelCode(String channelcode);
	
	public String selectDeviceCodeByCode(@Param("channelCode")String channelCode);
	
	public void updateChannelStatusByChannelCode(@Param("channelCode")String channelCode,@Param("channelStatus")Integer channelStatus);
}
