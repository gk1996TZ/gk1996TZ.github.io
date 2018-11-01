package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.SiteGroup;

/**
 * 	工地组Mapper
*/
@Repository
public interface SiteGroupMapper extends BaseMapper<SiteGroup>{

	/**
	 * 	校验工地组是否存在
	*/
	public Long validateExist(@Param("siteGroupName")String siteGroupName,@Param("areaId")String areaId);

	/**
	 * 	根据区域id查询区域下面所有的工地组
	*/
	public List<SiteGroup> selectByAreaId(@Param("areaId")String areaId);
	/**
	 * @Description: 根据工地组id查询所有的工地id
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月5日  下午5:55:15
	 * @param:siteGroupId 传入一个工地组id
	 * @return:List<String> 返回工地组下所有的工地id
	 */
	public List<String> querySiteIdsBySiteGroupId(@Param("siteGroupId")String siteGroupId);
}