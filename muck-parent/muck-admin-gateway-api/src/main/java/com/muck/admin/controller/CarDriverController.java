package com.muck.admin.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Car;
import com.muck.domain.CarDriver;
import com.muck.domain.Company;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CarDriverExcelQueryTemplate;
import com.muck.excelquerytemplate.CarDriverSummaryExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.CarDriverQueryForm;
import com.muck.request.CarDriverForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.CarDriverService;
import com.muck.service.CarService;
import com.muck.service.CompanyService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;
import com.muck.utils.UploadUtils;

/**
 * 车辆驾驶员控制器
 */
@RestController("AdminCarDriverController")
@RequestMapping("/admin/cardriver")
public class CarDriverController extends BaseController {

	@Autowired
	private CarDriverService carDriverService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	
	@Autowired
	private PropertiesService propertiesService;
	/**
	 * 添加车辆驾驶员
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	public ResponseResult save(CarDriverForm carDriverForm) {

		// 1、保存添加车辆驾驶员
		CarDriver carDriver = new CarDriver();
		BeanUtils.copyProperties(carDriverForm, carDriver);
		
		String companyId = carDriverForm.getCompanyId();
		String companyName = carDriverForm.getCompanyName();
		if(StringUtils.isNotBlank(companyId)){
			// 查询企业
			Company company = companyService.queryById(companyId);
			if(company != null){
				carDriver.setCompanyId(company.getId());
				carDriver.setCompanyName(company.getCompanyName());
			}
		}else if(StringUtils.isNotBlank(companyName)){
			//添加企业
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			carDriver.setCompanyId(company.getId());
			carDriver.setCompanyName(companyName);
		}
		if(carDriver != null){
			CarDriver carDrivers = carDriverService.queryByIdNumber(carDriver.getIdNumber());
			if(carDrivers == null){
				//如果分驾驶员不存在，则添加驾驶员
				carDriverService.save(carDriver);
			}else {
				//如果驾驶员已存在，则修改驾驶员
				carDriver.setId(carDrivers.getId());
				if(carDriver.equals(carDrivers)){
					throw new BizException(StatusCode.CAR_DRIVER_ALREADY_EXISTS_AND_NOUPDATE);
				}else {
					carDriverService.editById(carDriver);
				}
			}	
		}
		/*// 2、保存车辆驾驶员,同时保存车辆驾驶员所对应的家庭成员
		carDriverService.save(carDriver);*/

