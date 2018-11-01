package com.muck.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.BaseEntity;
import com.muck.domain.Car;
import com.muck.domain.CarAndOrCarDriver;
import com.muck.domain.CarDriver;
import com.muck.domain.CarDriverFamilyMember;
import com.muck.domain.CarGroup;
import com.muck.domain.Statistics;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.handler.IdTypeHandler;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CarGroupMapper;
import com.muck.mapper.CarMapper;
import com.muck.query.CarQueryForm;
import com.muck.response.SimpleCarListResponse;
import com.muck.response.StatusCode;
import com.muck.service.CarService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelWaterMarkUtils;
import com.muck.utils.FileStreamUtils;
import com.muck.utils.ImageUtils;
import com.muck.utils.XSSFDateUtil;

/**
 * @Description: 车辆Service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月16日 上午11:19:47
 */
@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService {

	@Autowired
	private CarMapper carMapper;

	@Autowired
	private CarGroupMapper carGroupMapper;

	@Autowired
	private PropertiesService propertiesService;
	/**
	 * 添加车辆
	 */
	public void save(Car car) {

		// 1、判断是否需要保存车辆组,不管是新名称还是旧名称,统一查询数据库
		String carGroupName = car.getCarGroupName();
		if (StringUtils.isNotBlank(carGroupName)) {
			// 如果不为空,则需要查询数据库
			CarGroup carGroup = carGroupMapper.selectByGroupNameAndCompany(carGroupName, car.getCompanyId());
			if (carGroup == null) {
				// 表示不存在,则创建车辆组
				carGroup = new CarGroup();
				carGroup.setCompanyId(car.getCompanyId());
				carGroup.setCompanyName(car.getCompanyName());
				carGroup.setGroupName(carGroupName);
				carGroup.setCreatedTime(new Date());
				carGroup.setUpdatedTime(carGroup.getCreatedTime());
				// 添加车辆组
				carGroupMapper.insert(carGroup);
				car.setCarGroupId(IdTypeHandler.encode(carGroup.getIdRaw()));
			}
			// 将车辆组id和车辆组名称统一设置到car对象中
			car.setCarGroupId(carGroup.getId());
			car.setCarGroupName(carGroupName);
		}

		// 2、保存车辆
		super.save(car);
	}

	@Override
	public List<Car> queryData(String wherSQL, List<Object> whereParams) {
		// 第一步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? " where 1 = 1 " : " where " + wherSQL;
		// 第二步、拼接sql语句
		String selectSQL = " SELECT " + "car.id, " + "car.car_code, " + "car.car_card_color, "
				+ "car.car_driver_name, " + "car.car_driver_phone, " + "car.car_driver_id_number, "
				+ "car.car_type," + "car.car_color, " + "car.car_image_path,"
				+ "car.area_id, " + "car.area_name, " + "car.company_id," + "car.company_name, "
				+ "car.company_principal_name," + "car.company_principal_phone," +"car.company_contact,"+ "car.car_group_id, "
				+ "car.car_group_name, " + "car.car_gps_machine_number," + "car.car_vehicle_model, " + "car.is_lock,"
				+ "car.is_running, " + "car.car_engine_number," + "car.car_use_nature, " + "car.car_wpmi, "
				+ "car.force_scrap," + "car.car_closing_date," +"car.vouch_payload," + "car.outline_size," + "car.brand_model, "
				+ "car.registration_date, " + "car.road_transport_license_number," + "car.certificate_date,"
				+ "car.obturator, " + "car.cargps, " + "car.reflect_light_number,"
				+ "car.jet_printing_company_name," + "car.paste_reflect_stickers," + "car.install_spray_system, "
				+ "car.memo, " + "car.operator_id, " + "car.operator_name," + "car.created_time," +"car.car_owner_of_vehicle,"
				+ "car_driver.driver_name," + "car_driver.driver_phone" + " from "
				+ " t_car car left join t_car_driver car_driver on car.car_code = car_driver.car_code and car.car_driver_id_number = car_driver.id_number " + where;
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}
	@Override
	public Car queryByIdNumber(String idNumber) {
		return carMapper.queryByIdNumber(idNumber);
	}

	@Override
	public List<Car> queryDataForElectricFence(String whereSQL, List<Object> whereParams) {
		// 第一步、获取where条件
		String where = StringUtils.isBlank(whereSQL) ? " where 1 = 1 " : " where " + whereSQL;
		String selectSQL = "SELECT "
				+ " car.car_code,car.company_id,car.company_name,car.car_owner_of_vehicle,car.company_contact, "
				+ " ef.id as electric_fence_id,ef.electric_fence_name"
				+ " from "
				+ " t_car car left join t_electric_fence_car efc on car.car_code = efc.car_code "
				+ " left join t_electric_fence ef on efc.electric_fence_id = ef.id"
				+ where;
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return carMapper.queryDataForElectricFence(selectSQL);
	}
	@Override
	public void editByCarCode(Car car) {
		carMapper.editByCarCode(car);
	}

	
	@Override
	public List<Car> readExcelListData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		List<Car> listCar = new ArrayList<Car>();
		File file = new File(path);
		// 如果文件不存在
		if(!file.exists()){
			return listCar;
		}
		// 如果该目录不是文件
		if(!file.isFile()){
			return listCar;
		}
		// 获取文件类型
		String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
		
		try {
			InputStream is = new FileInputStream(path);
			org.apache.poi.ss.usermodel.Workbook wb = null;
			if("xls".equals(fileType)){
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
			}else if ("xlsx".equals(fileType)){
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
			}
			Iterator<org.apache.poi.ss.usermodel.Sheet> sheetIterator = wb.sheetIterator();
			while(sheetIterator.hasNext()){
				org.apache.poi.ss.usermodel.Sheet sheet = sheetIterator.next();
				// 循环遍历工作薄中的数据
				// 获取模版中的字段上的注解，并放到一个list中
				List<String> tableKeyName = new ArrayList<String>();

				if (baseExcelQueryTemplate != null) {
					// 获取对象的类的对象
					Class<?> cls = baseExcelQueryTemplate.getClass();
					if (cls != null) {
						// 获取对象的类的对象的属性集合
						Field[] fields = cls.getDeclaredFields();
						if (fields != null) {
							// 遍历属性数组
							for (Field field : fields) {
								// 获取属性上面指定的注解
								ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
								// 获取注解指定属性的值
								String name = excelTemplate.name();
								// 将属性值添加到指定的list集合中
								tableKeyName.add(name);
							}
						}
					}
				}
				// 遍历除表头之外的每一行数据
				Map<String, String> tableBody = new HashMap<String, String>();
				// 2表示从第三行开始，9表示到第十行结束
				for (int i = 2; i <= 9; i++) {
					// 获取当前行的数据
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
					// 遍历当前行的每一列数据
					// row.getLastCellNum() 获取当前行的最后一列的列号
					for (int j = 0; j < row.getLastCellNum(); j++) {
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 如果当前单元格里的内容包含在表格字段列表中
						if (tableKeyName.contains(cell.getStringCellValue().trim())) {
							// 则获取该单元格右面的一个单元格的内容
							org.apache.poi.ss.usermodel.Cell cellValue = row.getCell(j + 1);
							if(cellValue  != null){
								if (cellValue.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
									// 判断当前单元格是否进行时间格式化了
									if (XSSFDateUtil.isCellDateFormatted(cellValue)) {
										tableBody.put(cell.getStringCellValue(),
												new SimpleDateFormat("yyyy-MM-dd").format(cellValue.getDateCellValue()));
									}else {
										short format = cellValue.getCellStyle().getDataFormat();
										if(format == 14 | format == 20 | format == 31| format == 32 | format == 57 | format == 58 | format == 176){
											tableBody.put(cell.getStringCellValue(), new SimpleDateFormat("yyyy-MM-dd")
													.format(cellValue.getDateCellValue()));
										}else {
											cellValue.setCellType(Cell.CELL_TYPE_STRING);
											tableBody.put(cell.getStringCellValue(), cellValue.getStringCellValue());
										}
									}
								}else{
									// 将数据添加到指定的map中
									cellValue.setCellType(Cell.CELL_TYPE_STRING);
									tableBody.put(cell.getStringCellValue(), cellValue.getStringCellValue());
								}
							}
						}
					}
				}
				// 获取当前的实体
				Class<?> cs = Class.forName(this.entityClass.getName());
				// 通过字节码获取实例
				Object obj = cs.newInstance();

				if (baseExcelQueryTemplate != null) {
					// 获取对象的类的对象
					Class<?> cls = baseExcelQueryTemplate.getClass();
					if (cls != null) {
						// 获取对象的类的对象的属性集合
						Field[] fields = cls.getDeclaredFields();
						if (fields != null) {
							// 遍历属性数组
							for (Field field : fields) {
								// 获取ExcelTemplate注解
								ExcelTemplate template = field.getAnnotation(ExcelTemplate.class);
								// 获取注解的name属性值
								String name = template.name();
								// 通过name属性值获取map中的对应的值
								String value = tableBody.get(name);
								// 获取当前实体中指定的属性
								Field f = cs.getDeclaredField(field.getName().replace("has_", ""));
								// 设置属性是可以访问的
								f.setAccessible(true);
								String type = f.getGenericType().toString();
								String typeReal = "";
								if (type.contains("<") && type.contains(">")) {
									typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1,
											type.indexOf("<"));
								} else {
									typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
								}
								if ("date".equalsIgnoreCase(typeReal)) {
									if(!StringUtils.isBlank(value)){
										try {
											f.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(value));
										} catch (ParseException e) {
											try {
												f.set(obj, new SimpleDateFormat("yyyy.MM.dd").parse(value));
											} catch (ParseException e1) {
												try {
													f.set(obj, new SimpleDateFormat("yyyy年MM月dd日").parse(value));
												} catch (ParseException e2) {
													String str = value ;
													if(!StringUtils.isBlank(str)){
														if(str.contains(".")){
															String [] arrStr = str.split(".");
															if(arrStr != null){
																String date = "";
																if(arrStr.length == 1){
																	if(arrStr[0].length() == 2){
																		date = "19"+arrStr[0]+"-01-01";
																	}else if (arrStr[0].length() == 4){
																		date = arrStr[0]+"-01-01";
																	}
																}else if (arrStr.length == 2){
																	if(arrStr[0].length() == 2){
																		date = "19"+arrStr[0]+"-"+arrStr[1]+"-01";
																	}else if (arrStr[0].length() == 4){
																		date = arrStr[0]+"-"+arrStr[1]+"-01";
																	}
																	
																}else if (arrStr.length == 3){
																	if(arrStr[0].length() == 2){
																		date = "19"+arrStr[0]+"-"+arrStr[1]+"-"+arrStr[2];
																		
																	}else if (arrStr[0].length() == 4){
																		date = arrStr[0]+"-"+arrStr[1]+"-"+arrStr[2];
																	}
																}
																try {
																	if(!StringUtils.isBlank(date)){
																		f.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(date));
																	}
																} catch (ParseException e3) {
																	logger.error("时间格式转化异常");
																	e3.printStackTrace();
																}
															}
														}
													}
												}
											}
											
										}
									}
								} else if ("integer".equalsIgnoreCase(typeReal)) {
									if(!StringUtils.isBlank(value)){
										f.set(obj, Integer.parseInt(value));
									}
								} else if ("boolean".equalsIgnoreCase(typeReal)){
									if(value == null){
										f.set(obj, false);
									}else if("否".equals(value)){
										f.set(obj, false);
									}else if("是".equals(value)){
										f.set(obj, true);
									}
								} else {
									if(value != null){
										if("黄".equals(value.trim())){
											f.set(obj, 2+"");
										}else if("黄色".equals(value.trim())){
											f.set(obj, 2+"");
										}else if("蓝".equals(value.trim())){
											f.set(obj, 1+"");
										}else if("蓝色".equals(value.trim())){
											f.set(obj, 1+"");
										}else {
											f.set(obj, value);
										}
									}else{
										f.set(obj, value);
									}
								}
							}
						}
					}
				}
				// 将通过反射获取到的数据封装到当前的类型中返回
				Car car = (Car) cs.cast(obj);
				listCar.add(car);
			}
		} catch (FileNotFoundException e) {
			logger.error("导入车辆时出现文件未找到异常",e);
		} catch (IOException e) {
			logger.error("导入车辆时出现数据流异常",e);
		} catch (ClassNotFoundException e) {
			logger.error("导入车辆时出现加载类未找到异常",e);
		} catch (NumberFormatException e) {
			logger.error("导入车辆时出现数值转化异常",e);
		} catch (IllegalArgumentException e) {
			logger.error("导入车辆时出现的异常",e);
		} catch (IllegalAccessException e) {
			logger.error("导入车辆时出现的异常");
		} catch (InstantiationException e) {
			logger.error("导入车辆时出现的异常");
		} catch (NoSuchFieldException e) {
			logger.error("导入车辆时出现属性未找到异常",e);
		} catch (SecurityException e) {
			logger.error("导入车辆时出现的异常",e);
		} catch (Exception e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return listCar;
	}

	@Override
	public Car readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
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
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
			} else {
			}
			org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheet("13.建筑垃圾运输车辆情况表");
			// 循环遍历工作薄中的数据
			// 获取模版中的字段上的注解，并放到一个list中
			List<String> tableKeyName = new ArrayList<String>();

			if (baseExcelQueryTemplate != null) {
				// 获取对象的类的对象
				Class<?> cls = baseExcelQueryTemplate.getClass();
				if (cls != null) {
					// 获取对象的类的对象的属性集合
					Field[] fields = cls.getDeclaredFields();
					if (fields != null) {
						// 遍历属性数组
						for (Field field : fields) {
							// 获取属性上面指定的注解
							ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
							// 获取注解指定属性的值
							String name = excelTemplate.name();
							// 将属性值添加到指定的list集合中
							tableKeyName.add(name);
						}
					}
				}
			}

			// 遍历除表头之外的每一行数据
			Map<String, String> tableBody = new HashMap<String, String>();
			// 2表示从第三行开始，10表示到第十一行结束
			for (int i = 2; i <= 10; i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(i);
				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for (int j = 0; j < row.getLastCellNum(); j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					// 设置单元格数据为String类型
					cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
					// 如果当前单元格里的内容包含在表格字段列表中
					if (tableKeyName.contains(cell.getStringCellValue())) {
						// 则获取该单元格右面的一个单元格的内容
						org.apache.poi.ss.usermodel.Cell cellValue = row.getCell(j + 1);
						if(cellValue  != null){
							if (cellValue.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
								// 判断当前单元格是否进行时间格式化了
								if (XSSFDateUtil.isCellDateFormatted(cellValue)) {
									tableBody.put(cell.getStringCellValue(),
											new SimpleDateFormat("yyyy-MM-dd").format(cellValue.getDateCellValue()));
								}
							}else{
								// 将数据添加到指定的map中
								tableBody.put(cell.getStringCellValue(), cellValue.getStringCellValue());
							}
						}
					}
				}
			}
			// 获取当前的实体
			Class<?> cs = Class.forName(this.entityClass.getName());
			// 通过字节码获取实例
			Object obj = cs.newInstance();

			if (baseExcelQueryTemplate != null) {
				// 获取对象的类的对象
				Class<?> cls = baseExcelQueryTemplate.getClass();
				if (cls != null) {
					// 获取对象的类的对象的属性集合
					Field[] fields = cls.getDeclaredFields();
					if (fields != null) {
						// 遍历属性数组
						for (Field field : fields) {
							// 获取ExcelTemplate注解
							ExcelTemplate template = field.getAnnotation(ExcelTemplate.class);
							// 获取注解的name属性值
							String name = template.name();
							// 通过name属性值获取map中的对应的值
							String value = tableBody.get(name);
							// 获取当前实体中指定的属性
							Field f = cs.getDeclaredField(field.getName().replace("has_", ""));
							// 设置属性是可以访问的
							f.setAccessible(true);
							String type = f.getGenericType().toString();
							String typeReal = "";
							if (type.contains("<") && type.contains(">")) {
								typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1,
										type.indexOf("<"));
							} else {
								typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
							}
							if ("date".equalsIgnoreCase(typeReal)) {
								try {
									if(!StringUtils.isBlank(value)){
										f.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(value));
									}
								} catch (ParseException e) {
									logger.error("导入车辆时出现时间格式转化异常",e);
								}
							} else if ("integer".equalsIgnoreCase(typeReal)) {
								if(!StringUtils.isBlank(value)){
									f.set(obj, Integer.parseInt(value));
								}
							} else {
								f.set(obj, value);
							}
						}
					}
				}
			}
			// 将通过反射获取到的数据封装到当前的类型中返回
			Car car = (Car) cs.cast(obj);
			return car;
		} catch (IOException e) {
			logger.error("导入车辆时出现的数据流异常",e);
		} catch (ClassNotFoundException e) {
			logger.error("导入车辆时出现加载类未找到异常",e);
		} catch (InstantiationException e) {
			logger.error("导入车辆时出现的异常",e);
		} catch (IllegalAccessException e) {
			logger.error("导入车辆时出现的异常",e);
		} catch (NoSuchFieldException e) {
			logger.error("导入车辆时出现属性未找到异常",e);
		} catch (SecurityException e) {
			logger.error("导入车辆时出现的异常",e);
		} catch (IllegalArgumentException e) {
			logger.error("导入车辆时出现的异常",e);
		}
		return null;
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
				sheet1 = wb.getSheet("9.企业车辆情况汇总");
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
				sheet1 = wb.getSheet("9.企业车辆情况汇总");
			} else {
				logger.debug("Excel格式不支持");
			}
			// 循环遍历工作薄中的数据
			// 表头每列的字段名
			List<String> tableHeadName = new ArrayList<String>();
			// 如果工作薄对象不为空
			if (sheet1 != null) {
				// 获取表头数据
				// 企业车辆情况汇总表头数据在第二行，下标为1
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(1);
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
			// 表身数据从第3行开始，下标为2
			for (int i = 2; i <= sheet1.getLastRowNum(); i++) {
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
						if(j == 0){
							if(value != null && value.length() < 31){
								tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
							}else {
								tableBody.put(tableHeadName.get(j),null);
							}
						}else {
							tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
						}
					}
				}
				list.add(tableBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//剔除所有字段是空的情况下
		List<Map<String,String>> remove = new ArrayList<Map<String,String>>();
		for(Map<String,String> map : list){
			Set<String> keySet =map.keySet();
			int temp = 0;
			for(String key : keySet){
				if(map.get(key) == null | "".equals(map.get(key))){
					temp ++;
				}
			}
			if(temp == keySet.size()){
				remove.add(map);
			}
		}
		list.removeAll(remove);
		return list;
	}
	
	@Override
	public Map<String, Object> createExcelData(BaseExcelQueryTemplate excelTemplate, BaseEntity baseEntity) {
		// 声明一个存放表格数据的Map
		Map<String,Object> tableData = new HashMap<String,Object>();
		//自定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				//获取实体对象的属性名
				String name = f.getName();
				// 得到该对象中此属性的值
				Object val = f.get(baseEntity);
				// 如果属性值不为空执行的操作
				if (val != null) {
					String type = f.getGenericType().toString();
					String typeReal = "";
					if (type.contains("<") && type.contains(">")) {
						typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1,
								type.indexOf("<"));
					} else {
						typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
					}
					if(typeReal.equalsIgnoreCase("date")){
						Field keyField = cls.getDeclaredField("has_"+name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class); 
						Date date = (Date) val;
						tableData.put(template.name(),sdf.format(date));
					}else if(typeReal.equalsIgnoreCase("integer")){
						Field keyField = cls.getDeclaredField("has_"+name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class); 
						String str = String.valueOf(val);
						tableData.put(template.name(),str);
					}else {
						Field keyField = cls.getDeclaredField("has_"+name);
						ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
						String str = String.valueOf(val);
						tableData.put(template.name(),str);
					}
				}
			}
			return tableData;
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (NoSuchFieldException e) {
			System.out.println("未找到的属性");
		}
		
		return null;
	}

	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<Car> list) {
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
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		// 生成表身数据
		// 遍历传入的数据列表
		if (list != null) {
			try {
				for (Car car : list) {
					tableBody = new TreeMap<String, String>();
					// 获取对象的类的对象
					Class<?> tcls = car.getClass();
					// 遍历表头map的所有的key
					Set<String> keySet = tableHead.keySet();
					// 遍历表头map所有的key
					for (String key : new ArrayList<String>(keySet)) {
						// 通过key获取列表数据对象中的对应的属性
						// 将key中的has_替换成"",保持和数据实体类中的属性名一致
						Field filed = null;
						if (key.contains("has_")) {
							filed = tcls.getDeclaredField(key.replace("has_", ""));
						}
						if (filed != null) {
							// 设置属性是可以访问的
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
							Object obj = filed.get(car);
							if (obj != null) {
								// 如果获取到的是时间类型，则转化一下时间格式
								if ("date".equalsIgnoreCase(typeReal)) {
									Date date = (Date) obj;
									// 将该key与属性值添加到当前条数据的Excel表单元格中
									tableBody.put(key, sdf.format(date));
								} else if ("list".equalsIgnoreCase(typeReal)) {
									if ("carDrivers".equals(filed.getName())) {
										List<CarDriver> carDrivers = car.getCarDrivers();
										if (carDrivers != null && carDrivers.size() > 0) {
											// 遍历车辆下面的驾驶员列表
											Integer temp = 1;
											for (CarDriver carDriver : carDrivers) {
												// 追加表头数据
												tableHead.put("驾驶员" + temp + "姓名", "驾驶员" + temp + "姓名");
												tableHead.put("驾驶员" + temp + "联系方式", "驾驶员" + temp + "联系方式");
												// 追加表身数据
												tableBody.put("驾驶员" + temp + "姓名", carDriver.getDriverName());
												tableBody.put("驾驶员" + temp + "联系方式", carDriver.getDriverPhone());
												temp++;
											}
										}
									}
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
			} catch (NoSuchFieldException e) {
				System.out.println("未找到的属性！");
				e.printStackTrace();
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return data;
	}

	//=========================将车辆列表导出到一个excel表中，一个车辆占用一个工作簿的操作===================
	
	@Override
	public String createExcelPOI(BaseExcelQueryTemplate excelTemplate, String fileName, List<Car> data, String[] water) {
		//获取模版文件路径
		String filePath = propertiesService.getWindowsRootPath()+File.separator+"excelTemplate"+File.separator+fileName;
		
		File file = new File(filePath);
		if(!file.exists()){
			logger.error("导出车辆详情列表到一个工作簿中时车辆模版不存在");
			return "";
		}
		if(!file.isFile()){
			logger.error("导出车辆详情列表到一个工作簿中时传入的不是一个模板文件名");
			return "";
		}
		
		if(data == null | data.size() == 0 ){
			return "";
		}
		//获取excel表格数据，并放到一个list中
		List<Map<String,Object>> listCarMaps = new ArrayList<Map<String,Object>>();
		for(Car car : data){
			Map<String,Object> map = createExcelData(excelTemplate,car);
			listCarMaps.add(map);
		}
		if(listCarMaps.size() == 0){
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath
		 * 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径 subdirectory
		 * 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath =File.separator+ "excel"+ File.separator + "download" + File.separator + "car" + File.separator
				+ "企业建筑垃圾运输车辆情况表"+ ".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
		// 创建目标文件
		File destFile = new File(destPath);
		try {
			FileUtils.copyFile(file, destFile);
			// 获取文件类型
			String fileType = destPath.substring(destPath.lastIndexOf(".") + 1, destPath.length());
			//获取文件输入流
			InputStream is = new FileInputStream(destFile);
			// 创建一个工作薄
			org.apache.poi.ss.usermodel.Workbook wb = null;
			org.apache.poi.ss.usermodel.Sheet sheet = null;
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
			} else {
				logger.debug("Excel格式不支持");
			}
			//如果数据总数小于工作簿数量，则删除多余的工作簿
			if(listCarMaps.size() <= wb.getNumberOfSheets()){
				for(int index = listCarMaps.size() ;index < wb.getNumberOfSheets();index++){
					wb.removeSheetAt(index);
				}
			}
			Integer temp = 0;
			for(Map<String,Object> map : listCarMaps){
				sheet = wb.getSheetAt(temp);
				// 2表示从第三行开始，9表示到第十行结束
				for (int i = 2; i <= 9; i++) {
					// 获取当前行的数据
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
					// 遍历当前行的每一列数据
					// row.getLastCellNum() 获取当前行的最后一列的列号
					for(int j = 0; j < row.getLastCellNum(); j++){
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 如果当前单元格里的内容包含在map的key值中
						String cellStringValue = cell.getStringCellValue().replace(" ", "");
						//根据字段名获取value值
						if(map.containsKey(cellStringValue)){
							org.apache.poi.ss.usermodel.Cell cellValue = row.getCell(j+1);
							Object value = map.get(cellStringValue);
							if(value != null && value instanceof String){
								cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								cellValue.setCellValue(String.valueOf(value));
							}
						}
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
				temp ++;
			}
			wb.close();
			return destPath;
		} catch (IOException e) {
			System.out.println("走到这里了");
		}
		return null;
	}

	
	//导出车辆和驾驶员汇总表
	@Override
	public String createExcelPOI(List<CarAndOrCarDriver> carAndOrCarDrivers, String fileName, String[] water) {
		if(StringUtils.isBlank(fileName)){
			return "";
		}
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator + fileName;
		File file = new File(filePath);
		if(!file.exists()){
			return "";
		}
		if(!file.isFile()){
			return "";
		}
		//拼接本地存放表格数据的目标文件绝对路径
		String destPath = propertiesService.getWindowsRootPath() 
				+ File.separator + "excel" + File.separator + "download" 
				+ File.separator + "carAndCarDriver"
				+ File.separator +"企业驾驶人员车辆汇总表.xlsx";
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
			
			if(sheet != null){
				if(carAndOrCarDrivers != null && carAndOrCarDrivers.size() > 0){
					//创建一个单元格时间格式
					CellStyle cellStyle = wb.createCellStyle();
					CreationHelper creationHelper = wb.getCreationHelper();
					cellStyle.setDataFormat(
							creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
							);
					
					//声明当前车辆驾驶员信息的行码
					Integer temp = 2;
					//声明当前驾驶员家属的行码
					Integer familyTemp = 2;
					//序号
					Integer no = 1;
					//遍历车辆驾驶员列表
					for(CarAndOrCarDriver carAndOrCarDriver : carAndOrCarDrivers){
					
						//获取驾驶员家属
						List<CarDriverFamilyMember> carDriverFamilyMembers = carAndOrCarDriver.getCarDriverFamilyMembers();
						//驾驶员家庭成员数量
						Integer carDriverFamilyMemberCount = 0;
						if(carDriverFamilyMembers != null && carDriverFamilyMembers.size() > 0){
							carDriverFamilyMemberCount = carDriverFamilyMembers.size();
							for(CarDriverFamilyMember carDriverFamilyMember : carDriverFamilyMembers){
								Row rowCarDriverFamilyMember = sheet.createRow(familyTemp);
								//家庭成员
								Cell cell28 = rowCarDriverFamilyMember.createCell(27);
								cell28.setCellType(Cell.CELL_TYPE_STRING);
								cell28.setCellValue(carDriverFamilyMember.getName());
								//关系
								Cell cell29 = rowCarDriverFamilyMember.createCell(28);
								cell29.setCellType(Cell.CELL_TYPE_STRING);
								cell29.setCellValue(carDriverFamilyMember.getRelation());
								//联系电话
								Cell cell30 = rowCarDriverFamilyMember.createCell(29);
								cell30.setCellType(Cell.CELL_TYPE_STRING);
								cell30.setCellValue(carDriverFamilyMember.getPhone());
								//工作单位
								Cell cell31 = rowCarDriverFamilyMember.createCell(30);
								cell31.setCellType(Cell.CELL_TYPE_STRING);
								cell31.setCellValue(carDriverFamilyMember.getWorkUnit());
								familyTemp++;
							}
						}
						
						//合并单元格 
						//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
						if(carDriverFamilyMemberCount > 1){
							for(int i = 0;i < 27;i++){
								sheet.addMergedRegion(new CellRangeAddress(temp,temp+carDriverFamilyMemberCount-1,i,i));
							}
						}
						//将车辆驾驶员信息放到excel中
						Row row = sheet.getRow(temp);
						if(row == null){
							row = sheet.createRow(temp);
						}
						//序号
						Cell cell1 = row.createCell(0);
						cell1.setCellType(Cell.CELL_TYPE_STRING);
						cell1.setCellValue(no++);
						//车辆所有人（企业名称）
						Cell cell2 = row.createCell(1);
						cell2.setCellType(Cell.CELL_TYPE_STRING);
						cell2.setCellValue(carAndOrCarDriver.getCarOwnerOfVehicle());
						//车牌号
						Cell cell3 = row.createCell(2);
						cell3.setCellType(Cell.CELL_TYPE_STRING);
						cell3.setCellValue(carAndOrCarDriver.getCarCode());
						//发动机号码
						Cell cell4 = row.createCell(3);
						cell4.setCellType(Cell.CELL_TYPE_STRING);
						cell4.setCellValue(carAndOrCarDriver.getCarEngineNumber());
						//车辆类型
						Cell cell5 = row.createCell(4);
						cell5.setCellType(Cell.CELL_TYPE_STRING);
						cell5.setCellValue(carAndOrCarDriver.getCarType());
						//车辆识别代号
						Cell cell6 = row.createCell(5);
						cell6.setCellType(Cell.CELL_TYPE_STRING);
						cell6.setCellValue(carAndOrCarDriver.getCarWpmi());
						//使用性质
						Cell cell7 = row.createCell(6);
						cell7.setCellType(Cell.CELL_TYPE_STRING);
						cell7.setCellValue(carAndOrCarDriver.getCarUseNature());
						//核定载质量
						Cell cell8 = row.createCell(7);
						cell8.setCellType(Cell.CELL_TYPE_STRING);
						cell8.setCellValue(carAndOrCarDriver.getVouchPayload());
						//外廓尺寸
						Cell cell9 = row.createCell(8);
						cell9.setCellType(Cell.CELL_TYPE_STRING);
						cell9.setCellValue(carAndOrCarDriver.getOutlineSize());
						//品牌型号
						Cell cell10 = row.createCell(9);
						cell10.setCellType(Cell.CELL_TYPE_STRING);
						cell10.setCellValue(carAndOrCarDriver.getCarTradeMark());
						//注册登记日期
						Cell cell11 = row.createCell(10);
						cell11.setCellValue(carAndOrCarDriver.getRegistrationDate());
						cell11.setCellStyle(cellStyle);
						//强制报废期止
						Cell cell12 = row.createCell(11);
						cell12.setCellValue(carAndOrCarDriver.getForceScrap());
						cell12.setCellStyle(cellStyle);
						//道路运输证号
						Cell cell13 = row.createCell(12);
						cell13.setCellType(Cell.CELL_TYPE_STRING);
						cell13.setCellValue(carAndOrCarDriver.getRoadTransportLicenseNumber());
						//车牌颜色
						Cell cell14 = row.createCell(13);
						cell14.setCellType(Cell.CELL_TYPE_STRING);
						String carCardColor = carAndOrCarDriver.getCarCardColor();
						if(carCardColor != null){
							if("1".equals(carCardColor)){
								cell14.setCellValue("蓝色");
							}else if("2".equals(carCardColor)){
								cell14.setCellValue("黄色");
							}
						}
						//车辆分组
						Cell cell15 = row.createCell(14);
						cell15.setCellType(Cell.CELL_TYPE_STRING);
						cell15.setCellValue(carAndOrCarDriver.getCarGroupName());
						//驾驶人员
						Cell cell16 = row.createCell(15);
						cell16.setCellType(Cell.CELL_TYPE_STRING);
						cell16.setCellValue(carAndOrCarDriver.getDriverName());
						//驾驶员联系电话
						Cell cell17 = row.createCell(16);
						cell17.setCellType(Cell.CELL_TYPE_STRING);
						cell17.setCellValue(carAndOrCarDriver.getDriverPhone());
						//性别
						Cell cell18 = row.createCell(17);
						cell18.setCellType(Cell.CELL_TYPE_STRING);
						cell18.setCellValue(carAndOrCarDriver.getDriverSex());
						//出生年月
						Cell cell19 = row.createCell(18);
						cell19.setCellValue(carAndOrCarDriver.getDriverBirth());
						cell19.setCellStyle(cellStyle);
						//民族
						Cell cell20 = row.createCell(19);
						cell20.setCellType(Cell.CELL_TYPE_STRING);
						cell20.setCellValue(carAndOrCarDriver.getDriverNation());
						//婚否
						Cell cell21 = row.createCell(20);
						cell21.setCellType(Cell.CELL_TYPE_STRING);
						cell21.setCellValue(carAndOrCarDriver.getIsMarry());
						//学历
						Cell cell22 = row.createCell(21);
						cell22.setCellType(Cell.CELL_TYPE_STRING);
						cell22.setCellValue(carAndOrCarDriver.getDriverEducation());
						//身份证号
						Cell cell23 = row.createCell(22);
						cell23.setCellType(Cell.CELL_TYPE_STRING);
						cell23.setCellValue(carAndOrCarDriver.getIdNumber());
						//身份证地址
						Cell cell24 = row.createCell(23);
						cell24.setCellType(Cell.CELL_TYPE_STRING);
						cell24.setCellValue(carAndOrCarDriver.getIdNumberAddress());
						//现住址
						Cell cell25 = row.createCell(24);
						cell25.setCellType(Cell.CELL_TYPE_STRING);
						cell25.setCellValue(carAndOrCarDriver.getLiveAddress());
						//道路运输从业人员从业资格证
						Cell cell26 = row.createCell(25);
						cell26.setCellType(Cell.CELL_TYPE_STRING);
						cell26.setCellValue(carAndOrCarDriver.getQualificationCertificate());
						//驾驶证档案编号
						Cell cell27 = row.createCell(26);
						cell27.setCellType(Cell.CELL_TYPE_STRING);
						cell27.setCellValue(carAndOrCarDriver.getDriverLicenseFileNumber());
						
						//将车辆驾驶员信息的行数加上驾驶员家庭成员人数,表示下一个车辆驾驶员数据所在行数
						if(carDriverFamilyMemberCount > 1){
							temp = temp + carDriverFamilyMemberCount;
						}else {
							temp ++ ;
						}
					}
				}
			}
			if(water != null && water.length > 0){
				// 水印文件存放目录
				String imgPath = propertiesService.getWindowsCreateWaterRootPath() + File.separator +"水印"+System.currentTimeMillis()+".png";
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
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println("数据流异常");
		}
		return null;
	}
	
	//导出单个车辆
	@Override
	public String createExcelPOI(String fileName, Map<String, Object> excelData, String[] water) {
		if(StringUtils.isBlank(fileName)){
			return "";
		}
		if(excelData == null){
			return "";
		}
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator + fileName;
		File file = new File(filePath);
		if(!file.exists()){
			return "";
		}
		if(!file.isFile()){
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath
		 * 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径 subdirectory
		 * 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath =File.separator+ "excel"+ File.separator + "download" + File.separator + "car" + File.separator
				+ new Date().getTime() + ".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
		// 创建目标文件
		File destFile = new File(destPath);
		try {
			FileUtils.copyFile(file, destFile);
			// 获取文件类型
			String fileType = destPath.substring(destPath.lastIndexOf(".") + 1, destPath.length());
			//获取文件输入流
			InputStream is = new FileInputStream(destFile);
			// 创建一个工作薄
			org.apache.poi.ss.usermodel.Workbook wb = null;
			org.apache.poi.ss.usermodel.Sheet sheet = null;
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else {
				logger.debug("Excel格式不支持");
			}
			// 2表示从第三行开始，10表示到第十一行结束
			for (int i = 2; i <= 10; i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for(int j = 0; j < row.getLastCellNum(); j++){
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					// 设置单元格数据为String类型
					cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
					// 如果当前单元格里的内容包含在map的key值中
					String cellStringValue = cell.getStringCellValue().replace(" ", "");
					//根据字段名获取value值
					if(excelData.containsKey(cellStringValue)){
						org.apache.poi.ss.usermodel.Cell cellValue = row.getCell(j+1);
						Object value = excelData.get(cellStringValue);
						if(value != null && value instanceof String){
							cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							cellValue.setCellValue(String.valueOf(value));
						}
					}
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
			//return destPath;
			return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");
		} catch (IOException e) {
			System.out.println("走到这里了");
		}
		return null;
	}

	@Override
	public void setCompanyAndCarRelation() {
		carMapper.setCompanyAndCarRelation();
	}

	protected BaseMapper<Car> getMapper() {
		return carMapper;
	}

	protected String getFields() {
		return "id,car_code,car_driver_name,car_card_color, car_driver_phone,area_id,area_name, company_name,"
				+ "company_principal_name,company_principal_phone,company_contact,car_group_id,car_group_name,car_gps_machine_number,"
				+ "car_vehicle_model,brand_model,is_lock,is_running,created_time,updated_time,memo";
	}

	@Override
	public Integer updateCarsIsRunning(Integer isRunning, String carCodes) {
		return carMapper.updateCarsIsRunning(isRunning, carCodes);
	}
	
	// ---------------- 前台功能-----------------------


	/**
	 * 根据企业id,车牌号查询车辆列表(不带分页)
	 */
	public List<SimpleCarListResponse> queryCarsByCondition(CarQueryForm queryForm) {
		return carMapper.selectCarsByCondition(queryForm);
	}

	/***
	 * 	获取车辆的车牌号和车辆颜色的车辆List集合,主要是组装获取车辆GPS数据的请求参数
	*/
	public List<Map<String, Object>> queryCarNoAndColorList(Integer type) {
		return carMapper.selectCarNoAndColorList(type);
	}

	@Override
	public List<Car> queryAll() {
		
		return carMapper.queryAll();
	}

	@Override
	public List<Statistics> statisticsCompCarCout() {
		return carMapper.statisticsCompCarCout();
	}

	@Override
	public List<Statistics> statisticsCarGroupCout() {
		return carMapper.statisticsCarGroupCout();
	}

	@Override
	public void updateRunning(boolean b) {
		carMapper.updateRunning(b);
	}
	public List<Car> queryCarByCarCodes(String carCodes) {
		return carMapper.queryCarByCarCodes(carCodes);
	}

	@Override
	public Car queryByCarCodeAndColor(String carCode, String carColor) {
		return carMapper.selectCarByCarCodeAndColor(carCode, carColor);
	}

	@Override
	public Car selectCarByCarCodeAndCarCardColor(String carCode, String carCardColor) {
		return carMapper.selectCarByCarCodeAndCarCardColor(carCode, carCardColor);
	}

	@Override
	public List<Car> queryBySql(String sql) {
		return carMapper.queryData(sql);
	}

	@Override
	public Car queryByCarCode(String carCode) {
		// TODO Auto-generated method stub
		return carMapper.queryByCarCode(carCode);
	}
}
