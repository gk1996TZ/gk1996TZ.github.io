package com.muck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.VideoPlayback;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.VideoPlaybackMapper;
import com.muck.service.VideoPlaybackService;

/**
* @Description: 视频回放Service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:35:58
*/
@Service
public class VideoPlaybackServiceImpl extends BaseServiceImpl<VideoPlayback> implements VideoPlaybackService {

	@Autowired
	private VideoPlaybackMapper videoPlaybackMapper;
	
	
	
	//---------------------------------
	
	@Override
	protected BaseMapper<VideoPlayback> getMapper() {
		return videoPlaybackMapper;
	}
	
	@Override
	protected String getFields() {
		return null;
	}
}
