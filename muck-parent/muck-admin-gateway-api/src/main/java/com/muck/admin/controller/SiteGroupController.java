package com.muck.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.SiteGroup;
import com.muck.exception.base.BizException;
import com.muck.request.SiteAndSiteGroupForm;
import com.muck.request.SiteGroupForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.SiteGroupService;
import com.muck.utils.UniqueUtils;

/**
 *	工地组Controller 
*/
@RestController("AdminSiteGroupController")
@RequestMapping("/admin/sitegroup")
public class SiteGroupController extends BaseController {

	@Autowired
	private SiteGroupService siteGroupService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 	添加工地组
	*/
	@RequestMapping(value = "save.action" , method = RequestMethod.POST)
	public ResponseResult save(SiteGroupForm siteGroupForm){
		
		// 第一步、校验工地组是否存在(根据工地组名称和区域)
		boolean result = siteGroupService.validateSiteGroupExist(siteGroupForm.getSiteGroupName(),siteGroupForm.getSiteGroupAreaId());
		if(!result){
			throw new BizException(StatusCode.SITE_GROUP_REPEAT);
		}
		
		// 第二步、保存工地组
		SiteGroup siteGroup = new SiteGroup();
		siteGroup.setSiteGroupName(siteGroupForm.getSiteGroupName());	// 工地组名称
		siteGroup.setSiteGroupCode(UniqueUtils.generateUniqueCode());	// 工地组编码
		String areaId = siteGroupForm.getSiteGroupAreaId();
		if(StringUtils.isNotBlank(areaId)){
			siteGroup.setArea(areaService.queryById(areaId));
		}
		siteGroupService.save(siteGroup);
		
		return ResponseResult.ok(siteGroup);
	}
	
	/**
	 * 	删除工地分组
	*/
	@RequestMapping(value = "deleteById.action" , method = RequestMethod.GET)
	public ResponseResult deleteById(String siteGroupId){
		
		siteGroupService.deleteById(siteGroupId);
		
		return ResponseResult.ok();
	}
	
	/**
	 *	修改工地分组 
	*/
	@RequestMapping(value = "editById.action" , method = RequestMethod.POST)
	public ResponseResult editById(SiteGroupForm siteGroupForm){
		
		SiteGroup siteGroup = siteGroupService.queryById(siteGroupForm.getSiteGroupId());
		if(siteGroup == null){
			return ResponseResult.notFound();
		}
		
		// 修改工作组名称
		siteGroup.setSiteGroupName(siteGroupForm.getSiteGroupName());
		
		// 修改区域
		String areaId = siteGroupForm.getSiteGroupAreaId();
		if(StringUtils.isNotBlank(areaId)){
			siteGroup.setArea(areaService.queryById(areaId));
		}
		// 更新
		siteGroupService.editById(siteGroup);
		
		return ResponseResult.ok();
	}
	
	
	/**
	 * 	移动功能
	 * 		将工地siteId添加到工地组siteGroupId中
	*/
	@RequestMapping(value = "move.action" , method = RequestMethod.POST)
	public ResponseResult move(SiteAndSiteGroupForm form){
		
		siteGroupService.move(form.getSiteId(),form.getSiteGroupId());
		
		return ResponseResult.ok();
	}
	
	/**
	 * 	从工作组中删除指定的工地
	 * 		将工地siteId添加到工地组siteGroupId中
	*/
	@RequestMapping(value = "deleteSiteFromGroup.action" , method = RequestMethod.GET)
	public ResponseResult deleteSiteFromGroup(String siteId,String siteGroupId){
		
		if(StringUtils.isBlank(siteId)){
			throw new BizException(StatusCode.SITE_ID_BLANK);
		}
		
		if(StringUtils.isBlank(siteGroupId)){
			throw new BizException(StatusCode.SITE_GROUP_ID_BLANK);
		}
		
		// 根据工地id从工作组中删除
		siteGroupService.deleteSiteFromGroup(siteId,siteGroupId);
		
		return ResponseResult.ok();
	}
	
	
}
