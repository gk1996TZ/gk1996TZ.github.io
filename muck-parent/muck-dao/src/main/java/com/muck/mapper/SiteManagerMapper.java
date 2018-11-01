package com.muck.mapper;

import com.muck.domain.Site;

/**
* @Description: 工地和管理员Mapper
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月15日 下午2:36:52
 */
public interface SiteManagerMapper {

	/**
	* @Description: 添加工地和项目负责人关系
	* @param:描述1描述
	* @author: 展昭
	* @date: 2018年5月15日 下午2:37:12
	 */
	public void insert(Site site);

}
