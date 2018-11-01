package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Site;

/**
 * 	工地和工地组Mapper
*/
@Repository
public interface SiteAndSiteGroupMapper {

	/**
	 * 	添加工地和工地组的关系
	*/
	public void insert(@Param("siteId")String siteId,@Param("siteGroupId")String siteGroupId);

	/**
	 * 	根据工地组id查询此工地组下面所有的工地
	*/
	public List<Site> selectSitesBySiteGroupId(@Param("siteGroupId")String siteGroupId);
	
	/**
	 * 	根据工作组id删除工作组和工地的关联数据
	*/
	public void deleteBySiteGroupId(@Param("siteGroupId")String siteGroupId);

	/***
	 * 	 根据工地id从工作组中删除
	*/
	public void deleteSiteFromGroup(@Param("siteId")String siteId,@Param("siteGroupId")String siteGroupId);
}
