package com.muck.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.muck.admin.utils.WebUtils;
import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.domain.Car;
import com.muck.domain.CarDriver;
import com.muck.domain.CarGroup;
import com.muck.domain.Company;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CarExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.CarQueryForm;
import com.muck.request.CarForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CarGroupService;
import com.muck.service.CarService;
import com.muck.service.CompanyService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;
import com.muck.utils.UploadUtils;

/**
 * @Description: 车辆Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月17日 下午5:40:35
 */
@RestController("AdminCarController")
@RequestMapping("/admin/car")
public class CarController extends BaseController {

	@Autowired
	private CarService carService; // 车辆service

	@Autowired
	private AreaService areaService; // 区域service

	@Autowired
	private CompanyService companyService; // 企业service

	@Autowired
	private ExcelOutputLogService excelOutputLogService;

	@Autowired
	private CarGroupService carGroupService;

	@Autowired
	private PropertiesService propertiesService;
	/**
	 * 添加车辆
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorModel = "车辆", operatorDesc = "添加车辆")
	public ResponseResult save(CarForm carForm, HttpSession session) {

		// 组装我们需要的实体对象的数据(设置基本信息)

		// 封装车辆信息
		Car car = new Car();
		// 车牌号
		car.setCarCode(carForm.getCarCode());
		// 车辆类型
		car.setCarType(carForm.getCarType());
		// 车辆颜色
		car.setCarColor(carForm.getCarColor());
		// 车辆图片路径
		car.setCarImagePath(carForm.getCarImagePath());
		//车辆所属人
		car.setCarOwnerOfVehicle(carForm.getCarOwnerOfVehicle());
		
		//驾驶员
		if(!StringUtils.isBlank(carForm.getCarDriverName())){
			List<CarDriver> carDrivers = new ArrayList<CarDriver>();
			CarDriver carDriver = new CarDriver();
			carDriver.setCarCode(car.getCarCode());
			carDriver.setDriverName(carForm.getCarDriverName());
			carDriver.setDriverPhone(carForm.getCarDriverPhone());
			car.setCarDrivers(carDrivers);
		}
		
		
		// 区域id
		String areaId = carForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			Area area = areaService.queryById(areaId);
			if (area != null) {
				car.setAreaId(area.getId()); // 设置区域
				car.setAreaName(area.getAreaName());
			}
		}
		// 企业id
		String companyId = carForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			Company company = companyService.queryById(carForm.getCompanyId());
			if (company != null) {
				car.setCompanyId(company.getId()); // 设置企业
				car.setCompanyName(company.getCompanyName());
				car.setCompanyContact(company.getCompanyContact());
			}
		}
		// 企业名字
		String companyName = carForm.getCompanyName();
		if(!StringUtils.isBlank(companyName)){
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			car.setCompanyId(company.getId());
			car.setCompanyName(companyName);
		}
		
		// 判断前台是否传入车辆组id,如果是传入则表示前台用户是选择车辆组
		String carGroupId = carForm.getCarGroupId();
		if(!StringUtils.isBlank(carGroupId)) {
			CarGroup carGroup = carGroupService.queryById(carGroupId);
			if (carGroup != null) {
				car.setCarGroupId(carGroup.getId());
				car.setCarGroupName(carGroup.getGroupName());
			}
		}
		
		/**
			判断是否传入车辆组名称,如果传入则表示用户是输入,分情况
			1、用户传入的名称是新的车辆组名称
			2、用户传入的名称是已经存在的车辆组名称
			思路:
				不管是新名称还是旧名称,统一查询数据库
			注意:
				这个业务需要放在service层来做,保证数据库事务
		*/ 
		String carGroupName = carForm.getCarGroupName();
		if (!StringUtils.isBlank(carGroupName)) {
			
			// 如果传入的车辆组存在则设置
			car.setCarGroupName(carGroupName);
		}
		// 车辆gps机号
		car.setCarGpsMachineNumber(carForm.getCarGpsMachineNumber());
		// 车辆型号
		car.setCarVehicleModel(carForm.getCarVehicleModel());
		// 是否锁车,默认不锁车(1:不锁车, 0 : 锁车)
		car.setLock(true); // 默认不锁车
		// 是否运行,默认运行(1:运行,0:不运行)
		car.setRunning(true); // 车辆默认运行
		// 车辆发动机号码
		car.setCarEngineNumber(carForm.getCarEngineNumber());
		// 车辆使用性质
		car.setCarUseNature(carForm.getCarUseNature());
		// 车辆识别代码
		car.setCarWpmi(carForm.getCarWpmi());
		// 强制报废期止
		car.setForceScrap(carForm.getForceScrap());
		// 运输车辆截止日期
		car.setCarClosingDate(carForm.getCarClosingDate());
		// 核定载质量 单位：吨
		car.setVouchPayload(carForm.getVouchPayload());
		// 外廓尺寸
		car.setOutlineSize(carForm.getOutlineSize());
		// 品牌型号
		car.setBrandModel(carForm.getBrandModel());
		// 注册登记日期
		car.setRegistrationDate(carForm.getRegistrationDate());
		// 道路运输证号
		car.setRoadTransportLicenseNumber(carForm.getRoadTransportLicenseNumber());
		// 发证日期
		car.setCertificateDate(carForm.getCertificateDate());
		// 密闭装置
		car.setObturator(carForm.getObturator());
		// 卫星定位系统，行车记录仪
		car.setCargps(carForm.getCargps());
		// 安装顶灯和具有反光功能的放大号牌
		car.setReflectLightNumber(carForm.getReflectLightNumber());
		// 喷印企业的名称，标志，编号
		car.setJetPrintingCompanyName(carForm.getJetPrintingCompanyName());
		// 粘贴反光标贴
		car.setPasteReflectStickers(carForm.getPasteReflectStickers());
		// 安装自喷淋系统
		car.setInstallSpraySystem(carForm.getInstallSpraySystem());
		// 车辆备注说明
		car.setMemo(carForm.getMemo());
		// 保存
		carService.save(car);
		return ResponseResult.ok(car.getId());
	}

	/**
	 * 根据驾驶员身份证查询车辆具体信息
	 */
	@RequestMapping(value = "queryByIdNumber.action", method = RequestMethod.GET)
	public ResponseResult queryByIdNumber(String idNumber) {

		// 根据车辆id查询车辆
		Car car = carService.queryByIdNumber(idNumber);
		if (car == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(car);
	}

	/**
	 * 根据车辆id查询车辆具体信息
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String carId) {

		// 根据车辆id查询车辆
		Car car = carService.queryById(carId);
		if (car == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(car);
	}
	
	
	
	/*@RequestMapping("queryByCode.action")
	public ResponseResult queryByCarCode(String carCode) {

		return ResponseResult.ok(carService.queryByCarCode(carCode));
	}
*/
	/**
	 * 根据车辆id删除车辆具体信息
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@Logger(operatorModel = "车辆", operatorDesc = "根据id删除车辆")
	public ResponseResult deleteById(String carId) {
		// 查询车辆
		Car car = carService.queryById(carId);
		if (car == null) {
			throw new BizException(StatusCode.NOT_FOUND);
		}
		// 根据车辆编号删除车辆详情
		carService.deleteById(carId);
		return ResponseResult.ok();
	}

	/**
	 * 根据车牌号修改车辆信息
	 */
	@RequestMapping(value = "editByCarCode.action", method = RequestMethod.POST)
	@Logger(operatorModel = "车辆", operatorDesc = "根据车牌号修改车辆信息")
	public ResponseResult editByCarCode(CarForm carForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步、根据车辆carId去查询车辆
		// 组装我们需要的实体对象的数据(设置基本信息)
		// 封装车辆信息
		Car car = new Car();
		// 车牌号
		car.setCarCode(carForm.getCarCode());
		// 车辆类型
		car.setCarType(carForm.getCarType());
		// 车辆颜色
		car.setCarColor(carForm.getCarColor());
		// 车辆图片路径
		car.setCarImagePath(carForm.getCarImagePath());
		//车辆所属人
		car.setCarOwnerOfVehicle(carForm.getCarOwnerOfVehicle());
		// 区域id
		String areaId = carForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			Area area = areaService.queryById(areaId);
			if (area != null) {
				car.setAreaId(area.getId()); // 设置区域
				car.setAreaName(area.getAreaName());
			}
		}
		// 企业id
		String companyId = carForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			Company company = companyService.queryById(carForm.getCompanyId());
			if (company != null) {
				car.setCompanyId(company.getId()); // 设置企业
				car.setCompanyName(company.getCompanyName());
				car.setCompanyContact(company.getCompanyContact());
			}
		}
		// 企业名字
		String companyName = carForm.getCompanyName();
		if(!StringUtils.isBlank(companyName)){
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			car.setCompanyId(company.getId());
			car.setCompanyName(companyName);
		}
		// 车辆组id
		String carGroupId = carForm.getCarGroupId();
		if (!StringUtils.isBlank(carGroupId)) {
			CarGroup carGroup = carGroupService.queryById(carGroupId);
			if (carGroup != null) {
				car.setCarGroupId(carGroup.getId());
				car.setCarGroupName(carGroup.getGroupName());
			}
		} else {
			String carGroupName = carForm.getCarGroupName();
			if (!StringUtils.isBlank(carGroupName)) {
				CarGroup carGroup = new CarGroup();
				carGroup.setCompanyId(companyId);
				carGroup.setGroupName(carGroupName);
				carGroup.setOperatorId(IdTypeHandler.decode(manager.getId()));
				carGroup.setOperatorName(manager.getManagerName());
				carGroup.setOperatorPhone(manager.getManagerPhone());
				carGroupService.save(carGroup);
				car.setCarGroupId(IdTypeHandler.encode(carGroup.getIdRaw()));
				car.setCarGroupName(carGroupName);
			}
		}
		// 车辆gps机号
		car.setCarGpsMachineNumber(carForm.getCarGpsMachineNumber());
		// 车辆型号
		car.setCarVehicleModel(carForm.getCarVehicleModel());
		// 是否锁车,默认不锁车(1:不锁车, 0 : 锁车)
		car.setLock(true); // 默认不锁车
		// 是否运行,默认运行(1:运行,0:不运行)
		car.setRunning(true); // 车辆默认运行
		// 车辆发动机号码
		car.setCarEngineNumber(carForm.getCarEngineNumber());
		// 车辆使用性质
		car.setCarUseNature(carForm.getCarUseNature());
		// 车辆识别代码
		car.setCarWpmi(carForm.getCarWpmi());
		// 强制报废期止
		car.setForceScrap(carForm.getForceScrap());
		// 运输车辆截止日期
		car.setCarClosingDate(carForm.getCarClosingDate());
		// 核定载质量 单位：吨
		car.setVouchPayload(carForm.getVouchPayload());
		// 外廓尺寸
		car.setOutlineSize(carForm.getOutlineSize());
		// 品牌型号
		car.setBrandModel(carForm.getBrandModel());
		// 注册登记日期
		car.setRegistrationDate(carForm.getRegistrationDate());
		// 道路运输证号
		car.setRoadTransportLicenseNumber(carForm.getRoadTransportLicenseNumber());
		// 发证日期
		car.setCertificateDate(carForm.getCertificateDate());
		// 密闭装置
		car.setObturator(carForm.getObturator());
		// 卫星定位系统，行车记录仪
		car.setCargps(carForm.getCargps());
		// 安装顶灯和具有反光功能的放大号牌
		car.setReflectLightNumber(carForm.getReflectLightNumber());
		// 喷印企业的名称，标志，编号
		car.setJetPrintingCompanyName(carForm.getJetPrintingCompanyName());
		// 粘贴反光标贴
		car.setPasteReflectStickers(carForm.getPasteReflectStickers());
		// 安装自喷淋系统
		car.setInstallSpraySystem(carForm.getInstallSpraySystem());
		// 车辆备注说明
		car.setMemo(carForm.getMemo());
		// 更新
		carService.editByCarCode(car);
		return ResponseResult.ok();
	}

	/**
	 * 根据车辆id修改车辆信息
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	@Logger(operatorModel = "车辆", operatorDesc = "根据车辆id修改车辆信息")
	public ResponseResult editById(CarForm carForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步、根据车辆carId去查询车辆
		// 组装我们需要的实体对象的数据(设置基本信息)
		// 封装车辆信息
		Car car = new Car();
		// 车辆id
		car.setId(carForm.getCarId());
		// 车牌号
		car.setCarCode(carForm.getCarCode());
		// 车辆类型
		car.setCarType(carForm.getCarType());
		// 车辆颜色
		car.setCarColor(carForm.getCarColor());
		// 车辆图片路径
		car.setCarImagePath(carForm.getCarImagePath());
		//车辆所属人
		car.setCarOwnerOfVehicle(carForm.getCarOwnerOfVehicle());
		// 区域id
		String areaId = carForm.getAreaId();
		if (StringUtils.isNotBlank(areaId)) {
			Area area = areaService.queryById(areaId);
			if (area != null) {
				car.setAreaId(area.getId()); // 设置区域
				car.setAreaName(area.getAreaName());
			}
		}
		// 企业id
		String companyId = carForm.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			Company company = companyService.queryById(carForm.getCompanyId());
			if (company != null) {
				car.setCompanyId(company.getId()); // 设置企业
				car.setCompanyName(company.getCompanyName());
				car.setCompanyContact(company.getCompanyContact());
			}
		}
		// 企业名字
		String companyName = carForm.getCompanyName();
		if(!StringUtils.isBlank(companyName)){
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			car.setCompanyId(company.getId());
			car.setCompanyName(companyName);
		}
		// 车辆组id
		String carGroupId = carForm.getCarGroupId();
		if (!StringUtils.isBlank(carGroupId)) {
			CarGroup carGroup = carGroupService.queryById(carGroupId);
			if (carGroup != null) {
				car.setCarGroupId(carGroup.getId());
				car.setCarGroupName(carGroup.getGroupName());
			}
		} else {
			String carGroupName = carForm.getCarGroupName();
			if (!StringUtils.isBlank(carGroupName)) {
				CarGroup carGroup = new CarGroup();
				carGroup.setCompanyId(companyId);
				carGroup.setGroupName(carGroupName);
				carGroup.setOperatorId(IdTypeHandler.decode(manager.getId()));
				carGroup.setOperatorName(manager.getManagerName());
				carGroup.setOperatorPhone(manager.getManagerPhone());
				carGroupService.save(carGroup);
				car.setCarGroupId(IdTypeHandler.encode(carGroup.getIdRaw()));
				car.setCarGroupName(carGroupName);
			}
		}
		// 车辆gps机号
		car.setCarGpsMachineNumber(carForm.getCarGpsMachineNumber());
		// 车辆型号
		car.setCarVehicleModel(carForm.getCarVehicleModel());
		// 是否锁车,默认不锁车(1:不锁车, 0 : 锁车)
		car.setLock(true); // 默认不锁车
		// 是否运行,默认运行(1:运行,0:不运行)
		car.setRunning(true); // 车辆默认运行
		// 车辆发动机号码
		car.setCarEngineNumber(carForm.getCarEngineNumber());
		// 车辆使用性质
		car.setCarUseNature(carForm.getCarUseNature());
		// 车辆识别代码
		car.setCarWpmi(carForm.getCarWpmi());
		// 强制报废期止
		car.setForceScrap(carForm.getForceScrap());
		// 运输车辆截止日期
		car.setCarClosingDate(carForm.getCarClosingDate());
		// 核定载质量 单位：吨
		car.setVouchPayload(carForm.getVouchPayload());
		// 外廓尺寸
		car.setOutlineSize(carForm.getOutlineSize());
		// 品牌型号
		car.setBrandModel(carForm.getBrandModel());
		// 注册登记日期
		car.setRegistrationDate(carForm.getRegistrationDate());
		// 道路运输证号
		car.setRoadTransportLicenseNumber(carForm.getRoadTransportLicenseNumber());
		// 发证日期
		car.setCertificateDate(carForm.getCertificateDate());
		// 密闭装置
		car.setObturator(carForm.getObturator());
		// 卫星定位系统，行车记录仪
		car.setCargps(carForm.getCargps());
		// 安装顶灯和具有反光功能的放大号牌
		car.setReflectLightNumber(carForm.getReflectLightNumber());
		// 喷印企业的名称，标志，编号
		car.setJetPrintingCompanyName(carForm.getJetPrintingCompanyName());
		// 粘贴反光标贴
		car.setPasteReflectStickers(carForm.getPasteReflectStickers());
		// 安装自喷淋系统
		car.setInstallSpraySystem(carForm.getInstallSpraySystem());
		// 车辆备注说明
		car.setMemo(carForm.getMemo());
		// 更新
		carService.editById(car);
		return ResponseResult.ok();
	}
	
	
	
	// --------------------------------------------

	/**
	 * @Description: 根据不同条件分页查询所有的车辆
	 * @author: 展昭
	 * @date: 2018年4月23日 上午10:47:55
	 */
	@RequestMapping(value = "queryCars.action", method = RequestMethod.POST)
	public ResponseResult queryCars(CarQueryForm carQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Car> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 区域id
		String areaId = carQueryForm.getAreaId();
		// 企业id
		String companyId = carQueryForm.getCompanyId();
		// 车牌号
		String carCode = carQueryForm.getCarCode();
		// 根据车辆组查询
		String carGroupId = carQueryForm.getCarGroupId();
		queryHelper.addCondition(areaId, "area_id=%d", true)
				.addCondition("%"+carCode+"%", "car_code like '%s'", false)
				.addCondition(companyId, "company_id=%d", true)
				.addCondition(carGroupId, "car_group_id = %d", true)
				.addCondition(1, "deleted = %d", false);

		pageView = carService.queryPageData(carQueryForm.getPageNum(), carQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}

	/**
	 * @Description: 导出Excel表格数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月25日 下午5:15:04
	 * @param: carQueryForm
	 *             请求的条件参数，用来接收前台传过来的查询数据的条件参数
	 * @param: excelTemplate
	 *             请求的模版参数，用来接收前台传过来的生成Excel表格的字段
	 * @param: session
	 *             传入一个HttpSession会话，用来获取当前会话里的登陆用户对象
	 * @return: ResponseResult 返回下载生成的Excel表格的路径和操作的信息
	 */
	@RequestMapping("exportExcel.action")
	@Logger(operatorModel = "车辆管理", operatorDesc = "导出车辆数据")
	public ResponseResult exportExcel(CarQueryForm carQueryForm, CarExcelQueryTemplate excelTemplate,
			HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 创建一个QueryHelper帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据车牌号查询
		queryHelper.addCondition(carQueryForm.getCarCode(), "car.car_code = '%s'", false)
				// 根据车主姓名查询
				.addCondition(carQueryForm.getCarDriverName(), "car.car_driver_name = '%s'", false)
				// 根据车主联系方式查询
				.addCondition(carQueryForm.getCarDriverPhone(), "car.car_driver_phone = '%s'", false)
				.addCondition(1, "car.deleted= %d", false);
		// 根据车辆所属区域查询
		String parentCode = carQueryForm.getAreaCode();
		if (!StringUtils.isBlank(parentCode)) {
			// 获取所有的子级区域编号
			String areaCodes = areaService.querySubAreaCodesByParentCode(parentCode);
			queryHelper.addCondition(areaCodes, "car.area_code in (%s)", false);
		}
		// 根据企业id查询车辆
		queryHelper.addCondition(carQueryForm.getCompanyId(), "car.company_id = %d", true)
				// 根据企业名称查询车辆
				.addCondition(carQueryForm.getCompanyName(), "car.company_name = '%s'", false)
				// 根据车辆所属企业负责人名称查询车辆
				.addCondition(carQueryForm.getCompanyPrincipalName(), "car.company_principal_name = '%s'", false)
				// 根据车辆所属企业负责人联系方式查询
				.addCondition(carQueryForm.getCompanyPrincipalPhone(), "car.company_principal_phone = '%s'", false)
				// 根据车辆组名称查询车辆
				.addCondition(carQueryForm.getCarGroupName(), "car.car_group_name = '%s'", false)
				// 根据车辆GPS机号查询
				.addCondition(carQueryForm.getCarGpsMachineNumber(), "car.car_gps_machine_number = '%s'", false)
				// 根据车辆型号查询
				.addCondition(carQueryForm.getCarVehicleModel(), "car.car_vehicle_model = '%s'", false)
				// 根据车辆锁定状态来查询
				.addCondition(carQueryForm.getIsLock(), "car.is_lock = %d", false)
				// 根据车辆运行状态查询
				.addCondition(carQueryForm.getIsRunning(), "car.is_running = %d", false);
		List<Car> list = carService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		// 生成Excel表格数据
		List<Map<String, String>> data = carService.createExcelData(excelTemplate, list);
		// 生成水印
		String[] water = new String[] { 
		};
		// 生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"car", data,propertiesService.getWindowsCreateWaterRootPath(),water);
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
		//DownloadFileByIO.downloadFileFromServer(outputExcelPath, response,"导出车辆数据");
		return ResponseResult.ok(outputExcelPath);
	}

	/**
	 * @Description: 导入Excel表格数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日 上午10:40:49
	 * @param: request
	 *             传入一个HttpServletRequest请求
	 * @return:ResponseResult 返回操作的信息
	 */
	@RequestMapping("importExcel.action")
	@Logger(operatorModel = "车辆管理", operatorDesc = "导入车辆数据")
	public ResponseResult importExcel(HttpServletRequest request) {
		final Manager manager = (Manager) request.getSession().getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步：将导入的Excel表格存放到指定的本地目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"car", request);
		// 第二步：从本地存放好的文件中读取数据
		List<Map<String, String>> excelData = ExcelUtil.read(propertiesService.getCurrentServerForImage(),path,propertiesService.getWindowsRootPath());
		// 第三步：将表格中的数据转化为添加到数据库中的数据列表
		List<Car> list = carService.selectDataFromExcelMapData(new CarExcelQueryTemplate(), excelData);
		if (list != null) {
			String companyId = request.getParameter("companyId");
			for (Car car : list) {
				if (!StringUtils.isBlank(companyId)) {
					car.setCompanyId(companyId);
				}
				car.setOperatorId(IdTypeHandler.decode(manager.getId()));
				car.setOperatorName(manager.getManagerName());
			}
			// 将导入的数据添加到数据库中
			carService.saveAll(list);
		}
		return ResponseResult.ok();
		/*
		 * FutureTask<ResponseResult> future = new
		 * FutureTask<ResponseResult>(new Callable<ResponseResult>() {
		 * 
		 * @Override public ResponseResult call() throws Exception {
		 * 
		 * } }); ExecutorService executorService
		 * =Executors.newSingleThreadScheduledExecutor();
		 * executorService.submit(future);
		 */
	}

	/**
	 * @Description: 导入企业车辆情况汇总
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日 上午10:40:49
	 * @param: request
	 *             传入一个HttpServletRequest请求
	 * @return:ResponseResult 返回操作的信息
	 */
	@RequestMapping("importExcelSummary.action")
	@Logger(operatorModel = "车辆管理", operatorDesc = "导入企业车辆情况汇总")
	public ResponseResult importExcelSummary(HttpServletRequest request) {
		Manager manager = WebUtils.getManagerFromSession(request);
		// 第一步：将导入的Excel表格存放到指定的本地目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"car", request);
		// 第二步：从本地存放好的文件中读取数
		List<Map<String, String>> excelData = carService.readExcelData(path);
		// 第三步：将表格中的数据转化为添加到数据库中的数据列表
		List<Car> list = carService.selectDataFromExcelMapData(new CarExcelQueryTemplate(), excelData);
		if (list != null) {
			String companyId = request.getParameter("companyId");
			for (Car car : list) {
				if (!StringUtils.isBlank(companyId)) {
					car.setCompanyId(companyId);
				}
				car.setOperatorId(IdTypeHandler.decode(manager.getId()));
				car.setOperatorName(manager.getManagerName());
			}
			// 将导入的数据添加到数据库中
			carService.saveAll(list);
		}
		return ResponseResult.ok();
		/*
		 * FutureTask<ResponseResult> future = new
		 * FutureTask<ResponseResult>(new Callable<ResponseResult>() {
		 * 
		 * @Override public ResponseResult call() throws Exception {
		 * 
		 * } }); ExecutorService executorService
		 * =Executors.newSingleThreadScheduledExecutor();
		 * executorService.submit(future);
		 */
	}
	
	/**
	 * @Description:导入车辆详情
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月21日  下午8:36:52
	 * @Param:request 传入一个含有文件的请求
	 * @Return:ResponseResult 返回导入的操作信息
	 */
	@RequestMapping("importCarInfo.action")
	public ResponseResult importCarInfo(HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：将表格文件存放到本机制定的文件夹目录下，并返回存放到本地的目录
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"carInfo", request);
		//第二步：读取Excel表格中的数据
		Car car = carService.readExcelData(new CarExcelQueryTemplate(), path);
		return ResponseResult.ok(car);
		/*Car cars = carService.queryByCarCode(car.getCarCode());
		if(cars == null){
			carService.save(car);
		}else {
			if(cars.equals(car)){
				throw new BizException(StatusCode.CAR_ALREADY_EXISTS);
			}else {
				car.setId(cars.getId());
				carService.editById(car);
			}
		}
		return ResponseResult.ok();*/
	}
	
	/**
	 * @Description: 导出车辆详情
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月3日  下午11:38:50
	 * @param: carCode 传入一个车牌号
	 * @param: session 传入一个HttpSession
	 */
	@RequestMapping("exportCarInfo.action")
	public ResponseResult exportCarInfo(String carCode,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：根据车牌号获取车辆详情
		Car car = null ;//carService.queryByCarCode(carCode);
		if(car == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		//第二步：生成Excel表格数据
		Map<String,Object> excelData = carService.createExcelData(new CarExcelQueryTemplate(), car);
		//第三步：生成水印，并将表格数据填充到excel表格中，文件名(建筑垃圾运输车辆情况表.xlsx) 
		String[] water = new String[]{
		};
		String downloadPath = carService.createExcelPOI("企业建筑垃圾运输车辆情况表.xlsx", excelData,water);
		if(StringUtils.isBlank(downloadPath)){
			throw new BizException(StatusCode.EXPORT_FAILE);
		}
		//DownloadFileByIO.downloadFileFromServer(downloadPath, response, "建筑垃圾运输车辆情况表");
		return ResponseResult.ok(downloadPath);
	}
	/**
	 * @Description: 查询所有的车辆
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日 下午1:06:00
	 * @return:ResponseResult 返回所有的车辆
	 */
	@RequestMapping("queryAllCars.action")
	public ResponseResult queryCars() {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, " car.deleted = %d", false);
		return ResponseResult.ok(carService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}
}
