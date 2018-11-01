package com.muck.admin.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Area;
import com.muck.domain.CarPark;
import com.muck.domain.Company;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CarExcelQueryTemplate;
import com.muck.excelquerytemplate.CarParkExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.CarParkQueryForm;
import com.muck.request.CarParkForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CarParkService;
import com.muck.service.CompanyService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;
import com.muck.utils.UploadUtils;

/**
 * @Description: 停车场Controller
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午10:42:16
 */
@RestController("AdminCarParkController")
@RequestMapping("/admin/carPark")
public class CarParkController extends BaseController{

	@Autowired
	private CarParkService carParkService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PropertiesService propertiesService;
	/**
	 * @Description: 查询停车场详情
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  下午3:19:49
	 * @param:carParkId 传入一个停车场id
	 * @return:ResponseResult 返回停车场详情
	 */
	@RequestMapping("queryCarParkInfo.action")
	public ResponseResult queryCarParkInfo(String carParkId){
		CarPark carPark = carParkService.queryById(carParkId);
		if(carPark == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		return ResponseResult.ok(carPark);
	}
	
	/**
	 * @Description: 获取停车场的分页数据
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日  上午10:30:33
	 * @param: carParkQueryForm 传入查询停车场的条件参数
	 * @return:ResponseResult 返回含有停车场分页数据的结果集
	 */
	@RequestMapping("carPark.action")
	public ResponseResult carPark(CarParkQueryForm carParkQueryForm){
		
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<CarPark> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据

		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();

		// 停车场编号
		String carParkCode = carParkQueryForm.getCarParkCode();

		//停车场名称
		String carParkName = carParkQueryForm.getCarParkName();
		
		//停车场所属区域编号
		String parentCode = carParkQueryForm.getAreaCode();
		
		//停车场所属企业的id
		String companyId = carParkQueryForm.getCompanyId();
		
		//停车场所属企业名称
		String companyName = carParkQueryForm.getCompanyName();
		queryHelper.addCondition(carParkCode, "car_park_code = '%s'", false)
							.addCondition(carParkName,"car_park_name = '%s'", false);
		String areaCodes = "";
		if(!StringUtils.isBlank(parentCode)){
			areaCodes = areaService.querySubAreaCodesByParentCode(parentCode);
		}
		queryHelper.addCondition(areaCodes,"area_code in (%s) ", false)
							.addCondition(companyId, "company_id = %d", true)
							.addCondition(companyName, "company_name = '%s'", false)
							.addCondition(1, "deleted = %d",false);
		pageView = carParkService.queryPageData(carParkQueryForm.getPageNum(), carParkQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}
	
	/**
	 * @Description: 添加停车场数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日  上午10:47:39
	 * @param: carParkForm 传入一个含有停车场数据的请求对象
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping(value = "save.action" , method = RequestMethod.POST)
	public ResponseResult save(CarParkForm carParkForm,HttpSession session){
		// 获取添加数据时需要的数据
		CarPark carPark = new CarPark();
		carPark.setCarParkCode(carParkForm.getCarParkCode());
		carPark.setCarParkName(carParkForm.getCarParkName());
		String companyId = carParkForm.getCompanyId();
		if(!StringUtils.isBlank(companyId)){
			Company company = companyService.queryById(companyId);
			if(company != null){
				carPark.setCompanyId(company.getId());
				carPark.setCompanyName(company.getCompanyName());
			}
		}
		String areaId = carParkForm.getAreaId();
		if(!StringUtils.isBlank(areaId)){
			Area area = areaService.queryById(carParkForm.getAreaId());
			if(area != null){
				carPark.setAreaId(area.getId());
				carPark.setAreaName(area.getAreaName());
			}
		}
		carPark.setMemo(carParkForm.getMemo());
		carParkService.save(carPark);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 根据id逻辑删除（修改数据的状态）停车场记录的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日  上午10:38:26
	 * @param: carParkId 传入一个停车场的id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String carParkId){
		if(StringUtils.isBlank(carParkId)){
			throw new BizException(StatusCode.ID_BLANK);
		}
		carParkService.deleteById(carParkId);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 根据id真实删除停车场记录的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日  上午10:39:47
	 * @param: carParkId 传入一个停车场id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String carParkId){
		if(StringUtils.isBlank(carParkId)){
			throw new BizException(StatusCode.ID_BLANK);
		}
		carParkService.deleteByIdReal(carParkId);
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 根据id修改停车场数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日  上午11:02:58
	 * @param: carParkForm 传入一个停车场数据请求参数
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping(value = "editById.action" , method = RequestMethod.POST)
	public ResponseResult editById(CarParkForm carParkForm){
		
		//获取修改时需要的数据
		CarPark carPark = new CarPark();
		carPark.setId(carParkForm.getCarParkId());
		carPark.setCarParkCode(carParkForm.getCarParkCode());
		carPark.setCarParkName(carParkForm.getCarParkName());
		String companyId = carParkForm.getCompanyId();
		// 企业id
		if(!StringUtils.isBlank(companyId)){
			Company company = companyService.queryById(companyId);
			if(company != null){
				carPark.setCompanyId(company.getId());
				carPark.setCompanyName(company.getCompanyName());
			}
		}
		
		// 区域id
		String areaId = carParkForm.getAreaId();
		if(StringUtils.isNotBlank(areaId)){
			Area area = areaService.queryById(areaId);
			if(area != null){
				carPark.setAreaId(area.getId());
				carPark.setAreaCode(area.getAreaCode());
				carPark.setAreaName(area.getAreaName());
			}
		}
		
		carPark.setMemo(carParkForm.getMemo());
		carParkService.editById(carPark);
		
		return ResponseResult.ok();
	}
	
	/**
	 * @Description: 导出停车场Excel表格
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日  下午11:05:24
	 * @param: carParkQueryForm 传入请求数据的参数
	 * @param: excelTemplate 传入请求的Excel模版参数
	 * @param: session 传入一个session会话
	 * @return:ResponseResult 返回表格下载的路径和操作的结果信息
	 */
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(CarParkQueryForm carParkQueryForm,CarParkExcelQueryTemplate excelTemplate,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//创建一个QueryHelper帮助对象
		QueryHelper queryHelper = new QueryHelper();
		//根据停车场编号查询
		queryHelper.addCondition(carParkQueryForm.getCarParkCode(), "car_park_code = '%s'", false)
		//根据停车场名称查询
		.addCondition(carParkQueryForm.getCarParkName(), "car_park_name = '%s'", false);
		//根据停车场所属区域查询
		String parentCode = carParkQueryForm.getAreaCode();
		if(!StringUtils.isBlank(parentCode)){
			//获取所有的子级区域编号
			String areaCodes = areaService.querySubAreaCodesByParentCode(parentCode);
			queryHelper.addCondition(areaCodes,"area_code in (%s)",false);
		}
		//根据企业id查询停车场
		queryHelper.addCondition(carParkQueryForm.getCompanyId(), "company_id = %d",true)
		//根据企业名称查询停车场
		.addCondition(carParkQueryForm.getCompanyName(),"company_name = '%s'",false);
		List<CarPark> list = carParkService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		//生成Excel表格数据
		List<Map<String,String>> data = carParkService.createExcelData(excelTemplate, list);
		//生成水印
		String [] water = new String []{
		};
		//生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"carPark", data,propertiesService.getWindowsCreateWaterRootPath(),water);
		
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
		return ResponseResult.ok(outputExcelPath);
	}
	/**
	 * @Description: 停车场导入Excel表格
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日  下午10:51:00
	 * @param: request 传入一个含有Excel表格文件的HttpServletRequest 请求
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("importExcel.action")
	public ResponseResult importExcel(HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		//第一步：将导入的Excel表格存放到指定的本地目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(),"carPark", request);
		//第二步：从本地存放好的文件中读取数据
		List<Map<String,String>> excelData = ExcelUtil.read(propertiesService.getCurrentServerForImage(),path,propertiesService.getWindowsRootPath());
		//第三步：将表格中的数据转化为添加到数据库中的数据列表
		List<CarPark> list = carParkService.selectDataFromExcelMapData(new CarExcelQueryTemplate(), excelData);
		if(list != null){
			for(CarPark carPark : list){
				carPark.setOperatorId(IdTypeHandler.decode(manager.getId()));
				carPark.setOperatorName(manager.getManagerName());
			}
			//将导入的数据添加到数据库中
			carParkService.saveAll(list);
		}
		return ResponseResult.ok();
	}
	/**
	 * @Description: 查询停车场树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日  下午12:01:37
	 * @return:ResponseResult 返回停车场树
	 */
	@RequestMapping("queryCarParkTree.action")
	public ResponseResult queryCarParkTree(){
		return ResponseResult.ok(carParkService.queryCarParkTree());
	}
}
