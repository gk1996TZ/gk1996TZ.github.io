package com.muck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.SiteGroup;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SiteAndSiteGroupMapper;
import com.muck.mapper.SiteGroupMapper;
import com.muck.service.SiteGroupService;

/**
 * 	工地组Service
*/
@Service
public class SiteGroupServiceImpl extends BaseServiceImpl<SiteGroup> implements SiteGroupService{

	@Autowired
	private SiteGroupMapper siteGroupMapper;
	
	@Autowired
	private SiteAndSiteGroupMapper siteAndSiteGroupMapper;
	
	/**
	 * 	校验工地组是否存在
	*/
	public boolean validateSiteGroupExist(String siteGroupName,String areaId) {
		
		Long count = siteGroupMapper.validateExist(siteGroupName,areaId);
		
		return (count == null || count <= 0) ? true : false;
	}
	
	/**
	 * 	将工地添加到工地组中
	*/
	public void move(String siteId, String siteGroupId) {
		siteAndSiteGroupMapper.insert(siteId, siteGroupId);
	}
	
	/**
	 * 	根据工作组id删除工作组
	 * 		1、删除工作组
	 * 		2、删除t_site_sitegroup中间关联表与工作组对应的工地数据
	*/
	@Override
	public void deleteById(String siteGroupId) {

		// 1、删除工作组
		super.deleteById(siteGroupId);
		
		// 2、删除关联关系
		siteAndSiteGroupMapper.deleteBySiteGroupId(siteGroupId);
	}
	
	/***
	 * 	根据工地id从工作组中删除
	*/
	public void deleteSiteFromGroup(String siteId, String siteGroupId) {
		
		// 根据工地id从工作组中删除
		siteAndSiteGroupMapper.deleteSiteFromGroup(siteId,siteGroupId);
	}
	@Override
	public String querySiteIdsBySiteGroupId(String siteGroupId) {
		List<String> siteIds = siteGroupMapper.querySiteIdsBySiteGroupId(siteGroupId);
		if(siteIds != null){
			StringBuilder sb = new StringBuilder();
			for(String siteId : siteIds){
				sb.append(siteId);
				sb.append(",");
			}
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length()-1);
			}
			return sb.toString();
		}
		return null;
	}
	
	//----------------------------------
	

	@Override
	protected BaseMapper<SiteGroup> getMapper() {
		return siteGroupMapper;
	}

	@Override
	protected String getFields() {
		return null;
	}
}
