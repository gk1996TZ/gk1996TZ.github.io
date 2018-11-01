package com.muck.service;

import com.muck.domain.SystemSettings;

/**
* @Description: 查询登录企业的系统信息
* @author: 朱俊亮 
* @date: 2018年4月25日 下午10:57:20
*/
public interface SystemSettingsService extends BaseService<SystemSettings>{

	/**
	 * @Description: 查询系统设置
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月26日 下午12:07:18
	 * @return: SystemSettings 返回查询到的系统设置
	 */
	public SystemSettings query();
	/**
	 * @Description: 修改系统设置
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月26日 下午12:27:00
	 * @param: systemSettings 传入需要被修改的系统设置
	 * @return: void
	 */
	public void update(SystemSettings systemSettings);
}
