package com.muck.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.SiteProjectInfo;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.SiteProjectInfoMapper;
import com.muck.response.StatusCode;
import com.muck.service.PropertiesService;
import com.muck.service.SiteProjectInfoService;

/**
 * 工地项目详情service实现
 */
@Service
public class SiteProjectInfoServiceImpl extends BaseServiceImpl<SiteProjectInfo> implements SiteProjectInfoService {

	@Autowired
	private SiteProjectInfoMapper siteProjectInfoMapper;

	@Autowired
	private PropertiesService propertiesService;

	@Override
	protected BaseMapper<SiteProjectInfo> getMapper() {
		return siteProjectInfoMapper;
	}

	@Override
	protected String getFields() {
		return " * ";
	}

	/**
	 * 读取项目详情并返回列表
	 * 
	 */
	public List<SiteProjectInfo> readExcel(String path) throws Exception {

		if (path != null) {
			File file = new File(path);
			if (!file.exists() || !file.isFile()) {
				throw new BizException(StatusCode.UNKNOWN);
			}

			// 获取文件类型
			String fileType = path.substring(path.lastIndexOf(".") + 1);

			// 创建工作簿
			Workbook workbook = null;

			InputStream is = new FileInputStream(file);

			if (fileType.equals("xls")) {
				workbook = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				workbook = new XSSFWorkbook(is);
			}

			Iterator<Sheet> sheets = workbook.iterator();

			// 总数据的集合
			List<SiteProjectInfo> projectInfos = new ArrayList<SiteProjectInfo>();

			// 遍历所有的sheet
			while (sheets.hasNext()) {
				Sheet sheet = sheets.next();

				if (sheet != null) {

					SiteProjectInfo siteProjectInfo = null;

					Row row0 = sheet.getRow(0);

					// 项目所属区域
					if (row0 == null) {
						// 如果为空,则不执行下面操作
						continue;
					}
					// 实例化对象
					siteProjectInfo = new SiteProjectInfo();
					Cell cell0 = row0.getCell(0);
					String str = cell0.getStringCellValue();

					// 截取表头获取所属区域
					if (StringUtils.isNotBlank(str)) {
						String areaName = str.substring(str.indexOf("表") + 1);
						siteProjectInfo.setAreaName(areaName);
					}

					Row row1 = sheet.getRow(1);

					// 项目名称
					Cell cell1 = row1.getCell(1);
					String projectName = cell1.getStringCellValue();
					if (StringUtils.isBlank(projectName)) {
						continue;
					}
					siteProjectInfo.setProjectName(projectName.trim());

					Row row2 = sheet.getRow(2);

					// 工地设备注册码
					Cell cell2 = row2.getCell(1);
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					String siteDeviceRegisterCode = cell2.getStringCellValue();
					siteProjectInfo.setSiteDeviceRegisterCode(siteDeviceRegisterCode);

					Row row3 = sheet.getRow(3);

					// 项目地址
					Cell cell3 = row3.getCell(1);
					String projectAddress = cell3.getStringCellValue();
					siteProjectInfo.setProjectAddress(projectAddress);

					Row row5 = sheet.getRow(5);

					// 建设规模
					Cell cell5 = row5.getCell(1);
					String projectScale = cell5.getStringCellValue();
					siteProjectInfo.setProjectScale(projectScale);

					// 施工工期
					Cell cell6 = row5.getCell(5);
					cell6.setCellType(Cell.CELL_TYPE_STRING);
					String projectPeriod = cell6.getStringCellValue();
					siteProjectInfo.setProjectPeriod(projectPeriod);

					Row row6 = sheet.getRow(6);

					// 建设单位
					Cell cell7 = row6.getCell(1);
					String buildUnit = cell7.getStringCellValue();
					siteProjectInfo.setBuildUnit(buildUnit);

					// 建设单位负责人
					Cell cell8 = row6.getCell(3);
					String buildUnitManager = cell8.getStringCellValue();
					siteProjectInfo.setBuildUnitManager(buildUnitManager);

					// 建设单位负责人联系电话
					Cell cell9 = row6.getCell(5);
					cell9.setCellType(Cell.CELL_TYPE_STRING);
					String buildUnitManagerPhone = cell9.getStringCellValue();
					siteProjectInfo.setBuildUnitManagerPhone(buildUnitManagerPhone);

					Row row7 = sheet.getRow(7);

					// 施工单位
					Cell cell10 = row7.getCell(1);
					String constructionUnit = cell10.getStringCellValue();
					siteProjectInfo.setConstructionUnit(constructionUnit);

					// 施工单位负责人
					Cell cell11 = row7.getCell(3);
					String constructionUnitManager = cell11.getStringCellValue();
					siteProjectInfo.setConstructionUnitManager(constructionUnitManager);

					// 施工单位负责任人联系方式
					Cell cell12 = row7.getCell(5);
					cell12.setCellType(Cell.CELL_TYPE_STRING);
					String constructionUnitManagerPhone = cell12.getStringCellValue();
					siteProjectInfo.setConstructionUnitManagerPhone(constructionUnitManagerPhone);

					Row row8 = sheet.getRow(8);

					// 监理单位
					Cell cell13 = row8.getCell(1);
					String supervisionUnit = cell13.getStringCellValue();
					siteProjectInfo.setSupervisionUnit(supervisionUnit);

					// 监理单位负责人
					Cell cell14 = row8.getCell(3);
					String supervisionUnitManager = cell14.getStringCellValue();
					siteProjectInfo.setSupervisionUnitManager(supervisionUnitManager);

					// 监理单位负责人联系方式
					Cell cell15 = row8.getCell(5);
					cell15.setCellType(Cell.CELL_TYPE_STRING);
					String supervisionUnitManagerPhone = cell15.getStringCellValue();
					siteProjectInfo.setSupervisionUnitManagerPhone(supervisionUnitManagerPhone);

					Row row9 = sheet.getRow(9);

					// 设计单位
					Cell cell16 = row9.getCell(1);
					String designUnit = cell16.getStringCellValue();
					siteProjectInfo.setDesignUnit(designUnit);

					// 设计单位负责人
					Cell cell17 = row9.getCell(3);
					String designUnitManager = cell17.getStringCellValue();
					siteProjectInfo.setDesignUnitManager(designUnitManager);

					// 设计单位负责人联系方式
					Cell cell18 = row9.getCell(5);
					cell18.setCellType(Cell.CELL_TYPE_STRING);
					String designUnitManagerPhone = cell18.getStringCellValue();
					siteProjectInfo.setDesignUnitManagerPhone(designUnitManagerPhone);

					Row row10 = sheet.getRow(10);

					// 土方单位
					Cell cell19 = row10.getCell(1);
					String earthworkUnit = cell19.getStringCellValue();
					siteProjectInfo.setEarthworkUnit(earthworkUnit);

					// 土方单位负责任人
					Cell cell20 = row10.getCell(3);
					String earthworkUnitManager = cell20.getStringCellValue();
					siteProjectInfo.setEarthworkUnitManager(earthworkUnitManager);

					// 土方单位负责人联系方式
					Cell cell21 = row10.getCell(5);
					cell21.setCellType(Cell.CELL_TYPE_STRING);
					String earthworkUnitManagerPhone = cell21.getStringCellValue();
					siteProjectInfo.setEarthworkUnitManagerPhone(earthworkUnitManagerPhone);

					Row row11 = sheet.getRow(11);

					// 运输单位
					Cell cell22 = row11.getCell(1);
					String transportUnit = cell22.getStringCellValue();
					siteProjectInfo.setTransportUnit(transportUnit);

					// 运输单位负责人
					Cell cell23 = row11.getCell(3);
					String transportUnitManager = cell23.getStringCellValue();
					siteProjectInfo.setTransportUnitManager(transportUnitManager);

					// 运输单位负责人联系方式
					Cell cell24 = row11.getCell(5);
					cell24.setCellType(Cell.CELL_TYPE_STRING);
					String transportUnitManagerPhone = cell24.getStringCellValue();
					siteProjectInfo.setTransportUnitManagerPhone(transportUnitManagerPhone);

					Row row12 = sheet.getRow(12);

					// 项目出入口
					Cell cell25 = row12.getCell(1);
					String entranceCleaners = cell25.getStringCellValue();
					siteProjectInfo.setEntranceCleaners(entranceCleaners);

					// 项目出入口负责人
					Cell cell26 = row12.getCell(3);
					String entranceCleanersManager = cell26.getStringCellValue();
					siteProjectInfo.setEntranceCleanersManager(entranceCleanersManager);

					// 项目出入口负责人联系方式
					Cell cell27 = row12.getCell(5);
					cell27.setCellType(Cell.CELL_TYPE_STRING);
					String entranceCleanersManagerPhone = cell27.getStringCellValue();
					siteProjectInfo.setEntranceCleanersManagerPhone(entranceCleanersManagerPhone);

					Row row13 = sheet.getRow(13);

					// 视频监控
					Cell cell28 = row13.getCell(1);
					String videoSurveillance = cell28.getStringCellValue();
					siteProjectInfo.setVideoSurveillance(videoSurveillance);

					// 视频监控负责人
					Cell cell29 = row13.getCell(3);
					String videoSurveillanceManager = cell29.getStringCellValue();
					siteProjectInfo.setVideoSurveillanceManager(videoSurveillanceManager);

					// 视频监控负责人联系方式
					Cell cell30 = row13.getCell(5);
					cell30.setCellType(Cell.CELL_TYPE_STRING);
					String videoSurveillanceManagerPhone = cell30.getStringCellValue();
					siteProjectInfo.setVideoSurveillanceManagerPhone(videoSurveillanceManagerPhone);

					// 工程进度描述
					Row row14 = sheet.getRow(14);

					Cell cell31 = row14.getCell(1);
					String projectInfoMemo = cell31.getStringCellValue();
					siteProjectInfo.setProjectInfoMemo(projectInfoMemo);

					siteProjectInfo.setDeleted(true);
					siteProjectInfo.setCreatedTime(new Date());
					// 添加到集合
					projectInfos.add(siteProjectInfo);
				}

			}
			is.close();
			workbook.close();
			return projectInfos;

		}
		throw new RuntimeException("上传文件接口出错");
	}

