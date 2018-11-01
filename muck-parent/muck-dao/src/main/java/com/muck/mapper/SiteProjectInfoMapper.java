package com.muck.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.SiteProjectInfo;

/**
 * 	工地项目详情
*/
@Repository
public interface SiteProjectInfoMapper extends BaseMapper<SiteProjectInfo>{
	
	/**根据工地Id查询工地详情*/
	public SiteProjectInfo querySiteProjectInfoBySiteId(@Param("siteId")String siteId);
	
	/**根据工地Id查询工地详情*/
	public SiteProjectInfo querySiteProjectInfoBySiteId1(@Param("id")String siteId);
	
	/**根据注册号查询工地详情*/
	public SiteProjectInfo getProjectInfoByRegisterCode(@Param("registerCode")String registerId);

}
