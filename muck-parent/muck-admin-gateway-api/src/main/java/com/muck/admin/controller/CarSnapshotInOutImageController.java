package com.muck.admin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.CarSnapshotInOutImage;
import com.muck.domain.ExcelOutputLog;
import com.muck.domain.Manager;
import com.muck.excelquerytemplate.CarSnapshotImageTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.request.SnapshotImageForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CarSnapshotInOutImageService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelUtil;

@RestController("AdminCarSnapshotInOutInageController")
@RequestMapping(value = "/admin/carSnapshotInOutImageController/")
public class CarSnapshotInOutImageController extends BaseController {

	@Autowired
	private CarSnapshotInOutImageService carSnapshotInOutImageService;

	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private ExcelOutputLogService excelOutputLogService;
	
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "getCarSnapshotInOutInfo.action", method = RequestMethod.POST)
	public ResponseResult getCarSnapshotInOutInfo(SnapshotImageForm snapshotImageForm) {
		// 0、初始化: 创建PageView对象,这个对象就是给前端用户的所有数据
		PageView<CarSnapshotInOutImage> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("snapshot_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据工地id查询
		// 根据处置场id
		String siteId = snapshotImageForm.getSiteId();
		String disposalId = snapshotImageForm.getDisposalId();
		// 车牌号
		String carCode = snapshotImageForm.getCarCode();
		// 停车场id
		String carParkId = snapshotImageForm.getCarParkId();
		// 抓拍起始时间
		String snapshotTimeStart = snapshotImageForm.getSnapshotTimeStart();
		// 抓拍终止时间
		String snapshotTimeEnd = snapshotImageForm.getSnapshotTimeEnd();
		// 企业id
		String companyId = snapshotImageForm.getCompanyId();
		// 区域id
		String areaId = snapshotImageForm.getAreaId();
		// 抓拍图片
		String snapshotType = snapshotImageForm.getSnapshotType();
		// 创建时间
		String createdTime = snapshotImageForm.getCreatedTime();
		if (StringUtils.isNotBlank(siteId)) {
			queryHelper.addCondition(siteId, "site_id = %d", true);
		}
		if (StringUtils.isNotBlank(disposalId)) {
			queryHelper.addCondition(disposalId, "disposal_id=%d", true);
		}
		if (StringUtils.isNotBlank(companyId)) {
			queryHelper.addCondition(companyId, "company_id=%d", true);
		}
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition(carCode, "car_code='%s'", false);
		}
		if (StringUtils.isNotBlank(carParkId)) {
			queryHelper.addCondition(carParkId, "car_park_id= %d",true);
		}
		if(StringUtils.isNotBlank(areaId)){
			String areaIds = areaService.querySubAreaIdsByParentId(areaId);
			queryHelper.addCondition(areaIds, " area_id in (%s)", false);
		}
		if(StringUtils.isNotBlank(snapshotType)){
			queryHelper.addCondition(snapshotType, " snapshot_type = '%s' ", false);
		}
		if (StringUtils.isNotBlank(snapshotTimeStart)) {
			queryHelper.addCondition(snapshotTimeStart, "snapshot_time>='%s'", false);
		}
		if (StringUtils.isNotBlank(snapshotTimeEnd)) {
			queryHelper.addCondition(snapshotTimeEnd, "snapshot_time<='%s'", false).addCondition(1, "deleted=%d",
					false);
		}

		if (StringUtils.isNotBlank(createdTime)){
			queryHelper.addCondition(createdTime, "created_time >= '%s'", false);
		}
		pageView = carSnapshotInOutImageService.queryPageData(snapshotImageForm.getPageNum(),
				snapshotImageForm.getPageSize(), queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);

		/*List<CarSnapshotInOutImage> resultList = pageView.getDatas();
		Set<CarSnapshotInOutImage> resultSet = new TreeSet<CarSnapshotInOutImage>(resultList);
		pageView.setDatas(new ArrayList<>(resultSet));
		return ResponseResult.ok(getPageView(pageView,snapshotImageForm,queryHelper,orderby));*/
		return ResponseResult.ok(pageView);
	}

	private PageView<CarSnapshotInOutImage> getPageView(PageView<CarSnapshotInOutImage> pageView,SnapshotImageForm snapshotImageForm,QueryHelper queryHelper,LinkedHashMap<String, String> orderby){
		if(pageView != null){
			List<CarSnapshotInOutImage> listCarSnapshotInOutImage = pageView.getDatas();
			if(listCarSnapshotInOutImage != null && listCarSnapshotInOutImage.size() < snapshotImageForm.getPageSize()){
				//获取下一页的数据
				snapshotImageForm.setPageNum(snapshotImageForm.getPageNum() + 1);
				PageView<CarSnapshotInOutImage> pageViewTemp = carSnapshotInOutImageService.queryPageData(snapshotImageForm.getPageNum(),
						snapshotImageForm.getPageSize(), queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
				//对下一页数据进行去重复操作
				List<CarSnapshotInOutImage> resultList = pageViewTemp.getDatas();
				Set<CarSnapshotInOutImage> resultSet = new TreeSet<CarSnapshotInOutImage>(resultList);
				List<CarSnapshotInOutImage> listTemp = new ArrayList<>(resultSet);
				pageViewTemp.setDatas(listTemp);
				//将下一页的去重复后的追加到传入的pageView中
				pageView.getDatas().addAll(listTemp);
				//如果添加完成后依然小于单个页面条数
				if(pageView.getDatas().size() < snapshotImageForm.getPageSize()){
					//递归调用
					pageView = getPageView(pageView,snapshotImageForm,queryHelper,orderby);
				}else if (pageView.getDatas().size() > snapshotImageForm.getPageSize()){
					pageView.setDatas(pageView.getDatas().subList(0,Integer.parseInt(snapshotImageForm.getPageSize().toString())));
					pageView = getPageView(pageView,snapshotImageForm,queryHelper,orderby);
				}
			}
		}
		System.out.println(pageView.getDatas().size());
		return pageView;
	}
	/*
	 * 导出车辆抓拍信息
	 */
	@RequestMapping(value = "exportCarSnapShotInfo.action")
	public ResponseResult exportCarSnapShotInfo(SnapshotImageForm snapshotImageForm, HttpSession session) {
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		// 根据工地id查询
		String siteId = snapshotImageForm.getSiteId();
		// 根据处置场id
		String disposalId = snapshotImageForm.getDisposalId();
		// 车牌号
		String carCode = snapshotImageForm.getCarCode();
		// 停车场id
		String carParkId = snapshotImageForm.getCarParkId();
		// 抓拍起始时间
		String snapshotTimeStart = snapshotImageForm.getSnapshotTimeStart();
		// 抓拍终止时间
		String snapshotTimeEnd = snapshotImageForm.getSnapshotTimeEnd();
		if (StringUtils.isNotBlank(siteId)) {
			queryHelper.addCondition(siteId, "site_id = %d", true);
		}
		if (StringUtils.isNotBlank(disposalId)) {
			queryHelper.addCondition(disposalId, "disposal_id=%d", true);
		}
		if (StringUtils.isNotBlank(carCode)) {
			queryHelper.addCondition(carCode, "car_code='%s'", false);
		}
		if (StringUtils.isBlank(carParkId)) {
			queryHelper.addCondition(carParkId, "car_park_id= %d",true);
		}
		if (StringUtils.isNotBlank(snapshotTimeStart)) {
			queryHelper.addCondition(snapshotTimeStart, "snapshot_time>='%s'", false);
		}
		if (StringUtils.isNotBlank(snapshotTimeEnd)) {
			queryHelper.addCondition(snapshotTimeEnd, "snapshot_time<='%s'", false);
		}
		// 根据条件查询所有的记录
		List<CarSnapshotInOutImage> list = carSnapshotInOutImageService.queryData(queryHelper.getWhereSQL(),
				queryHelper.getWhereParams());
		List<CarSnapshotInOutImage> listTemp = new ArrayList<CarSnapshotInOutImage>(new HashSet<CarSnapshotInOutImage>(list));
		Collections.sort(listTemp);
		if (!list.isEmpty()) {
			// 生成表格信息
			List<Map<String, String>> excelData = carSnapshotInOutImageService
					.createExcelData(new CarSnapshotImageTemplate(), listTemp);
			// 生成Excel表格，并返回下载路径
			String outputExcelPath = ExcelUtil.createExcelPOI(propertiesService.getCurrentServerForExcel(),
					propertiesService.getWindowsRootPath(), "carSnapshotInOutImage", excelData,
					propertiesService.getWindowsCreateWaterRootPath(), null);
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
	/**
	 * @Description: 默认获取最新的十条抓拍记录
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年7月29日 下午5:18:38
	 * @Return: ResponseResult 返回最新的10条抓拍记录
	 */
	@RequestMapping("getTopTen.action")
	public ResponseResult getTopTen(){
		
		return ResponseResult.ok(carSnapshotInOutImageService.getTopTen());
	}

}
