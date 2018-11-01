package com.muck.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.admin.utils.WebUtils;
import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.domain.Car;
import com.muck.domain.CarAndOrCarDriver;
import com.muck.domain.CarDriver;
import com.muck.domain.Company;
import com.muck.domain.CompanyManager;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CarDriverExcelQueryTemplate;
import com.muck.excelquerytemplate.CarExcelQueryTemplate;
import com.muck.excelquerytemplate.CompanyExcelQueryTemplate;
import com.muck.excelquerytemplate.CompanyManagerExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.CompanyQueryForm;
import com.muck.request.CompanyForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CarDriverService;
import com.muck.service.CarService;
import com.muck.service.CompanyManagerService;
import com.muck.service.CompanyService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ArrayUtils;
import com.muck.utils.CompressedFilesUtils;
import com.muck.utils.ExcelUtil;
import com.muck.utils.FileUtils;
import com.muck.utils.UploadUtils;

/**
 * @Description: 企业Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月19日 上午11:38:13
 */
@RestController("AdminCompanyController")
@RequestMapping("/admin/company/")
public class CompanyController extends BaseController {
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyManagerService companyManagerService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	@Autowired
	private CarService carService;
	@Autowired
	private CarDriverService carDriverService;
	@Autowired
	private PropertiesService propertiesService;
	/**
	 * @Description: 添加企业
	 * @author: 展昭
	 * @date: 2018年4月19日 上午11:40:13
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	@Logger(operatorModel = "企业", operatorDesc = "添加企业")
	public ResponseResult save(CompanyForm companyForm) {
		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Company company = new Company();
		// 企业名称
		company.setCompanyName(companyForm.getCompanyName());
		// 企业注册地址
		company.setCompanyRegisteredAddress(companyForm.getCompanyRegisteredAddress());
		// 企业面积
		String companyAcreage = companyForm.getCompanyAcreage();
		if(StringUtils.isNotBlank(companyAcreage)){
			if(companyAcreage.contains("㎡")){
				company.setCompanyAcreage(companyAcreage);
			}else{
				company.setCompanyAcreage(companyAcreage + "㎡");
			}
		}
		
		// 企业自有或租赁
		company.setCompanyOwnLease(companyForm.getCompanyOwnLease());
		// 企业停车场地址
		company.setCompanyCarParkAddress(companyForm.getCompanyCarParkAddress());
		// 企业停车场面积
		String companyCarParkAcreage = companyForm.getCompanyCarParkAcreage();
		if(StringUtils.isNotBlank(companyCarParkAcreage)){
			if(companyCarParkAcreage.contains("㎡")){
				company.setCompanyCarParkAcreage(companyCarParkAcreage);
			}else{
				company.setCompanyCarParkAcreage(companyCarParkAcreage + "㎡");
			}
		}
		// 企业停车场自有或租赁
		company.setCompanyCarParkOwnLease(companyForm.getCompanyCarParkOwnLease());
		// 营业执照注册号
		company.setCompanyBusinessLicense(companyForm.getCompanyBusinessLicense());
		company.setCompanyBusinessLicenseCloseDate(companyForm.getCompanyBusinessLicenseCloseDate());
		// 道路普通货物运输经营许可证号
		company.setCompanyRoadLicense(companyForm.getCompanyRoadLicense());
		company.setCompanyRoadLicenseCloseDate(companyForm.getCompanyRoadLicenseCloseDate());
		// 企业类型
		company.setCompanyType(companyForm.getCompanyType());
		// 企业类别
		company.setCompanyCategory(companyForm.getCompanyCategory());
		// 企业创建时间
		company.setCompanyCreationTime(companyForm.getCompanyCreationTime());
		// 企业联系方式
		company.setCompanyContact(companyForm.getCompanyContact());
		// 企业传真
		company.setCompanyFacsimile(companyForm.getCompanyFacsimile());
		// 企业url
		company.setCompanyUrl(companyForm.getCompanyUrl());
		// 企业电子信箱
		company.setCompanyEmail(companyForm.getCompanyEmail());
		// 法定代表人
		company.setCompanyLegalRepresentative(companyForm.getCompanyLegalRepresentative());
		// 法定代表人联系方式
		company.setCompanyLegalRepresentativePhone(companyForm.getCompanyLegalRepresentativePhone());
		// 企业经理
		company.setCompanyDirector(companyForm.getCompanyDirector());
		// 企业经理联系方式
		company.setCompanyDirectorPhone(companyForm.getCompanyDirectorPhone());
		// 车队负责人
		company.setCompanyMotorcadePrincipal(companyForm.getCompanyMotorcadePrincipal());
		// 车队负责人联系方式
		company.setCompanyMotorcadePrincipalPhone(companyForm.getCompanyMotorcadePrincipalPhone());
		// 企业员工总数
		company.setCompanyEmployeeNumber(companyForm.getCompanyEmployeeNumber());
		// 企业管理员总人数
		company.setCompanyAdministratorNumber(companyForm.getCompanyAdministratorNumber());
		// 企业驾驶员总人数
		company.setCompanyDriverNumber(companyForm.getCompanyDriverNumber());
		// 企业普通员工人数
		company.setCompanyGeneralNumber(companyForm.getCompanyGeneralNumber());
		// 企业负责人
		company.setCompanyPrincipalName(companyForm.getCompanyPrincipalName());
		// 企业负责人联系方式
		company.setCompanyPrincipalPhone(companyForm.getCompanyPrincipalPhone());
		// 企业入驻时间
		company.setCompanyArrivalTime(companyForm.getCompanyArrivalTime());
		String areaId = companyForm.getAreaId();
		if (!StringUtils.isBlank(areaId)) {
			Area area = areaService.queryById(companyForm.getAreaId());
			if (area != null) {
				company.setAreaId(area.getId());
				company.setAreaName(area.getAreaName());
			}
		}
		company.setCompanyLogo(companyForm.getCompanyLogo()); // 企业logo
		company.setAreaCodes(companyForm.getAreaCodes()); // 区域编号
		company.setDeviceCodes(companyForm.getDeviceCodes()); // 设备编号
		company.setMemo(companyForm.getMemo());
		// 第二步、添加保存
		companyService.save(company);

		return ResponseResult.ok(IdTypeHandler.encode(company.getIdRaw()));
	}

	/**
	 * @Description: 根据企业id删除企业
	 * @param:企业id
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:22:14
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@Logger(operatorModel = "企业", operatorDesc = "根据企业id删除企业")
	public ResponseResult deleteById(String companyId) {

		companyService.deleteById(companyId);

		return ResponseResult.ok();
	}

	/**
	 * @Description: 修改企业
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:27:59
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	@Logger(operatorModel = "企业", operatorDesc = "根据企业id修改企业")
	public ResponseResult editById(CompanyForm companyForm) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Company company = new Company();
		// 企业id
		company.setId(companyForm.getCompanyId());
		// 企业名称
		company.setCompanyName(companyForm.getCompanyName());
		// 企业注册地址
		company.setCompanyRegisteredAddress(companyForm.getCompanyRegisteredAddress());
		// 企业面积
		company.setCompanyAcreage(companyForm.getCompanyAcreage());
		// 企业自有或租赁
		company.setCompanyOwnLease(companyForm.getCompanyOwnLease());
		// 企业停车场地址
		company.setCompanyCarParkAddress(companyForm.getCompanyCarParkAddress());
		// 企业停车场面积
		company.setCompanyCarParkAcreage(companyForm.getCompanyCarParkAcreage());
		// 企业停车场自有或租赁
		company.setCompanyCarParkOwnLease(companyForm.getCompanyCarParkOwnLease());
		// 营业执照注册号
		company.setCompanyBusinessLicense(companyForm.getCompanyBusinessLicense());
		company.setCompanyBusinessLicenseCloseDate(companyForm.getCompanyBusinessLicenseCloseDate());
		// 道路普通货物运输经营许可证号
		company.setCompanyRoadLicense(companyForm.getCompanyRoadLicense());
		company.setCompanyRoadLicenseCloseDate(companyForm.getCompanyRoadLicenseCloseDate());
		// 企业类型
		company.setCompanyType(companyForm.getCompanyType());
		// 企业类别
		company.setCompanyCategory(companyForm.getCompanyCategory());
		// 企业创建时间
		company.setCompanyCreationTime(companyForm.getCompanyCreationTime());
		// 企业联系方式
		company.setCompanyContact(companyForm.getCompanyContact());
		// 企业传真
		company.setCompanyFacsimile(companyForm.getCompanyFacsimile());
		// 企业url
		company.setCompanyUrl(companyForm.getCompanyUrl());
		// 企业电子信箱
		company.setCompanyEmail(companyForm.getCompanyEmail());
		// 法定代表人
		company.setCompanyLegalRepresentative(companyForm.getCompanyLegalRepresentative());
		// 法定代表人联系方式
		company.setCompanyLegalRepresentativePhone(companyForm.getCompanyLegalRepresentativePhone());
		// 企业经理
		company.setCompanyDirector(companyForm.getCompanyDirector());
		// 企业经理联系方式
		company.setCompanyDirectorPhone(companyForm.getCompanyDirectorPhone());
		// 车队负责人
		company.setCompanyMotorcadePrincipal(companyForm.getCompanyMotorcadePrincipal());
		// 车队负责人联系方式
		company.setCompanyMotorcadePrincipalPhone(companyForm.getCompanyMotorcadePrincipalPhone());
		// 企业员工总数
		company.setCompanyEmployeeNumber(companyForm.getCompanyEmployeeNumber());
		// 企业管理员总人数
		company.setCompanyAdministratorNumber(companyForm.getCompanyAdministratorNumber());
		// 企业驾驶员总人数
		company.setCompanyDriverNumber(companyForm.getCompanyDriverNumber());
		// 企业普通员工人数
		company.setCompanyGeneralNumber(companyForm.getCompanyGeneralNumber());
		// 企业负责人
		company.setCompanyPrincipalName(companyForm.getCompanyPrincipalName());
		// 企业负责人联系方式
		company.setCompanyPrincipalPhone(companyForm.getCompanyPrincipalPhone());
		// 企业入驻时间
		company.setCompanyArrivalTime(companyForm.getCompanyArrivalTime());
		
		company.setCompanyCarNumber(companyForm.getCompanyCarNumber());
		company.setCompanyCarYellowCardNumber(companyForm.getCompanyCarYellowCardNumber());
		company.setCompanyCarBlueCardNumber(companyForm.getCompanyCarBlueCardNumber());
		
		String areaId = companyForm.getAreaId();
		if (!StringUtils.isBlank(areaId)) {
			Area area = areaService.queryById(companyForm.getAreaId());
			if (area != null) {
				company.setAreaId(area.getId());
				company.setAreaName(area.getAreaName());
			}
		}
		company.setCompanyLogo(companyForm.getCompanyLogo()); // 企业logo
		company.setAreaCodes(companyForm.getAreaCodes()); // 区域编号
		company.setDeviceCodes(companyForm.getDeviceCodes()); // 设备编号
		company.setMemo(companyForm.getMemo());
		// 第二步、修改保存
		companyService.editById(company);

		return ResponseResult.ok();

	}
	
	/*
	 * 导出企业基本情况表
	 * */
	
