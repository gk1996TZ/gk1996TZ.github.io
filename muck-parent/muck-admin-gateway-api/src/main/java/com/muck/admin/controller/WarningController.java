package com.muck.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.muck.domain.Device;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.domain.Warning;
import com.muck.excelquerytemplate.WarningExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.WarningQueryForm;
import com.muck.response.ResponseResult;
import com.muck.response.SimpleDeviceTypeInfoResponse;
import com.muck.response.StatusCode;
import com.muck.service.DeviceService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.service.WarningService;
import com.muck.utils.ExcelUtil;

/**
 * 
 * @Description: 告警Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月4日 上午10:38:29
 */
@RestController("AdminWarningController")
@RequestMapping("/admin/warning")
public class WarningController extends BaseController {

	@Autowired
	private WarningService warningService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	/***
	 * @Description: 根据告警id查询具体告警信息
	 * @author: 展昭
	 * @date: 2018年5月4日 上午11:03:36
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String warningId) {

		Warning warning = warningService.queryById(warningId);

		if (warning == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(warning);
	}

	/**
	 * @Description: 添加设备预警信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月9日 下午6:26:21
	 * @param: warningQueryForm
	 *             传入预警信息请求参数
	 * @return: ResponseResult 返回是否添加成功
	 */
	@RequestMapping(value="insert.action",method = RequestMethod.POST)
	// 1.先通过设备编号获取设备，如果设备不存在，则返回数据不存在的信息
	public ResponseResult insert(WarningQueryForm warningQueryForm) {
		Device device = deviceService.queryByDeviceCode(warningQueryForm.getDeviceCode());
		if (device == null) {
			return ResponseResult.notFound();
		}
		// 2.组装需要添加的数据
		Warning warning = new Warning();
		warning.setDeviceCode(warningQueryForm.getDeviceCode());
		warning.setWarningType(warningQueryForm.getWarningType());
		warning.setWarningStatus(warningQueryForm.getWarningStatus());
		warning.setWarningContent(warningQueryForm.getWarningContent());
		warning.setCreatedTime(new Date());
		warning.setWarningTime(new Date());
		// 3.添加数据
		warningService.save(warning);
		// 4.添加完数据后，获取设备信息封装到异常信息中
		warning.setDevice(device);
		return ResponseResult.ok(warning);
	}

	/**
	 * @Description: 修改告警信息的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月5日  下午3:38:41
	 * @param: warningQueryForm 传入告警信息
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("editById.action")
	public ResponseResult editById(WarningQueryForm warningQueryForm){
		SimpleDeviceTypeInfoResponse deviceType = deviceService.queryDeviceTypeByChannelCode(warningQueryForm.getChannelCode());
		if(deviceType == null){
			throw new BizException(StatusCode.NOT_FOUND);
		}
		Device device = deviceService.queryById(deviceType.getDeviceId());
		
		// 2.组装需要添加的数据
		Warning warning = new Warning();
		// 设备编号
		warning.setDeviceCode(warningQueryForm.getDeviceCode());
		//设备名称
		warning.setDeviceName(device.getDeviceName());
		//设备类型对应告警类型
		warning.setWarningType(deviceType.getDeviceType());
		Integer warningStatus = warningQueryForm.getWarningStatus();
		if(warningStatus !=null){
			warning.setWarningStatus(warningQueryForm.getWarningStatus());
			if(warningStatus == 0){
				warning.setWarningContent("设备离线了");
			}else if(warningStatus == 1){
				warning.setWarningContent("设备上线了");
			}
		}
		
		warning.setDisposalId(deviceType.getDisposalId());
		warning.setSiteId(deviceType.getSiteId());
		//车辆
		warning.setCarCode(warningQueryForm.getCarCode());
		warning.setCreatedTime(new Date());
		warning.setMemo(warningQueryForm.getMemo());
		// 3.添加数据
		warningService.save(warning);
		// 4.添加完数据后，获取设备信息封装到异常信息中
		warning.setDevice(device);
		return ResponseResult.ok();
	}
	
	
	/***
	 * @Description: 根据不同条件分页查询所有的告警信息
	 * @author: 展昭
	 * @date: 2018年5月4日 上午11:23:31
	 */
	@RequestMapping(value = "queryWarnings.action", method = RequestMethod.POST)
	public ResponseResult queryWarnings(WarningQueryForm warningQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Warning> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("warning_time", "desc");

		// 2、判断页面是否存在传入query=true,如果是,则表示是要查询,否则直接返回所有数据

		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false);

