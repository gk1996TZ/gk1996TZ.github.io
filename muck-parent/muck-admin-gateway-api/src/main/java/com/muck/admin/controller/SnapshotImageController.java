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

import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.domain.SnapshotImage;
import com.muck.excelquerytemplate.SnapshotImageTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.request.SnapshotImageForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.service.SnapshotImageService;
import com.muck.utils.ExcelUtil;

/**
 * @Description:抓拍记录管理
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年4月27日 上午9:19:30
 */
@RestController("AdminSnapshotImageController")
@RequestMapping("/admin/snapshotImage/")
public class SnapshotImageController {

	@Autowired
	private SnapshotImageService snapshotImageService;

	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private ExcelOutputLogService excelOutputLogService;

	/**
	 * @Description: 查询抓拍图片
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 上午9:55:37
	 * @param: snapshotImageForm
	 * @return: ResponseResult
	 */
	@RequestMapping("querysnapshotImages.action")
	public ResponseResult snapshotImage(SnapshotImageForm snapshotImageForm) {
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<SnapshotImage> pageView = null;

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 抓拍用户
		String operator_name = snapshotImageForm.getOperatorName();
		// 根据区域查询抓拍图片
		String areaId = snapshotImageForm.getAreaId();
		// 根据工地查询抓拍图片
		String siteId = snapshotImageForm.getSiteId();
		// 根据处置场查询抓拍图片
		String disposalId = snapshotImageForm.getDisposalId();
		// 根据停车场查询抓拍图片
		String carParkId = snapshotImageForm.getCarParkId();
		// 抓拍起始时间
		String snapshotTimeStart = snapshotImageForm.getSnapshotTimeStart();
		// 抓拍终止时间
		String snapshotTimeEnd = snapshotImageForm.getSnapshotTimeEnd();
		
		if(StringUtils.isNotBlank(operator_name)){
			queryHelper.addCondition("'%"+operator_name+"%'", "operator_name like %s", false);
		}
		
		queryHelper.addCondition(areaId, "area_id = %d", true).addCondition(siteId, "site_id = %d", true)
				.addCondition(disposalId, "disposal_id = %d", true).addCondition(carParkId, "car_park_id = %d", true)
				.addCondition(snapshotTimeStart, "snapshot_time > '%s'", false)
				.addCondition(snapshotTimeEnd, "snapshot_time < '%s'", false).addCondition(1, "deleted=%d", false);
		pageView = snapshotImageService.queryPageData(snapshotImageForm.getPageNum(), snapshotImageForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}

	/**
	 * @Description: 删除抓拍记录
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年4月27日 上午10:23:22
	 * @param: id
	 *             传入一个抓拍记录的id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String imageId) {
		snapshotImageService.deleteById(imageId);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 根据id删除（真实删除）抓拍图片信息
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日 上午10:40:45
	 * @param: imageId
	 *             图片id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String imageId) {
		snapshotImageService.deleteByIdReal(imageId);
		return ResponseResult.ok();
	}

	/**
	 * @Description: 抓拍记录树（ 工地区域>工地 处置场区域>处置场 停车场区域>停车场 ）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月1日 下午1:12:59
	 * @return:ResponseResult 返回抓拍记录树
	 */
	@RequestMapping("querySnapshotImageTree.action")
	public ResponseResult querySnapshotImageTree() {
		return ResponseResult.ok();
	}

	/*
	 * 导出抓拍信息
	 */
	@RequestMapping(value = "exportExcelData.action", method = RequestMethod.POST)
	public ResponseResult exportExcelData(SnapshotImageForm snapshotImageForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}

		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 抓拍用户
		String operatorName = snapshotImageForm.getOperatorName();
		// 根据区域查询抓拍图片
		String areaId = snapshotImageForm.getAreaId();
		// 根据工地查询抓拍图片
		String siteId = snapshotImageForm.getSiteId();
		// 根据处置场查询抓拍图片
		String disposalId = snapshotImageForm.getDisposalId();
		// 根据停车场查询抓拍图片
		String carParkId = snapshotImageForm.getCarParkId();
		// 抓拍起始时间
		String snapshotTimeStart = snapshotImageForm.getSnapshotTimeStart();
		// 抓拍终止时间
		String snapshotTimeEnd = snapshotImageForm.getSnapshotTimeEnd();
		if (StringUtils.isNotBlank(areaId)) {
			queryHelper.addCondition(areaId, "area_id = %d", true);
		}
		if (StringUtils.isNotBlank(siteId)) {
			queryHelper.addCondition(siteId, "site_id = %d", true);
		}
		if (StringUtils.isNotBlank(disposalId)) {
			queryHelper.addCondition(disposalId, "disposal_id = %d", true);
		}
		if (StringUtils.isNotBlank(carParkId)) {
			queryHelper.addCondition(carParkId, "car_park_id = %d", true);
		}
		if (StringUtils.isNotBlank(operatorName)) {
			queryHelper.addCondition(operatorName, "operator_name='%s'", false);
		}
		if (StringUtils.isNotBlank(snapshotTimeStart)) {
			queryHelper.addCondition(snapshotTimeStart, "snapshot_time >= '%s'", false);
		}
		if (StringUtils.isNotBlank(snapshotTimeEnd)) {
			queryHelper.addCondition(snapshotTimeEnd, "snapshot_time <= '%s'", false).addCondition(1, "deleted=%d",
					false);
		}

		// 根据拼接的条件查询记录数
		List<SnapshotImage> snapshotImages = snapshotImageService.queryData(queryHelper.getWhereSQL(),
				queryHelper.getWhereParams());

		// 生成Excel表格，并返回下载路径
		if (snapshotImages != null && snapshotImages.size() > 0) {

			// 生成表格数据
			List<Map<String, String>> excelData = snapshotImageService.createExcelData(new SnapshotImageTemplate(),
					snapshotImages);
			// 生成水印
			String[] water = new String[] {
			};
			//生成导出路径
			String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),propertiesService.getWindowsRootPath(), "snapshotImage",
					excelData, propertiesService.getWindowsCreateWaterRootPath(), water);
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
		}else {
			throw new BizException(StatusCode.NOT_FOUND);
		}

	}

}
