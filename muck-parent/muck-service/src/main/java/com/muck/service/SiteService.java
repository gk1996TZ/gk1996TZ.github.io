package com.muck.service;

import java.util.List;

import com.muck.domain.Area;
import com.muck.domain.Channel;
import com.muck.domain.Site;
import com.muck.domain.SiteGroup;

public interface SiteService extends BaseService<Site>{
	/**
	 * @Description: 通过负责人id查询工地id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 上午11:24:38
	 * @param: managerIds 传入一系列负责人的id
	 * @return: String 返回工地id
	 */
	public String querySiteIdsByManagerIds(String managerIds);
	/**
	 * @Description: 获取所有的工地id
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午10:14:29
	 * @return: String 返回工地id
	 */
	public String querySiteIdsAll();
	
	/***
	 * 根据通道号查询工地详情
	*/
	public Site querySiteInfoByChannelCode(String channelCode);

	/**
	 * @Description: 根据设备编号查询工地id 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午2:58:58
	 * @param: deviceCode 传入一个设备编号
	 * @return: String 返回该设备所属工地的工地id
	 */
	public String querySiteIdByDeviceCode(String deviceCode);
	
	/**
	 * @Description: 通过工地id查询该工地下所有的设备的所有的通道 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午3:04:05
	 * @param: siteId 传入一个工地id
	 * @return: List<Channel> 返回含有当前工地的所有的设备的所有的通道号
	 */
	public List<Channel> queryChannelBySiteId(String siteId);
	
	
	//----------------------------------------
	
	/**
	 * 	根据工地id修改工地项目进度
	*/
	public void editSiteProjectProcess(String siteId,String memo);

	/**
	 * 	初始化工地组织树
	*/
	public void initTreeSites(List<Area> areas);
	
	/**
	 * 	根据区域id查询区域下面所有的工地
	*/
	public List<Site> querySitesByAreaId(String areaId);
	
	/**
	 * 	根据区域id查询区域下面所有的工地组
	*/
	public List<SiteGroup> querySiteGroupsByAreaId(String areaId);
	
	/**
	 * 根据设备注册码查询工地id
	 * */
	public String getSiteIdByRegisterCode(String registerCode);
	
	/**
	 * 批量更新
	 * */
	public void updateBatch(String registerCode,String channelCode);
}