	/**
	 * 导出Excel数据
	 */
	public String exportExcelData2(List<SiteProjectInfo> siteProjectInfos) throws Exception {

		if (siteProjectInfos == null || siteProjectInfos.isEmpty()) {
			throw new RuntimeException("请选择要导出的工地");
		}

		// 创建一个新的excel
		Workbook workbook = new HSSFWorkbook();

		for (int i = 0; i < siteProjectInfos.size(); i++) {

			SiteProjectInfo spi = siteProjectInfos.get(i);
			Sheet sheet = workbook.createSheet((i + 1) + "." + spi.getProjectName()); // 项目名称

			sheet.autoSizeColumn(1, true);

			// 定义样式
			HSSFCellStyle cellStyleCenter = ExportFileNameUtils.initColumnHeadStyle(workbook);// 表头样工
			HSSFCellStyle cellStyleRight = ExportFileNameUtils.initColumnCenterstyle(workbook);// 单元格样式
			HSSFCellStyle cellStyleLeft = ExportFileNameUtils.initColumnCenterstyle(workbook);
			cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 右对齐
			cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左对齐

			sheet.setColumnWidth(0, 7200);
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 5000);

			try {
				HSSFRow row = null;
				Cell cell = null;
				for (int j = 0; j < 15; j++) {
					row = (HSSFRow) sheet.createRow(j);
					for (int k = 0; k <= 5; k++) {
						cell = row.createCell(k);
						cell.setCellStyle(cellStyleCenter);
					}
				}

				// 填充数据
				cell = sheet.getRow(0).getCell(0);
				cell.setCellValue(new HSSFRichTextString("项目情况登记表   ") + spi.getAreaName());

				cell = sheet.getRow(1).getCell(0);
				cell.setCellValue(new HSSFRichTextString("项目名称"));
				cell = sheet.getRow(1).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getProjectName()));

