package com.muck.admin.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.domain.Company;
import com.muck.domain.Device;
import com.muck.domain.DeviceType;
import com.muck.domain.Disposal;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.domain.Site;
import com.muck.excelquerytemplate.DeviceExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.DeviceQueryForm;
import com.muck.request.DeviceForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CarService;
import com.muck.service.CompanyService;
import com.muck.service.DeviceService;
import com.muck.service.DisposalService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.ManagerService;
import com.muck.service.PropertiesService;
import com.muck.service.SiteService;
import com.muck.utils.ExcelUtil;

/***
 * @Description: 设备Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 下午4:56:18
 */
@RestController("AdminDeviceController")
@RequestMapping("/admin/device")
public class DeviceController extends BaseController {

	@Autowired
	private DeviceService deviceService; // 设备service

	@Autowired
	private AreaService areaService; // 区域service

	@Autowired
	private CompanyService companyService; // 企业service

	@Autowired
	private ManagerService managerService; // 系统用户service

	@Autowired
	private CarService carService; // 车辆service

	@Autowired
	private DisposalService disposalService; // 处置场service

	@Autowired
	private PropertiesService propertiesService;//

	@Autowired
	private ExcelOutputLogService excelOutputLogService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Description: 查询设备列表
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 上午10:38:34
	 * @param: deviceForm
	 *             传入查询的参数
	 * @return: ResponseResult 返回结果集
	 */
	/*
	 * @RequestMapping("queryDevices.action") public ResponseResult
	 * queryDevices(DeviceForm deviceForm){ // 0、初始化:
	 * 创建PageView对象,这个对象就是给前端用户的所有数据 PageView<Device> pageView = null;
	 * 
	 * // 1、组装orderby LinkedHashMap<String, String> orderby = new
	 * LinkedHashMap<String,String>(); orderby.put("created_time", "desc");
	 * 
	 * // 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据
	 * if("true".equals(deviceForm.getQuery())){ //
	 * 如果是要分页查询,那么就创建一个QueryHelper的查询帮助对象 QueryHelper queryHelper = new
	 * QueryHelper(); String areaId = deviceForm.getAreaId(); // 拼接查询条件
	 * queryHelper.addCondition(areaService.querySubAreaIdsByParentId(areaId),
	 * "device_area_id in (%s)" , false); pageView =
	 * deviceService.queryPageData(deviceForm.getPageNum(),deviceForm.
	 * getPageSize(),queryHelper.getWhereSQL(),queryHelper.getWhereParams(),
	 * orderby); }else{ pageView = deviceService.queryPageData(); } return
	 * ResponseResult.ok(pageView); }
	 */
	/***
	 * @Description: 添加设备
	 * @author: 展昭
	 * @date: 2018年4月19日 下午4:59:19
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorModel = "设备", operatorDesc = "添加设备")
	public ResponseResult save(DeviceForm deviceForm) {

		// 第一步、设置基本信息
		Device device = new Device();
		device.setDeviceCode(deviceForm.getDeviceCode()); // 设备号
		device.setDeviceFactoryName(deviceForm.getDeviceFactoryName()); // 设备厂家
		device.setDeviceInstalledTime(deviceForm.getDeviceInstalledTime()); // 设备安装日期
		device.setDeviceInstalledUser(deviceForm.getDeviceInstalledUser()); // 设备安装人
		device.setDeviceInstalledUserPhone(deviceForm.getDeviceInstalledUserPhone()); // 设备安装人联系方式
		device.setDeviceLatitude(deviceForm.getDeviceLatitude()); // 设备纬度
		device.setDeviceLongitude(deviceForm.getDeviceLongitude()); // 设备经度
		device.setDeviceMadeTime(deviceForm.getDeviceMadeTime()); // 设备出厂日期
		device.setDeviceAddress(deviceForm.getDeviceAddress()); // 设备地址
		device.setIsRunning(true); // 设备的运行状态
		device.setDeviceAddress(deviceForm.getDeviceAddress()); // 设备地址
		device.setDeviceName(deviceForm.getDeviceName()); // 设备名称
		device.setDeviceType(deviceForm.getDeviceType()); // 设备所属类型
		device.setDeviceIp(deviceForm.getDeviceIp()); // 设备ip
		device.setDeviceRegisterId(deviceForm.getDeviceRegisterId()); // 注册id
		device.setDeviceLoginName(deviceForm.getDeviceLoginName()); // 设备登录名
		device.setDeviceLoginPassword(deviceForm.getDeviceLoginPassword()); // 设备登录密码
		device.setDevicePort(deviceForm.getDevicePort()); // 设备端口号
		device.setDeviceChannelNums(deviceForm.getDeviceChannelNums()); // 设备下面的通道数
		device.setDeviceServerType(deviceForm.getDeviceServerType()); // 设备的视频服务器(1:中心服务器)
		device.setMemo(deviceForm.getMemo()); // 设备备注信息
		device.setDeviceRealType(deviceForm.getDeviceRealType()); // 设备的物理类型(1:NVR)

		// 第二步、设置设备的关联信息
		String areaId = deviceForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			// 设置设备所属的区域
			Area area = areaService.queryById(areaId);
			device.setArea(area);
		}

		String companyId = deviceForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			// 设置设备所属企业
			Company company = companyService.queryById(companyId);
			device.setCompany(company);
		}

		// 企业名字
		String companyName = deviceForm.getCompanyName();
		if (!StringUtils.isBlank(companyName)) {
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			device.setCompany(company);
		}

		String managerId = deviceForm.getManagerId();
		if (StringUtils.isNotBlank(managerId)) {
			// 设置设备所属于的系统用户
			Manager manager = managerService.queryById(managerId);
			device.setManager(manager);
		}

		String disposalId = deviceForm.getDisposalId();
		if (StringUtils.isNotBlank(disposalId)) {
			// 处置场
			device.setDisposal(disposalService.queryByIDSimple(disposalId));
		}

		String carId = deviceForm.getCarId();
		if (StringUtils.isNotBlank(carId)) {
			// 车辆
			device.setCar(carService.queryByIDSimple(carId));
		}

		// 第三步、添加保存
		deviceService.save(device);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据设备id删除一个设备
	 * @author: 展昭
	 * @date: 2018年4月20日 上午9:53:02
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@Logger(operatorModel = "设备", operatorDesc = "根据设备id删除一个设备")
	public ResponseResult deleteById(String deviceId) {

		deviceService.deleteById(deviceId);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 修改设备
	 * @author: 展昭
	 * @date: 2018年4月20日 上午9:53:02
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	@Logger(operatorModel = "设备", operatorDesc = "根据设备id修改一个设备")
	public ResponseResult editById(DeviceForm deviceForm) {

		// 第一步、设置基本信息
		Device device = new Device();

		device.setId(deviceForm.getDeviceId()); // 主键id
		device.setDeviceCode(deviceForm.getDeviceCode()); // 设备号
		device.setDeviceFactoryName(deviceForm.getDeviceFactoryName()); // 设备厂家
		device.setDeviceInstalledTime(deviceForm.getDeviceInstalledTime()); // 设备安装日期
		device.setDeviceLatitude(deviceForm.getDeviceLatitude()); // 设备纬度
		device.setDeviceLongitude(deviceForm.getDeviceLongitude()); // 设备经度
		device.setDeviceMadeTime(deviceForm.getDeviceMadeTime()); // 设备出厂日期
		device.setDeviceAddress(deviceForm.getDeviceAddress()); // 设备地址
		device.setIsRunning(true);
		device.setDeviceInstalledUser(deviceForm.getDeviceInstalledUser()); // 设备安装人
		device.setDeviceInstalledUserPhone(deviceForm.getDeviceInstalledUserPhone()); // 设备安装人联系方式
		device.setDeviceRegisterCode(deviceForm.getDeviceRegisterCode());// 设备的注册码

		// 设备的运行状态
		device.setDeviceAddress(deviceForm.getDeviceAddress()); // 设备地址
		device.setDeviceName(deviceForm.getDeviceName()); // 设备名称
		device.setDeviceType(deviceForm.getDeviceType()); // 设备所属类型
		// 获取设备类型
		Integer type = deviceForm.getDeviceType();
		List<String> channels = deviceService.queryChannelByDeviceCode(deviceForm.getDeviceCode());
		if (channels != null && channels.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String channelCode : channels) {
				sb.append("'").append(channelCode).append("'").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			String str = sb.toString();

			// 工地
			if (type == DeviceType.SITE.getType()) {
				// 维护工地的注册码
				// 批量更新
				siteService.updateBatch(deviceForm.getDeviceRegisterCode(), "(" + str + ")");

			} else if (type == DeviceType.DISPOSAL.getType()) {
				// w维护处置场的注册码
				// 批量更新
				disposalService.updateBatch(deviceForm.getDeviceRegisterCode(), "(" + str + ")");
			}
		}
		device.setDeviceIp(deviceForm.getDeviceIp()); // 设备ip
		device.setDeviceRegisterId(deviceForm.getDeviceRegisterId()); // 注册id
		device.setDeviceLoginName(deviceForm.getDeviceLoginName()); // 设备登录名
		device.setDeviceLoginPassword(deviceForm.getDeviceLoginPassword()); // 设备登录密码
		device.setDevicePort(deviceForm.getDevicePort()); // 设备端口号
		device.setDeviceChannelNums(deviceForm.getDeviceChannelNums()); // 设备下面的通道数
		device.setDeviceServerType(deviceForm.getDeviceServerType()); // 设备的视频服务器(1:中心服务器)
		device.setMemo(deviceForm.getMemo()); // 设备备注信息
		device.setDeviceRealType(deviceForm.getDeviceRealType()); // 设备的物理类型(1:NVR)

		// 第二步、设置设备的关联信息
		String areaId = deviceForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			// 设置设备所属的区域
			Area area = areaService.queryById(areaId);
			device.setArea(area);
		}

		String companyId = deviceForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			// 设置设备所属企业
			Company company = companyService.queryById(companyId);
			device.setCompany(company);
		}

		String managerId = deviceForm.getManagerId();
		if (StringUtils.isNotBlank(managerId)) {
			// 设置设备所属于的系统用户
			Manager manager = managerService.queryById(managerId);
			device.setManager(manager);
		}

		String disposalId = deviceForm.getDisposalId();
		if (StringUtils.isNotBlank(disposalId)) {
			// 处置场
			device.setDisposal(disposalService.queryByIDSimple(disposalId));
		}

		String carId = deviceForm.getCarId();
		if (StringUtils.isNotBlank(carId)) {
			// 车辆
			device.setCar(carService.queryByIDSimple(carId));
		}

		// 第三步、添加保存
		deviceService.editById(device);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id查询设备信息
	 * @param: 设备id
	 * @author: 展昭
	 * @date: 2018年4月20日 上午10:40:15
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String deviceId) {

		Device device = deviceService.queryById(deviceId);
		if (device == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(device);
	}

	/**
	 * 查询设备列表 根据条件查询带分页数据
	 */

