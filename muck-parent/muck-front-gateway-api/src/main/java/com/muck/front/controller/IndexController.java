package com.muck.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.response.IndexDeviceGPSData;
import com.muck.response.ResponseResult;
import com.muck.service.ChannelService;

/**
 * 	首页Controller
*/
@RestController("FrontIndexController")
@RequestMapping("/front/index")
public class IndexController extends BaseController{

	@Autowired
	private ChannelService channelService;	// 通道service
	
	/**
	 * 	查询所有的通道
		*/
	@RequestMapping(value = "queryAllChannels.action" , method = RequestMethod.GET)
	public ResponseResult queryAllChannels(){
		
		List<IndexDeviceGPSData> channels = channelService.queryAllChannels();
		
		return ResponseResult.ok(channels);
	}

}
