package com.muck.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.response.DaHuaPlatFormConfig;
import com.muck.response.ResponseResult;
import com.muck.service.PropertiesService;

/***
* @Description: 配置信息
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 下午2:02:12
 */
@RestController("AdminPropertiesController")
@RequestMapping("/admin/config/")
public class PropertiesController {

	@Autowired
	private PropertiesService propertiesService;
	
	/***
	* @Description: 获取大华的登录平台的配置信息
	* @author: 展昭
	* @date: 2018年5月9日 下午2:04:09
	 */
	@RequestMapping(value = "queryDaHuaConfig.action" , method = RequestMethod.GET)
	public ResponseResult queryDaHuaConfig(){
		
		DaHuaPlatFormConfig config = propertiesService.getDaHuaPlatFormConfig();
		if(config == null){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(config);
	}
	
	
	
}
