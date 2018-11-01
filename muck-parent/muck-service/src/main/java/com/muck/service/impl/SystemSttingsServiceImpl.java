package com.muck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.SystemSettings;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SystemSettingsMapper;
import com.muck.response.StatusCode;
import com.muck.service.SystemSettingsService;

/**
 * @Description: 系统设置模块的具体实现
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年4月25日 下午12:04:35
 */
@Service
public class SystemSttingsServiceImpl extends BaseServiceImpl<SystemSettings> implements SystemSettingsService{

	@Autowired
	SystemSettingsMapper systemSettingsMapper;
	
	@Override
	public SystemSettings query() {
		SystemSettings systemSettings = systemSettingsMapper.query();
		if (systemSettings == null) {
			throw new BizException(StatusCode.NOT_FOUND);
		}
		return systemSettings;
	}
	
	@Override
	public void update(SystemSettings systemSettings) {
		Integer result = systemSettingsMapper.update();
		if(result <=0){
			throw new BizException(StatusCode.NOT_FOUND);
		}
	}

	//---------------------------------------------------
	@Override
	protected BaseMapper<SystemSettings> getMapper() {
		return systemSettingsMapper;
	}
	@Override
	protected String getFields() {
		return "id";
	}

}
