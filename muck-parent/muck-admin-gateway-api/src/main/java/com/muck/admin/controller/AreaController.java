package com.muck.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.exception.base.BizException;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.AreaQueryForm;
import com.muck.request.AreaForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.utils.CollectionUtils;

/**
 * @Description: 区域controller
 * @author: 展昭
 * @date: 2018年4月18日 下午3:31:35
 */
@RestController("AdminAreaController")
@RequestMapping("/admin/area")
public class AreaController extends BaseController {

	@Autowired
	private AreaService areaService; // 区域Service
	
	/**
	 * @Description: 添加区域
	 * @author: 展昭
	 * @date: 2018年4月18日 下午4:30:33
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorModel = "区域", operatorDesc = "添加区域")
	public ResponseResult save(AreaForm areaForm) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Area area = new Area();
		area.setAreaLatitude(areaForm.getAreaLatitude());
		area.setAreaLongitude(areaForm.getAreaLongitude());
		area.setAreaName(areaForm.getAreaName());
		area.setMemo(areaForm.getMemo());

		// 第二步、判断是否存在parentAreaId,如果有表示刚刚添加的区域为这个parentAreaId的子区域,如果没有,则表示是顶级区域
		String parentAreaId = areaForm.getParentAreaId();
		if (StringUtils.isNotBlank(parentAreaId)) {
			// parentAreaId不为空表示有父区域,则设置父区域
			Area parentArea = areaService.queryById(parentAreaId);
			if (parentArea != null) {
				// 再次判断是否存在父区域,如果有,则真正的设置
				area.setParent(parentArea); // 设置子与父的关系
				parentArea.getChildAreas().add(area); // 设置关系
			}
		}

		// 第四步、添加保存
		areaService.save(area);
		
		/**
		 * 	创建一个新的Map主要目的是返回给前台使用
		 * 		至于为什么不直接返回area,原因在于Area对象里面有循环引用,会导致循环递归解析.
		*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", area.getId());
		map.put("areaName", area.getAreaName());
		map.put("createdTime", area.getCreatedTime());
		map.put("updatedTime", area.getUpdatedTime());
		Area parent = area.getParent();
		if(parent != null){
			map.put("parentAreaName", parent.getAreaName());
		}
		
		return ResponseResult.ok(map);
	}

	/**
	 * @Description: 根据区域id删除区域
	 * @param: 区域id
	 * @author: 展昭
	 * @date: 2018年4月19日 上午10:15:43
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@Logger(operatorModel = "区域", operatorDesc = "根据区域id删除区域")
	public ResponseResult deleteById(String areaId) {

		// 第一步、根据区域id查询此区域下面是否有子区域
		Long count = areaService.querySubAreaCount(areaId);
		if (count != null && count > 0L) {
			throw new BizException(StatusCode.AREA_HAS_SUB_AREA);
		}

		// 第二步、如果区域id下面没有子区域,则直接删除(其实是禁用)
		areaService.deleteById(areaId);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id修改区域
	 * @param: 请求参数实体
	 * @author: 展昭
	 * @date: 2018年4月19日 上午10:59:31
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	@Logger(operatorModel = "区域", operatorDesc = "根据id修改区域")
	public ResponseResult editById(AreaForm areaForm) {

		// 第一步、根据区域的id查询区域
		Area area = areaService.queryById(areaForm.getAreaId());
		if(area == null){
			return ResponseResult.notFound();
		}
		// 第二步、设置基本修改的内容
		area.setAreaLatitude(areaForm.getAreaLatitude());
		area.setAreaLongitude(areaForm.getAreaLongitude());
		area.setAreaName(areaForm.getAreaName());

		// 第三步、判断是否传入parentAreaId,如果有则表示要修改的区域是这个parentAreaId的子区域
		String parentAreaId = areaForm.getParentAreaId();
		if (StringUtils.isNotBlank(parentAreaId)) {
			Area parentArea = areaService.queryById(parentAreaId);
			if (parentArea != null) {
				// 再次判断是否存在父区域,如果有,则真正的设置
				area.setParent(parentArea); // 设置子与父的关系
				parentArea.getChildAreas().add(area); // 设置关系
			}
		}

		// 第四步、修改保存
		areaService.editById(area);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据区域areaId查询区域
	 * @param: 区域areaId
	 * @author: 展昭
	 * @date: 2018年4月19日 上午11:22:47
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String areaId) {

		// 根据区域id查询区域
		Area area = areaService.queryById(areaId);
		if (area == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(area);
	}

	/**
	 * @Description: 查询顶级区域(一级区域)
	 * @author: 展昭
	 * @date: 2018年4月18日 下午3:32:51
	 */
	@RequestMapping(value = "queryTopAreas.action", method = RequestMethod.GET)
	public ResponseResult queryTopAreas() {

		List<Area> areas = areaService.queryTopAreas();
		if (areas == null || areas.isEmpty()) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(areas);
	}

