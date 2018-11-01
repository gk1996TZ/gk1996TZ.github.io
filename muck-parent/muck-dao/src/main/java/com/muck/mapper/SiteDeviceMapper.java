package com.muck.mapper;

import org.apache.ibatis.annotations.Param;

import com.muck.domain.Site;

/***
* @Description: 工地和设备管理关系Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月16日 下午2:41:04
*/
public interface SiteDeviceMapper {

	/**
	* @Description: 添加工地和设备的关联关系
	* @author: 展昭
	* @date: 2018年5月16日 下午2:43:13
	 */
	public void insert(@Param("site")Site site,@Param("deviceCodes")String[] deviceCodes);
}