	@RequestMapping(value="exportCompanyInfo.action",method=RequestMethod.POST)
	public ResponseResult exportCompanyInfo(String companyId) throws Exception{
		//先根据企业id查询企业
		Company company=companyService.queryById(companyId);
		if(company==null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		String filePath=companyService.exportCompanyInfo("企业信息表", company);
		return ResponseResult.ok(filePath);
	}
	
	
	

	/**
	 * @Description: 根据企业id查询企业
	 * @author: 展昭
	 * @date: 2018年4月19日 下午3:27:59
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String companyId) {

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		Company company = companyService.queryById(companyId);
		if (company == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(company);
	}

	/**
	 * @Description: 查询所有的企业
	 * @author: 展昭
	 * @date: 2018年4月27日 下午6:37:41
	 * 
	 * @RequestMapping(value = "statisticsCompanyList.action" , method =
	 *                       RequestMethod.POST) public ResponseResult
	 *                       statisticsCompanyList(CompanyQueryForm
	 *                       companyQueryForm){ return
	 *                       ResponseResult.ok(companyService.
	 *                       queryCompanyPageData(companyQueryForm)); }
	 */

	/**
	 * @Description: 查询所有的企业
	 * @author: 展昭
	 * @date: 2018年4月27日 下午6:37:41
	 */
	@RequestMapping(value = "statisticsCompanyList.action", method = RequestMethod.POST)
	public ResponseResult statisticsCompanyList(CompanyQueryForm companyQueryForm) {

		QueryHelper helper = new QueryHelper();
		helper.addCondition(1, "c.deleted = %d", false);

		// 企业名称
		String companyName = companyQueryForm.getCompanyName();
		if (!StringUtils.isBlank(companyName)) {
			helper.addCondition("'%" + companyName + "%'", "c.company_name like %s", false);
		}
		// 企业法人
		String companyLegalRepresentative = companyQueryForm.getCompanyLegalRepresentative();
		if (!StringUtils.isBlank(companyLegalRepresentative)) {
			helper.addCondition("'%" + companyLegalRepresentative + "%'", "c.company_legal_representative like %s", false);
		}

		PageView<Company> pageView = companyService.queryPageData(companyQueryForm.getPageNum(),
				companyQueryForm.getPageSize(), helper.getWhereSQL(), helper.getWhereParams(), null);

		return ResponseResult.ok(pageView);
	}

	/**
	 * @Description: 查询所有企业
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日 下午3:55:21
	 * @return:ResponseResult 返回含有所有企业的结果集
	 */
	@RequestMapping("queryCompanys.action")
	public ResponseResult queryCompanys() {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, " company.deleted = %d ", false);
		List<Company> companys = companyService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		if (companys != null && companys.size() == 1 && companys.get(0) == null) {
			companys.remove(0);
		}
		return ResponseResult.ok(companys);
	}

