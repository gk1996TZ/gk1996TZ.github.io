package com.muck.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Channel;
import com.muck.domain.ChannelInfo;
import com.muck.domain.Company;
import com.muck.domain.DeviceType;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ChannelMapper;
import com.muck.page.PageView;
import com.muck.response.IndexDeviceGPSData;
import com.muck.service.ChannelService;

/**
 * 通道service
 */
@Service
public class ChannelServiceImpl extends BaseServiceImpl<Channel> implements ChannelService {

	@Autowired
	private ChannelMapper channelMapper;

	/**
	 * 查询所有的通道
	 */
	public List<IndexDeviceGPSData> queryAllChannels() {
		return channelMapper.selectAllChannels();
	}

	/*
	 * 通过channelCode查询channel表获取通道类型
	 */
	@Override
	public int selectChaneelType(String channelCode) {
		return channelMapper.selectChaneelType(channelCode);
	}

	/*
	 * 通过通道号获取通道类型在查询通道信息
	 */
	@Override
	public ChannelInfo getChannelInfo(String channelCode) {
		if (StringUtils.isNotBlank(channelCode)) {
			int channelType = this.selectChaneelType(channelCode);
			ChannelInfo info = null;
			if (channelType == DeviceType.SITE.getType()) {
				// 通过通道号查询
				info = channelMapper.selectSiteInfoByChannelCode(channelCode);
			} else if (channelType == DeviceType.DISPOSAL.getType()) {
				// 根据通道号查询处置场
				info = channelMapper.selectDisposalInfoByChannelCode(channelCode);
			}
			return info;
		}
		return null;
	}

	@Override
	public PageView<Channel> queryPageData(Long currentPageNum, Long pageSize, String whereSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 用来查询数据总数的sql
		String sql = "select count(device.id) "
					+ " from "
					+ " t_device device left join t_channel channel on device.device_code = channel.device_code "
					+ " where 1 = 1 " + whereSQL;
		// 用来查询数据的sql
		String selectSQL = "select " 
					+ " channel.channel_code "
					+ " from "
					+ " t_device device left join t_channel channel on device.device_code = channel.device_code "
					+ " where 1 = 1 " + whereSQL + limit;

		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = channelMapper.selectTotalRecoreds(sql);

		PageView<Channel> pageView = new PageView<>(count, currentPageNum, pageSize);

		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		List<Channel> channels = channelMapper.selectPageData(selectSQL);
		pageView.setDatas(channels);
		return pageView;
	}
	
	@Override
	public void updateChannelStatusByChannelCode(String channelCode, Integer channelStatus) {
		channelMapper.updateChannelStatusByChannelCode(channelCode, channelStatus);
	}

	// ===================================
	@Override
	protected BaseMapper<Channel> getMapper() {
		return channelMapper;
	}

	@Override
	protected String getFields() {
		return "*";
	}

}