	/***
	 * @Description: 根据某一父区域的id查询该区域下面的子区域
	 * @param:描述1描述
	 * @author: 展昭
	 * @date: 2018年4月18日 下午3:40:05
	 */
	@RequestMapping(value = "querySubAreasByParentId.action", method = RequestMethod.GET)
	public ResponseResult querySubAreasByParentId(String parentId) {

		List<Area> areas = areaService.querySubAreasByParentId(parentId,null);

		if (areas == null || areas.isEmpty()) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(areas);
	}

	/***
	 * @Description: 递归查询所有区域(包括所有的子区域)
	 * @param:描述1描述
	 * @author: 展昭
	 * @date: 2018年4月18日 下午3:40:05
	 */
	@RequestMapping(value = "queryAllAreas.action", method = RequestMethod.GET)
	public ResponseResult queryAllAreas() {

		List<Area> areas = areaService.queryAreasByDeep(null);

		return ResponseResult.ok(CollectionUtils.toList(areas));
	}

	/**
	 * @Description: 后台区域管理区域列表（含条件）查询
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月19日 下午8:13:08
	 * @param: areaQueryForm
	 *             传入查询区域的条件
	 * @return:ResponseResult 返回含有区域列表的结果集
	 */
	@RequestMapping("SYS_area.action")
	public ResponseResult area(AreaQueryForm areaQueryForm) {
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Area> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		if ("true".equals(areaQueryForm.getQuery())) {

			// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
			QueryHelper queryHelper = new QueryHelper();

			// 获取父级的区域编号
			String parentCodes = areaQueryForm.getAreaCode();

			// 获取父级区域列表下所有的子级区域编号
			StringBuilder areaCodes = new StringBuilder("");
			if (!StringUtils.isBlank(parentCodes)) {
				String[] arr_parentCode = parentCodes.split(",");
				for (String parentCode : arr_parentCode) {
					String subAreaCodes = areaService.querySubAreaCodesByParentCode(parentCode);
					if (!StringUtils.isBlank(subAreaCodes)) {
						areaCodes.append(subAreaCodes);
						areaCodes.append(",");
					}
				}
				if (areaCodes.length() > 0) {
					areaCodes.deleteCharAt(areaCodes.length() - 1);
				}
			}
			String[] arr_areaCodes = areaCodes.toString().split(",");
			StringBuilder sb = new StringBuilder();
			for (String areaCode : arr_areaCodes) {
				sb.append("'" + areaCode + "'");
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			queryHelper.addCondition(sb, " area_code  in (%s) ", false);
			pageView = areaService.queryPageData(areaQueryForm.getPageNum(), areaQueryForm.getPageSize(),
					queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		} else {
			pageView = areaService.queryPageData();
		}
		List<Area> listArea = pageView.getDatas();
		if (listArea != null && listArea.size() > 0) {
			for (Area area : listArea) {
				area.setParent(areaService.queryByAreaCode(area.getParentCode()));
			}
		}
		return ResponseResult.ok(pageView);
	}

	/**
	 * 	查询所有的区域
	*/
	@RequestMapping(value = "queryAll.action" ,method = RequestMethod.GET )
	public ResponseResult queryAll(AreaQueryForm queryForm) {
		
		List<Area> areas = areaService.queryAllAreas();
		
		return ResponseResult.ok(areas);
	}
	
	/**
	 * 	查询所有的区域
	*/
	@RequestMapping(value = "xx.action" ,method = RequestMethod.GET )
	public void xx() {
		
		List<String> list = new ArrayList<String>();  
        list.add("cmd.exe");  
        list.add("/c");  
        list.add("start");  
        list.add("\"" + "ScreenCapture.exe" + "\"");  
        list.add("\"" + "D:\\" + "\"");  
        ProcessBuilder pBuilder = new ProcessBuilder(list);  
        try {
			pBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
