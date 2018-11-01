package com.muck.service;

import com.muck.helper.OrganizationResult;

/**
* @Description: 组织结构service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月10日 下午5:47:52
 */
public interface OrganizationService {

	/**
	* @Description: 解析组织结构树
	* @author: 展昭
	* @date: 2018年5月10日 下午5:42:07
	 */
	public OrganizationResult analyzeOrganization(String xmlValue);

	/**
	* @Description: 添加区域、设备信息、通道信息
	* @author: 展昭
	* @date: 2018年5月10日 下午6:55:44
	*/
	public void addOrganization(OrganizationResult result);
}
