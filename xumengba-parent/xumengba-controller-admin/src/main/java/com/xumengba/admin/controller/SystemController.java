package com.xumengba.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.System;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.SystemService;

@RestController("AdminSystemController")
@RequestMapping("/admin/system")
public class SystemController extends BaseController{

	@Autowired
	private SystemService systemService;
	
	/**
	 * @Description:修改系统设置信息
	 * @author:甘坤
	 * @date: 2018年10月10日 下午3:02:28
	 * @param: @param system
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value="editSys.action",method=RequestMethod.POST)
	public ResponseResult editSys(System system){
		systemService.updateSystem(system);
		
		return ResponseResult.ok();
	}
	
	
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