		// 告警状态
		Integer status = warningQueryForm.getWarningStatus();
		queryHelper.addCondition(status, "warning_status=%d", false);
		
		// 工地id
		String siteId = warningQueryForm.getSiteId();
		queryHelper.addCondition(siteId, "site_id = %d", true);
		
		//处置场id
		String disposalId = warningQueryForm.getDisposalId();
		queryHelper.addCondition(disposalId, "disposal_id = %d", true);
		
		// 停车场id
		String carParkId = warningQueryForm.getCarParkId();
		queryHelper.addCondition(carParkId, "car_park_id = %d", true);
		
		// 车牌号
		String carCode = warningQueryForm.getCarCode();
		queryHelper.addCondition(carCode, "car_code = '%s'",false);
		
		Date start = warningQueryForm.getBeginDate();
		Date end = warningQueryForm.getEndDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(start != null){
			String time = sdf.format(start);
			queryHelper.addCondition(time,"warning_time > '%s'",false);
		}
		if(end != null){
			String time = sdf.format(end);
			queryHelper.addCondition(time, "warning_time < '%s'", false);
		}
		//时间起止
		String timeStartEnd = warningQueryForm.getTimeStartEnd();
		if(!StringUtils.isBlank(timeStartEnd)){
			String [] time = timeStartEnd.split(" - ");
			String startTime = time[0].replaceAll(" ", "");
			String endTime = time[1].replaceAll(" ", "");
			try {
				startTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(startTime));
				endTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(endTime));
			} catch (ParseException e) {
			}
			queryHelper.addCondition(startTime,"warning_time > '%s'",false)
			.addCondition(endTime, "warning_time < '%s'", false);
		}
		pageView = warningService.queryPageData(warningQueryForm.getPageNum(), warningQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);

		return ResponseResult.ok(pageView);
	}

	/**
	 * @Description:
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月4日  下午10:57:15
	 * @param: warningQueryForm
	 * @param: session
	 * @return: ResponseResult
	 */
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(WarningQueryForm warningQueryForm,String [] ids,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 根据条件查询数据
		QueryHelper queryHelper = new QueryHelper();
		String id = warningQueryForm.getId();
		String deviceId = warningQueryForm.getDeviceId();
		String disposalId = warningQueryForm.getDisposalId();
		String siteId = warningQueryForm.getSiteId();
		String carParkId = warningQueryForm.getCarParkId();
		String carCode = warningQueryForm.getCarCode();
		queryHelper.addCondition(id, "id = %d", true)
		.addCondition(deviceId, "device_id = %d",true)
		.addCondition(disposalId, "disposal_id = %d", true)
		.addCondition(siteId, "site_id = %d", true)
		.addCondition(carParkId, "car_park_id = %d", true)
		.addCondition(carCode, "car_code = '%s'", false);
		if(ids != null && ids.length > 0){
			StringBuilder sb = new StringBuilder("");
			for(String i : ids){
				sb.append(IdTypeHandler.decode(i)).append(",");
			}
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length()-1);
			}
			queryHelper.addCondition(sb.toString(), "id in (%s)", false);
		}
		//时间起止
		String timeStartEnd = warningQueryForm.getTimeStartEnd();
		if(!StringUtils.isBlank(timeStartEnd)){
			String [] time = timeStartEnd.split(" - ");
			String startTime = time[0].replaceAll(" ", "");
			String endTime = time[1].replaceAll(" ", "");
			try {
				startTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(startTime));
				endTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(endTime));
			} catch (ParseException e) {
			}
			queryHelper.addCondition(startTime,"warning_time > '%s'",false)
			.addCondition(endTime, "warning_time < '%s'", false);
		}
		List<Warning> list = warningService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		//生成表格数据
		WarningExcelQueryTemplate excelQueryTemplate = new WarningExcelQueryTemplate("true","true","true","true","true","true","true");
		List<Map<String,String>> data = warningService.createExcelData(excelQueryTemplate, list);
		String[] water = new String[] { 
		};
		// 生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"warning", data,propertiesService.getWindowsCreateWaterRootPath(),water);
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
