package com.muck.front.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Area;
import com.muck.domain.AreaType;
import com.muck.domain.Channel;
import com.muck.domain.Site;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.ChannelQueryForm;
import com.muck.response.ResponseResult;
import com.muck.service.AreaService;
import com.muck.service.ChannelService;
import com.muck.service.SiteService;

/**
 * 工地Controller
 */
@RestController("FrontSiteController")
@RequestMapping("/front/site/")
public class SiteController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteService siteService; // 工地service

	@Autowired
	private AreaService areaService;// 区域service

	@Autowired
	private ChannelService channelService;
	/***
	 * 工地树
	*/
	@RequestMapping(value = "initTreeSites.action", method = RequestMethod.GET)
	public ResponseResult initTreeSites() {

		// 查询区域为工地
		Integer areaType = AreaType.SITE.getType();
		
		// 1、查询所有的区域
		List<Area> areas = areaService.queryAreasByDeep(areaType);

		// 2、查询根据区域查询每个区域下面的所有的工地
		siteService.initTreeSites(areas);

		return ResponseResult.ok(areas);
	}

	/***
	 *	根据通道号查询工地详情 
	*/
	@RequestMapping(value = "querySitInfoByChannelCode.action" , method = RequestMethod.GET)
	public ResponseResult querySitInfoByChannelCode(String channelCode){
		
		if (StringUtils.isBlank(channelCode)) {
			logger.error("调用querySitInfoByChannelCode接口时CHANNEL_CODE_BLANK为空");
		}
		try {
			Site site = siteService.querySiteInfoByChannelCode(channelCode);

			return ResponseResult.ok(site);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}

	/**
	 * 根据id查询工地
	 */
	@RequestMapping(value = "querySiteInfoById.action", method = RequestMethod.GET)
	public ResponseResult querySiteInfoById(String siteId) {

		Site site = siteService.queryById(siteId);
		if (site == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(site);
	}
	
	
	
	/**
	 * @Description: 根据区域查询通道（带分页）
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月2日 下午12:26:19
	 * @Param: String areaId
	 * @Return: ResponseResult
	 */
	@RequestMapping("queryChannelByAreaId.action")
	public ResponseResult queryChannelByAreaId(ChannelQueryForm channelQueryForm){
		String areaId = channelQueryForm.getAreaId();
		if(StringUtils.isBlank(areaId)){
			logger.error("调用queryChannelByAreaId接口时areaId为空");
		}
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Channel> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("channel.created_time", "desc");
		
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		
		String areaIds = areaService.querySubAreaIdsByParentId(areaId);
		
		queryHelper.addCondition(areaIds," device.device_area_id in (%s) ", false);
		pageView = channelService.queryPageData(channelQueryForm.getPageNum(), channelQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		
		return ResponseResult.ok(pageView);
	}
}