	/***
	 * @Description: 根据区域编码查询企业
	 * @author: 展昭
	 * @date: 2018年5月17日 上午11:27:22
	 */
	@RequestMapping(value = "queryCompanyByAreaCode.action", method = RequestMethod.GET)
	public ResponseResult queryCompanyByAreaCode(String areaCode) {

		if (StringUtils.isBlank(areaCode)) {
			throw new BizException(StatusCode.AREA_CODE_BLANK);
		}

		List<Company> companies = companyService.queryCompanyByAreaCode(areaCode);
		if (companies == null || companies.isEmpty()) {
			return ResponseResult.notFound();
		}

		return ResponseResult.ok(companies);
	}

	
	
	//==================================================================
	//======================以下是多个企业导入和导出的操作========================
	//==================================================================
	
	
	
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(CompanyQueryForm companyQueryForm, String hasCompany, String hasCar,
			String hasCarDriver, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		CompanyExcelQueryTemplate companyExcelQueryTemplate = new CompanyExcelQueryTemplate("true", "true", "true",
				"true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true",
				"true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true",
				"true", "true", "true", "true","true","true","true","true","true");
		// 创建一个QueryHelper帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据企业id查询企业
		queryHelper.addCondition(companyQueryForm.getCompanyId(), "company.id=%d", true)
				// 根据企业名称查询企业
				.addCondition(companyQueryForm.getCompanyName(), "company.company_name='%s'", false);

		// 查询需要导出的数据
		List<Company> list = companyService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());

