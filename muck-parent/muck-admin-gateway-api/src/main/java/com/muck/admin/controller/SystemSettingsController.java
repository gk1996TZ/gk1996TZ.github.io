package com.muck.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.SystemSettings;
import com.muck.request.SystemSettingsForm;
import com.muck.response.ResponseResult;
import com.muck.service.SystemSettingsService;

/**
 * @Description: 系统设置控制层
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年4月25日 下午5:34:22
 */
@RestController("AdminSystemSetController")
@RequestMapping("/admin/systemSet")
public class SystemSettingsController {

	@Autowired
	SystemSettingsService systemSettingsService;
	/**
	 * @Description: 根据id查询系统设置
	 * @version: v1.0.0
	 * @author 朱俊亮 
	 * @date: 2018年4月25日 下午5:47:54
	 * @param: id 传入系统设置的id值
	 * @return: ResponseResult 返回含有系统设置信息的结果
	 */
	@RequestMapping(value = "systemSet.action",method=RequestMethod.GET)
	public ResponseResult systemSet(){
		SystemSettings systemSettings = systemSettingsService.query();
		if(systemSettings == null){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(systemSettings);
	}
	/**
	 * @Description: 设置系统设置的操作
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月25日 下午5:49:50
	 * @param: SystemSettingsForm 传入含有系统设置信息的系统设置对象
	 * @return: ResponseResult 返回系统设置记录的id
	 */
	@RequestMapping(value = "setSystemSet.action",method=RequestMethod.POST)
	public ResponseResult setSystemSet(SystemSettingsForm systemSettingsForm){
		// 第一步  : 组装实体类所需要的信息
		SystemSettings systemSettings = new SystemSettings();
		systemSettings.setId(systemSettingsForm.getId());
		systemSettings.setSysCopyright(systemSettingsForm.getSysCopyright());
		systemSettings.setSysIp(systemSettingsForm.getSysIp());
		systemSettings.setSysPort(systemSettingsForm.getSysPort());
		systemSettings.setSysCompanyLogo(systemSettingsForm.getSysCompanyLogo());
		// 第二步  : 进行保存或者修改的操作
		if(systemSettings.getId() == null || "null".equals(systemSettings.getId())){
			systemSettings.setId(null);
			systemSettingsService.save(systemSettings);
		}else {
			systemSettingsService.editById(systemSettings);
		}
		return ResponseResult.ok(systemSettings.getId());
	}
}