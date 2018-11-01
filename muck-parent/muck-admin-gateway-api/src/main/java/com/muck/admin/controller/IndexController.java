package com.muck.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.muck.response.IndexDeviceGPSData;
import com.muck.response.ResponseResult;
import com.muck.service.ChannelService;

/**
 * 	首页Controller
*/
@RestController("AdminIndexController")
@RequestMapping("/admin/index")
public class IndexController extends BaseController{

	/**
	 * 	查询所有的通道
	
	@RequestMapping(value = "queryAllChannels.action" , method = RequestMethod.GET)
	public ResponseResult queryAllChannels(){
		
		List<IndexDeviceGPSData> channels = channelService.queryAllChannels();
		return ResponseResult.ok(channels);
	}
	*/
}
