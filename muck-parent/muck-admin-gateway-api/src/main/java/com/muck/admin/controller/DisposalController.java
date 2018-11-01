package com.muck.admin.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Area;
import com.muck.domain.Company;
import com.muck.domain.Device;
import com.muck.domain.Disposal;
import com.muck.domain.DisposalCarTurnover;
import com.muck.domain.DisposalMuckTurnover;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.DisposalExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.DisposalQueryForm;
import com.muck.query.DisposalVedioQueryForm;
import com.muck.request.DisposalForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CompanyService;
import com.muck.service.DeviceService;
import com.muck.service.DisposalCarService;
import com.muck.service.DisposalMuckService;
import com.muck.service.DisposalService;
import com.muck.service.DisposalVedioService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;
import com.muck.utils.UploadUtils;

/**
 * @Description: 处置场管理controller
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月4日 下午7:52:11
 */
@RestController("AdminDisposalController")
@RequestMapping("/admin/disposal")
public class DisposalController extends BaseController {

	@Autowired
	private DisposalService disposalService;
	@Autowired
	private DisposalCarService disposalCarService;
	@Autowired
	private DisposalMuckService disposalMuckService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DisposalVedioService disposalVedioService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PropertiesService propertiesService;

	/**
	 * @Description:
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:32:41
	 * @param:@param disposalForm
	 * @param:@param session
	 * @return:ResponseResult
	 */
	@RequestMapping("save.action")
	public ResponseResult save(DisposalForm disposalForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			// throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 获取添加处置场时的数据参数
		Disposal disposal = new Disposal();
		// 将请求的数据参数映射到指定的对象中
		BeanUtils.copyProperties(disposalForm, disposal);
		disposal.setDisposalId(disposalForm.getChannelCode());
		// disposal.setOperatorId(IdTypeHandler.decode(manager.getId()));
		// disposal.setOperatorName(manager.getManagerName());
		// 设置处置场所属企业
		String companyId = disposalForm.getCompanyId();
		if (!StringUtils.isBlank(companyId)) {
			Company company = companyService.queryById(companyId);
			if (company != null) {
				disposal.setCompanyId(company.getId());
				disposal.setCompanyName(company.getCompanyName());
			}
		}
		// 设置处置场所属区域
		String areaId = disposalForm.getAreaId();
		if (!StringUtils.isBlank(areaId)) {
			Area area = areaService.queryById(areaId);
			if (area != null) {
				disposal.setAreaId(area.getId());
				disposal.setAreaName(area.getAreaName());
				disposal.setAreaCode(area.getAreaCode());
			}
		}
		disposalService.save(disposal);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id删除（逻辑删除）处置场数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:40:32
	 * @param: disposalId
	 *             传入一个处置场id
	 * @return:ResponseResult 返回操作的信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		disposalService.deleteById(id);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据处置场id删除（真实删除）处置场数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:39:39
	 * @param: disposalId
	 *             传入一个处置场id
	 * @return:ResponseResult 返回操作的信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String disposalId) {
		if (StringUtils.isBlank(disposalId)) {
			throw new BizException(StatusCode.AREA_ID_BLANK);
		}
		disposalService.deleteByIdReal(disposalId);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 修改处置场数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:47:09
	 * @param: disposalForm
	 *             传入一个处置场的数据参数
	 * @return:ResponseResult 返回修改的操作信息
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	public ResponseResult edit(DisposalForm disposalForm) {

		//
		// 处置场id(通道号)
		String id = disposalForm.getId();
		if (StringUtils.isBlank(id)) {
			throw new BizException(StatusCode.DISPOSAL_ID_BLANK);
		}
		// 更新
		Disposal disposal = new Disposal();
		BeanUtils.copyProperties(disposalForm, disposal);
		disposal.setDisposalId(disposalForm.getChannelCode());

		// 设置处置场所属企业
		String companyId = disposalForm.getCompanyId();
		if (!StringUtils.isBlank(companyId)) {
			Company company = companyService.queryById(companyId);
			if (company != null) {
				disposal.setCompanyId(company.getId());
				disposal.setCompanyName(company.getCompanyName());
			}
		}
		// 设置处置场所属区域
		String areaId = disposalForm.getAreaId();
		if (!StringUtils.isBlank(areaId)) {
			Area area = areaService.queryById(areaId);
			if (area != null) {
				disposal.setAreaId(area.getId());
				disposal.setAreaName(area.getAreaName());
			}
		}
		disposalService.editById(disposal);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 查询处置场的分页数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月4日 下午8:06:31
	 * @param: disposalForm
	 *             传入查询的条件
	 * @return: ResponseResult 返回含有分页数据的结果集对象
	 */
	@RequestMapping("disposal.action")
	public ResponseResult disposal(DisposalForm disposalForm) {
		PageView<Disposal> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据地域查询处置场
		String areaId = disposalForm.getAreaId();
		// 根据企业id查询处置场
		String companyId = disposalForm.getCompanyId();
		// 根据通道号查询处置场
		String disposalId = disposalForm.getChannelCode();
		// 根据处置场id查询处置场
		String id = disposalForm.getId();
		if(!StringUtils.isBlank(areaId)){
			queryHelper.addCondition(areaService.querySubAreaIdsByParentId(areaId), "area_id in (%s)", false);
		}
				queryHelper.addCondition(companyId, "company_id = %d", true)
				.addCondition(disposalId, "disposal_id = %s", false)
				.addCondition(id,"id = %d", true)
				.addCondition(1, "deleted = %d", false);
		pageView = disposalService.queryPageData(disposalForm.getPageNum(), disposalForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}

	/*
	 * @RequestMapping("disposal.action") public ResponseResult
	 * disposal(DisposalForm disposalForm){ //调用dll文件里的方法 String result =
	 * DLLLibrary.INSTANCE.say(new WString("111")); // 0、初始化:
	 * 创建PageView对象,这个对象就是给前端用户的所有数据 PageView<Disposal> pageView = null;
	 * 
	 * // 1、组装orderby LinkedHashMap<String, String> orderby = new
	 * LinkedHashMap<String,String>(); orderby.put("created_time", "desc");
	 * 
	 * // 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
	 * if("true".equals(disposalForm.getQuery())){
	 * 
	 * // 如果是要查询,那么就创建一个QueryHelper的查询帮助对象 QueryHelper queryHelper = new
	 * QueryHelper();
	 * 
	 * // 根据地域查询工地 String areaId = disposalForm.getAreaId();
	 * 
	 * // 根据企业id查询工地 String companyId = disposalForm.getCompanyId();
	 * 
	 * queryHelper.addCondition(areaService.querySubAreaIdsByParentId(areaId),
	 * "area_id in (%s)",false) .addCondition(companyId, "company_id = %d",
	 * false); pageView =
	 * disposalService.queryPageData(disposalForm.getPageNum(),disposalForm.
	 * getPageSize(),queryHelper.getWhereSQL(),queryHelper.getWhereParams(),
	 * orderby); }else{ pageView = disposalService.queryPageData(); } return
	 * ResponseResult.ok(pageView); }
	 */
	/**
	 * @Description: 查询处置场视频
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月17日 下午2:34:47
	 * @param: disposalVedioQueryForm
	 *             传入查询处置场视频的请求参数
	 * @return: ResponseResult 返回含有处置场设备信息的结果集
	 */
	@RequestMapping(value = "queryDisposalVideo.action", method = RequestMethod.POST)
	public ResponseResult querySiteVideo(DisposalVedioQueryForm disposalVedioQueryForm) {
		return ResponseResult.ok(disposalVedioService.queryDisposalVedio(disposalVedioQueryForm));
	}

	/**
	 * @Description: 根据id查询处置场详情
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午12:10:58
	 * @param: id
	 *             传入处置场id
	 * @return: ResponseResult 返回含有处置场详情的结果集对象
	 */
	@RequestMapping("disposalInfo.action")
	public ResponseResult disposalInfo(String id) {
		Disposal disposal = disposalService.queryById(id);
		if (disposal == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(disposal);
	}

	/**
	 * @Description: 处置场进出渣土数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午2:05:59
	 * @return: ResponseResult 返回含有处置场进出渣土数据的结果集
	 */
	@RequestMapping("disposalMuckInfo.action")
	public ResponseResult disposalMuckInfo(String disposalId, Integer count) {
		List<DisposalMuckTurnover> list = disposalMuckService.queryDisposalMuckTurnover(disposalId, count);
		if (list == null | list.size() == 0) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(list);
	}

	/**
	 * @Description: 处置场车辆进出数据
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月7日 下午2:08:18
	 * @return: ResponseResult 返回含有处置场车辆进出数据的结果集
	 */
	@RequestMapping("disposalCarInfo.action")
	public ResponseResult disposalCarInfo(String disposalId, Integer count) {
		List<DisposalCarTurnover> list = disposalCarService.queryDisposalCarTurnover(disposalId, count);
		if (list == null | list.size() == 0) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(list);
	}

	/**
	 * @Description: 根据处置场id查询设备
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:56:33
	 * @param: siteId
	 *             传入一个处置场id
	 * @return: ResponseResult 返回含有设备列表的结果集
	 */
	@RequestMapping("queryDevicesByDisposalId.action")
	public ResponseResult queryDevicesByDisposalId(String id) {

		List<Device> listDevice = null;
		if (!StringUtils.isBlank(id)) {
			QueryHelper queryHelper = new QueryHelper();

			// 获取设备编号所有查询到的设备编号
			String deviceCodes = deviceService.queryDeviceCodeByDisposalId(id);
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
	 * @Description: 获取所有处置场的所有的设备的运行状态
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午11:16:33
	 * @return: ResponseResult 返回含有所有处置场所有的设备的运行状态信息统计
	 */
	@RequestMapping("queryDeviceStatistics.action")
	public ResponseResult queryDeviceEveryDay() {
		// 获取所有的处置场id
		String disposalIds = disposalService.queryDisposalIdsAll();
		// 获取所有的处置场设备
		List<Device> listDevice = null;
		if (!StringUtils.isBlank(disposalIds)) {
			QueryHelper queryHelper = new QueryHelper();
			// 获取设备编号所有查询到的设备编号
			String deviceCodes = deviceService.queryDeviceCodeByDisposalIds(disposalIds);
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

	/**
	 * @Description: 导出处置场Excel表格
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午2:12:43
	 * @param: disposalQueryForm
	 *             传入一个处置场请求
	 * @param: session
	 *             传入一个HttpSession请求会话
	 * @return:ResponseResult 返回含有下载表格的路径和操作信息的结果集
	 */
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(DisposalQueryForm disposalQueryForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 创建一个QueryHelper帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据处置场名称查询
		queryHelper.addCondition(disposalQueryForm.getDisposalName(), "disposal_name = '%s'", false);
		// 根据处置场所属区域查询
		String parentCode = disposalQueryForm.getAreaCode();
		if (!StringUtils.isBlank(parentCode)) {
			// 获取所有的子级区域编号
			String areaCodes = areaService.querySubAreaCodesByParentCode(parentCode);
			queryHelper.addCondition(areaCodes, "area_code in (%s)", false);
		}
		// 根据处置场所属区域名称查询
		queryHelper.addCondition(disposalQueryForm.getAreaName(), "area_name = '%s'", false)
				// 根据企业id查询处置场
				.addCondition(disposalQueryForm.getCompanyId(), "company_id = %d", true)
				// 根据企业名称查询处置场
				.addCondition(disposalQueryForm.getCompanyName(), "company_name = '%s'", false)
				// 处置场id
				.addCondition(disposalQueryForm.getId(), "id = %d", true)
				.addCondition(1, "deleted= %d", false);
		List<Disposal> list = disposalService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		// 生成Excel表格数据
		DisposalExcelQueryTemplate excelTemplate = new DisposalExcelQueryTemplate("true", "true", "true", "true",
				"true", "true", "true","true");
		List<Map<String, String>> data = disposalService.createExcelData(excelTemplate, list);
		// 生成水印
		String[] water = new String[] {
		};
		// 生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),
				propertiesService.getWindowsRootPath(), "disposal", data,
				propertiesService.getWindowsCreateWaterRootPath(), water);

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
		}
		return ResponseResult.ok(outputExcelPath);
	}

	/**
	 * @Description: 导入处置场Excel
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
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(), "disposal", request);
		// 第二步：从本地存放好的文件中读取数据
		List<Map<String, String>> excelData = ExcelUtil.read(propertiesService.getCurrentServerForImage(), path,
				propertiesService.getWindowsRootPath());
		// 第三步：将表格中的数据转化为添加到数据库中的数据列表
		List<Disposal> list = disposalService.selectDataFromExcelMapData(new DisposalExcelQueryTemplate(), excelData);
		if (list != null) {
			for (Disposal disposal : list) {
				disposal.setOperatorId(IdTypeHandler.decode(manager.getId()));
				disposal.setOperatorName(manager.getManagerName());
			}
			// 将导入的数据添加到数据库中
			disposalService.saveAll(list);
		}
		return ResponseResult.ok();
	}

	/**
	 * @Description: 查询所有的处置场
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日 下午4:18:56
	 * @return:ResponseResult 返回含有所有处置场的结果集
	 */
	@RequestMapping("queryAllDisposals.action")
	public ResponseResult queryAllDisposal() {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, " deleted = %d ", false);
		return ResponseResult.ok(disposalService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}

	@RequestMapping("queryDisposalTree.action")
	public ResponseResult queryDisposalTree() {
		return ResponseResult.ok(disposalService.queryDisposalTree());
	}
}