		if ("true".equals(hasCompany) && "false".equals(hasCar) && "false".equals(hasCarDriver)) {
			// 这里只导出企业数据，不包含车辆数据和驾驶员数据
			if (list != null) {
				for (Company company : list) {
					// 这里重新给企业的车辆列表赋值，清空车辆数据，包含车辆里的驾驶员数据
					company.setCars(new ArrayList<Car>());
				}
			}
		}
		if ("true".equals(hasCompany) && "true".equals(hasCar) && "false".equals(hasCarDriver)) {
			// 这里导出企业数据和车辆数据，不包含驾驶员数据
			if (list != null) {
				for (Company company : list) {
					List<Car> cars = company.getCars();
					if (cars != null) {
						for (Car car : cars) {
							// 这里重新给车辆的驾驶员列表赋值，清空驾驶员数据
							car.setCarDrivers(new ArrayList<CarDriver>());
						}
					}
				}
			}
		}
		if ("true".equals(hasCompany) && "false".equals(hasCar) && "true".equals(hasCarDriver)) {
			// 这里导出企业数据和驾驶员数据，不包含车辆数据
			if (list != null) {
				// 先将车辆里的驾驶员数据提到一个存放驾驶员的列表中
				for (Company company : list) {
					List<CarDriver> carDrivers = new ArrayList<CarDriver>();
					List<Car> cars = company.getCars();
					if (cars != null) {
						for (Car car : cars) {
							// 将车辆里的驾驶员添加到指定的驾驶员容器中
							carDrivers.addAll(car.getCarDrivers());
						}
					}
					// 将当前企业里的车辆数据清空
					company.setCars(new ArrayList<Car>());
					// 将驾驶员数据赋值给企业里的驾驶员数据上
					company.setCarDrivers(carDrivers);
				}
			}
		}
		if ("true".equals(hasCompany) && "true".equals(hasCar) && "true".equals(hasCarDriver)) {
			// 这里导出企业数据、车辆数据和驾驶员数据，原数据不做处理
		}
		if ("false".equals(hasCompany) && "true".equals(hasCar) && "false".equals(hasCarDriver)) {
			// 这里导出车辆数据，不包含企业数据和驾驶员数据
			// 声明一个存放车辆数据的列表
			List<Car> cars = new ArrayList<Car>();
			if (list != null) {
				for (Company company : list) {
					List<Car> listCar = company.getCars();
					if (listCar != null) {
						for (Car car : listCar) {
							car.setCarDrivers(new ArrayList<CarDriver>());
						}
						// 将企业里的车辆列表放到存放车辆的列表中
						cars.addAll(listCar);
					}
				}
			}
			// 生成表格数据
			List<Map<String, String>> data = carService.createExcelData(new CarExcelQueryTemplate(), cars);
			// 生成水印
			String[] water = new String[] { manager.getManagerName(), manager.getManagerPhone(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
			// 生成Excel表格，并返回下载路径
			String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"car", data,propertiesService.getWindowsCreateWaterRootPath(), water);
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
		if ("false".equals(hasCompany) && "true".equals(hasCar) && "true".equals(hasCarDriver)) {
			// 这里导出车辆数据和驾驶员数据，不包含企业数据
			List<Car> cars = new ArrayList<Car>();
			if (list != null) {
				for (Company company : list) {
					// 将企业里的车辆列表放到存放车辆的列表中
					cars.addAll(company.getCars());
				}
			}
			// 生成表格数据
			List<Map<String, String>> data = carService.createExcelData(new CarExcelQueryTemplate(), cars);
			// 生成水印
			String[] water = new String[] { 
			};
			// 生成Excel表格，并返回下载路径
			String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"car", data,propertiesService.getWindowsCreateWaterRootPath(), water);
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
		if ("false".equals(hasCompany) && "false".equals(hasCar) && "true".equals(hasCarDriver)) {
			// 这里导出驾驶员数据，不包含企业数据和车辆数据
			// 声明一个存放驾驶员的列表
			List<CarDriver> carDrivers = new ArrayList<CarDriver>();
			if (list != null) {
				// 遍历企业列表
				for (Company company : list) {
					List<Car> cars = company.getCars();
					if (cars != null) {
						// 遍历企业里的车辆列表
						for (Car car : cars) {
							List<CarDriver> listCarDriver = car.getCarDrivers();
							if (listCarDriver != null) {
								// 将车辆里的驾驶员列表放到指定的驾驶员容器中
								carDrivers.addAll(listCarDriver);
							}
						}
					}
				}
			}
			// 生成表格数据
			List<Map<String, String>> data = carDriverService.createExcelData(new CarDriverExcelQueryTemplate(),
					carDrivers);
			// 生成水印
			String[] water = new String[] { manager.getManagerName(), manager.getManagerPhone(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
			// 生成Excel表格，并返回下载路径
			String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"carDriver", data,propertiesService.getWindowsCreateWaterRootPath(), water);
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
		if ("false".equals(hasCompany) && "false".equals(hasCar) && "false".equals(hasCarDriver)) {
			// 这里不不导出任何数据
			return ResponseResult.ok("未选择导出数据！");
		}
		// 生成表格数据
		List<Map<String, String>> data = companyService.createExcelData(companyExcelQueryTemplate, list);
		// 生成水印
		String[] water = new String[] {};
		// 生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"company", data,propertiesService.getWindowsCreateWaterRootPath(),water);
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

	@RequestMapping("importExcel.action")
	@Logger(operatorModel = "企业管理", operatorDesc = "导入企业数据")
	public ResponseResult importExcel(HttpServletRequest request) throws Exception {
		Manager manager = WebUtils.getManagerFromSession(request);
		// 第一步：将导入的Excel表格存放到指定的本地目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"company", request);
		// 第二步：从本地存放好的文件中读取数
		List<Map<String, String>> excelData = companyService.readExcelData(path);
		// 第三步：将表格数据封装成相应的实体对象数据
		List<Company> companys = companyService.selectDataFromExcelMapData(new CompanyExcelQueryTemplate(), excelData);
		if (companys != null) {
			for (Company company : companys) {
				company.setOperatorId(IdTypeHandler.decode(manager.getId()));
				company.setOperatorName(manager.getManagerName());
				company.setDeleted(true);
			}
		}
		companyService.saveAll(companys);
		return ResponseResult.ok();
	}

	
	
	
	
	
	//==================================================================
	//======================以下是单个企业导入和导出的操作========================
	//==================================================================
	
	
	
	
	
	
	@RequestMapping(value = "importExcelData.action", method = RequestMethod.POST)
	@Logger(operatorModel = "企业管理", operatorDesc = "导入企业数据")
	public ResponseResult importExcelData(HttpServletRequest request) throws Exception {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步： 将压缩文件保存到本地指定的目录下，并返回文件的绝对路径
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"company", request);

		if(StringUtils.isBlank(path)){
			throw new BizException(StatusCode.DONT_FILE);
		}
		
		// 第二步：将压缩文件解压，并返回解压后的文件存放的父目录
		String parentPath = "";
		try {
			parentPath = CompressedFilesUtils.extract(path);
		} catch (Exception e1) {
			//删除指定文件夹内容
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.EXTRACT_ERROR);
		}
		if(StringUtils.isBlank(parentPath)){
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.EXTRACT_ERROR);
		}
		// 第三步：如果解压成功获取解压后的文件路径，存放在map中，key为文件名称(不包含文件后缀)，value为文件绝对路径
		Map<String,String> filePaths = FileUtils.getSubFilePaths(parentPath);
		if(filePaths == null | filePaths.size() == 0){
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.EXTRACT_NO_FILE);
		}
		// 第四步：读取Excel表格文件中的数据并封装
		//  4.1 获取存放企业信息工作簿和企业管理人员工作簿的文件
		String companyFilePath = filePaths.get("企业基本情况表");
		//  4.2  获取企业表格数据，并转化为企业实体数据
		Company company = companyService.readExcelData(new CompanyExcelQueryTemplate(), companyFilePath);
		if(company == null){
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.IMPORT_COMPANY_NULL);
		}
		company.setOperatorId(IdTypeHandler.decode(manager.getId()));
		company.setOperatorName(manager.getManagerName());
		//  4.3 获取企业管理人员表格数据
		List<Map<String, String>> companyManagerExcelData = companyManagerService.readExcelData(companyFilePath);
		//  4.4 将企业管理人员表格数据转化为实体数据
		List<CompanyManager> listCompanyManagers = companyManagerService.selectDataFromExcelMapData(new CompanyManagerExcelQueryTemplate(), companyManagerExcelData);
		