		return ResponseResult.ok();
	}

	/**
	 * 车辆驾驶员列表
	 */
	@RequestMapping(value = "queryCarDrivers.action", method = RequestMethod.POST)
	public ResponseResult queryCarDrivers(CarDriverQueryForm carDriverQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<CarDriver> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");

		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		// 驾驶员名称
		String driverName = carDriverQueryForm.getDriverName();
		if (StringUtils.isNotBlank(driverName)) {
			queryHelper.addCondition("'%" + driverName + "%'", "driver_name like %s", false);
		}

		// 工作单位
		String companyName = carDriverQueryForm.getCompanyName();
		if (StringUtils.isNotBlank(companyName)) {
			queryHelper.addCondition("'%" + companyName + "%'", "company_name like %s", false);
		}
		// 车辆号码
		String carCode = carDriverQueryForm.getCarCode();
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition("'%" + carCode + "%'", "car_code like %s", false);
		}

		pageView = carDriverService.queryPageData(carDriverQueryForm.getPageNum(), carDriverQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}

	/**
	 * 根据驾驶员id查询驾驶详情
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String carDriverId) {

		CarDriver carDriver = carDriverService.queryById(carDriverId);
		if (carDriver == null) {
			return ResponseResult.notFound();
		}
		Car car = carService.queryByIdNumber(carDriver.getIdNumber());
		if(car != null){
			carDriver.setCarId(car.getId());
		}
		return ResponseResult.ok(carDriver);
	}

	
	
	/**
	 * 根据驾驶员id查询驾驶详情
	 */
	@RequestMapping(value = "queryByPhone.action", method = RequestMethod.GET)
	public ResponseResult queryByPhone(String carDriverPhone) {

		CarDriver carDriver = carDriverService.queryByPhone(carDriverPhone);
		if (carDriver == null) {
			return ResponseResult.notFound();
		}
		Car car = carService.queryByIdNumber(carDriver.getIdNumber());
		if(car != null){
			carDriver.setCarId(car.getId());
		}
		return ResponseResult.ok(carDriver);
	}
	
	/**
	 * 根据车辆驾驶员id删除车辆驾驶员
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult deleteById(String carDriverId) {

		carDriverService.deleteById(carDriverId);

		return ResponseResult.ok();
	}

	/**
	 * 根据车辆驾驶员id修改车辆驾驶员
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	public ResponseResult editById(CarDriverForm carDriverForm) {

		String carDriverId = carDriverForm.getCarDriverId();
		if (StringUtils.isBlank(carDriverId)) {
			return ResponseResult.notFound();
		}

		// 1、修改车辆驾驶员
		CarDriver carDriver = new CarDriver();
		carDriver.setId(carDriverId);
		BeanUtils.copyProperties(carDriverForm, carDriver);
		
		String companyId = carDriverForm.getCompanyId();
		String companyName = carDriverForm.getCompanyName();
		if(StringUtils.isNotBlank(companyId)){
			// 查询企业
			Company company = companyService.queryById(companyId);
			if(company != null){
				carDriver.setCompanyId(company.getId());
				carDriver.setCompanyName(company.getCompanyName());
			}
		}else if(StringUtils.isNotBlank(companyName)){
			//添加企业
			Company company = new Company();
			company.setCompanyName(companyName);
			companyService.save(company);
			carDriver.setCompanyId(company.getId());
			carDriver.setCompanyName(companyName);
		}
		// 2、修改车辆驾驶员的同时也要修改驾驶员的家庭成员
		carDriverService.editById(carDriver);

		return ResponseResult.ok();
	}
	
	/***
	 * 	根据企业id查询驾驶员列表
	*/
	@RequestMapping(value = "queryCarDriversByCompanyId.action" , method = RequestMethod.POST)
	public ResponseResult queryCarDriversByCompanyId(CarDriverQueryForm queryForm){
		
		QueryHelper helper = new QueryHelper();
		helper.addCondition(1, "deleted = %d", false);
	
		String companyId = queryForm.getCompanyId();
		helper.addCondition(companyId, "company_id = %d", true);
		
		PageView<CarDriver> pageView = carDriverService.queryPageData(queryForm.getPageNum(), queryForm.getPageSize(), 
										helper.getWhereSQL(), 
										helper.getWhereParams(), 
										null);
		
		return ResponseResult.ok(pageView);
	}
	
	
	/***
	 * 	根据车牌号查询车辆是否存在，并返回基本信息
	*/
	@RequestMapping(value = "queryCarByCarCode.action" , method = RequestMethod.GET)
	public ResponseResult queryCarByCarCode(String carCode,String carCardColor){
		if(StringUtils.isBlank(carCode)){
			throw new BizException(StatusCode.CAR_CODE_BLANK);
		}
		Car car = carService.selectCarByCarCodeAndCarCardColor(carCode, carCardColor);
		if(car == null){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(car);
	}
	/**
	 * @Description: 导出Excel表格数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月9日  下午9:17:31
	 * @param: carDriverQueryForm 传入查询数据的请求参数
	 * @return:ResponseResult 返回含有Excel下载链接和操作信息的结果集
	 */
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(CarDriverQueryForm carDriverQueryForm,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：创建一个用来查询数据的帮助类对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(carDriverQueryForm.getCarCode(), "car_code = '%s'",false)
		.addCondition(carDriverQueryForm.getCompanyId(),"company_id = %d" , true)
		.addCondition(1, "deleted=%d", false);
		
		//第二步：根据条件查询驾驶员数据
		List<CarDriver> list = carDriverService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		//第三步：生成表格数据
		CarDriverExcelQueryTemplate excelTemplate = new CarDriverExcelQueryTemplate("true", "true", "true",
				"true", "true", "true", "true", "true","true", "true", "true", "true", "true", "true", "true", "true",
				"true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", 
				"true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true");
		List<Map<String,String>> excelData = carDriverService.createExcelData(excelTemplate , list);
		//生成水印
		String [] water = new String[]{
		};
		//生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"carDriver", excelData,propertiesService.getWindowsCreateWaterRootPath(),water);
		if(!StringUtils.isBlank(outputExcelPath)){
			//如果生成的路径不为""，组成一个Excel文件导出的操作日志对象
			ExcelOutputLog excelOutputLog = new ExcelOutputLog();
			excelOutputLog.setOutputExcelName(outputExcelPath.substring(outputExcelPath.lastIndexOf("/")+1));
			excelOutputLog.setOutputExcelPath(outputExcelPath);
			excelOutputLog.setOperatorId(IdTypeHandler.decode(manager.getId()));
			excelOutputLog.setOperatorName(manager.getManagerName());
			Date date = new Date();
			excelOutputLog.setCreatedTime(date);
			excelOutputLog.setUpdatedTime(date);
			//添加该条导出日志记录
			excelOutputLogService.save(excelOutputLog);
		}
		//DownloadFileByIO.downloadFileFromServer(outputExcelPath, response, "导出驾驶员数据");
		return ResponseResult.ok(outputExcelPath);
	}
	/**
	 * @Description: 导入企业驾驶员汇总信息表格数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月9日  下午9:19:08
	 * @param: request 传入一个含有文件请求的HttpServletRequest 请求
	 * @return:ResponseResult 返回导入Excel表格数据的操作信息
	 */
	@RequestMapping("importExcelSummary.action")
	public  ResponseResult importExcelSummary(HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：将表格文件存放到本机制定的文件夹目录下，并返回存放到本地的目录
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"carDriver", request);
		if(StringUtils.isBlank(path)){
			
		}
		//第二步：读取Excel表格中的数据
		List<Map<String,String>> excelData = carDriverService.readExcelData(path);
		//第三步：将表格数据封装成相应的实体对象数据
		List<CarDriver> list = carDriverService.selectDataFromExcelMapData(new CarDriverSummaryExcelQueryTemplate(), excelData);
		//第四步：设置数据的操作人
		if(list != null){
			for(CarDriver carDriver : list){
				carDriver.setOperatorId(IdTypeHandler.decode(manager.getId()));
				carDriver.setOperatorName(manager.getManagerName());
				carDriver.setOperatorPhone(manager.getManagerPhone());
				carDriver.setCompanyId(request.getParameter("companyId"));
				carDriver.setDeleted(true);
			}
		}
		//第五步：将封装好的数据添加到数据库中
		carDriverService.saveAll(list);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 导入驾驶员信息情况表
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月22日  下午1:52:06
	 * @Param: request 传入一个含有表格文件的请求
	 * @Return:ResponseResult 返回操作信息
	 */
	@RequestMapping("importCarDriverInfo.action")
	public ResponseResult importCarDriverInfo(HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：将表格文件存放到本机制定的文件夹目录下，并返回存放到本地的目录
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"carDriverInfo", request);
		//path = "E:"+File.separator+"data"+File.separator+"document"+File.separator+"excel"+File.separator+"金华市建筑垃圾运输服务企业准入申请表（电子档）.xls";
		//第二步：读取Excel表格中的数据
		CarDriver carDriver = carDriverService.readExcelData(new CarDriverExcelQueryTemplate(),path);
		return ResponseResult.ok(carDriver);
	}
	
	/**
	 * @Description: 导出驾驶员详情
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月28日  上午11:46:23
	 * @Param:id 传入一个驾驶员id
	 * @Return:ResponseResult 返回导出驾驶员的Excel文件请求路径
	 */
	@RequestMapping("exportCarDriverInfo.action")
	public ResponseResult exportCarDriverInfo(String id,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：根据id获取驾驶员详情
		CarDriver carDriver = carDriverService.queryById(id);
		if(carDriver == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		//第二步：生成Excel表格数据
		Map<String,Object> excelData = carDriverService.createExcelData(new CarDriverExcelQueryTemplate(), carDriver);
		//第三步：生成水印，并将表格数据填充到excel表格中，文件名(驾驶人员信息情况表.xlsx) 
		String[] water = new String[]{
		};
		String downloadPath = carDriverService.createExcelPOI("企业驾驶员情况表.xlsx", excelData,water);
		//DownloadFileByIO.downloadFileFromServer(downloadPath, response, "驾驶人员信息情况表");
		if(!StringUtils.isBlank(downloadPath)){
			return ResponseResult.ok(downloadPath);
		}else {
			throw new BizException(StatusCode.EXPORT_TEMPLATE_NULL);
		}
	}
	/**
	 * @Description: 通过手机号校验驾驶员是否存在 
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月3日 上午11:02:14
	 * @Param:  phone
	 * @Return: ResponseResult
	 */
	@RequestMapping("isExistsByPhone.action")
	public ResponseResult isExistsByPhone(String phone){
		boolean flag = false;
		CarDriver carDriver = carDriverService.queryByPhone(phone);
		if(carDriver != null){
			flag = true;
		}
		return ResponseResult.ok(flag);
	}
}
