package com.muck.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.BaseEntity;
import com.muck.domain.Company;
import com.muck.domain.CompanyManager;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CompanyAreaMapper;
import com.muck.mapper.CompanyDeviceMapper;
import com.muck.mapper.CompanyMapper;
import com.muck.page.PageView;
import com.muck.query.CompanyQueryForm;
import com.muck.response.SimpleCompanyResponse;
import com.muck.response.StatusCode;
import com.muck.service.CompanyService;
import com.muck.service.PropertiesService;
import com.muck.utils.ArrayUtils;
import com.muck.utils.ExcelWaterMarkUtils;
import com.muck.utils.ImageUtils;
import com.muck.utils.XSSFDateUtil;

/**
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月16日 上午11:24:16
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper; // 企业Mapper

	@Autowired
	private CompanyAreaMapper companyAreaMapper; // 企业和入驻的区域Mapper

	@Autowired
	private CompanyDeviceMapper companyDeviceMapper; // 企业和设备关联Mapper

	@Autowired
	private PropertiesService propertiesService;

	@Override
	public Company queryByName(String companyName) {
		return companyMapper.queryByName(companyName);
	}

	/**
	 * 
	 * @Description: 统计企业列表 1、包括企业基本信息 2、工程中正在使用的车辆数
	 * @author: 展昭
	 * @date: 2018年4月27日 下午6:56:46
	 */
	public List<Map<String, Object>> statisticsCompanyList() {
		return companyMapper.statisticsCompanyList();
	}

	/**
	 * 添加企业 1、添加企业基本信息 2、添加企业入驻区域 3、添加企业下面的设备
	 */
	public void save(Company company) {

		// 1、添加基本信息
		super.save(company);

		// 2、判断企业是否有选择了入驻区域
		String[] areaCodes = company.getAreaCodes();
		if (ArrayUtils.isNotEmpty(areaCodes)) {
			// 添加区域
			companyAreaMapper.insert(company, areaCodes);
		}

		// 3、判断企业是否选择了设备
		String[] deviceCodes = company.getDeviceCodes();
		if (ArrayUtils.isNotEmpty(deviceCodes)) {
			// 添加设备
			companyDeviceMapper.insert(company, deviceCodes);
		}
	}

	/***
	 * 根据区域编码查询企业
	 */
	@Override
	public List<Company> queryCompanyByAreaCode(String areaCode) {
		return companyMapper.selectCompanyByAreaCode(areaCode);
	}

	@Override
	public PageView<Company> queryPageData(Long currentPageNum, Long pageSize, String whereSQL,
			List<Object> whereParams, LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 用来查询数据总数的sql
		String sql = "select count(c.id) from t_company c where 1 = 1 " + whereSQL;
		// 用来查询数据的sql
		String selectSQL = "select " + "c.id, c.company_name, c.company_registered_address, c.area_id, c.area_name, "
				+ "c.company_acreage, c.company_own_lease, c.company_car_park_address, c.company_car_park_acreage,"
				+ "c.company_car_park_own_lease, c.company_business_license,c.company_business_license_close_date,"
				+ "c.company_road_license,c.company_road_license_close_date,"
				+ "c.company_type, c.company_creation_time, c.company_contact, c.company_facsimile, c.company_url,"
				+ "c.company_email, c.company_legal_representative, c.company_legal_representative_phone,"
				+ "c.company_director, c.company_director_phone, c.company_motorcade_principal, c.company_motorcade_principal_phone,"
				+ "c.company_employee_number, c.company_administrator_number, c.company_driver_number,"
				+ "c.company_car_number, c.company_car_yellow_card_number, c.company_car_blue_card_number,"
				+ "c.company_principal_name,"
				+ "c.company_principal_phone, c.company_scale, c.company_logo, c.company_arrival_time, c.operator_id,"
				+ "c.operator_name, c.created_time, c.updated_time,"
				+ " (SELECT COUNT(car.id) FROM t_car car where car.company_id = c.id) as total "
				+ " from t_company c where 1 = 1 " + whereSQL + limit;
		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = companyMapper.selectTotalRecoreds(sql);

		PageView<Company> pageView = new PageView<>(count, currentPageNum, pageSize);

		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		List<Company> companies = companyMapper.selectPageData(selectSQL);

		pageView.setDatas(companies);

		return pageView;
	}

	@Override
	public List<Company> queryData(String whereSQL, List<Object> whereParams) {
		// 第一步、获取要查询的字段
		String queryFields = getFields();
		if (StringUtils.isBlank(queryFields)) {
			throw new BizException(StatusCode.QUERY_FIELDS_NOT_FOUND);
		}

		// 第三步、获取where条件
		String where = StringUtils.isBlank(whereSQL) ? "" : " where " + whereSQL;
		// 第四步、拼接sql语句
		String selectSQL = " SELECT "
				+ "company.id, company.company_name, company.company_registered_address, company.area_id, company.area_name, "
				+ "company.company_acreage,company.company_own_lease,company.company_car_park_address,company.company_car_park_acreage,"
				+ "company.company_car_park_own_lease,company.company_business_license,company.company_business_license_close_date,"
				+ "company.company_road_license,company.company_road_license_close_date,"
				+ "company.company_type,company.company_category,company.company_creation_time,company.company_contact,company.company_facsimile,company.company_url,"
				+ "company.company_email,company.company_legal_representative,company.company_legal_representative_phone,"
				+ "company.company_director,company.company_director_phone,company.company_motorcade_principal,company.company_motorcade_principal_phone,"
				+ "company.company_employee_number,company.company_administrator_number,company.company_general_number,company.company_driver_number,"
				+ "company.company_car_number, company.company_car_yellow_card_number, company.company_car_blue_card_number,"
				+ "company.company_principal_name,"
				+ "company.company_principal_phone,company.company_scale,company.company_logo,company.company_arrival_time,company.operator_id,"
				+ "company.operator_name,company.created_time,company.updated_time,"
				+ " (SELECT COUNT(car.id) FROM t_car car where car.company_id = company.id) as car_total, "
				+ " (SELECT COUNT(car_driver.id) FROM t_car_driver car_driver where car_driver.company_id = company.id) as driver_total, "

				+ "car.car_code," + "car.car_group_name," + "car_driver.driver_name," + "car_driver.driver_phone"
				+ " from " + " t_company company left join t_car car on company.id = car.company_id"
				+ " left join t_car_driver car_driver on car.car_code = car_driver.car_code" + where;
		// 第五步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}

	@Override
	public PageView<Company> queryCompanyPageData(CompanyQueryForm companyQueryForm) {
		String limit = super.buildLimit(companyQueryForm.getPageNum(), companyQueryForm.getPageSize());
		// 用来查询数据总数的sql
		String sql = "select count(c.id) from t_company c ";
		// 用来查询数据的sql
		String selectSQL = "select " + " c.id, c.company_name, c.company_registered_address, c.area_id, c.area_name, "
				+ "c.company_acreage, c.company_own_lease, c.company_car_park_address, c.company_car_park_acreage,"
				+ "c.company_car_park_own_lease, c.company_business_license,c.company_business_license_close_date,"
				+ "c.company_road_license,c.company_road_license_close_date,"
				+ "c.company_type, c.company_creation_time, c.company_contact, c.company_facsimile, c.company_url,"
				+ "c.company_email, c.company_legal_representative, c.company_legal_representative_phone,"
				+ "c.company_director, c.company_director_phone, c.company_motorcade_principal, c.company_motorcade_principal_phone,"
				+ "c.company_employee_number, c.company_administrator_number, c.company_driver_number, c.company_principal_name,"
				+ "c.company_principal_phone, c.company_scale, c.company_logo, c.company_arrival_time, c.operator_id,"
				+ "c.operator_name, c.created_time, c.updated_time,"
				+ " (SELECT COUNT(car.id) FROM t_car car where car.company_id = c.id) as total " + " from t_company c";
		// + ",t_area a,t_company_area ca ";
		// 用来排序的sql
		StringBuilder orderBy = new StringBuilder("");

		selectSQL += " where 1=1 and c.deleted = 1";
		// + " and c.id = ca.company_id and ca.area_code = a.area_code ";
		sql += " where 1=1 and c.deleted = 1 ";
		// + " and c.id = ca.company_id and ca.area_code = a.area_code ";
		List<Object> params = new ArrayList<Object>();
		String companyName = companyQueryForm.getCompanyName();
		if (!StringUtils.isBlank(companyName)) {
			selectSQL += " and company_name like %s";
			sql += " and company_name like %s";
			params.add("'%" + companyName + "%'");
		}
		String companyPrincipalName = companyQueryForm.getCompanyPrincipalName();
		if (!StringUtils.isBlank(companyPrincipalName)) {
			selectSQL += " and company_principal_name like %s";
			sql += " and company_principal_name like %s";
			params.add("'%" + companyPrincipalName + "%'");
		}
		if (orderBy.length() > 0) {
			orderBy = orderBy.deleteCharAt(orderBy.length() - 1);
			selectSQL += " order by " + orderBy;
		}
		selectSQL += limit;
		sql = super.setQueryParams(sql, params, null);
		Long totalRecord = companyMapper.selectTotalRecoreds(sql);
		PageView<Company> pageView = new PageView<>(totalRecord, companyQueryForm.getPageNum(),
				companyQueryForm.getPageSize());
		selectSQL = super.setQueryParams(selectSQL, params, pageView);
		List<Company> datas = companyMapper.selectPageData(selectSQL);
		pageView.setDatas(datas);
		return pageView;
	}

	@Override
	public PageView<Company> queryCompanyPageData() {
		return queryCompanyPageData(null);
	}

	// 读Excel并封装实体
	@SuppressWarnings("resource")
	@Override
	public Company readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		Company company = null;
		if (path != null) {
			File file = new File(path);
			if (!file.exists() || !file.isFile()) {
				return null;
			}
			// 获取文件类型
			String fileType = path.substring(path.lastIndexOf(".") + 1);
			InputStream is = null;
			try {
				is = new FileInputStream(file);

				// 创建一个工作薄
				Workbook workbook = null;
				if (fileType.equals("xls")) {
					workbook = new HSSFWorkbook(is);
				} else if (fileType.equals("xlsx")) {
					workbook = new XSSFWorkbook(is);
				} else {
					throw new RuntimeException("不是Excel类型");
				}
				company = new Company();
				Sheet sheet = workbook.getSheet("企业基本情况");
				if (sheet == null) {
					// 如果获取不到说明可能上传的是单个表
					sheet = workbook.getSheetAt(0);
				}
				if (sheet != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					// 企业名称
					Row row1 = sheet.getRow(1);
					Cell cell1 = row1.getCell(1);
					String companyName = cell1.getStringCellValue();
					if (!StringUtils.isBlank(companyName)) {
						company.setCompanyName(companyName.trim());
					}
					// 企业注册地址
					Row row2 = sheet.getRow(2);
					String companyAdd = row2.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyAdd)) {
						company.setCompanyRegisteredAddress(companyAdd);
					}
					// 企业面积
					row2.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					String companyMianji = row2.getCell(6).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyMianji)) {
						company.setCompanyAcreage(companyMianji);
					}
					// 企业自有或租赁
					Row row3 = sheet.getRow(3);
					String ownOrRent = row3.getCell(6).getStringCellValue().trim();
					if (!StringUtils.isBlank(ownOrRent)) {
						company.setCompanyOwnLease(ownOrRent);
					}
					// 企业停车场地址
					Row row4 = sheet.getRow(4);
					String companyParkAdd = row4.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyParkAdd)) {
						company.setCompanyCarParkAddress(companyParkAdd);
					}
					// 企业停车场面积
					row4.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					String parkMianji = row4.getCell(6).getStringCellValue().trim();
					if (!StringUtils.isBlank(parkMianji)) {
						company.setCompanyCarParkAcreage(parkMianji);
					}
					// 企业停车场自有或租赁
					Row row5 = sheet.getRow(5);
					String parkOwnOrRent = row5.getCell(6).getStringCellValue().trim();
					if (!StringUtils.isBlank(parkOwnOrRent)) {
						company.setCompanyCarParkOwnLease(parkOwnOrRent);
					}
					// 企业营业执照号
					Row row6 = sheet.getRow(6);
					row6.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String companyBussLicense = row6.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyBussLicense)) {
						company.setCompanyBusinessLicense(companyBussLicense);
					}
					// 企业营业执照号截止日期
					Cell date1 = row6.getCell(6);
					if (date1 != null) {
						if(date1.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
							// 判断当前单元格是否进行时间格式化了
							if (XSSFDateUtil.isCellDateFormatted(date1)) {
								company.setCompanyBusinessLicenseCloseDate(date1.getDateCellValue());
							}else {
								short format = date1.getCellStyle().getDataFormat();
								if(format == 14 | format == 20 | format == 31| format == 32 | format == 57 | format == 58 | format == 176){
									company.setCompanyBusinessLicenseCloseDate(date1.getDateCellValue());
								}else {
									// 设置单元格数据为String类型
									date1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
									String date = date1.getStringCellValue();
									if(!StringUtils.isBlank(date)){
										try {
											company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy.MM.dd").parse(date));
										} catch (Exception e) {
											try {
												company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy年MM月dd日").parse(date.trim()));
											} catch (Exception e2) {
												if("长期".equals(date)){
													Calendar calendar = Calendar.getInstance();
													calendar.set(Calendar.YEAR, 2037);
													calendar.set(Calendar.MONTH,11);
													calendar.set(Calendar.DAY_OF_MONTH, 31);
													try {
														company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())));
													} catch (Exception e1) {
														e1.printStackTrace();
													}
												}else {
													e.printStackTrace();
												}
											}
										}
									}
								}
							}
						}else {
							// 设置单元格数据为String类型
							date1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							String date = date1.getStringCellValue();
							if(!StringUtils.isBlank(date)){
								try {
									company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy.MM.dd").parse(date.trim()));
								} catch (Exception e) {
									try {
										company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy年MM月dd日").parse(date.trim()));
									} catch (Exception e2) {
										if("长期".equals(date)){
											Calendar calendar = Calendar.getInstance();
											calendar.set(Calendar.YEAR, 2037);
											calendar.set(Calendar.MONTH,11);
											calendar.set(Calendar.DAY_OF_MONTH, 31);
											try {
												company.setCompanyBusinessLicenseCloseDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())));
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}else {
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
					// 道路普通货物运输经营许可证号
					Row row7 = sheet.getRow(7);
					row7.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String companyRoadGeneral = row7.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyRoadGeneral)) {
						company.setCompanyRoadLicense(companyRoadGeneral);
					}
					// 道路普通货物运输经营许可证号截止日期
					Cell date2 = row7.getCell(6);
					if (date2 != null) {
						// 判断当前单元格是否进行时间格式化了
						if(date2.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
							if (XSSFDateUtil.isCellDateFormatted(date2)) {
								company.setCompanyRoadLicenseCloseDate(date2.getDateCellValue());
							}else {
								short format = date2.getCellStyle().getDataFormat();
								if(format == 14 | format == 20 | format == 31| format == 32 | format == 57 | format == 58 | format == 176){
									company.setCompanyRoadLicenseCloseDate(date2.getDateCellValue());
								}else {
									// 设置单元格数据为String类型
									date2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
									String date = date2.getStringCellValue();
									if(!StringUtils.isBlank(date)){
										try {
											company.setCompanyRoadLicenseCloseDate(new SimpleDateFormat("yyyy.MM.dd").parse(date));
										} catch (Exception e) {
											if("长期".equals(date)){
												Calendar calendar = Calendar.getInstance();
												calendar.set(Calendar.YEAR, 2037);
												calendar.set(Calendar.MONTH,11);
												calendar.set(Calendar.DAY_OF_MONTH, 31);
												try {
													company.setCompanyRoadLicenseCloseDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())));
												} catch (Exception e1) {
													e1.printStackTrace();
												}
											}else {
												e.printStackTrace();
											}
										}
									}
								}
							}
						}else {
							// 设置单元格数据为String类型
							date2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							String date = date2.getStringCellValue();
							if(!StringUtils.isBlank(date)){
								try {
									company.setCompanyRoadLicenseCloseDate(new SimpleDateFormat("yyyy.MM.dd").parse(date));
								} catch (Exception e) {
									if("长期".equals(date)){
										Calendar calendar = Calendar.getInstance();
										calendar.set(Calendar.YEAR, 2037);
										calendar.set(Calendar.MONTH,11);
										calendar.set(Calendar.DAY_OF_MONTH, 31);
										try {
											company.setCompanyRoadLicenseCloseDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())));
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}else {
										e.printStackTrace();
									}
								}
							}
						}
					}
					// 企业类型
					Row row8 = sheet.getRow(8);
					String companyType = row8.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyType)) {
						company.setCompanyType(companyType);
					}
					// 建立时间
					
					Cell date3 = row8.getCell(4);
					if(date3 != null) {
						if(date3.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
							if (XSSFDateUtil.isCellDateFormatted(date3)) {
								company.setCompanyCreationTime(date3.getDateCellValue());
							}else {
								short format = date3.getCellStyle().getDataFormat();
								if(format == 14 | format == 20 | format == 31| format == 32 | format == 57 | format == 58 | format == 176){
									company.setCompanyCreationTime(date3.getDateCellValue());
								}else {
									// 设置单元格数据为String类型
									date3.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
									String date = date3.getStringCellValue();
									if(!StringUtils.isBlank(date)){
										try {
											company.setCompanyCreationTime(new SimpleDateFormat("yyyy.MM.dd").parse(date.trim()));
										} catch (Exception e) {
											try {
												company.setCompanyCreationTime(new SimpleDateFormat("yyyy-MM-dd").parse(date.trim()));
											} catch (Exception e1) {
												try {
													company.setCompanyCreationTime(new SimpleDateFormat("yyyy年MM月dd日").parse(date.trim()));
												} catch (Exception e2) {
													e2.printStackTrace();
												}
											}
										}
									}
								}
							}
						}else {
							// 设置单元格数据为String类型
							date2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							String date = date2.getStringCellValue();
							if(!StringUtils.isBlank(date)){
								try {
									company.setCompanyRoadLicenseCloseDate(new SimpleDateFormat("yyyy.MM.dd").parse(date));
								} catch (Exception e) {
									try {
										company.setCompanyCreationTime(new SimpleDateFormat("yyyy-MM-dd").parse(date.trim()));
									} catch (Exception e1) {
										try {
											company.setCompanyCreationTime(new SimpleDateFormat("yyyy年MM月dd日").parse(date.trim()));
										} catch (Exception e2) {
											e2.printStackTrace();
										}
									}
								}
							}
						}
					}
					// 企业联系方式
					Row row9 = sheet.getRow(9);
					row9.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String companyContact = row9.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyContact)) {
						company.setCompanyContact(companyContact);
					}
					// 企业传真
					row9.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String companyFacsimile = row9.getCell(4).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyFacsimile)) {
						company.setCompanyFacsimile(companyFacsimile);
					}
					// 企业网址
					Row row10 = sheet.getRow(10);
					String companyUrl = row10.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyUrl)) {
						company.setCompanyUrl(companyUrl);
					}
					// 企业电子信箱
					row10.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String companyEmail = row10.getCell(4).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyEmail)) {
						company.setCompanyEmail(companyEmail);
					}
					// 法定代表人
					Row row11 = sheet.getRow(11);
					String companyLegalRepresentative = row11.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyLegalRepresentative)) {
						company.setCompanyLegalRepresentative(companyLegalRepresentative);
					}
					// 法定代表人联系方式
					row11.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String companyLegalRepresentativePhone = row11.getCell(4).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyLegalRepresentativePhone)) {
						company.setCompanyLegalRepresentativePhone(companyLegalRepresentativePhone);
					}
					// 企业经理
					Row row12 = sheet.getRow(12);
					String companyDirector = row12.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyDirector)) {
						company.setCompanyDirector(companyDirector);
					}
					// 企业经理联系方式
					row12.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String companyDirectorPhone = row12.getCell(4).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyDirectorPhone)) {
						company.setCompanyDirectorPhone(companyDirectorPhone);
					}
					// 车队负责人
					Row row13 = sheet.getRow(13);
					String companyMotorcadePrincipal = row13.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyMotorcadePrincipal)) {
						company.setCompanyMotorcadePrincipal(companyMotorcadePrincipal);
					}
					// 车队负责人联系方式
					row13.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					String companyMotorcadePrincipalPhone = row13.getCell(4).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyMotorcadePrincipalPhone)) {
						company.setCompanyMotorcadePrincipalPhone(companyMotorcadePrincipalPhone);
					}
					// 职工总人数
					Row row14 = sheet.getRow(15);
					row14.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String companyEmployeeNumber = row14.getCell(1).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyEmployeeNumber)) {
						company.setCompanyEmployeeNumber(Integer.parseInt(companyEmployeeNumber));
					}
					// 管理人员人数
					row14.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					String companyAdministratorNumber = row14.getCell(2).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyAdministratorNumber)) {
						company.setCompanyAdministratorNumber(Integer.parseInt(companyAdministratorNumber));
					}
					// 驾驶员人数
					row14.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					String companyDriverNumber = row14.getCell(5).getStringCellValue().trim();
					if (!StringUtils.isBlank(companyDriverNumber)) {
						company.setCompanyDriverNumber(Integer.parseInt(companyDriverNumber));
					}
					
					// 车辆数量
					Row row18 = sheet.getRow(17);
					// 车辆总数
					row18.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					String companyCarNumber = row18.getCell(1).getStringCellValue().trim();
					if(!StringUtils.isBlank(companyCarNumber)){
						company.setCompanyCarNumber(Integer.parseInt(companyCarNumber));
					}
					// 黄牌车辆数量
					row18.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					String companyCarYellowCardNumber = row18.getCell(2).getStringCellValue().trim();
					if(!StringUtils.isBlank(companyCarYellowCardNumber)){
						company.setCompanyCarYellowCardNumber(Integer.parseInt(companyCarYellowCardNumber));
					}
					// 蓝牌车辆数量
					row18.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					String companyCarBlueCardNumber = row18.getCell(5).getStringCellValue().trim();
					if(!StringUtils.isBlank(companyCarBlueCardNumber)){
						company.setCompanyCarBlueCardNumber(Integer.parseInt(companyCarBlueCardNumber));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException(StatusCode.IMPORT_COMPANY_DATA_FAILE);
			}
		}
		return company;
	}

	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<Company> list) {
		// 声明一个存放表格数据的集合
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 存放到表格中的数据列表，包括表头和表身数据
		// 声明一个表头键值对容器，这里存放所有的表头信息
		Map<String, String> tableHead = new TreeMap<String, String>();
		/*
		 * 声明一个表身键值对容器， 这里需要注意的是，表头Map中的key和表身的每个Map的key需要保持一致，
		 * 以此来进行向Excel表格中添加数据避免数据顺序的紊乱
		 */
		Map<String, String> tableBody = null;

		// 获取传入的表格模版的类的对象
		Class<?> cls = excelTemplate.getClass();
		// 得到属性集合
		Field[] fs = cls.getDeclaredFields();
		// 遍历模版对象属性列表生成表头数据
		try {
			for (Field f : fs) {
				// 设置属性是可以访问的(私有的也可以)
				f.setAccessible(true);
				// 得到该对象中此属性的值
				Object val = f.get(excelTemplate);
				// 如果属性值不为空执行的操作
				if (val != null) {
					if ("true".equals(val.toString())) {
						// 获取属性名
						String fName = f.getName();
						// 获取属性的注解
						ExcelTemplate template = f.getAnnotation(ExcelTemplate.class);
						// 向表头键值对容器中添加表头数据,key值为属性名，value值为该属性上的注解的name属性值
						tableHead.put(fName, template.name());
					}
				}
			}
			// 将表头添加到数据列表中
			data.add(tableHead);
		} catch (SecurityException e) {
			logger.error("导出企业时出现的异常",e);
		} catch (IllegalArgumentException e) {
			logger.error("导出企业时出现的异常",e);
		} catch (IllegalAccessException e) {
			logger.error("导出企业时出现的异常",e);
		}
		// 生成表身数据
		// 遍历传入的数据列表
		if (list != null) {
			try {
				for (Company company : list) {
					// 获取对象的类的对象
					Class<?> tcls = company.getClass();
					// 遍历表头map的所有的key
					Set<String> keySet = tableHead.keySet();
					tableBody = new TreeMap<String, String>();
					// 遍历表头map所有的key
					for (String key : new ArrayList<String>(keySet)) {
						// 通过key获取列表数据对象中的对应的属性
						// 将key中的has_替换成"",保持和数据实体类中的属性名一致
						Field filed = null;
						if (key.contains("has_")) {
							filed = tcls.getDeclaredField(key.replace("has_", ""));
						}
						if (filed != null) {
							filed.setAccessible(true);
							String type = filed.getGenericType().toString();
							String typeReal = "";
							if (type.contains("<") && type.contains(">")) {
								typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1,
										type.indexOf("<"));
							} else {
								typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
							}
							// 获取该对象的此属性的值
							Object obj = filed.get(company);
							if (obj != null) {
								// 如果获取到的是时间类型，则转化一下时间格式
								if ("date".equalsIgnoreCase(typeReal)) {
									Date date = (Date) obj;
									// 将该key与属性值添加到当前条数据的Excel表单元格中
									tableBody.put(key, sdf.format(date));
								} else if ("list".equalsIgnoreCase(typeReal)) {
									continue;
								} else {
									// 将该key与属性值添加到当前条数据的Excel表单元格中
									tableBody.put(key, obj.toString());
								}
							} else {
								tableBody.put(key, "");
							}
						}
					}
					tableBody.remove("has_carDrivers");
					// 将该条数据添加到表身中
					data.add(tableBody);
				}
				// 将包含列表的数据表头删除掉
				Set<String> keySet = tableHead.keySet();
				List<String> listKey = new ArrayList<String>();
				for (String ks : keySet) {
					String value = tableHead.get(ks);
					if (value.contains("列表")) {
						listKey.add(ks);
					}
				}
				for (String key : listKey) {
					tableHead.remove(key);
				}
				// 将表头替换
				if (data.size() > 0) {
					data.set(0, tableHead);
				}

				System.out.println("表头：：" + tableHead);
			} catch (NoSuchFieldException e) {
				logger.error("导出企业时出现的属性未找到异常",e);
			} catch (SecurityException e) {
				logger.error("导出企业时出现的");
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return data;
	}

	public List<SimpleCompanyResponse> statisticsGroupCars() {
		return companyMapper.statisticsGroupCars();
	}

	// 导出企业信息并返回保存路径
	@SuppressWarnings("resource")
	public String exportCompanyInfo(String fileName, Company company) throws Exception {
		if (StringUtils.isBlank(fileName)) {
			fileName = new Date().getTime() + "";
		}
		// 文件模板路径
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate";
		File dire = new File(filePath);
		if (!dire.exists()) {
			throw new RuntimeException("访问路径不存在");
		}
		File[] files = dire.listFiles();
		for (File fs : files) {
			String fileNames = fs.getName();
			if (fileNames.substring(0, fileNames.indexOf(".")).equals(fileName)) {
				// 获取文件类型
				String fileType = fileNames.substring(fileNames.lastIndexOf(".") + 1);
				InputStream is = new FileInputStream(fs);
				// 创建一个工作薄
				Workbook workbook = null;
				if (fileType.equals("xls")) {
					workbook = new HSSFWorkbook(is);
				} else if (fileType.equals("xlsx")) {
					workbook = new XSSFWorkbook(is);
				} else {
					throw new RuntimeException("不是Excel类型");
				}

				// 拼接下载路径
				String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator
						+ "company" + File.separator + new Date().getTime() + "." + fileType;
				String destPath = propertiesService.getWindowsRootPath() + downloadPath;
				File destFile = new File(destPath);

				// 将模板数据copy到指定路径
				FileUtils.copyFile(fs, destFile);
				Sheet sheet = workbook.getSheetAt(0);

				if (sheet != null) {
					Row row1 = sheet.getRow(8);
					// 企业名称
					Cell cell1 = row1.getCell(1);
					cell1.setCellValue(company.getCompanyName());

					// 企业注册地址
					Row row2 = sheet.getRow(9);
					row2.getCell(1).setCellValue(company.getCompanyRegisteredAddress());

					// 企业面积
					row2.getCell(6).setCellValue(company.getCompanyAcreage());

					// 企业自有或租赁
					Row row3 = sheet.getRow(10);
					row3.getCell(6).setCellValue(company.getCompanyOwnLease());

					// 企业停车场地址
					Row row4 = sheet.getRow(11);
					row4.getCell(1).setCellValue(company.getCompanyCarParkAddress());

					// 企业停车场面积
					row4.getCell(6).setCellValue(company.getCompanyCarParkAcreage());

					// 企业停车场自有或租赁
					Row row5 = sheet.getRow(12);
					row5.getCell(6).setCellValue(company.getCompanyCarParkOwnLease());

					// 企业营业执照号
					Row row6 = sheet.getRow(13);
					row6.getCell(1).setCellValue(company.getCompanyBusinessLicense());

					// 企业营业执照截止时间
					Date businessCloseDate = company.getCompanyBusinessLicenseCloseDate();
					row6.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					if (businessCloseDate != null) {
						row6.getCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(businessCloseDate));
					} else {
						row6.getCell(6).setCellValue("");
					}

					// 道路普通货物运输经营许可证号
					Row row7 = sheet.getRow(14);
					row7.getCell(1).setCellValue(company.getCompanyRoadLicense());

					// 道路普通货物运输经营许可证截止时间
					Date roadDate = company.getCompanyRoadLicenseCloseDate();
					row7.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					if (roadDate != null) {
						row7.getCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(roadDate));
					} else {
						row7.getCell(6).setCellValue("");
					}

					// 企业类型
					Row row8 = sheet.getRow(15);
					row8.getCell(1).setCellValue(company.getCompanyType());

					// 建立时间
					Date date = company.getCompanyCreationTime();
					row8.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					if (date != null) {
						row8.getCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(date));
						;
					} else {
						row8.getCell(4).setCellValue("");
						;
					}
					// 企业联系方式
					Row row9 = sheet.getRow(16);
					row9.getCell(1).setCellValue(company.getCompanyContact());

					// 企业传真
					row9.getCell(4).setCellValue(company.getCompanyFacsimile());

					// 企业网址
					Row row10 = sheet.getRow(17);
					row10.getCell(1).setCellValue(company.getCompanyUrl());

					// 企业电子信箱
					row10.getCell(4).setCellValue(company.getCompanyEmail());
					;

					// 法定代表人
					Row row11 = sheet.getRow(18);
					row11.getCell(1).setCellValue(company.getCompanyLegalRepresentative());

					// 法定代表人联系方式
					row11.getCell(4).setCellValue(company.getCompanyLegalRepresentativePhone());

					// 企业经理
					Row row12 = sheet.getRow(19);
					row12.getCell(1).setCellValue(company.getCompanyDirector());

					// 企业经理联系方式
					row12.getCell(4).setCellValue(company.getCompanyDirectorPhone());

					// 车队负责人
					Row row13 = sheet.getRow(20);
					row13.getCell(1).setCellValue(company.getCompanyMotorcadePrincipal());

					// 车队负责人联系方式
					row13.getCell(4).setCellValue(company.getCompanyMotorcadePrincipalPhone());

					// 职工总人数
					Row row14 = sheet.getRow(22);
					if (company.getCompanyEmployeeNumber() != null) {
						row14.getCell(1).setCellValue(company.getCompanyEmployeeNumber());
					}

					// 管理人员人数
					if (company.getCompanyAdministratorNumber() != null) {
						row14.getCell(2).setCellValue(company.getCompanyAdministratorNumber());
					}

					// 驾驶员人数
					if (company.getDriverTotal() != null) {
						row14.getCell(5).setCellValue(company.getDriverTotal());
					}
					FileOutputStream fos = new FileOutputStream(destFile);
					workbook.write(fos);
					fos.flush();
					fos.close();
					return "http://" + propertiesService.getCurrentServerForExcel()
							+ downloadPath.replaceAll("\\\\", "/");
				}
			}
		}
		throw new BizException(StatusCode.INTERNAL_ERROR);
	}

	@Override
	public String queryCompanyNameById(String id) {
		return companyMapper.queryCompanyNameById(id);
	}

	@Override
	public List<Map<String, String>> readExcelData(String path) {
		// 声明一个存放表格数据的Map<String,String>类型的list集合，key值为表头字段名，value为单元格的数据值
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// 如果传出的路径为空
		if (StringUtils.isBlank(path)) {
			return null;
		}
		File file = new File(path);
		// 如果文件不存在
		if (!file.exists()) {
			return null;
		}
		// 如果该目录不是文件
		if (!file.isFile()) {
			return null;
		}
		// 获取文件类型
		String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
		try {
			// 获取文件输入流
			InputStream stream = new FileInputStream(path);
			// 创建一个工作薄
			org.apache.poi.ss.usermodel.Workbook wb = null;
			org.apache.poi.ss.usermodel.Sheet sheet1 = null;
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(stream);
				sheet1 = wb.getSheet("14.企业信息汇总");
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
				sheet1 = wb.getSheet("14.企业信息汇总");
			} else {
				logger.debug("Excel格式不支持");
			}
			// 循环遍历工作薄中的数据
			// 表头每列的字段名
			List<String> tableHeadName = new ArrayList<String>();
			// 如果工作薄对象不为空
			if (sheet1 != null) {
				// 获取表头数据
				// 企业信息表头数据在第一行，下标为0
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(0);
				if (row != null) {
					// 遍历表头数据
					for (org.apache.poi.ss.usermodel.Cell cell : row) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 将表头名称添加到相应的容器中
						tableHeadName.add(cell.getStringCellValue());
					}
				}
			}
			// 遍历除表头之外的每一行数据
			Map<String, String> tableBody = null;
			// sheet1.getLastRowNum() 获取最后一行的行号
			// 表身数据从第2行开始，下标为1
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				tableBody = new HashMap<String, String>();
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(i);

				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for (int j = 0; j < row.getLastCellNum(); j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					// 设置单元格数据为String类型
					if (cell != null) {
						// 将单元格设置为字符串类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 将表头数据添加到map中
						String value = cell.getStringCellValue();
						if (j == 0) {
							if (value != null && value.length() < 31) {
								tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
							} else {
								tableBody.put(tableHeadName.get(j), null);
							}
						} else {
							tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
						}
					}
				}
				list.add(tableBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 剔除所有字段是空的情况下
		List<Map<String, String>> remove = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : list) {
			Set<String> keySet = map.keySet();
			int temp = 0;
			for (String key : keySet) {
				if (map.get(key) == null | "".equals(map.get(key))) {
					temp++;
				}
			}
			if (temp == keySet.size()) {
				remove.add(map);
			}
		}
		list.removeAll(remove);
		return list;
	}

	@Override
	public Map<String, Object> createExcelData(BaseExcelQueryTemplate excelTemplate, BaseEntity baseEntity) {
		// 声明一个存放表格数据的Map
		Map<String, Object> tableData = new HashMap<String, Object>();
		// 自定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfZn = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
		// 获取传入的表格模版的类的对象
		Class<?> cls = excelTemplate.getClass();
		// 获取传入的实体对象的类的对象
		Class<?> encls = baseEntity.getClass();
		// 得到实体对象的属性集合
		Field[] enfs = encls.getDeclaredFields();
		
		// 遍历实体对象属性列表
		try {
			for (Field f : enfs) {
				// 设置属性是可以访问的(私有的也可以)
				f.setAccessible(true);
				// 获取实体对象的属性名
				String name = f.getName();
				// 得到该对象中此属性的值
				Object val = f.get(baseEntity);
				// 如果属性值不为空执行的操作
				if (val != null) {
					String type = f.getGenericType().toString();
					String typeReal = "";
					if (type.contains("<") && type.contains(">")) {
						typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1, type.indexOf("<"));
					} else {
						typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
					}
					if (typeReal.equalsIgnoreCase("date")) {
						Field keyField = cls.getDeclaredField("has_" + name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
						Date date = (Date) val;
						if (name.equals("companyCreationTime")) {
							tableData.put(template.name(), sdfZn.format(date));
						}else {
							tableData.put(template.name(), sdf.format(date));
						}
					} else if (typeReal.equalsIgnoreCase("integer")) {
						Field keyField = cls.getDeclaredField("has_" + name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
						tableData.put(template.name(), val);
					} else if (typeReal.equalsIgnoreCase("boolean")) {
						
					} else {
						Field keyField = cls.getDeclaredField("has_" + name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
						String str = String.valueOf(val);
						tableData.put(template.name(), str);
					}
				}
			}
			return tableData;
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (NoSuchFieldException e) {
		}
		return null;
	}
	
	@Override
	public String createExcelPOI(String fileName, Map<String, Object> excelData, String[] water) {
		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		if (excelData == null) {
			return "";
		}
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator
				+ fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		if (!file.isFile()) {
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径
		 * subdirectory 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator + "company"
				+ File.separator +new Date().getTime()+".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
		// 创建目标文件
		File destFile = new File(destPath);
		
		try {
			//将资源文件拷贝到目标文件下
			FileUtils.copyFile(file, destFile);
			// 获取文件类型
			String fileType = destPath.substring(destPath.lastIndexOf(".") + 1, destPath.length());
			// 获取文件输入流
			InputStream is = new FileInputStream(destFile);
			// 创建一个工作薄
			org.apache.poi.ss.usermodel.Workbook wb = null;
			org.apache.poi.ss.usermodel.Sheet sheet = null;
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
				sheet = wb.getSheet("企业基本情况");
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				sheet = wb.getSheet("企业基本情况");
			} else {
				logger.debug("Excel格式不支持");
			}
			
			if (sheet != null) {
				SimpleDateFormat sdfEn = new SimpleDateFormat("yyyy-MM-dd");
				//创建一个单元格时间格式
				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper creationHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
						creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
						);
				// 企业名称
				Row row1 = sheet.getRow(1);
				Cell cell1 = row1.getCell(1);
				String companyName = (String) excelData.get("企业名称");
				if (!StringUtils.isBlank(companyName)) {
					cell1.setCellValue(companyName);
				}
				// 企业注册地址
				Row row2 = sheet.getRow(2);
				String companyAdd = (String) excelData.get("企业注册地址");
				if (!StringUtils.isBlank(companyAdd)) {
					row2.getCell(1).setCellValue(companyAdd);
				}
				// 企业面积
				row2.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				String companyMianji = (String) excelData.get("企业面积"); 
				if (!StringUtils.isBlank(companyMianji)) {
					row2.getCell(6).setCellValue(companyMianji);
				}
				// 企业自有或租赁
				Row row3 = sheet.getRow(3);
				String ownOrRent = (String) excelData.get("企业自有或租赁");
				if (!StringUtils.isBlank(ownOrRent)) {
					row3.getCell(6).setCellValue(ownOrRent);;
				}
				// 企业停车场地址
				Row row4 = sheet.getRow(4);
				String companyParkAdd = (String) excelData.get("企业停车场地址");
				if (!StringUtils.isBlank(companyParkAdd)) {
					row4.getCell(1).setCellValue(companyParkAdd);;
				}
				// 企业停车场面积
				row4.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				String parkMianji = (String) excelData.get("停车场面积");
				if (!StringUtils.isBlank(parkMianji)) {
					row4.getCell(6).setCellValue(parkMianji);
				}
				// 企业停车场自有或租赁
				Row row5 = sheet.getRow(5);
				String parkOwnOrRent = (String) excelData.get("停车场自有或租赁");
				if (!StringUtils.isBlank(parkOwnOrRent)) {
					row5.getCell(6).setCellValue(parkOwnOrRent);
				}
				// 企业营业执照号
				Row row6 = sheet.getRow(6);
				row6.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String companyBussLicense = (String) excelData.get("营业执照注册号");
				if (!StringUtils.isBlank(companyBussLicense)) {
					row6.getCell(1).setCellValue(companyBussLicense);
				}
				// 企业营业执照号截止日期
				String strDate1 = (String) excelData.get("营业执照注册号截止日期");
				if(!StringUtils.isBlank(strDate1)){
					row6.getCell(6).setCellValue(sdfEn.parse(strDate1));
					//设置时间格式
					row6.getCell(6).setCellStyle(cellStyle);
				}
				
				// 道路普通货物运输经营许可证号
				Row row7 = sheet.getRow(7);
				row7.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				//String companyRoadGeneral = row7.getCell(1).getStringCellValue().trim();
				String companyRoadGeneral = (String) excelData.get("道路普通货物运输经营许可证号");
				if (!StringUtils.isBlank(companyRoadGeneral)) {
					row7.getCell(1).setCellValue(companyRoadGeneral);
				}
				// 道路普通货物运输经营许可证号截止日期
				String strDate2 = (String) excelData.get("道路普通货物运输经营许可证号截止日期");
				if(!StringUtils.isBlank(strDate2)){
					row7.getCell(6).setCellValue(sdfEn.parse(strDate2));
					//设置时间格式
					row7.getCell(6).setCellStyle(cellStyle);
				}
				// 企业类型
				Row row8 = sheet.getRow(8);
				//String companyType = row8.getCell(1).getStringCellValue().trim();
				String companyType = (String) excelData.get("企业类型");
				if (!StringUtils.isBlank(companyType)) {
					row8.getCell(1).setCellValue(companyType);
				}
				//企业建立时间
				String companyCreateTime = (String) excelData.get("企业建立时间");
				if (!StringUtils.isBlank(companyCreateTime)) {
					row8.getCell(4).setCellValue(companyCreateTime);
				}
				// 企业联系电话
				Row row9 = sheet.getRow(9);
				row9.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String companyContact = (String) excelData.get("企业联系电话");
				if (!StringUtils.isBlank(companyContact)) {
					row9.getCell(1).setCellValue(companyContact);
				}
				// 企业传真
				row9.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String companyFacsimile = (String) excelData.get("企业传真");
				if (!StringUtils.isBlank(companyFacsimile)) {
					row9.getCell(4).setCellValue(companyFacsimile);
				}
				// 企业网址
				Row row10 = sheet.getRow(10);
				String companyUrl = (String) excelData.get("企业网址");
				if (!StringUtils.isBlank(companyUrl)) {
					row10.getCell(1).setCellValue(companyUrl);
				}
				// 企业电子信箱
				String companyEmail = (String) excelData.get("企业电子信箱");
				if (!StringUtils.isBlank(companyEmail)) {
					row10.getCell(4).setCellValue(companyEmail);
				}
				// 法定代表人
				Row row11 = sheet.getRow(11);
				String companyLegalRepresentative = (String) excelData.get("法定代表人");
				if (!StringUtils.isBlank(companyLegalRepresentative)) {
					row11.getCell(1).setCellValue(companyLegalRepresentative);
				}
				// 法定代表人联系电话
				row11.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String companyLegalRepresentativePhone = (String) excelData.get("法定代表人联系电话");
				if (!StringUtils.isBlank(companyLegalRepresentativePhone)) {
					row11.getCell(4).setCellValue(companyLegalRepresentativePhone);
				}
				// 企业经理
				Row row12 = sheet.getRow(12);
				String companyDirector = (String) excelData.get("企业经理");
				if (!StringUtils.isBlank(companyDirector)) {
					row12.getCell(1).setCellValue(companyDirector);
				}
				// 企业经理联系电话
				row12.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String companyDirectorPhone = (String) excelData.get("企业经理联系电话");
				if (!StringUtils.isBlank(companyDirectorPhone)) {
					row12.getCell(4).setCellValue(companyDirectorPhone);
				}
				// 车队负责人
				Row row13 = sheet.getRow(13);
				String companyMotorcadePrincipal = (String) excelData.get("车队负责人");
				if (!StringUtils.isBlank(companyMotorcadePrincipal)) {
					row13.getCell(1).setCellValue(companyMotorcadePrincipal);
				}
				// 车队负责人联系电话
				row13.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String companyMotorcadePrincipalPhone = (String) excelData.get("车队负责人联系电话");
				if (!StringUtils.isBlank(companyMotorcadePrincipalPhone)) {
					row13.getCell(4).setCellValue(companyMotorcadePrincipalPhone);
				}
				// 职工总人数
				Row row14 = sheet.getRow(15);
				row14.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyEmployeeNumber = (Integer) excelData.get("职工总人数");
				if (companyEmployeeNumber!=null) {
					row14.getCell(1).setCellValue(companyEmployeeNumber+"");
				}
				// 管理人员人数
				row14.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyAdministratorNumber = (Integer) excelData.get("管理人员人数");
				if (companyAdministratorNumber!=null) {
					row14.getCell(2).setCellValue(companyAdministratorNumber);
				}
				// 驾驶员人数
				row14.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyDriverNumber = (Integer) excelData.get("驾驶员人数");
				if (companyDriverNumber!=null) {
					row14.getCell(5).setCellValue(companyDriverNumber);
				}
				
				// 车辆数量
				Row row18 = sheet.getRow(17);
				// 车辆总数
				row18.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyCarNumber = (Integer) excelData.get("车辆总数");
				if(companyCarNumber!=null){
					row18.getCell(1).setCellValue(companyCarNumber);
				}
				// 黄牌车辆数量
				row18.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyCarYellowCardNumber = (Integer) excelData.get("黄牌车辆数");
				if(companyCarYellowCardNumber!=null){
					row18.getCell(2).setCellValue(companyCarYellowCardNumber);
				}
				// 蓝牌车辆数量
				row18.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				Integer companyCarBlueCardNumber = (Integer) excelData.get("蓝牌车辆数");
				if(companyCarBlueCardNumber!=null){
					row18.getCell(5).setCellValue(companyCarBlueCardNumber);
				}
			}
			if(water != null && water.length > 0){
				// 水印文件存放目录
				String imgPath = propertiesService.getWindowsCreateWaterRootPath() + File.separator + "水印.png";
				// 生成水印图片
				ImageUtils.createWaterMark(water, imgPath);
				// 将水印图片加载到表格中
				ExcelWaterMarkUtils.setWaterMarkToExcel(wb, sheet, imgPath, 0, 10, 9, 52, 3, 50, 0, 0);
			}
			FileOutputStream fs = new FileOutputStream(destFile);
			wb.write(fs);
			fs.flush();
			fs.close();
			//返回导出生成的excel文件在本地的路径
			return destPath;
			//return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");
		} catch (IOException e) {
			logger.error("导出企业出现异常");
		} catch (ParseException e) {
			logger.error("导出企业时时间格式转化异常");
		}
		return null;
	}
	
	//79317931
	
	/*
	 * 导出企业列表（汇总表）包含企业管理人员数据
	 * */
	@Override
	public String createExcelPOI(String fileName, List<Company> listCompany, List<CompanyManager> listCompanyManager,
			String[] water) {
		if (StringUtils.isBlank(fileName)) {
			return "";
		}
		//拼接服务器端存放的模版文件绝对路径
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator
				+ fileName;
		//创建模版文件对象
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		if (!file.isFile()) {
			return "";
		}
		//拼接本地存放表格数据的目标文件绝对路径
		String destPath = propertiesService.getWindowsRootPath() 
				+ File.separator + "excel" + File.separator + "download" 
				+ File.separator + "company"
				+ File.separator +"企业信息汇总表.xlsx";
		// 创建目标文件
		File destFile = new File(destPath);
		try {
			FileUtils.copyFile(file, destFile);
			// 获取文件类型
			String fileType = destPath.substring(destPath.lastIndexOf(".") + 1, destPath.length());
			// 获取文件输入流
			InputStream is = new FileInputStream(destFile);
			// 创建一个工作薄
			org.apache.poi.ss.usermodel.Workbook wb = null;
			org.apache.poi.ss.usermodel.Sheet sheet = null;
			//通过输入流创建表格对象，并获取第一个工作簿，因模版因素只需获取第一个工作簿即可
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else {
				logger.debug("Excel格式不支持");
			}
			if (sheet != null) {
				
				//声明一个map，这个map中存放每个企业的管理人员，key值为企业的加密后的id，
				//value为管理人员列表
				Map<String,List<CompanyManager>> companyManagerMap = new HashMap<String,List<CompanyManager>>();
				if(listCompanyManager != null && listCompanyManager.size() > 0){
					//获取所有的企业id
					List<String> listCompanyIds = new ArrayList<String>();
					for(CompanyManager companyManager : listCompanyManager){
						listCompanyIds.add(companyManager.getCompanyId());
					}
					//去重复
					Set<String> setCompanyIds = new HashSet<String>(listCompanyIds);
					//遍历去重复后的企业id
					for(String companyId : setCompanyIds){
						List<CompanyManager> list = new ArrayList<CompanyManager>();
						//遍历所有的企业管理人员
						for(CompanyManager companyManager : listCompanyManager){
							//根据企业id将企业放到一个list中
							if(companyId.equals(companyManager.getCompanyId())){
								list.add(companyManager);
							}
						}
						//将管理人员列表添加到map中
						companyManagerMap.put(companyId,list);
					}
				}
				
				
				//遍历企业列表
				if(listCompany != null && listCompany.size() > 0){
					//创建一个单元格时间格式
					CellStyle cellStyle = wb.createCellStyle();
					CreationHelper creationHelper = wb.getCreationHelper();
					cellStyle.setDataFormat(
							creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
							);
					//声明当前企业的行码
					Integer temp = 2;
					//声明当前企业管理人员的行码
					Integer managerTemp = 2;
					//序号
					Integer no = 1;
					for(Company company : listCompany){
						
						//获取该企业的管理人员
						List<CompanyManager> companyManagers = companyManagerMap.get(company.getId());
						
						Integer companyManagerCount = 0;
						if(companyManagers != null && companyManagers.size() > 0){
							companyManagerCount = companyManagers.size();
							
							for(CompanyManager companyManager : companyManagers){
								Row rowManager = sheet.createRow(managerTemp);
								//姓名
								Cell cell30 = rowManager.createCell(29);
								cell30.setCellType(Cell.CELL_TYPE_STRING);
								cell30.setCellValue(companyManager.getCompanyManagerName());
								//性别
								Cell cell31 = rowManager.createCell(30);
								cell31.setCellType(Cell.CELL_TYPE_STRING);
								cell31.setCellValue(companyManager.getCompanyManagerSex());
								//岗位
								Cell cell32 = rowManager.createCell(31);
								cell32.setCellType(Cell.CELL_TYPE_STRING);
								cell32.setCellValue(companyManager.getCompanyManagerPost());
								//联系电话
								Cell cell33 = rowManager.createCell(32);
								cell33.setCellType(Cell.CELL_TYPE_STRING);
								cell33.setCellValue(companyManager.getCompanyManagerPhone());
								//身份证号码
								Cell cell34 = rowManager.createCell(33);
								cell34.setCellType(Cell.CELL_TYPE_STRING);
								cell34.setCellValue(companyManager.getCompanyManagerIdNumber());
								//所属企业
								Cell cell35 = rowManager.createCell(34);
								cell35.setCellType(Cell.CELL_TYPE_STRING);
								cell35.setCellValue(company.getCompanyName());
								managerTemp ++ ;
							}
						}
						
						
						//合并单元格 
						//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
						if(companyManagerCount > 1){
							for(int i = 0;i < 29;i++){
								sheet.addMergedRegion(new CellRangeAddress(temp,temp+companyManagerCount-1,i,i));
							}
						}
						//将企业的信息放到工作簿中
						Row row = sheet.getRow(temp);
						if(row == null){
							row = sheet.createRow(temp);
						}
						//序号
						Cell cell1 = row.createCell(0);
						cell1.setCellType(Cell.CELL_TYPE_STRING);
						cell1.setCellValue(no++);
						//企业名称
						Cell cell2 = row.createCell(1);
						cell2.setCellType(Cell.CELL_TYPE_STRING);
						cell2.setCellValue(company.getCompanyName());
						//营业执照注册号
						Cell cell3 = row.createCell(2);
						cell3.setCellType(Cell.CELL_TYPE_STRING);
						cell3.setCellValue(company.getCompanyBusinessLicense());
						//截止时间
						Cell cell4 = row.createCell(3);
						cell4.setCellValue(company.getCompanyBusinessLicenseCloseDate());
						cell4.setCellStyle(cellStyle);
						//企业注册地址
						Cell cell5 = row.createCell(4);
						cell5.setCellType(Cell.CELL_TYPE_STRING);
						cell5.setCellValue(company.getCompanyRegisteredAddress());
						//面积(企业面积)
						Cell cell6 = row.createCell(5);
						cell6.setCellType(Cell.CELL_TYPE_STRING);
						cell6.setCellValue(company.getCompanyAcreage());
						//自有或租赁
						Cell cell7 = row.createCell(6);
						cell7.setCellType(Cell.CELL_TYPE_STRING);
						cell7.setCellValue(company.getCompanyOwnLease());
						//企业停车场地址
						Cell cell8 = row.createCell(7);
						cell8.setCellType(Cell.CELL_TYPE_STRING);
						cell8.setCellValue(company.getCompanyCarParkAddress());
						//面积(停车场面积)
						Cell cell9 = row.createCell(8);
						cell9.setCellType(Cell.CELL_TYPE_STRING);
						cell9.setCellValue(company.getCompanyCarParkAcreage());
						//自有或租赁
						Cell cell10 = row.createCell(9);
						cell10.setCellType(Cell.CELL_TYPE_STRING);
						cell10.setCellValue(company.getCompanyCarParkOwnLease());
						//道路普通货物运输经营许可证号
						Cell cell11 = row.createCell(10);
						cell11.setCellType(Cell.CELL_TYPE_STRING);
						cell11.setCellValue(company.getCompanyRoadLicense());
						//截止时间
						Cell cell12 = row.createCell(11);
						cell12.setCellValue(company.getCompanyRoadLicenseCloseDate());
						cell12.setCellStyle(cellStyle);
						//企业类型
						Cell cell13 = row.createCell(12);
						cell13.setCellType(Cell.CELL_TYPE_STRING);
						cell13.setCellValue(company.getCompanyType());
						//企业建立时间
						Cell cell14 = row.createCell(13);
						cell14.setCellValue(company.getCompanyCreationTime());
						cell14.setCellStyle(cellStyle);
						//企业联系电话
						Cell cell15 = row.createCell(14);
						cell15.setCellType(Cell.CELL_TYPE_STRING);
						cell15.setCellValue(company.getCompanyContact());
						//企业传真
						Cell cell16 = row.createCell(15);
						cell16.setCellType(Cell.CELL_TYPE_STRING);
						cell16.setCellValue(company.getCompanyFacsimile());
						//企业电子信箱
						Cell cell17 = row.createCell(16);
						cell17.setCellType(Cell.CELL_TYPE_STRING);
						cell17.setCellValue(company.getCompanyEmail());
						//法定代表人
						Cell cell18 = row.createCell(17);
						cell18.setCellType(Cell.CELL_TYPE_STRING);
						cell18.setCellValue(company.getCompanyLegalRepresentative());
						//联系电话
						Cell cell19 = row.createCell(18);
						cell19.setCellType(Cell.CELL_TYPE_STRING);
						cell19.setCellValue(company.getCompanyLegalRepresentativePhone());
						//企业经理
						Cell cell20 = row.createCell(19);
						cell20.setCellType(Cell.CELL_TYPE_STRING);
						cell20.setCellValue(company.getCompanyDirector());
						//联系电话
						Cell cell21 = row.createCell(20);
						cell21.setCellType(Cell.CELL_TYPE_STRING);
						cell21.setCellValue(company.getCompanyDirectorPhone());
						//车队负责人
						Cell cell22 = row.createCell(21);
						cell22.setCellType(Cell.CELL_TYPE_STRING);
						cell22.setCellValue(company.getCompanyMotorcadePrincipal());
						//联系电话
						Cell cell23 = row.createCell(22);
						cell23.setCellType(Cell.CELL_TYPE_STRING);
						cell23.setCellValue(company.getCompanyMotorcadePrincipalPhone());
						//职工总人数
						Cell cell24 = row.createCell(23);
						cell24.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyEmployeeNumber = company.getCompanyEmployeeNumber();
						if(companyEmployeeNumber != null){
							cell24.setCellValue(companyEmployeeNumber);
						}else {
							cell24.setCellValue("");
						}
						//管理人员人数
						Cell cell25 = row.createCell(24);
						cell25.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyAdministratorNumber = company.getCompanyAdministratorNumber();
						if(companyAdministratorNumber != null){
							cell25.setCellValue(companyAdministratorNumber);
						}else {
							cell25.setCellValue("");
						}
						//驾驶员人数
						Cell cell26 = row.createCell(25);
						cell26.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyDriverNumber = company.getCompanyDriverNumber();
						if(companyDriverNumber != null){
							cell26.setCellValue(companyDriverNumber);
						}else {
							cell26.setCellValue("");
						}
						//车辆总数
						Cell cell27 = row.createCell(26);
						cell27.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyCarNumber = company.getCompanyCarNumber();
						if(companyCarNumber != null){
							cell27.setCellValue(companyCarNumber);
						}else {
							cell27.setCellValue("");
						}
						//黄牌车辆数
						Cell cell28 = row.createCell(27);
						cell28.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyCarYellowCardNumber = company.getCompanyCarYellowCardNumber();
						if(companyCarYellowCardNumber != null){
							cell28.setCellValue(companyCarYellowCardNumber);
						}else {
							cell28.setCellValue("");
						}
						//蓝牌车辆数
						Cell cell29 = row.createCell(28);
						cell29.setCellType(Cell.CELL_TYPE_NUMERIC);
						Integer companyCarBlueCardNumber = company.getCompanyCarBlueCardNumber();
						if(companyCarBlueCardNumber != null){
							cell29.setCellValue(companyCarBlueCardNumber);
						}else {
							cell29.setCellValue("");
						}
						
						//将企业的行数加上企业管理人员人数，表示下一个企业所在行数
						if(companyManagerCount > 1){
							temp = temp + companyManagerCount;
						}else {
							temp ++ ;
						}
					}
				}
			}
			if(water != null && water.length > 0){
				// 水印文件存放目录
				String imgPath = propertiesService.getWindowsCreateWaterRootPath() + File.separator + "水印"+System.currentTimeMillis()+".png";
				// 生成水印图片
				ImageUtils.createWaterMark(water, imgPath);
				// 将水印图片加载到表格中
				ExcelWaterMarkUtils.setWaterMarkToExcel(wb, sheet, imgPath, 0, 10, 9, 52, 3, 50, 0, 0);
			}
			FileOutputStream fs = new FileOutputStream(destFile);
			wb.write(fs);
			fs.flush();
			fs.close();
			//返回导出生成的excel文件在本地的路径
			return destPath;
		} catch (IOException e) {
			logger.error("导出企业失败，数据流异常");
			return "";
		}
	}

	// -------------------------
	protected BaseMapper<Company> getMapper() {
		return companyMapper;
	}

	@Override
	protected String getFields() {
		return "id, company_name, company_registered_address,area_id,area_name,"
				+ "company_acreage, company_own_lease, company_car_park_address, company_car_park_acreage,"
				+ "company_car_park_own_lease, company_business_license,company_business_license_close_date, company_road_license,company_road_license_close_date,"
				+ "company_type, company_creation_time, company_contact, company_facsimile, company_url,"
				+ "company_email, company_legal_representative, company_legal_representative_phone,"
				+ "company_director, company_director_phone, company_motorcade_principal, company_motorcade_principal_phone,"
				+ "company_employee_number, company_administrator_number, company_driver_number,"
				+ "company_car_number,company_car_yellow_card_number,company_car_blue_card_number,"
				+ "company_principal_name,"
				+ "company_principal_phone, company_scale, company_logo, company_arrival_time, operator_id,"
				+ "operator_name, created_time, updated_time";
	}
}
