package com.muck.admin.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.Area;
import com.muck.domain.Company;
import com.muck.domain.Manager;
import com.muck.domain.Site;
import com.muck.domain.SiteProjectInfo;
import com.muck.exception.base.BizException;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.query.SiteProjectInfoQueryForm;
import com.muck.request.SiteForm;
import com.muck.request.SiteProjectInfoForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.service.CompanyService;
import com.muck.service.DeviceService;
import com.muck.service.ExcelOutputLogService;
import com.muck.service.PropertiesService;
import com.muck.service.SiteProjectInfoService;
import com.muck.service.SiteService;
import com.muck.utils.UploadUtils;

@RestController("AdminSiteProjectInfo")
@RequestMapping("/admin/siteProjectInfo/")
public class SiteProjectInfoController extends BaseController {

	@Autowired
	private AreaService areaService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PropertiesService propertiesService;//

	@Autowired
	private ExcelOutputLogService excelOutputLogServic;

	@Autowired
	private SiteProjectInfoService siteProjectInfoService;

	/**
	 * 导入项目详情信息 导入的是列表
	 * 
	 */

	@RequestMapping(value = "importProjectInfoExcelData.action", method = RequestMethod.POST)
	@Logger(operatorModel = "工地管理", operatorDesc = "导入项目详情")
	public ResponseResult importProjectInfoExcelData(HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		if (manager == null) {
			throw new BizException(StatusCode.MANAGER_LOGIN_RESTART);
		}
		// 第一步： 将表格文件保存到本地指定的目录下
		String path = UploadUtils.uploadFile(propertiesService.getWindowsRootPath(), "projectInfo", request);
		List<SiteProjectInfo> projectInfos = null;
		try {
			projectInfos = siteProjectInfoService.readExcel(path);
			// 批量插入
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(StatusCode.IMPORT_FAILE);
		}

		siteProjectInfoService.saveAll(projectInfos);

		return ResponseResult.ok();
	}

	/**
	 * 添加项目详情信息
	 */
	public ResponseResult addProjectInfo(SiteProjectInfoForm siteProjectInfoForm) {

		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		BeanUtils.copyProperties(siteProjectInfoForm, siteProjectInfo);
		siteProjectInfoService.save(siteProjectInfo);
		return ResponseResult.ok(siteProjectInfo.getIdRaw());
	}

	/**
	 * 根据id删除工地
	 * 逻辑删除
	 */
	@RequestMapping(value = "deleteById.action", method = RequestMethod.GET)
	public ResponseResult deleteById(String projectInfoId) {

		siteProjectInfoService.deleteById(projectInfoId);

		return ResponseResult.ok();
	}

