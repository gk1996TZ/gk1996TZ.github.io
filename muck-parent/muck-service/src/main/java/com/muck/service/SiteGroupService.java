package com.muck.service;

import com.muck.domain.SiteGroup;

/**
 * 	工地组Service
*/
public interface SiteGroupService extends BaseService<SiteGroup> {

	/**
	 * 	校验工地组是否存在
	*/
	public boolean validateSiteGroupExist(String siteGroupName,String areaId);

	/**
	 * 	将工地添加到工作组中
	*/
	public void move(String siteId, String siteGroupId);

	/**
	 * 	根据工地id从工作组中删除
	*/
	public void deleteSiteFromGroup(String siteId, String siteGroupId);
	
	
	/**
	 * @Description: 根据工地组id查询所有的工地id
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月5日  下午5:55:15
	 * @param:siteGroupId 传入一个工地组id
	 * @return:String 返回工地组下所有的工地id
	 */
	public String querySiteIdsBySiteGroupId(String siteGroupId);

}
