package com.muck.mapper;

import org.springframework.stereotype.Repository;

import com.muck.domain.SystemSettings;

/**
 * @Description: 系统设置mapper
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月10日 上午10:48:11
 *
 */
@Repository
public interface SystemSettingsMapper extends BaseMapper<SystemSettings>{

	public SystemSettings query();
	public Integer update();
}