	/**
	 * 根据id查询工地
	 */
	@RequestMapping(value = "queryById.action", method = RequestMethod.GET)
	public ResponseResult queryById(String projectInfoId) {

		SiteProjectInfo siteProjectInfo = siteProjectInfoService.queryById(projectInfoId);
		if (siteProjectInfo == null) {
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(siteProjectInfo);
	}

	/***
	 * 根据id修改项目信息
	 */
	@RequestMapping(value = "editById.action", method = RequestMethod.POST)
	public ResponseResult editById(SiteProjectInfoForm siteProjectInfoForm) {
		String projectInfoId = siteProjectInfoForm.getId(); // 工地id
		if (StringUtils.isBlank(projectInfoId)) {
			throw new BizException(StatusCode.SITE_ID_BLANK);
		}

		// 第一步、组装我们需要的实体对象的数据(设置基本信息)
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		BeanUtils.copyProperties(siteProjectInfoForm, siteProjectInfo);
		siteProjectInfoService.editById(siteProjectInfo);

		return ResponseResult.ok();
	}

	/**
	 * 工地项目列表 更新项目进度
	 */
	@RequestMapping(value = "editSiteProjectProcess.action", method = RequestMethod.POST)
	public ResponseResult editSiteProjectProcessNew(String siteProjectInfoId, String siteProcessMemo) {

		if (StringUtils.isBlank(siteProjectInfoId)) {
			throw new BizException(StatusCode.SITE_PROJECT_INFO_ID_BLANK);
		}

		if (StringUtils.isBlank(siteProcessMemo)) {
			throw new BizException(StatusCode.SITE_PROCESS_MEMO_BLANK);
		}
		SiteProjectInfo siteProjectInfo = new SiteProjectInfo();
		siteProjectInfo.setId(siteProjectInfoId);
		siteProjectInfo.setProjectInfoMemo(siteProcessMemo);

		// 更新数据库
		siteProjectInfoService.editById(siteProjectInfo);

		return ResponseResult.ok();
	}

	/**
	 * 项目详情列表 带分页
	 */
	@RequestMapping(value = "queryPageDatas.action", method = RequestMethod.POST)
	public ResponseResult queryPageDatas(SiteProjectInfoQueryForm siteProjectInfoQueryForm) {
		// 查询实体
		QueryHelper queryHelper = new QueryHelper();

		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		queryHelper.addCondition(1, "deleted=%d", false);
		PageView<SiteProjectInfo> pageView = siteProjectInfoService.queryPageData(siteProjectInfoQueryForm.getPageNum(),
				siteProjectInfoQueryForm.getPageSize(), queryHelper.getWhereSQL(), queryHelper.getWhereParams(),
				orderby);

		return ResponseResult.ok(pageView);
	}

	/**
	 * 导出单个或多个项目
	 */
//	@RequestMapping(value = "exportExcelData.action", method = RequestMethod.GET)
//	public ResponseResult exportExcelData(String siteProjectInfoId) {
//		List<SiteProjectInfo> siteProjectInfos = new ArrayList<SiteProjectInfo>();
//		// 传入的id不为空说明单个导出
//		if (StringUtils.isNotBlank(siteProjectInfoId)) {
//			SiteProjectInfo siteProjectInfo = siteProjectInfoService.queryById(siteProjectInfoId);
//			if (siteProjectInfo != null) {
//				siteProjectInfos.add(siteProjectInfo);
//			}
//		} else {
//			// 说明导出全部
//			QueryHelper queryHelper = new QueryHelper();
//			queryHelper.addCondition(1, "deleted=%d", false);
//
//			List<SiteProjectInfo> siteProjectInfos2 = siteProjectInfoService.queryData(queryHelper.getWhereSQL(),
//					queryHelper.getWhereParams());
//			siteProjectInfos.addAll(siteProjectInfos2);
//		}
//		
//		String path="";
//		try {
//			 path=siteProjectInfoService.exportExcelData2(siteProjectInfos);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BizException(StatusCode.EXPORT_FAILE);
//		}
//
//		return ResponseResult.ok(path);
//	}
	/**
	 * 导出单个或多个项目
	 * 单出的是列表数据
	 */
	@RequestMapping(value = "exportListExcelData.action", method = RequestMethod.GET)
	public ResponseResult exportListExcelData(String siteProjectInfoId){
		
		
		List<SiteProjectInfo> siteProjectInfos = new ArrayList<SiteProjectInfo>();
		// 传入的id不为空说明单个导出
		if (StringUtils.isNotBlank(siteProjectInfoId)) {
			SiteProjectInfo siteProjectInfo = siteProjectInfoService.queryById(siteProjectInfoId);
			if (siteProjectInfo != null) {
				siteProjectInfos.add(siteProjectInfo);
			}
		} else {
			// 说明导出全部
			QueryHelper queryHelper = new QueryHelper();
			queryHelper.addCondition(1, "deleted=%d", false);

			List<SiteProjectInfo> siteProjectInfos2 = siteProjectInfoService.queryData(queryHelper.getWhereSQL(),
					queryHelper.getWhereParams());
			siteProjectInfos.addAll(siteProjectInfos2);
		}
		String path="";
		try {
			 path=siteProjectInfoService.exportExcelDatas(siteProjectInfos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(StatusCode.EXPORT_FAILE);
		}

		return ResponseResult.ok(path);
	}
	
}