				cell = sheet.getRow(2).getCell(0);
				cell.setCellValue(new HSSFRichTextString("工地设备注册码"));
				cell = sheet.getRow(2).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getSiteDeviceRegisterCode()));

				cell = sheet.getRow(3).getCell(0);
				cell.setCellValue(new HSSFRichTextString("项目地址"));
				cell = sheet.getRow(3).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getProjectAddress()));

				cell = sheet.getRow(4).getCell(0);
				cell.setCellValue(new HSSFRichTextString("工程概况"));

				cell = sheet.getRow(4).getCell(1);
				cell.setCellValue(new HSSFRichTextString("建设规模"));

				cell = sheet.getRow(4).getCell(5);
				cell.setCellValue(new HSSFRichTextString("施工工期(天)"));

				cell = sheet.getRow(5).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getProjectScale()));

				cell = sheet.getRow(5).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getProjectPeriod()));

				cell = sheet.getRow(6).getCell(0);
				cell.setCellValue(new HSSFRichTextString("建设单位"));
				cell = sheet.getRow(6).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getBuildUnit()));
				cell = sheet.getRow(6).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(6).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getBuildUnitManager()));
				cell = sheet.getRow(6).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(6).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getBuildUnitManagerPhone()));

				cell = sheet.getRow(7).getCell(0);
				cell.setCellValue(new HSSFRichTextString("施工单位"));
				cell = sheet.getRow(7).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getConstructionUnit()));
				cell = sheet.getRow(7).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(7).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getConstructionUnitManager()));
				cell = sheet.getRow(7).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(7).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getConstructionUnitManagerPhone()));

				cell = sheet.getRow(8).getCell(0);
				cell.setCellValue(new HSSFRichTextString("监理单位"));
				cell = sheet.getRow(8).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getSupervisionUnit()));
				cell = sheet.getRow(8).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(8).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getSupervisionUnitManager()));
				cell = sheet.getRow(8).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(8).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getSupervisionUnitManagerPhone()));

				cell = sheet.getRow(9).getCell(0);
				cell.setCellValue(new HSSFRichTextString("设计单位"));
				cell = sheet.getRow(9).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getDesignUnit()));
				cell = sheet.getRow(9).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(9).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getDesignUnitManager()));
				cell = sheet.getRow(9).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(9).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getDesignUnitManagerPhone()));

				cell = sheet.getRow(10).getCell(0);
				cell.setCellValue(new HSSFRichTextString("土方单位"));
				cell = sheet.getRow(10).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getEarthworkUnit()));
				cell = sheet.getRow(10).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(10).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getEarthworkUnitManager()));
				cell = sheet.getRow(10).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(10).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getEarthworkUnitManagerPhone()));

				cell = sheet.getRow(11).getCell(0);
				cell.setCellValue(new HSSFRichTextString("运输单位"));
				cell = sheet.getRow(11).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getTransportUnit()));
				cell = sheet.getRow(11).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(11).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getTransportUnitManager()));
				cell = sheet.getRow(11).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(11).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getTransportUnitManagerPhone()));

				cell = sheet.getRow(12).getCell(0);
				cell.setCellValue(new HSSFRichTextString("项目出入口"));
				cell = sheet.getRow(12).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getEntranceCleaners()));
				cell = sheet.getRow(12).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(12).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getEntranceCleanersManager()));
				cell = sheet.getRow(12).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(12).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getEntranceCleanersManagerPhone()));

				cell = sheet.getRow(13).getCell(0);
				cell.setCellValue(new HSSFRichTextString("视频监控"));
				cell = sheet.getRow(13).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getVideoSurveillance()));
				cell = sheet.getRow(13).getCell(2);
				cell.setCellValue(new HSSFRichTextString("项目负责人"));
				cell = sheet.getRow(13).getCell(3);
				cell.setCellValue(new HSSFRichTextString(spi.getVideoSurveillanceManager()));
				cell = sheet.getRow(13).getCell(4);
				cell.setCellValue(new HSSFRichTextString("联系电话"));
				cell = sheet.getRow(13).getCell(5);
				cell.setCellValue(new HSSFRichTextString(spi.getVideoSurveillanceManagerPhone()));

				cell = sheet.getRow(14).getCell(0);
				cell.setCellValue(new HSSFRichTextString("工程项目进度描述"));
				cell = sheet.getRow(14).getCell(1);
				cell.setCellValue(new HSSFRichTextString(spi.getProjectInfoMemo()));

				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // 项目情况
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5)); // 项目名称
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5)); // 工地设备注册码
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5)); // 项目地址
				sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0)); // 工程概况
				sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 4)); // 建设规模
				sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 4)); // 建设规模内容
				sheet.addMergedRegion(new CellRangeAddress(14, 14, 1, 5)); // 建设规模内容

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator + "siteProject"
				+ File.separator + new Date().getTime() + ".xls";
		String path = propertiesService.getWindowsRootPath() + downloadPath;

		FileOutputStream fos = new FileOutputStream(new File(path));
		workbook.write(fos);
		fos.close();
		workbook.close();
		return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");
	}

	/*
	 * 拼接下载和存放到本机的文件子路径 downloadPath 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径
	 * subdirectory 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
	 * 
	 * 
	 * /** 根据注册号查询项目详情
	 */
	@Override
	public SiteProjectInfo queryByRegisterCode(String registerCode) {

		SiteProjectInfo siteProjectInfo = siteProjectInfoMapper.getProjectInfoByRegisterCode(registerCode);

		return siteProjectInfo;
	}

	@Override
	public String exportExcelDatas(List<SiteProjectInfo> siteProjectInfos, String fileName) throws Exception {

		// 获取模版文件路径
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator
				+ fileName;

		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("导出工程详情列表到一个工作簿中时车辆模版不存在");
			return "";
		}
		if (!file.isFile()) {
			logger.error("导出工程详情列表到一个工作簿中时传入的不是一个模板文件名");
			return "";
		}

		if (siteProjectInfos == null | siteProjectInfos.size() == 0) {
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径
		 * subdirectory 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator
				+ "siteProjectInfo" + File.separator + new Date().getTime() + ".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;

		// 创建目标文件
		File destFile = new File(destPath);

		// 将模板文件拷贝到目标路径的文件
		FileUtils.copyFile(file, destFile);

		InputStream is = new FileInputStream(destFile);

		Workbook workbook = new XSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);

		int temp = 1;
		int num = 2;

		// 遍历集合生成表身数据
		for (SiteProjectInfo projectInfo : siteProjectInfos) {
			Row row = sheet.getRow(num);

			Cell cell0 = row.getCell(0);

			// 序号
			cell0.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell0.setCellValue(temp);

			// 项目名称
			cell0 = row.getCell(1);
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			cell0.setCellValue(projectInfo.getProjectName());

			// 设备注册码
			cell0 = row.getCell(2);
			cell0.setCellValue(projectInfo.getSiteDeviceRegisterCode());

			// 项目 地址
			cell0 = row.getCell(3);
			cell0.setCellValue(projectInfo.getProjectAddress());

			// 项目区域
			cell0 = row.getCell(4);
			cell0.setCellValue(projectInfo.getAreaName());

			// 建设单位及联系人及电话
			cell0 = row.getCell(5);

			String str = "";
			// 拼接字符串
			if (StringUtils.isNotBlank(projectInfo.getBuildUnit())) {
				str = projectInfo.getBuildUnit() + ":" + projectInfo.getBuildUnitManager()
						+ projectInfo.getBuildUnitManagerPhone();
				cell0.setCellValue(str);
			}

			// 施工单位
			cell0 = row.getCell(6);
			if (StringUtils.isNotEmpty(projectInfo.getConstructionUnit())) {
				str = projectInfo.getConstructionUnit() + ":" + projectInfo.getConstructionUnitManager()
						+ projectInfo.getConstructionUnitManagerPhone();
				cell0.setCellValue(str);
			}

			// 监理单位
			cell0 = row.getCell(7);
			if (StringUtils.isNotBlank(projectInfo.getSupervisionUnit())) {
				str = projectInfo.getSupervisionUnit() + ":" + projectInfo.getSupervisionUnitManager()
						+ projectInfo.getSupervisionUnitManagerPhone();
				cell0.setCellValue(str);
			}

			// 设计单位
			cell0 = row.getCell(8);
			if (StringUtils.isNotBlank(projectInfo.getDesignUnit()))
				str = projectInfo.getDesignUnit() + ":" + projectInfo.getDesignUnitManager()
						+ projectInfo.getDesignUnitManagerPhone();
			cell0.setCellValue(str);

			// 土方单位、
			cell0 = row.getCell(9);
			if (StringUtils.isNotBlank(projectInfo.getEarthworkUnit())) {
				str = projectInfo.getEarthworkUnit() + ":" + projectInfo.getEarthworkUnitManager()
						+ projectInfo.getEarthworkUnitManagerPhone();
				cell0.setCellValue(str);
			}

			// 运输单位
			cell0 = row.getCell(10);
			if (StringUtils.isNotBlank(projectInfo.getTransportUnit())) {
				str = projectInfo.getTransportUnit() + ":" + projectInfo.getTransportUnitManager()
						+ projectInfo.getTransportUnitManagerPhone();
				cell0.setCellValue(str);
			}

			// 项目出入口
			cell0 = row.getCell(11);
			if (StringUtils.isNotBlank(projectInfo.getEntranceCleaners())) {
				str = projectInfo.getEntranceCleaners() + ":" + projectInfo.getEntranceCleanersManager()
						+ projectInfo.getEntranceCleanersManagerPhone();
				cell0.setCellValue(str);
			}

			// 视频安装
			cell0 = row.getCell(12);
			if (StringUtils.isNotBlank(projectInfo.getVideoSurveillance())) {
				str = projectInfo.getVideoSurveillance() + ":" + projectInfo.getVideoSurveillanceManager()
						+ projectInfo.getVideoSurveillanceManagerPhone();
				cell0.setCellValue(str);
			}

			// 项目描述
			cell0 = row.getCell(13);
			str = projectInfo.getProjectInfoMemo();
			cell0.setCellValue(str);

			// 数字自增
			num++;
			temp++;

		}

		// 执行写入操作
		FileOutputStream fs = new FileOutputStream(destFile);
		workbook.write(fs);
		fs.flush();
		fs.close();
		workbook.close();
		is.close();
		return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");

	}

	@Override
	public String exportExcelDatas(List<SiteProjectInfo> siteProjectInfos) throws Exception {

		if (siteProjectInfos == null || siteProjectInfos.isEmpty()) {
			throw new RuntimeException("请选择要导出的工地");
		}

		// 创建一个新的excel
		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet("在建项目汇总"); // 项目名称

		sheet.autoSizeColumn(1, true);

		// 定义样式
		HSSFCellStyle cellStyleCenter = ExportFileNameUtils.initColumnHeadStyle(workbook);// 表头样工
		HSSFCellStyle cellStyleRight = ExportFileNameUtils.initColumnCenterstyle(workbook);// 单元格样式
		HSSFCellStyle cellStyleLeft = ExportFileNameUtils.initColumnCenterstyle(workbook);
		cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 右对齐
		cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左对齐

		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 8000);
		sheet.setColumnWidth(9, 8000);
		sheet.setColumnWidth(10, 8000);
		sheet.setColumnWidth(11, 8000);
		sheet.setColumnWidth(12, 8000);
		sheet.setColumnWidth(13, 8000);

		try {
			HSSFRow row = null;
			Cell cell = null;
			for (int j = 0; j < siteProjectInfos.size() + 2; j++) {
				row = (HSSFRow) sheet.createRow(j);
				for (int k = 0; k <= 13; k++) {
					cell = row.createCell(k);
					cell.setCellStyle(cellStyleCenter);
				}
			}
			// 填充数据
			cell = sheet.getRow(0).getCell(0);
			cell.setCellValue(new HSSFRichTextString("在建项目汇总"));

			// 表头
			cell = sheet.getRow(1).getCell(0);
			cell.setCellValue(new HSSFRichTextString("序号"));
			
			cell = sheet.getRow(1).getCell(1);
			cell.setCellValue(new HSSFRichTextString("项目名称"));
			
			cell = sheet.getRow(1).getCell(2);
			cell.setCellValue(new HSSFRichTextString("设备注册码"));

			cell = sheet.getRow(1).getCell(3);
			cell.setCellValue(new HSSFRichTextString("项目地址"));

			cell = sheet.getRow(1).getCell(4);
			cell.setCellValue(new HSSFRichTextString("项目区域"));

			cell = sheet.getRow(1).getCell(5);
			cell.setCellValue(new HSSFRichTextString("建设单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(6);
			cell.setCellValue(new HSSFRichTextString("施工单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(7);
			cell.setCellValue(new HSSFRichTextString("监理单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(8);
			cell.setCellValue(new HSSFRichTextString("设计单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(9);
			cell.setCellValue(new HSSFRichTextString("土方单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(10);
			cell.setCellValue(new HSSFRichTextString("运输单位及联系人、联系电话"));

			cell = sheet.getRow(1).getCell(11);
			cell.setCellValue(new HSSFRichTextString("项目出入口负责人、联系电话"));

			cell = sheet.getRow(1).getCell(12);
			cell.setCellValue(new HSSFRichTextString("视频安装联系人、联系电话"));
			
			cell = sheet.getRow(1).getCell(13);
			cell.setCellValue(new HSSFRichTextString("项目工程进度描述"));
			
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13)); // 在建项目汇总
			
			//表身数据
			int temp = 1;
			int num = 2;

			// 遍历集合生成表身数据
			for (SiteProjectInfo projectInfo : siteProjectInfos) {
				Row row1 = sheet.getRow(num);
				cell=row1.getCell(0);

				// 序号
				cell.setCellValue(new HSSFRichTextString(temp+""));

				// 项目名称
				cell = row1.getCell(1);
				cell.setCellValue(new HSSFRichTextString(projectInfo.getProjectName()));

				// 设备注册码
				cell = row1.getCell(2);
				cell.setCellValue(new HSSFRichTextString(projectInfo.getSiteDeviceRegisterCode()));

				// 项目 地址
				cell = row1.getCell(3);
				cell.setCellValue(new HSSFRichTextString(projectInfo.getProjectAddress()));

				// 项目区域
				cell = row1.getCell(4);
				cell.setCellValue(new HSSFRichTextString(projectInfo.getAreaName()));

				// 建设单位及联系人及电话
				cell = row1.getCell(5);

				String str = "";
				// 拼接字符串
				if (StringUtils.isNotBlank(projectInfo.getBuildUnit())) {
					str = projectInfo.getBuildUnit() + ":" + projectInfo.getBuildUnitManager()
							+ projectInfo.getBuildUnitManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 施工单位
				cell = row1.getCell(6);
				if (StringUtils.isNotEmpty(projectInfo.getConstructionUnit())) {
					str = projectInfo.getConstructionUnit() + ":" + projectInfo.getConstructionUnitManager()
							+ projectInfo.getConstructionUnitManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 监理单位
				cell = row1.getCell(7);
				if (StringUtils.isNotBlank(projectInfo.getSupervisionUnit())) {
					str = projectInfo.getSupervisionUnit() + ":" + projectInfo.getSupervisionUnitManager()
							+ projectInfo.getSupervisionUnitManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 设计单位
				cell = row1.getCell(8);
				if (StringUtils.isNotBlank(projectInfo.getDesignUnit()))
					str = projectInfo.getDesignUnit() + ":" + projectInfo.getDesignUnitManager()
							+ projectInfo.getDesignUnitManagerPhone();
				cell.setCellValue(new HSSFRichTextString(str));

				// 土方单位、
				cell = row1.getCell(9);
				if (StringUtils.isNotBlank(projectInfo.getEarthworkUnit())) {
					str = projectInfo.getEarthworkUnit() + ":" + projectInfo.getEarthworkUnitManager()
							+ projectInfo.getEarthworkUnitManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 运输单位
				cell= row1.getCell(10);
				if (StringUtils.isNotBlank(projectInfo.getTransportUnit())) {
					str = projectInfo.getTransportUnit() + ":" + projectInfo.getTransportUnitManager()
							+ projectInfo.getTransportUnitManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 项目出入口
				cell = row1.getCell(11);
				if (StringUtils.isNotBlank(projectInfo.getEntranceCleaners())) {
					str = projectInfo.getEntranceCleaners() + ":" + projectInfo.getEntranceCleanersManager()
							+ projectInfo.getEntranceCleanersManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 视频安装
				cell = row1.getCell(12);
				if (StringUtils.isNotBlank(projectInfo.getVideoSurveillance())) {
					str = projectInfo.getVideoSurveillance() + ":" + projectInfo.getVideoSurveillanceManager()
							+ projectInfo.getVideoSurveillanceManagerPhone();
					cell.setCellValue(new HSSFRichTextString(str));
				}

				// 项目描述
				cell = row1.getCell(13);
				str = projectInfo.getProjectInfoMemo();
				cell.setCellValue(new HSSFRichTextString(str));

				// 数字自增
				num++;
				temp++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator + "siteProject"
				+ File.separator + new Date().getTime() + ".xls";
		String path = propertiesService.getWindowsRootPath() + downloadPath;
		File file = new File(path);
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			if(!parentFile.mkdirs()){
				logger.error("父目录创建失败");
				throw new RuntimeException("父目录创建失败");
			}
		}
		if(!file.exists()){
			if(!file.createNewFile()){
				logger.error("文件创建失败");
				throw new RuntimeException("文件创建失败");
			}
		}
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();
		return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");
	}

	/**
	 * 生成表格数据并返回路径
	 */

}

@SuppressWarnings("deprecation")
class ExportFileNameUtils implements Serializable {

	private static final long serialVersionUID = -4087931483756061244L;

	/**
	 * 
	 * <br>
	 * <b>功能：</b>列头样式<br>
	 * <b>作者：</b>yixq<br>
	 * <b>@param wb <b>@return</b>
	 */

	public static HSSFCellStyle initColumnHeadStyle(Workbook wb) {
		HSSFCellStyle columnHeadStyle = (HSSFCellStyle) wb.createCellStyle();
		HSSFFont columnHeadFont = (HSSFFont) wb.createFont();
		columnHeadFont.setFontName("宋体");
		columnHeadFont.setFontHeightInPoints((short) 15);
		columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
		columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
		columnHeadStyle.setBorderRight((short) 1);// 边框的大小
		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
		// 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
		columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		return columnHeadStyle;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>单元格的默认样式<br>
	 * <b>作者：</b>yixq<br>
	 * <b>@param wb <b>@return</b>
	 */
	public static HSSFCellStyle initColumnCenterstyle(Workbook wb) {
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 15);
		HSSFCellStyle centerstyle = (HSSFCellStyle) wb.createCellStyle();
		centerstyle.setFont(font);
		centerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		centerstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		centerstyle.setWrapText(true);
		centerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderLeft((short) 1);
		centerstyle.setRightBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderRight((short) 1);
		centerstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		centerstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		centerstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		return centerstyle;
	}

}
