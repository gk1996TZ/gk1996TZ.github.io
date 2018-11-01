package com.muck.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Log;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.LogExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.LogQueryForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.LogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;

/**
* @Description: 日志Controller
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月2日 下午2:28:10
 */
@RestController("AdminLogController")
@RequestMapping("/admin/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	/**
	* @Description: 查询所有的日志
	* @author: 展昭
	* @date: 2018年5月2日 下午2:37:39
	 */
	@RequestMapping(value = "queryAll.action" , method = RequestMethod.GET)
	public ResponseResult queryAll(){
		List<Log> logs = logService.queryData();
		
		return ResponseResult.ok(logs);
	}
	
	/***
	* @Description: 查询最近若干月内的日志
	* @author: 展昭
	* @date: 2018年5月3日 上午10:56:17
	 */
	@RequestMapping(value = "queryNearestLogs.action" , method = RequestMethod.POST)
	public ResponseResult queryNearestLogs(LogQueryForm logQueryForm){
		
		QueryHelper helper = new QueryHelper();
		String userAccount = logQueryForm.getUserAccount();
		if(StringUtils.isNotBlank(userAccount)){
			helper.addCondition("'%" + userAccount + "%'", "operator_account like %s", false);
		}
		
		//PageView<Log> pageView = logService.queryNearestLogs(logQueryForm.getPageNum(),logQueryForm.getPageSize(),logQueryForm.getMonth(),);
		
		PageView<Log> pageView = logService.queryPageData(logQueryForm.getPageNum(), logQueryForm.getPageSize(), helper.getWhereSQL(), helper.getWhereParams());
		
		return ResponseResult.ok(pageView);
	}
	/**
	 * @Description:  条件查询操作日志的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月19日  下午4:54:39
	 * @param: logQueryForm 传入条件查询日志的参数
	 * @return:ResponseResult 返回含有查询到的操作日志的结果集对象
	 */
	@RequestMapping("log.action")
	public ResponseResult log(LogQueryForm logQueryForm){
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<Log> pageView = null;
		
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition("", "area_id=%d",false);
		String userAccount = logQueryForm.getUserAccount();
		if(StringUtils.isNotBlank(userAccount)){
			queryHelper.addCondition("'%" + userAccount + "%'", "operator_account like %s", false);
		}
		//时间起止
		String timeStartEnd = logQueryForm.getTimeStartEnd();
		if(!StringUtils.isBlank(timeStartEnd)){
			String [] time = timeStartEnd.split(" - ");
			String startTime = time[0].replaceAll(" ", "");
			String endTime = time[1].replaceAll(" ", "");
			try {
				startTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(startTime));
				endTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(endTime));
			} catch (ParseException e) {
			}
			queryHelper.addCondition(startTime,"operator_time >= '%s'",false)
			.addCondition(endTime, "operator_time <= '%s'", false);
		}
		pageView = logService.queryPageData(logQueryForm.getPageNum(),logQueryForm.getPageSize(),queryHelper.getWhereSQL(),queryHelper.getWhereParams());
		return ResponseResult.ok(pageView);
	}
	/**
	 * @Description: 导出日志信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月4日  下午12:55:51
	 * @param: logQueryForm 传入查询日志数据的条件
	 * @param: session 传入一个HttpSession
	 * @return:ResponseResult 返回含有Excel
	 */
	@RequestMapping("exportExcel.action")
	public ResponseResult exportExcel(LogQueryForm logQueryForm,HttpSession session){
		Manager manager = (Manager) session.getAttribute("manager");
		if(manager == null){
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		List<Log> list = new ArrayList<Log>();
		//查询需要导出的数据
		if(!StringUtils.isBlank(logQueryForm.getId())){
			Log log = logService.queryById(logQueryForm.getId());
			if(log == null){
				throw new BizException(StatusCode.NOT_FOUND);
			}
			list.add(log);
		}else{
			//查询出所有的日志记录
			List<Log> listLog = logService.queryNearestLogs();
			if(listLog == null | listLog.size() ==0){
				throw new BizException(StatusCode.NOT_FOUND);
			}
			String userAccount = logQueryForm.getUserAccount();
			//时间起止
			String timeStartEnd = logQueryForm.getTimeStartEnd();
			if(StringUtils.isBlank(userAccount) && StringUtils.isBlank(timeStartEnd)){
				list = listLog;
			}
			if(!StringUtils.isBlank(userAccount) && StringUtils.isBlank(timeStartEnd)){
				for(Log log : listLog){
					if(logQueryForm.getUserAccount().equals(log.getOperatorAccount())){
						list.add(log);
					}
				}
			}
			if(!StringUtils.isBlank(timeStartEnd) && StringUtils.isBlank(userAccount)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String [] time = timeStartEnd.split(" - ");
				try {
					Date start = sdf.parse(time[0]);
					Date end = sdf.parse(time[1]);
					for(Log log : listLog){
						Date logDate = log.getOperatorTime();
						if(logDate != null){
							if(logDate.after(start) && logDate.before(end)){
								list.add(log);
							}
						}
					}
				} catch (ParseException e) {
				}
			}
			if(!StringUtils.isBlank(timeStartEnd) && !StringUtils.isBlank(userAccount)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String [] time = timeStartEnd.split(" - ");
				try {
					Date start = sdf.parse(time[0]);
					Date end = sdf.parse(time[1]);
					for(Log log : listLog){
						Date logDate = log.getOperatorTime();
						if(logDate != null){
							if(logDate.after(start) && logDate.before(end)){
								if(!StringUtils.isBlank(userAccount)){
									if(logQueryForm.getUserAccount().equals(log.getOperatorAccount())){
										list.add(log);
									}
								}else {
									list.add(log);
								}
							}
						}
					}
				} catch (ParseException e) {
				}
			}
		} 
		//生成表格数据
		LogExcelQueryTemplate excelQueryTemplate = new LogExcelQueryTemplate(
				"true","true","true","true","true","true");
		List<Map<String,String>> data =logService.createExcelData(excelQueryTemplate, list);
		String[] water = new String[] {
		};
		// 生成Excel表格，并返回下载路径
		String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(),"log", data,propertiesService.getWindowsCreateWaterRootPath(),water);
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
