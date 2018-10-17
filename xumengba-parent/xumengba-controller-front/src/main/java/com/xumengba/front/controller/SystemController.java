package com.xumengba.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.System;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.SystemService;

@RestController("FrontSystemController")
@RequestMapping("/front/system")
public class SystemController extends BaseController{

	
	@Autowired
	private SystemService systemService;
	/**
	 * @Description:查看系统设置信息
	 * @author:甘坤
	 * @date: 2018年10月10日 下午3:06:54
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value="getSystem.action",method=RequestMethod.GET)
	public ResponseResult getSystem(){
		List<System>system=systemService.getSystem();
		
		return ResponseResult.ok(system);
		
	}
}
