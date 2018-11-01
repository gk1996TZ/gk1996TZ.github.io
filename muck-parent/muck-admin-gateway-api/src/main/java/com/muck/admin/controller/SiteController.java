package com.muck.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.domain.AreaType;
import com.muck.domain.Company;
import com.muck.domain.Device;
import com.muck.domain.Disposal;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.domain.Site;
import com.muck.domain.SiteProjectInfo;
import com.muck.excelquerytemplate.SiteExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.SiteQueryForm;
import com.muck.query.SiteVedioQueryForm;
import com.muck.request.SiteForm;
import com.muck.request.SiteProjectInfoForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CompanyService;
import com.muck.service.DeviceService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.service.SiteGroupService;
import com.muck.service.SiteProjectInfoService;
import com.muck.service.SiteService;
import com.muck.service.SiteVedioService;
import com.muck.utils.ArrayUtils;
import com.muck.utils.ExcelUtil;
import com.muck.utils.UploadUtils;

/**
 * @Description: 工地管理controller
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月2日 下午7:53:56
 */
@RestController("AdminSiteController")
@RequestMapping("/admin/site")
public class SiteController extends BaseController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PropertiesService propertiesService;//

	@Autowired
	private ExcelOutputLogService excelOutputLogService;

	@Autowired
	private SiteGroupService siteGroupService;

	@Autowired
	private SiteProjectInfoService siteProjectInfoService;
	/**
	 * @Description: 工地管理系统弹框筛选
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 下午1:50:44
	 * @param: SiteForm
	 *             筛选条件
	 * @return: ResponseResult 返回含有数据的结果集对象
	 * 
	 * 			@RequestMapping("site.action") public ResponseResult
	 *          site(SiteQueryForm siteQueryForm) { return
	 *          ResponseResult.ok(siteService.querySitePageData(siteQueryForm));
	 *          }
	 */

	@Autowired
	private SiteVedioService siteVedioService; // 工地视频service

	/**
	 * @Description: 条件查询工地通道
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午5:07:49
	 * @param: siteVedioQueryForm
	 *             传入查询的条件
	 * @return: ResponseResult 返回含有通道信息的结果集
	 */
	@RequestMapping(value = "querySiteVideo.action", method = RequestMethod.POST)
	public ResponseResult querySiteVideo(SiteVedioQueryForm siteVedioQueryForm) {
		return ResponseResult.ok(siteVedioService.querySiteVedio(siteVedioQueryForm));
	}

	/**
	 * @Description: 查询工地详情
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月8日 上午10:28:41
	 * @param: id
	 *             传入工地id
	 * @return: ResponseResult 返回含有工地详情的结果集对象
	 */
	@RequestMapping("siteInfo.action")
	public ResponseResult siteInfo(String id) {
		Site site = siteService.queryById(id);
		if (site == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(site);
	}

	/**
	 * @Description: 根据工地id查询设备
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月3日 下午2:17:27
	 * @param: siteIds
	 *             传入工地id
	 * @return: ResponseResult 返回含有设备列表的结果集
	 */
	@RequestMapping("queryDevicesBySiteId.action")
	public ResponseResult queryDevicesBySiteId(String siteId) {

		List<Device> listDevice = null;
		if (!StringUtils.isBlank(siteId)) {
			QueryHelper queryHelper = new QueryHelper();

			// 获取设备编号所有查询到的设备编号
			String deviceCodes = deviceService.queryDeviceCodeBySiteId(siteId);
			if (StringUtils.isBlank(deviceCodes)) {
				throw new BizException(StatusCode.NOT_FOUND);
			}
			// 拼接查询条件
			queryHelper.addCondition(deviceCodes, "device.device_code in (%s)", false);
			// 查询到设备的列表
			listDevice = deviceService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		} else {
			listDevice = deviceService.queryData();
		}
		return ResponseResult.ok(listDevice);
	}

	/**
	 * @Description: 通过通道号来查询工地下所有设备的的所有通道
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午2:46:55
	 * @param: channelCode
	 *             传入一个通道编号
	 * @return: ResponseResult 返回含有该工地所有设备的所有通道的结果集
	 */
	@RequestMapping("queryChannelByChannel.action")
	public ResponseResult queryChannelByChannel(String channelCode) {
		String deviceCode = deviceService.queryDeviceCodeByChannelCode(channelCode);
		String siteId = siteService.querySiteIdByDeviceCode(deviceCode);
		return ResponseResult.ok(siteService.queryChannelBySiteId(siteId));
	}

	/**
	 * @Description: 获取所有工地每日设备运行状态统计信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午10:40:30
	 * @return: ResponseResult 返回含有每日设备运行状态的统计信息结果集
	 */
	@RequestMapping("queryDeviceEveryDay.action")
	public ResponseResult queryDeviceEveryDay() {
		// 获取所有的工地id
		String siteIds = siteService.querySiteIdsAll();
		// 获取所有的工地设备
		List<Device> listDevice = null;
		if (!StringUtils.isBlank(siteIds)) {
			QueryHelper queryHelper = new QueryHelper();
			// 获取设备编号所有查询到的设备编号
			String deviceCodes = deviceService.queryDeviceCodeBySiteIds(siteIds);
			// 拼接查询条件
			queryHelper.addCondition(deviceCodes, "site_code in (%s)", false);
			// 查询到设备的列表
			listDevice = deviceService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		} else {
			listDevice = deviceService.queryData();
		}
		// 创建返回给前台的键值对容器
		Map<String, Object> map = new HashMap<String, Object>();
		Integer is_running = 0;
		Integer no_running = 0;
		if (listDevice != null) {
			for (Device device : listDevice) {
				if (device.getIsRunning()) {
					is_running += 1;
				} else {
					no_running += 1;
				}
			}
		}
		// 将正常运行和非正常运行的设备数量分别添加到键值对容器中返回给前台
		map.put("is_running", is_running);
		map.put("no_running", no_running);
		return ResponseResult.ok(map);
	}

	// --------------------------------------------------------------

	@RequestMapping("SYS_site.action")
	public ResponseResult sysSite(SiteQueryForm siteQueryForm) {
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Site> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("ts.created_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		if ("true".equals(siteQueryForm.getQuery())) {

			// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
			QueryHelper queryHelper = new QueryHelper();

			// 区域id
			String areaId = siteQueryForm.getAreaId();
			// 企业id
			String companyId = siteQueryForm.getCompanyId();

			queryHelper.addCondition(areaId, "area_id=%d", true).addCondition(companyId, "company_id=%d", true);

			pageView = siteService.queryPageData(siteQueryForm.getPageNum(), siteQueryForm.getPageSize(),
					queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		} else {
			pageView = siteService.queryPageData();
		}
		return ResponseResult.ok(pageView);
	}

	/**
	 * @Description: 逻辑删除工地信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月19日 下午2:15:18
	 * @param: siteId
	 *             传入一个工地id
	 * @return:ResponseResult 返回删除完需要删除的工地后的新的工地数据
	 */
	@RequestMapping("SYS_deleteSite.action")
	public ResponseResult SYS_deleteSite(String siteId) {
		if (StringUtils.isBlank(siteId)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		siteService.deleteById(siteId);
		return ResponseResult.ok();
	}

	@RequestMapping(value = "SYS_editSiteInfo.action", method = RequestMethod.POST)
	public ResponseResult editSiteInfo(@RequestBody SiteForm siteForm) {
		// 第一步、组装需要被修改的工地的实体对象
		Site site = new Site();
		site.setId(siteForm.getSiteId());
		site.setSiteName(siteForm.getSiteName());
		site.setSiteAddress(siteForm.getSiteAddress());
		site.setSiteCleanerName(siteForm.getSiteCleanerName());
		site.setSiteCleanerPhone(siteForm.getSiteCleanerPhone());
		site.setMemo(siteForm.getMemo());

		// 第二步、封装关联信息
		String areaId = siteForm.getAreaId();
		if (!StringUtils.isBlank(areaId)) {
			// 根据区域id查询区域
			Area area = areaService.queryById(areaId);
			site.setArea(area);
		}
		// 第三步、修改工地信息
		siteService.editById(site);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据工地id和企业id查询设备
	 * 
	 *               这个接口是为添加工地的时候准备的。
	 * @author: 展昭
	 * @date: 2018年5月16日 下午5:22:01
	 */
	@RequestMapping(value = "queryDevicesByCondition.action", method = RequestMethod.GET)
	public ResponseResult queryDevicesByCondition(SiteQueryForm siteQueryForm) {

		// 1、判断是否选择了区域
		String areaId = siteQueryForm.getAreaId();
		if (StringUtils.isBlank(areaId)) {
			throw new BizException(StatusCode.AREA_ID_BLANK);
		}

		// 2、判断是否选择了企业
		String[] companyIds = siteQueryForm.getCompanyIds();
		if (ArrayUtils.isEmpty(companyIds)) {
			throw new BizException(StatusCode.COMPANY_ID_BLANK);
		}

		List<Device> devices = deviceService.queryDevicesByCondition(areaId, companyIds);

		return ResponseResult.ok(devices);
	}

	// -------------

	/**
	 * 根据通道编号查询工地详情
	 */
	@RequestMapping(value = "querySiteInfoByChannelCode.action", method = RequestMethod.GET)
	public ResponseResult querySiteInfoByChannelCode(String channelCode) {

		if (StringUtils.isBlank(channelCode)) {
			throw new BizException(StatusCode.CHANNEL_CODE_BLANK);
		}

		Site site = siteService.querySiteInfoByChannelCode(channelCode);
		SiteProjectInfo siteProjectInfo = null;
		if (site != null) {
			if (StringUtils.isBlank(site.getSiteRegisterCode())) {
				// 说明这个工地没有注册码
				siteProjectInfo = new SiteProjectInfo();
				siteProjectInfo.setProjectName(site.getSiteName());
			}
			// 拿到设备的注册码
			String siteRegisterCode = site.getSiteRegisterCode();

			// 根据设备注册码查询项目详情
			siteProjectInfo = siteProjectInfoService.queryByRegisterCode(siteRegisterCode);
			if (siteProjectInfo == null) {
				// 如果为空,则构造返回数据
				siteProjectInfo = new SiteProjectInfo();
				siteProjectInfo.setProjectName(site.getSiteName());
			}

			return ResponseResult.ok(siteProjectInfo);

		}
		return ResponseResult.notFound();

	}

	// --------------------------------------------------------------------------

	/**
	 * @Description: 添加工地
	 * @author: 展昭
	 * @date: 2018年5月15日 上午10:15:25
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public ResponseResult save(SiteForm siteForm) {
		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Site site = new Site();
		site.setSiteName(siteForm.getSiteName()); // 工地名称
		site.setSiteAddress(siteForm.getSiteAddress()); // 工地地址
		site.setSiteCleanerName(siteForm.getSiteCleanerName()); // 保洁人员
		site.setSiteCleanerPhone(siteForm.getSiteCleanerPhone()); // 保洁人员联系电话
		site.setMemo(siteForm.getMemo()); // 工地描述
		site.setSiteProjectManagerOne(siteForm.getSiteProjectManagerOne());// 项目负责人1
		site.setSiteProjectManagerPhoneOne(siteForm.getSiteProjectManagerPhoneOne());// 项目负责人1联系方式
		site.setSiteProjectManagerTwo(siteForm.getSiteProjectManagerTwo());// 项目负责人2
		site.setSiteProjectManagerPhoneTwo(siteForm.getSiteProjectManagerPhoneTwo());// 项目负责人2联系方式
		site.setSiteProcessMemo(siteForm.getSiteProcessMemo()); // 工程进度
		// 第二步、封装关联信息
		String areaId = siteForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			// 查询区域
			Area area = areaService.queryById(areaId);
			if (area != null) {
				site.setArea(area);
			}
		}

		String companyId = siteForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			// 查询企业
			Company company = companyService.queryById(companyId);
			if (company != null) {
				site.setCompany(company);
			}
		}
		String companyName = siteForm.getCompanyName();
		if (!StringUtils.isBlank(companyName)) {
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			site.setCompany(company);
		}
		// 第三步、设置项目情况登记表
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		BeanUtils.copyProperties(siteForm, siteProjectInfo);
		siteProjectInfo.setMemo(siteForm.getProjectInfoMemo());

		// 第四步、设置关联关系
		site.setSiteProjectInfo(siteProjectInfo);

		// 第五步、保存工地
		siteService.save(site);

		return ResponseResult.ok();
	}

	/**
	 * 根据id删除工地
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	public ResponseResult deleteById(String siteId) {

		siteService.deleteById(siteId);

		return ResponseResult.ok();
	}

	/**
	 * 根据id查询工地
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String siteId) {

		Site site = siteService.queryById(siteId);
		if (site == null) {
			return ResponseResult.notFound();
		}
		if (StringUtils.isBlank(site.getSiteRegisterCode())) {
			throw new BizException(StatusCode.SITE_PROJECT_NOT_BIND);
		}
		String registerCode = site.getSiteRegisterCode();

		// 拿到这个注册码去查询项目详情表
		SiteProjectInfo siteProjectInfo = siteProjectInfoService.queryByRegisterCode(registerCode);

		if (siteProjectInfo == null) {
			throw new BizException(StatusCode.SITE_PROJECT_BIND_ERROR);
		}

		return ResponseResult.ok(siteProjectInfo);
	}

	/**
	 * 更新项目进度*(old)
	 */
	@RequestMapping(value = "editSiteProjectProcess.action", method = RequestMethod.POST)
	public ResponseResult editSiteProjectProcess(String siteProjectInfoId, String siteProcessMemo) {

		if (StringUtils.isBlank(siteProjectInfoId)) {
			throw new BizException(StatusCode.SITE_ID_BLANK);
		}

		if (StringUtils.isBlank(siteProcessMemo)) {
			throw new BizException(StatusCode.SITE_PROCESS_MEMO_BLANK);
		}
		// 维护修改的信息
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		siteProjectInfo.setId(siteProjectInfoId);
		siteProjectInfo.setProjectInfoMemo(siteProcessMemo);

		return ResponseResult.ok();
	}

	/**
	 * 更新项目进度*(new)
	 */
	@RequestMapping(value = "editSiteProjectProcessNew.action", method = RequestMethod.POST)
	public ResponseResult editSiteProjectProcessNew(String siteProjectInfoId, String siteProcessMemo) {

		if (StringUtils.isBlank(siteProjectInfoId)) {
			throw new BizException(StatusCode.SITE_PROJECT_INFO_ID_BLANK);
		}

		if (StringUtils.isBlank(siteProcessMemo)) {
			throw new BizException(StatusCode.SITE_PROCESS_MEMO_BLANK);
		}
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		siteProjectInfo.setId(siteProjectInfoId);
		siteProjectInfo.setProjectInfoMemo(siteProcessMemo);

		// 更新数据库
		siteProjectInfoService.editById(siteProjectInfo);

		return ResponseResult.ok();
	}

	/***
	 * 根据工地id修改工地项目信息
	 */
	@RequestMapping(value = "updateById.action", method = RequestMethod.POST)
	public ResponseResult updateById(SiteProjectInfoForm siteProjectInfoForm) {

		if (siteProjectInfoForm.getId() == null) {
			throw new BizException(StatusCode.SITE_PROJECT_INFO_ID_BLANK);
		}
		// 构造一个更新实体
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();

		BeanUtils.copyProperties(siteProjectInfoForm, siteProjectInfo);

		// 执行更新
		siteProjectInfoService.editById(siteProjectInfo);

		return ResponseResult.ok();
	}

	/**
	 * 校验工地是否已经维护了项目
	 */
	@RequestMapping(value = "validateSiteProjectExist.action", method = RequestMethod.GET)
	public ResponseResult validateSiteProjectExist(String siteId) {

		if (StringUtils.isBlank(siteId)) {
			throw new BizException(StatusCode.SITE_ID_BLANK);
		}
		Site siteDB = siteService.queryById(siteId);
		if (siteDB == null) {
			return ResponseResult.notFound();
		}
		if (StringUtils.isBlank(siteDB.getSiteRegisterCode())) {
			throw new BizException(StatusCode.SITE_PROJECT_NOT_BIND);
		}
		String registerCode = siteDB.getSiteRegisterCode();

		// 拿到这个注册码去查询项目详情表
		SiteProjectInfo siteProjectInfo = siteProjectInfoService.queryByRegisterCode(registerCode);

		if (siteProjectInfo == null) {
			throw new BizException(StatusCode.SITE_PROJECT_BIND_ERROR);
		}
		return ResponseResult.ok(siteProjectInfo);
	}

	/***
	 * 根据工地id修改工地信息
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	public ResponseResult editById(SiteForm siteForm) {
		String siteId = siteForm.getSiteId(); // 工地id
		if (StringUtils.isBlank(siteId)) {
			throw new BizException(StatusCode.SITE_ID_BLANK);
		}

		String siteProjectInfoId = siteForm.getSiteProjectInfoId(); // 项目情况id
		if (StringUtils.isBlank(siteProjectInfoId)) {
			throw new BizException(StatusCode.SITE_PROJECT_INFO_ID_BLANK);
		}

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Site site = new Site();
		site.setId(siteId);
		site.setSiteName(siteForm.getSiteName()); // 工地名称
		site.setSiteAddress(siteForm.getSiteAddress()); // 工地地址
		site.setSiteCleanerName(siteForm.getSiteCleanerName()); // 保洁人员
		site.setSiteCleanerPhone(siteForm.getSiteCleanerPhone()); // 保洁人员联系电话
		site.setMemo(siteForm.getMemo()); // 工地描述
		site.setSiteProjectManagerOne(siteForm.getSiteProjectManagerOne());// 项目负责人1
		site.setSiteProjectManagerPhoneOne(siteForm.getSiteProjectManagerPhoneOne());// 项目负责人1联系方式
		site.setSiteProjectManagerTwo(siteForm.getSiteProjectManagerTwo());// 项目负责人2
		site.setSiteProjectManagerPhoneTwo(siteForm.getSiteProjectManagerPhoneTwo());// 项目负责人2联系方式
		site.setSiteProcessMemo(siteForm.getSiteProcessMemo()); // 工程进度

		// 第二步、封装关联信息
		String areaId = siteForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			// 查询区域
			Area area = areaService.queryById(areaId);
			if (area != null) {
				site.setArea(area);
			}
		}

		String companyId = siteForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			// 查询企业
			Company company = companyService.queryById(companyId);
			if (company != null) {
				site.setCompany(company);
			}
		}
		String companyName = siteForm.getCompanyName();
		if (!StringUtils.isBlank(companyName)) {
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			site.setCompany(company);
		}
		// 第三步、设置项目情况登记表
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		siteProjectInfo.setId(siteProjectInfoId);
		BeanUtils.copyProperties(siteForm, siteProjectInfo);
		siteProjectInfo.setMemo(siteForm.getProjectInfoMemo());

		// 第四步、设置关联关系
		site.setSiteProjectInfo(siteProjectInfo);

		// 第五步、保存工地
		siteService.editById(site);

		return ResponseResult.ok();
	}

	/***
	 * @Description: 加载工地组织树
	 * @param:描述1描述
	 * @author: 展昭
	 * @date: 2018年4月18日 下午3:40:05
	 */
	@RequestMapping(value = "initTreeSites.action", method = RequestMethod.GET)
	public ResponseResult initTreeSites() {

		Integer areaType = AreaType.SITE.getType();

		// 1、查询所有的区域
		List<Area> areas = areaService.queryAreasByDeep(areaType);

		// 2、查询根据区域查询每个区域下面的所有的工地
		siteService.initTreeSites(areas);

		return ResponseResult.ok(areas);
	}

	/**
	 * 工地列表
	 */
	@RequestMapping(value = "querySites.action", method = RequestMethod.POST)
	public ResponseResult querySites(SiteQueryForm siteQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Site> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		// 区域id
		String areaId = siteQueryForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {

			String subAreaIds = areaService.querySubAreaIdsByParentId(areaId);
			if (StringUtils.isNotBlank(subAreaIds)) {
				queryHelper.addCondition(subAreaIds, "area_id in (%s)", false);
			}
		}
		// 工地组id
		String siteGroupId = siteQueryForm.getSiteGroupId();
		if (StringUtils.isNotBlank(siteGroupId)) {
			String siteIds = siteGroupService.querySiteIdsBySiteGroupId(siteGroupId);
			if (StringUtils.isNotBlank(siteIds)) {
				queryHelper.addCondition(siteIds, "id in (%s)", false);
			}
		}

		pageView = siteService.queryPageData(siteQueryForm.getPageNum(), siteQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);

		return ResponseResult.ok(pageView);
	}

	/**
	 * 工地列表不带分页
	 */
	@RequestMapping(value = "queryAllSites.action", method = RequestMethod.GET)
	public ResponseResult queryAllSites() {

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		List<Site> sites = siteService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());

		return ResponseResult.ok(sites);
	}

	
	
	/**
	 * @Description: 导入工地Excel
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:15:32
	 * @param: request
	 *             传入一个含有Excel表格文件的HttpServletRequest请求
	 * @return:ResponseResult 返回含有操作信息的结果集
	 */
	@RequestMapping("importExcel.action")
	public ResponseResult importExcel(HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步：将导入的Excel表格存放到指定的本地目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(), "site", request);
		// 第二步：从本地存放好的文件中读取数据
		List<Map<String, String>> excelData = ExcelUtil.read(propertiesService.getCurrentServerForImage(), path,
				propertiesService.getWindowsRootPath());
		// 第三步：将表格中的数据转化为添加到数据库中的数据列表
		List<Site> list = siteService.selectDataFromExcelMapData(new SiteExcelQueryTemplate(), excelData);
		if (list != null) {
			for (Site site : list) {
				site.setOperatorId(IdTypeHandler.decode(manager.getId()));
				site.setOperatorName(manager.getManagerName());
			}
			// 将导入的数据添加到数据库中
			siteService.saveAll(list);
		}
		return ResponseResult.ok();
	}
	
	
	
	
	/**
	 * 导出单个或多个工地信息
	 */

	@RequestMapping(value = "exportSiteExcelInfo.action", method = RequestMethod.POST)
	public ResponseResult exportSiteExcelInfo(SiteQueryForm siteQueryForm, HttpSession session) {
		// 0.验证登录
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		QueryHelper queryHelper = null;
		if (siteQueryForm.getSiteId() != null) {
			// 说明是导出单个工地
			String siteId = siteQueryForm.getSiteId();
			queryHelper = new QueryHelper();
			queryHelper.addCondition(siteId, "id=%d", true);
		} else {
			queryHelper = new QueryHelper();
			// 区域id
			String areaId = siteQueryForm.getAreaId();
			if (StringUtils.isNotBlank(areaId)) {
				queryHelper.addCondition(areaId, "area_id=%d", true);
			}
		}
		queryHelper.addCondition(1, "deleted=%s", false);

		List<Site> sites = siteService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		if (!sites.isEmpty()) {

			// 生成表格数据
			List<Map<String, String>> excelData = siteService.createExcelData(new SiteExcelQueryTemplate(), sites);
			if (!excelData.isEmpty()) {

				// 生成地址
				String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),
						propertiesService.getWindowsRootPath(), "site", excelData,
						propertiesService.getWindowsCreateWaterRootPath(), null);
				if (!StringUtils.isBlank(outputExcelPath)) {
					// 如果生成的路径不为""，组成一个Excel文件导出的操作日志对象
					ExcelOutputLog excelOutputLog = new ExcelOutputLog();
					excelOutputLog.setOutputExcelName(outputExcelPath.substring(outputExcelPath.lastIndexOf("/") + 1));
					excelOutputLog.setOutputExcelPath(outputExcelPath);
					excelOutputLog.setOperatorId(IdTypeHandler.decode(manager.getId()));
					excelOutputLog.setOperatorName(manager.getManagerName());
					Date date = new Date();
					excelOutputLog.setCreatedTime(date);
					excelOutputLog.setUpdatedTime(date);
					// 添加该条导出日志记录
					excelOutputLogService.save(excelOutputLog);
					return ResponseResult.ok(outputExcelPath);
				}
			}
		}
		throw new BizException(StatusCode.EXPORT_FAILE);
	}

	/**
	 * 传入工地设备注册码 查询工地id
	 */
	@RequestMapping(value = "getSiteId.action", method = RequestMethod.GET)
	public ResponseResult getSiteId(String registerCode) {

		if (StringUtils.isBlank(registerCode)) {
			throw new BizException(StatusCode.DEVICE_REGISITER_ID_BLANK);
		}
		String siteId = siteService.getSiteIdByRegisterCode(registerCode);
		if (siteId == null) {
			throw new BizException(StatusCode.SITE_PROJECT_NOT_BIND);
		}
		return ResponseResult.ok(siteId);
	}
}