		//  4.5 获取企业车辆表格数据（车辆表格文件可能有多个，但文件名字中都包含“企业建筑垃圾运输车辆情况表”几个字）
		//     获取企业驾驶员表格数据（驾驶员表格文件可能有多个，但文件名字中都包含“企业驾驶员情况表”几个字）
		List<Car> listCars = new ArrayList<Car>();
		List<CarDriver> listCarDrivers = new ArrayList<CarDriver>();
		
		// 遍历存放excel文件路径的map的key列表
		Set<String> keySet = filePaths.keySet();
		for(String key : keySet){
			if (key.contains("企业建筑垃圾运输车辆情况表")){
				List<Car> cars = carService.readExcelListData(new CarExcelQueryTemplate(), filePaths.get(key));
				listCars.addAll(cars);
			}else if (key.contains("企业驾驶员情况表")){
				List<CarDriver> carDrivers = carDriverService.readExcelListData(new CarDriverExcelQueryTemplate(),filePaths.get(key));
				listCarDrivers.addAll(carDrivers);
			}
		}
		
		//TODO 这里是导入企业时向数据库添加数据的操作，后期需要添加事务
	
		//第五步：执行添加数据库的操作
		//  5.1 执行添加企业的操作
		companyService.save(company);
		
		try {
			company = companyService.queryByName(company.getCompanyName());
		} catch (Exception e1) {
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.IMPORT_COMPANY_NAME_NULL);
		}
		if(company == null){
			File file = new File(path);
			if(file.exists()){
				File parentFile = file.getParentFile();
				if(parentFile.exists()){
					FileUtils.deleteFile(parentFile.getAbsolutePath());
				}
			}
			throw new BizException(StatusCode.IMPORT_COMPANY_NAME_NULL);
		}
		//  5.4 执行添加企业管理人员的操作
		for(CompanyManager companyManager : listCompanyManagers){
			if(companyManager != null){
				companyManager.setCompanyId(company.getId());
				companyManager.setCompanyName(company.getCompanyName());
				companyManager.setOperatorId(IdTypeHandler.decode(manager.getId()));
				companyManager.setOperatorName(manager.getManagerName());
				companyManager.setOperatorPhone(manager.getManagerPhone());
				Date date = new Date();
				companyManager.setCreatedTime(date);
				companyManager.setUpdatedTime(date);
				companyManager.setDeleted(true);
				if(companyManager.getCompanyManagerIdNumber()!=null){
					String str=companyManager.getCompanyManagerIdNumber();
					companyManager.setCompanyManagerIdNumber(str.trim());
				}
			}
			
		}
		try {

			companyManagerService.saveAll(listCompanyManagers);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(manager.getManagerName()+ "在导入企业"+company.getCompanyName()+"时导入的企业管理人员为空");
		}
		//  5.3 执行添加车辆的操作
		for(Car car : listCars){
			car.setCompanyId(company.getId());
			car.setCompanyName(company.getCompanyName());
			car.setCompanyContact(company.getCompanyContact());
			car.setCompanyPrincipalName(company.getCompanyPrincipalName());
			car.setCompanyPrincipalPhone(company.getCompanyPrincipalPhone());
			car.setOperatorId(IdTypeHandler.decode(manager.getId()));
			car.setOperatorName(manager.getManagerName());
			car.setOperatorPhone(manager.getManagerPhone());
			Date date = new Date();
			car.setCreatedTime(date);
			car.setUpdatedTime(date);
			car.setDeleted(true);
		}
		try {
			carService.saveAll(listCars);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(manager.getManagerName()+ "在导入企业"+company.getCompanyName()+"时导入的车辆为空");
		}
		//  5.4执行添加驾驶员的操作
		for(CarDriver carDriver : listCarDrivers){
			carDriver.setCompanyId(company.getId());
			carDriver.setCompanyName(company.getCompanyName());
			carDriver.setOperatorId(IdTypeHandler.decode(manager.getId()));
			carDriver.setOperatorName(manager.getManagerName());
			carDriver.setOperatorPhone(manager.getManagerPhone());
			Date date = new Date();
			carDriver.setCreatedTime(date);
			carDriver.setUpdatedTime(date);
			carDriver.setDeleted(true);
		}
		try {
			carDriverService.saveAll(listCarDrivers);
		} catch (RuntimeException e) {
			logger.error(manager.getManagerName()+ "在导入企业"+company.getCompanyName()+"时导入的驾驶人员为空",e);
		}
		
		//第六步：维护数据关联性
		//企业和车辆
		carService.setCompanyAndCarRelation();
		//企业和驾驶员
		carDriverService.setCompanyAndCarDriverRelation();
		//驾驶员和家庭成员
		carDriverService.setCarDriverAndCarDriverFamilyMemberRelation();
		//第七步：删除上传的压缩文件及解压文件
			//删除压缩文件
			FileUtils.deleteFile(path);
			//删除解压后的所有文件
			FileUtils.deleteFile(parentPath/*.substring(0,parentPath.lastIndexOf("\\"))*/);
		return ResponseResult.ok(company);
	}
	
	
	/**
	 * @Description: 导出单个或多个企业(包含企业管理人员)，可一并导出企业下的驾驶员(可选)，企业下的车辆(可选) 
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月14日 上午9:27:58
	 * @Param: companyIds 传入企业的id列表
	 * @Param: hasCars 传入是否导出车辆数据
	 * @Param: hasCarDrivers 传入是否导出驾驶员数据
	 * @Return: ResponseResult 返回最终文件的下载路径
	 */
	@RequestMapping("exportExcelData.action")
	public ResponseResult exportExcelData(String [] companyIds,Boolean hasCars,Boolean hasCarDrivers){

		// 第一步：根据企业id查询企业列表
		if(companyIds == null | companyIds.length == 0){
			logger.error(StatusCode.EXPORT_COMPANY_ID_NULL.getMessage());
			throw new BizException(StatusCode.EXPORT_COMPANY_ID_NULL);
		}
		QueryHelper queryHelper = new QueryHelper();
		StringBuffer sb = new StringBuffer("");
		for(String companyId : companyIds){
			sb.append(IdTypeHandler.decode(companyId));
			sb.append(",");
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length()-1);
		}
		queryHelper.addCondition(sb.toString(), "company.id in (%s)", false);
		List<Company> listCompanys = companyService.queryData(queryHelper.getWhereSQL(),queryHelper.getWhereParams());
		
		if(listCompanys == null | listCompanys.size() == 0){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		/*
		 * 	以下是导出多个企业
		 *		将获取到的企业列表数据放到excel文件中，排版方式是横向排列，
		 *		单个企业信息占一行，单个企业是定点放数据的，循环遍历多个企业，
		 *		根据企业id列表获取到的企业管理人员的排版，紧跟在企业数据的右侧依次排列，
		 *		单个企业管理人员占一行
		 * */
		
		/*
		 * 第二步：获取企业下面的管理人员
		 *将根据企业id列表获取驾驶员信息（包含家庭成员）
		 * 和根据企业id列表获取的车辆信息放到一个驾驶人员车辆信息中，
		 * */
		QueryHelper queryHelperManager = new  QueryHelper();
		queryHelperManager.addCondition(sb.toString(), "company_id in (%s)", false);
		List<CompanyManager> listManagers = companyManagerService.queryData(queryHelperManager.getWhereSQL(), queryHelperManager.getWhereParams());
		
		//第三步：获取企业下面的车辆列表和驾驶员列表
		List<Car> cars = new ArrayList<Car>();
		List<CarDriver> carDrivers = new ArrayList<CarDriver>();
		// 3.1获取车辆数据
		if(hasCars != null && hasCars){
			QueryHelper queryHelperCar = new QueryHelper();
			queryHelperCar.addCondition(sb.toString(), " car.company_id in (%s) ", false);
			List<Car> listCars = carService.queryData(queryHelperCar.getWhereSQL(), queryHelperCar.getWhereParams());
			if(listCars != null && listCars.size()>0){
				cars.addAll(listCars);
			}
		}
		// 3.2获取驾驶员数据
		if(hasCarDrivers != null && hasCarDrivers){
			QueryHelper queryHelperCarDriver = new QueryHelper();
			queryHelperCarDriver.addCondition(sb.toString(), " company_id in (%s) ", false);
			List<CarDriver> listCarDrivers = carDriverService.queryData(queryHelperCarDriver.getWhereSQL(),queryHelperCarDriver.getWhereParams());
			if(listCarDrivers != null && listCarDrivers.size() > 0 ){
				carDrivers.addAll(listCarDrivers);
			}
		}
		/* 3.3封装驾驶员车辆数据
		 * 将通过企业id列表获取到的车辆和驾驶人员的列表封装成的驾驶人员车辆信息放到
		 * 驾驶人员车辆汇总表中，单个驾驶人员车辆信息横向排列，包括家庭成员
		 * */
		List<CarAndOrCarDriver> carAndOrCarDrivers = new ArrayList<CarAndOrCarDriver>();
		if(cars.size() > 0 && carDrivers.size() == 0){
			//只导车辆
			for(Car car : cars){
				CarAndOrCarDriver carAndOrCarDriver = new CarAndOrCarDriver();
				BeanUtils.copyProperties(car, carAndOrCarDriver);
				carAndOrCarDrivers.add(carAndOrCarDriver);
			}
		}else if(cars.size() == 0 && carDrivers.size() > 0){
			//只导驾驶员
			for(CarDriver carDriver : carDrivers){
				CarAndOrCarDriver carAndOrCarDriver = new CarAndOrCarDriver();
				BeanUtils.copyProperties(carDriver, carAndOrCarDriver);
				carAndOrCarDrivers.add(carAndOrCarDriver);
			}
		}else if(cars.size() > 0 && carDrivers.size() > 0){
			//车辆和驾驶员都导
			for(CarDriver carDriver : carDrivers){
				CarAndOrCarDriver carAndOrCarDriver = new CarAndOrCarDriver();
				BeanUtils.copyProperties(carDriver, carAndOrCarDriver);
				for(Car car : cars){
					if((!StringUtils.isBlank(car.getCarDriverIdNumber())) && car.getCarDriverIdNumber().equals(carDriver.getIdNumber())){
						BeanUtils.copyProperties(car, carAndOrCarDriver);
						break;
					}
				}
				carAndOrCarDrivers.add(carAndOrCarDriver);
			}
		}
		for(CarAndOrCarDriver dd : carAndOrCarDrivers){
			System.out.println(dd);
		}
		// 第四步：将获取的数据放到excel文件中并将下载链接返回给前台
		//1.导出企业和企业管理人员汇总，并返回生成的excel文件在本地的路径
		String [] water = {
				
		};
		String companyDescPath = companyService.createExcelPOI("企业信息汇总表.xlsx",listCompanys,listManagers,water);
		
		String carAndOrDriverDescPath = "";
		//2.如果是有查询车辆驾驶员数据的话，便导出驾驶员车辆信息汇总表数据，并返回生成excel文件在本地的路径
		if(carAndOrCarDrivers.size() > 0){
			carAndOrDriverDescPath = carService.createExcelPOI(carAndOrCarDrivers,"企业驾驶人员车辆汇总表.xlsx", water);
		}
		String temp = File.separator+"excel"+File.separator+"download"+File.separator+"company"+File.separator+System.currentTimeMillis()+".zip";
		String zipFile = propertiesService.getWindowsRootPath()+temp;
		CompressedFilesUtils.reduce(zipFile,companyDescPath,carAndOrDriverDescPath);
		FileUtils.deleteFile(companyDescPath);
		FileUtils.deleteFile(carAndOrDriverDescPath);
		String downloadPath = "http://"+propertiesService.getCurrentServerForExcel()+temp.replaceAll("\\\\","/");
		return ResponseResult.ok(downloadPath);
		//以下是导出单个企业的情况，导出的是企业详情，包含选择导出的车辆和驾驶员
		//导出的车辆和驾驶员是详情，单个详情在一个工作簿中
		/*if(listCompanys.size() == 1){
			Company company = listCompanys.get(0);
			QueryHelper queryHelperOuther = new  QueryHelper();
			queryHelperOuther.addCondition(company.getId(), "company_id = %d", true);
			List<CompanyManager> listManagers = companyManagerService.queryData(queryHelperOuther.getWhereSQL(), queryHelperOuther.getWhereParams());
			
			//2.1 将企业数据和企业管理人员数据放到excel表格中
				//2.1.1将企业数据放到excel表格中
				Map<String,Object> excelDataCompany = companyService.createExcelData(new CompanyExcelQueryTemplate(), company);
				String[] water = new String[]{
				};
				//返回生成的excel在本地存放的目录
				String descPath = companyService.createExcelPOI("企业基本情况表.xlsx", excelDataCompany,water);
				//2.1.2将企业管理人员放到excel表格中，并和企业excel表格是同一个文件
				CompanyManagerExcelQueryTemplate companyManagerExcelQueryTemplate=new CompanyManagerExcelQueryTemplate("true","true", "true","true","true", "true","true", "true");
				List<Map<String,String>> excelDataCompanyManager = companyManagerService.createExcelData(companyManagerExcelQueryTemplate, listManagers);
				//返回excel文件在本地的路径，以此判断数据是否放入成功
				String returnPathCompanyManager = companyManagerService.createExcelPOI(descPath, excelDataCompanyManager, water);
			
			//2.2 将车辆数据放到excel表格中
				String returnPathCar = "";
				if(hasCars != null && hasCars){
					QueryHelper queryHelperCar = new QueryHelper();
					queryHelperCar.addCondition(company.getId(), " car.company_id = %d ", true);
					List<Car> listCars = carService.queryData(queryHelperCar.getWhereSQL(), queryHelperCar.getWhereParams());
					returnPathCar = carService.createExcelPOI(new CarExcelQueryTemplate(), "企业建筑垃圾运输车辆情况表.xlsx", listCars, water);
				}
			//2.3 将驾驶员数据放到excel表格中
				String returnPathCarDrivers = "";
				if(hasCarDrivers != null && hasCarDrivers){
					QueryHelper queryHelperCarDriver = new QueryHelper();
					queryHelperCarDriver.addCondition(company.getId(), " company_id = %d ", true);
					List<CarDriver> listCarDrivers = carDriverService.queryData(queryHelperCarDriver.getWhereSQL(),queryHelperCarDriver.getWhereParams());
					returnPathCarDrivers = carDriverService.createExcelPOI(new CarDriverExcelQueryTemplate(), "企业驾驶员情况表.xlsx", listCarDrivers, water);
				}
				
			//2.4 将多个文件打包到一个压缩包文件中
				String temp = File.separator+"excel"+File.separator+"download"+File.separator+"company"+File.separator+company.getCompanyName()+".zip";
				String zipFile = propertiesService.getWindowsRootPath()+temp;
				CompressedFilesUtils.reduce(zipFile,returnPathCompanyManager,returnPathCar,returnPathCarDrivers);
				FileUtils.deleteFile(descPath);
				FileUtils.deleteFile(returnPathCompanyManager);
				FileUtils.deleteFile(returnPathCar);
				FileUtils.deleteFile(returnPathCarDrivers);
				String downloadPath = "http://"+propertiesService.getCurrentServerForExcel()+temp.replaceAll("\\\\","/");
				return ResponseResult.ok(downloadPath);
		}*/
	}
	
	
}
