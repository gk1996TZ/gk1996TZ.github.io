package com.muck.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.helper.OrganizationResult;
import com.muck.response.ResponseResult;
import com.muck.service.OrganizationService;

/**
* @Description: 组织结构
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 下午2:19:52
 */
@RestController("AdminOrganizationController")
@RequestMapping("/admin/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	* @Description: 递归解析xml文件
	* @param: xml,xml格式的字符串
	* @author: 展昭
	* @date: 2018年5月9日 下午2:21:42
	 */
	@RequestMapping(value = "analyzeOrganization.action",method = RequestMethod.POST)
	public ResponseResult analyzeOrganization(String xml){
		
		System.out.println("xml--->" + xml);
		
		final OrganizationResult result = organizationService.analyzeOrganization(xml);
		
		// 开启线程,执行将区域、设备、通道插入操作
		new Thread(){
			public void run() {
				// 添加区域信息、设备信息、通道信息
				organizationService.addOrganization(result);
			};
		}.start();
		return ResponseResult.ok(result.getChannelCodes());
	}
}