	@RequestMapping(value = "queryDevices.action", method = RequestMethod.POST)
	public ResponseResult queryDevices(DeviceQueryForm deviceQueryForm) {

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");

		// 2、创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		// 区域id
		String areaId = deviceQueryForm.getAreaId();
		queryHelper.addCondition(areaId, "device_area_id = %d", true);

		// 工地id
		String siteId = deviceQueryForm.getSiteId();
		queryHelper.addCondition(siteId, "device_site_id = %d", true);

		// 设备名称
		String deviceName = deviceQueryForm.getDeviceName();
		if (StringUtils.isNotBlank(deviceName)) {
			queryHelper.addCondition("%" + deviceName + "%", "device_name like '%s'", false);
		}

		// 3、查询
		PageView<Device> pageView = deviceService.queryPageData(deviceQueryForm.getPageNum(),
				deviceQueryForm.getPageSize(), queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);

		return ResponseResult.ok(pageView);
	}

	/**
	 * 导出单个设备或导出设备列表
	 */
	@RequestMapping(value = "exportDeviceExcelInfo.action", method = RequestMethod.POST)
	public ResponseResult exportDeviceExcelInfo(DeviceQueryForm deviceQueryForm, HttpSession session) {
		// 0.验证登录
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		QueryHelper queryHelper = null;
		if (StringUtils.isNotBlank(deviceQueryForm.getDeviceId())) {
			// 如果传入设备id说明是单个导出
			queryHelper = new QueryHelper();
			String deviceId = deviceQueryForm.getDeviceId();
			queryHelper.addCondition(deviceId, "device.id = %d", true);
		} else {

			// 2、创建一个QueryHelper的查询帮助对象
			queryHelper = new QueryHelper();

			// 区域id
			String areaId = deviceQueryForm.getAreaId();
			// 工地id
			String siteId = deviceQueryForm.getSiteId();
			if (StringUtils.isNotBlank(areaId)) {
				queryHelper.addCondition(areaId, "device.device_area_id = %d", true);
			}
			if (StringUtils.isNotBlank(siteId)) {
				queryHelper.addCondition(siteId, "device.device_site_id = %d", true);
			}
		}
		queryHelper.addCondition(1, "device.deleted = %d", false);

		// 查询设备
		List<Device> devices = deviceService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());

		if (devices != null && devices.size() > 0) {
			// 生成表格数据
			List<Map<String, String>> excelData = deviceService.createExcelData(new DeviceExcelQueryTemplate(),
					devices);
			if (!excelData.isEmpty()) {
				// 生成导出路径
				// 生成水印
				String[] water = new String[] {};
				// 生成导出路径
				String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),
						propertiesService.getWindowsRootPath(), "device", excelData,
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
		}

		throw new BizException(StatusCode.EXPORT_FAILE);
	}

}
