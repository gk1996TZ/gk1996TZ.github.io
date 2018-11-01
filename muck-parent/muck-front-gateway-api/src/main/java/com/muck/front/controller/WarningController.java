package com.muck.front.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.ChannelInfo;
import com.muck.domain.Device;
import com.muck.domain.Disposal;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.domain.Site;
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
import com.muck.service.ChannelService;
import com.muck.service.DeviceService;
import com.muck.service.DisposalService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.service.SiteService;
import com.muck.service.WarningService;
import com.muck.utils.ExcelUtil;

/***
 * 告警Controller
 */
@RestController("FrontWarningController")
@RequestMapping("/front/warning/")
public class WarningController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChannelService channelService;
	@Autowired
	private WarningService warningService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DisposalService disposalService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	/*
	 * 点击告警单项进行单项信息查询
	 */
	@RequestMapping(value = "getChannelInfo.action", method = RequestMethod.GET)
	public ResponseResult getChannelInfo(String channelCode) {
		if (StringUtils.isBlank(channelCode)) {
			logger.error("调用getChannelInfo接口时channelCode为空");
		}
		try {
			ChannelInfo info= channelService.getChannelInfo(channelCode);
			return ResponseResult.ok(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}

	/**
	 * @Description: 根据id查询告警信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月28日  下午5:21:31
	 * @param: id 传入一个告警id
	 * @return:ResponseResult 返回告警信息
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String id){
		Warning warning = warningService.queryById(id);
		if(warning == null){
			logger.error("调用waring模块中的queryById接口时queryById为空");
		}
		return ResponseResult.ok(warning);
	}
	/**
	 * @Description: 添加设备预警信息
	 * @version: v1.0.0
	 * @date: 2018年5月9日 下午6:26:21
	 * @param: warningQueryForm
	 *             传入预警信息请求参数
	 * @return: ResponseResult 返回是否添加成功
	 */
	@RequestMapping(value = "save.action", method = RequestMethod.POST)
	// 1.先通过通道编号获取设备类别信息，如果设备不存在，则返回数据不存在的信息
	public ResponseResult save(WarningQueryForm warningQueryForm) {
		try {
			channelService.updateChannelStatusByChannelCode(warningQueryForm.getChannelCode(), warningQueryForm.getWarningStatus());
		} catch (Exception e) {
			logger.error("修改设备状态失败：通道号为"+warningQueryForm.getChannelCode()+";状态为："+warningQueryForm.getWarningStatus(), e);
		}
		return ResponseResult.ok();
	}

	/**
	 * 添加警告
	 * */
	private ResponseResult waringSave(WarningQueryForm warningQueryForm) {
		SimpleDeviceTypeInfoResponse deviceType = deviceService.queryDeviceTypeByChannelCode(warningQueryForm.getChannelCode());
		if(deviceType == null){
			Warning warning = new Warning();
			warning.setWarningStatus(warningQueryForm.getWarningStatus());
			return ResponseResult.ok(warning);
			//throw new BizException(StatusCode.NOT_FOUND);
		}
		Device device = deviceService.queryById(deviceType.getDeviceId());
		
		// 设备名称
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String warningName= sdf.format(date) + " " + device.getDeviceCode() + "号设备异常信息";
		Warning warning = warningService.queryByWarningName(warningName);
		if(warning != null){
			Integer warningStatus = warningQueryForm.getWarningStatus();
			if(warningStatus !=null){
				warning.setWarningStatus(warningQueryForm.getWarningStatus());
				if(warningStatus == 2){
					warning.setWarningContent("设备离线了");
					warning.setWarningTime(date);
				}else if(warningStatus == 1){
					warning.setWarningContent("设备上线了");
					warning.setWarningTime(date);
				}
			}
			warningService.editById(warning);
			return ResponseResult.ok(warning);
		}
		// 2.组装需要添加的数据
		warning = new Warning();
		warning.setWarningName(warningName);
		// 设备编号
		warning.setDeviceCode(warningQueryForm.getDeviceCode());
		//设备名称
		warning.setDeviceName(device.getDeviceName());
		//设备类型对应告警类型
		warning.setWarningType(deviceType.getDeviceType());
		//告警时间
		warning.setWarningTime(date);
		Integer warningStatus = warningQueryForm.getWarningStatus();
		if(warningStatus !=null){
			warning.setWarningStatus(warningQueryForm.getWarningStatus());
			if(warningStatus == 2){
				warning.setWarningContent("设备离线了");
			}else if(warningStatus == 1){
				warning.setWarningContent("设备上线了");
			}
		}
		String disposalId = deviceType.getDisposalId();
		String siteId = deviceType.getSiteId();
		
		if(!StringUtils.isBlank(disposalId)){
			warning.setDisposalId(disposalId);
			Disposal disposal = disposalService.queryById(disposalId);
			if(disposal != null){
				//告警地址
				warning.setWarningAddress(disposal.getAreaName());
			}
		}
		if(!StringUtils.isBlank(siteId)){
			warning.setSiteId(siteId);
			Site site = siteService.queryById(siteId);
			if(site != null){
				warning.setWarningAddress(site.getSiteAddress());
			}
		}
		//车辆
		warning.setCarCode(warningQueryForm.getCarCode());
		warning.setCreatedTime(new Date());
		warning.setMemo(warningQueryForm.getMemo());
		// 3.添加数据
		warningService.save(warning);
		// 4.添加完数据后，获取设备信息封装到异常信息中
		warning.setDevice(device);
		return ResponseResult.ok(warning);
	}
	
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String id){
		try {
			warningService.deleteById(id);
			return ResponseResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.build(500, "删除失败");
	}
	
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String id){
		
		try {
			warningService.deleteByIdReal(id);
			return ResponseResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.build(500, "删除失败");
	}
	
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
			if(warningStatus == 2){
				warning.setWarningContent("设备离线了");
			}else if(warningStatus == 1){
				warning.setWarningContent("设备上线了");
			}
		}
		
		String disposalId = deviceType.getDisposalId();
		String siteId = deviceType.getSiteId();
		
		if(!StringUtils.isBlank(disposalId)){
			warning.setDisposalId(disposalId);
			Disposal disposal = disposalService.queryById(disposalId);
			if(disposal != null){
				//告警地址
				warning.setWarningAddress(disposal.getAreaName());
			}
		}
		if(!StringUtils.isBlank(siteId)){
			warning.setSiteId(siteId);
			Site site = siteService.queryById(siteId);
			if(site != null){
				warning.setWarningAddress(site.getSiteAddress());
			}
		}
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
	
	
	/**
	 * @Description: 查询异常分页信息
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月26日  上午2:35:23
	 * @Param:warningQueryForm 传入查询异常数据的条件 
	 * 			 warningQueryForm.warningType 告警信息类别 1.工地2.车辆3.处置场
	 * @Return:ResponseResult 返回分页数据
	 */
	@RequestMapping("queryWarnings.action")
	public ResponseResult queryWarnings(WarningQueryForm warningQueryForm) {

		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Warning> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("w.created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 异常类别
		Integer warningType = warningQueryForm.getWarningType();
		queryHelper.addCondition(warningType, "w.warning_type=%d", false);
		//告警状态
		queryHelper.addCondition(2, "w.warning_status=%d", false).addCondition(1, "w.deleted=%d", false);
		//异常名称
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String strDate=sdf.format(date);
		
        Date start = warningQueryForm.getBeginDate();
		Date end = warningQueryForm.getEndDate();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(start != null){
			String time = ss.format(start);
			queryHelper.addCondition(time,"warning_time > '%s'",false);
		}
		if(end != null){
			String time = ss.format(end);
			queryHelper.addCondition(time, "warning_time < '%s'", false);
		}
		queryHelper.addCondition("%"+strDate+"%", "w.warning_name like '%s'", false);

		pageView = warningService.queryPageData(warningQueryForm.getPageNum(), warningQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby,warningType);
		return ResponseResult.ok(pageView);
	}
	
	
	/**
	 * @Description: 查询异常总数
	 * @Version:v1.0.0
	 * @Author:朱俊亮
	 * @Date:2018年6月26日  上午2:40:09
	 * @Return:ResponseResult 返回异常总数
	 */
	@RequestMapping("queryWarningCount.action")
	public ResponseResult queryWarningCount(){
		return ResponseResult.ok(warningService.queryWarningCount());
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
