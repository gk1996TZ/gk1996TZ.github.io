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
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.BaseEntity;
import com.muck.domain.Car;
import com.muck.domain.CarDriver;
import com.muck.domain.CarDriverFamilyMember;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CarDriverFamilyMemberMapper;
import com.muck.mapper.CarDriverMapper;
import com.muck.response.StatusCode;
import com.muck.service.CarDriverService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelWaterMarkUtils;
import com.muck.utils.FileStreamUtils;
import com.muck.utils.ImageUtils;
import com.muck.utils.XSSFDateUtil;

/***
 * 车辆驾驶员service实现
 */
@Service
public class CarDriverServiceImpl extends BaseServiceImpl<CarDriver> implements CarDriverService {

	@Autowired
	private CarDriverMapper carDriverMapper; // 车辆驾驶员Mapper

	@Autowired
	private CarDriverFamilyMemberMapper carDriverFamilyMemberMapper; // 车辆驾驶员家庭成员Mapper

	@Autowired
	private PropertiesService propertiesService;

	/**
	 * 保存车辆驾驶员及车辆驾驶员的家庭成员
	 */
	public void save(CarDriver carDriver) {

		// 1、保存车辆驾驶员
		super.save(carDriver);

		// 2、保存家庭成员(批量)
		List<CarDriverFamilyMember> members = carDriver.getCarDriverFamilyMembers();
		if (members != null && members.size() > 0) {
			carDriverFamilyMemberMapper.insertBatch(carDriver, members);
		}
	}

	/**
	 * 根据车辆驾驶员id修改驾驶员 1、修改驾驶员 2、修改驾驶员的家庭成员
	 * 思路:先清空在添加,这样做的目的在于就不用直接根据家庭成员表的id去做对应修改了。
	 * 
	 */
	public void editById(CarDriver carDriver) {

		// 1、更新驾驶员的基本信息
		super.editById(carDriver);

		// 2、更新驾驶员的家庭成员
		List<CarDriverFamilyMember> members = carDriver.getCarDriverFamilyMembers();
		if (members != null && !members.isEmpty()) {
			// 2.1 首先直接删除
			carDriverFamilyMemberMapper.deleteByCarDriverIdReal(carDriver.getId());

			// 2.2 然后在批量添加
			carDriver.setCreatedTime(new Date());
			carDriverFamilyMemberMapper.insertBatch(carDriver, members);
		}
	}

	@Override
	public CarDriver queryByIdNumber(String idNumber) {
		return carDriverMapper.queryByIdNumber(idNumber);
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
				sheet1 = wb.getSheet("8.企业驾驶人员汇总");
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
				sheet1 = wb.getSheet("8.企业驾驶人员汇总");
			} else {
				logger.debug("Excel格式不支持");
			}
			// 循环遍历工作薄中的数据
			// 表头每列的字段名
			List<String> tableHeadName = new ArrayList<String>();
			// 如果工作薄对象不为空
			if (sheet1 != null) {
				// 获取表头数据
				// 企业驾驶人员汇总表头数据在第三行，下标为2
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(2);
				if (row != null) {
					// 遍历表头数据
					for (org.apache.poi.ss.usermodel.Cell cell : row) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 将表头名称添加到相应的容器中
						String headName = cell.getStringCellValue();
						if (!StringUtils.isBlank(headName)) {
							tableHeadName.add(headName.replace(" ", ""));
						}
					}
				}
			}
			// 遍历除表头之外的每一行数据
			Map<String, String> tableBody = null;
			// sheet1.getLastRowNum() 获取最后一行的行号
			// 表身数据从第4行开始，下标为3
			for (int i = 3; i <= sheet1.getLastRowNum(); i++) {
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
	public List<CarDriver> readExcelListData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		// 声明一个存放所有从excel文件中读取到的驾驶员列表
		List<CarDriver> listCarDriver = new ArrayList<CarDriver>();
		File file = new File(path);
		// 如果文件不存在
		if (!file.exists()) {
			return listCarDriver;
		}
		// 如果该目录不是文件
		if (!file.isFile()) {
			return listCarDriver;
		}
		// 获取文件类型
		String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
		try {
			InputStream is = new FileInputStream(path);
			org.apache.poi.ss.usermodel.Workbook wb = null;
			if ("xls".equals(fileType)) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
			} else if ("xlsx".equals(fileType)) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
			}
			Iterator<org.apache.poi.ss.usermodel.Sheet> sheetIterator = wb.sheetIterator();

			int ddd = 0;
			while (sheetIterator.hasNext()) {
				org.apache.poi.ss.usermodel.Sheet sheet = sheetIterator.next();
				ddd = ddd +1;
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
				// 2表示从第三行开始，8表示到第九行结束，先获取从第三行到第九行的数据
				for (int i = 2; i <= 8; i++) {
					// 获取当前行的数据
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
					// 遍历当前行的每一列数据
					// row.getLastCellNum() 获取当前行的最后一列的列号
					for (int j = 0; j < row.getLastCellNum(); j++) {
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
						if (cell != null) {
							// 设置单元格数据为String类型
							cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							// 如果当前单元格里的内容包含在表格字段列表中
							String cellStringValue = cell.getStringCellValue().replace(" ", "");
							if (tableKeyName.contains(cellStringValue)) {

								// 则获取该单元格右面的一个单元格的内容
								org.apache.poi.ss.usermodel.Cell cellValue = null;
								if ("出生年月".equals(cellStringValue) | "学历".equals(cellStringValue)
										| "驾驶车辆号牌".equals(cellStringValue) | "工作单位".equals(cellStringValue)) {
									cellValue = row.getCell(j + 2);
								} else {
									cellValue = row.getCell(j + 1);
								}

								if (cellValue != null) {
									if (cellValue.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
										// 判断当前单元格是否进行时间格式化了
										if (XSSFDateUtil.isCellDateFormatted(cellValue)) {
											tableBody.put(cell.getStringCellValue(), new SimpleDateFormat("yyyy-MM-dd")
													.format(cellValue.getDateCellValue()));
										} else {
											short format = cellValue.getCellStyle().getDataFormat();
											if(format == 14 | format == 20 | format == 31| format == 32 | format == 57 | format == 58){
												tableBody.put(cell.getStringCellValue(), new SimpleDateFormat("yyyy-MM-dd")
														.format(cellValue.getDateCellValue()));
											}else {
												// 设置单元格数据为String类型
												cellValue.setCellType(Cell.CELL_TYPE_STRING);
												// 将数据添加到指定的map中
												tableBody.put(cell.getStringCellValue(), cellValue.getStringCellValue());
											}
										}
									} else {
										// 设置单元格数据为String类型
										cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
										// 将数据添加到指定的map中
										tableBody.put(cellStringValue, cellValue.getStringCellValue());
									}
								}

							}
						}
					}
				}
				// 获取家庭成员
				List<String> famliyKeyName = new ArrayList<String>();
				org.apache.poi.ss.usermodel.Row row10 = sheet.getRow(9);
				// 获取第十行的表头数据
				for (int i = 1; i < row10.getLastCellNum(); i++) {
					org.apache.poi.ss.usermodel.Cell cell = row10.getCell(i);
					// 设置单元格数据为String类型
					cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
					// 字段名添加到家庭成员的列表中
					String keyName = cell.getStringCellValue();
					if (!"".equals(keyName.replace(" ", ""))) {
						famliyKeyName.add("家庭成员" + keyName.replace(" ", ""));
					}
				}
				// 10表示从第11行开始，到第14行结束
				int temp = 0;
				for (int i = 10; i <= 13; i++) {
					// 获取当前行的数据
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
					if(isNullRow(row)){
						continue;
					}
					// 遍历当前行的每一列数据
					int j = 1;
					org.apache.poi.ss.usermodel.Cell cell1 = row.getCell(j);
					if(cell1 != null){
						cell1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						tableBody.put(famliyKeyName.get(0) + temp, cell1.getStringCellValue());
					}
					org.apache.poi.ss.usermodel.Cell cell2 = row.getCell(j += 1);
					if(cell2 != null){
						cell2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						tableBody.put(famliyKeyName.get(1) + temp, cell2.getStringCellValue());
					}

					org.apache.poi.ss.usermodel.Cell cell3 = row.getCell(j += 2);
					if(cell3 != null){
						cell3.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						tableBody.put(famliyKeyName.get(2) + temp, cell3.getStringCellValue());
					}

					org.apache.poi.ss.usermodel.Cell cell4 = row.getCell(j += 2);
					if(cell4 != null){
						cell4.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						tableBody.put(famliyKeyName.get(3) + temp, cell4.getStringCellValue());
					}

					// row.getLastCellNum() 获取当前行的最后一列的列号
					/*
					 * for (int j = 1; j < row.getLastCellNum(); j++) {
					 * org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					 * // 设置单元格数据为String类型
					 * cell.setCellType(org.apache.poi.ss.usermodel.Cell.
					 * CELL_TYPE_STRING); tableBody.put(famliyKeyName.get(j-1),
					 * cell.getStringCellValue()); }
					 */
					temp++;
				}
				tableBody.put("familyNumber", temp + "");
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
									if (!StringUtils.isBlank(value)) {
										try {
											f.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(value));
										} catch (Exception e) {
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
									if (!StringUtils.isBlank(value)) {
										f.set(obj, Integer.parseInt(value));
									}
								} else {
									try {
										f.set(obj, value);
									} catch (Exception e) {
										logger.error("数据异常");
									}
								}
							}
							// 将通过反射获取到的数据封装到当前的类型中并添加到指定的列表中
							CarDriver carDriver = (CarDriver) cs.cast(obj);
							List<CarDriverFamilyMember> carDriverFamilyMembers = new ArrayList<CarDriverFamilyMember>();
							if (tableBody.get("familyNumber") != null && !"".equals(tableBody.get("familyNumber"))) {
								Integer size = Integer.parseInt(tableBody.get("familyNumber"));
								if (size > 0) {
									for (int i = 0; i < size; i++) {
										CarDriverFamilyMember carDriverFamilyMember = new CarDriverFamilyMember();
										// 遍历家庭成员每个字段名
										for (String key : famliyKeyName) {
											for (Field field : fields) {
												// 获取属性上面指定的注解
												ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
												// 获取注解指定属性的值
												String name = excelTemplate.name();
												if (key.equals(name)) {
													// 获取当前实体中指定的属性
													Field f = carDriverFamilyMember.getClass()
															.getDeclaredField(field.getName().replace("has_", ""));
													f.setAccessible(true);
													f.set(carDriverFamilyMember, tableBody.get(name + i));
												}
											}
										}
										carDriverFamilyMembers.add(carDriverFamilyMember);
									}
									carDriver.setCarDriverFamilyMembers(carDriverFamilyMembers);
								}
							}

							if (fileType.equals("xls")) {
								// 如果导入的是xls类型的excel表格文件
								// 将工作薄向下转型到xls类型的工作薄
								org.apache.poi.hssf.usermodel.HSSFSheet hsheet = (org.apache.poi.hssf.usermodel.HSSFSheet) sheet;
								// 获取并遍历工作薄中所有的图片
								for (org.apache.poi.hssf.usermodel.HSSFShape shape : hsheet.getDrawingPatriarch()
										.getChildren()) {
									// 如果是xls类型的工作薄图片
									if (shape instanceof org.apache.poi.hssf.usermodel.HSSFPicture) {
										// 向下转型到xls类型的工作薄图片
										org.apache.poi.hssf.usermodel.HSSFPicture pic = (org.apache.poi.hssf.usermodel.HSSFPicture) shape;
										// 获取图片二进制数据
										byte[] data = pic.getPictureData().getData();
										// 声明图片存放到本地的路径
										String imagePath = File.separator + "image" + File.separator + "upload"
												+ File.separator + System.currentTimeMillis() + "."
												+ pic.getPictureData().suggestFileExtension();
										String savePath = propertiesService.getWindowsRootPath() + imagePath;
										// 保存图片到本地
										File imagefile = new File(savePath);
										File parentFile = imagefile.getParentFile();
										if (!parentFile.exists()) {
											if (!parentFile.mkdirs()) {
											}
										}
										if (!imagefile.exists()) {
											imagefile.createNewFile();
										}
										FileOutputStream fos = new FileOutputStream(imagefile);
										fos.write(data);
										fos.close();
										// 将图片访问路径封装到相应的实体中
										String requestPath = "http://" + propertiesService.getCurrentServerForImage()
												+ imagePath.replaceAll("\\\\", "/");
										carDriver.setHeadPath(requestPath);
									}
								}
							} else if (fileType.equals("xlsx")) {
								org.apache.poi.xssf.usermodel.XSSFDrawing xssfDrawing = (XSSFDrawing) sheet
										.getDrawingPatriarch();
								if (xssfDrawing != null) {
									// 获取所有的图片内容
									List<org.apache.poi.xssf.usermodel.XSSFShape> listShapes = xssfDrawing.getShapes();
									if (listShapes.size() == 1) {
										for (org.apache.poi.xssf.usermodel.XSSFShape shape : listShapes) {
											if (shape instanceof org.apache.poi.xssf.usermodel.XSSFPicture) {
												org.apache.poi.xssf.usermodel.XSSFPicture pic = (org.apache.poi.xssf.usermodel.XSSFPicture) shape;
												byte[] data = pic.getPictureData().getData();
												String imagePath = File.separator + "image" + File.separator + "upload"
														+ File.separator + System.currentTimeMillis() + "."
														+ pic.getPictureData().suggestFileExtension();
												String savePath = propertiesService.getWindowsRootPath() + imagePath;
												File imagefile = new File(savePath);
												File parentFile = imagefile.getParentFile();
												if (!parentFile.exists()) {
													if (!parentFile.mkdirs()) {

													}
												}
												if (!imagefile.exists()) {
													imagefile.createNewFile();
												}
												FileOutputStream fos = new FileOutputStream(imagefile);
												fos.write(data);
												fos.close();
												String requestPath = "http://"
														+ propertiesService.getCurrentServerForImage()
														+ imagePath.replaceAll("\\\\", "/");
												carDriver.setHeadPath(requestPath);
											}
										}
									}
								}
							} else {
								logger.debug("Excel格式不支持");
							}
							listCarDriver.add(carDriver);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("导入企业时的驾驶员表格文件不存在");
		} catch (IOException e) {
			logger.error("导入企业时的驾驶员时数据流异常");
		} catch (IllegalArgumentException e) {
			logger.error("导出企业时导出驾驶员出现参数异常");
		} catch (IllegalAccessException e) {
			logger.error("缺少类成员访问权限");
		} catch (ClassNotFoundException e) {
			logger.error("所加载的类不存在");
		} catch (NoSuchFieldException e) {
			logger.error("未识别到属性异常");
		} catch (SecurityException e) {
		} catch (InstantiationException e) {
		}
		return listCarDriver;

	}

	@Override
	public CarDriver readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
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
			org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheet("12.驾驶人员信息情况表");
			// System.out.println(sheet1.getSheetName());
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
			// 2表示从第三行开始，9表示到第十行结束，先获取从第三行到第十行的数据
			for (int i = 2; i <= 9; i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(i);
				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for (int j = 0; j < row.getLastCellNum(); j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					if (cell != null) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 如果当前单元格里的内容包含在表格字段列表中
						String cellStringValue = cell.getStringCellValue().replace(" ", "");
						if (tableKeyName.contains(cellStringValue)) {

							// 则获取该单元格右面的一个单元格的内容
							org.apache.poi.ss.usermodel.Cell cellValue = null;
							if ("出生年月".equals(cellStringValue) | "学历".equals(cellStringValue)
									| "驾驶车辆号牌".equals(cellStringValue) | "工作单位".equals(cellStringValue)) {
								cellValue = row.getCell(j + 2);
							} else {
								cellValue = row.getCell(j + 1);
							}

							if (cellValue != null) {
								if (cellValue.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
									// 判断当前单元格是否进行时间格式化了
									if (XSSFDateUtil.isCellDateFormatted(cellValue)) {
										tableBody.put(cell.getStringCellValue(), new SimpleDateFormat("yyyy-MM-dd")
												.format(cellValue.getDateCellValue()));
									}
								} else {
									// 设置单元格数据为String类型
									cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
									// 将数据添加到指定的map中
									tableBody.put(cellStringValue, cellValue.getStringCellValue());
								}
							}

						}
					}
				}
			}
			// 获取家庭成员
			List<String> famliyKeyName = new ArrayList<String>();
			org.apache.poi.ss.usermodel.Row row10 = sheet1.getRow(9);
			// 获取第十一行的表头数据
			for (int i = 1; i < row10.getLastCellNum(); i++) {
				org.apache.poi.ss.usermodel.Cell cell = row10.getCell(i);
				// 设置单元格数据为String类型
				cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				// 字段名添加到家庭成员的列表中
				String keyName = cell.getStringCellValue();
				if (!"".equals(keyName.replace(" ", ""))) {
					famliyKeyName.add("家庭成员" + keyName.replace(" ", ""));
				}
			}

			// 11表示从第12行开始，到第15行结束
			int temp = 0;
			for (int i = 11; i <= 14; i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(i);
				// 遍历当前行的每一列数据
				int j = 1;
				org.apache.poi.ss.usermodel.Cell cell1 = row.getCell(j);
				cell1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				tableBody.put(famliyKeyName.get(0) + temp, cell1.getStringCellValue());

				org.apache.poi.ss.usermodel.Cell cell2 = row.getCell(j += 1);
				cell2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				tableBody.put(famliyKeyName.get(1) + temp, cell2.getStringCellValue());

				org.apache.poi.ss.usermodel.Cell cell3 = row.getCell(j += 2);
				cell3.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				tableBody.put(famliyKeyName.get(2) + temp, cell3.getStringCellValue());

				org.apache.poi.ss.usermodel.Cell cell4 = row.getCell(j += 2);
				cell4.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				tableBody.put(famliyKeyName.get(3) + temp, cell4.getStringCellValue());

				// row.getLastCellNum() 获取当前行的最后一列的列号
				/*
				 * for (int j = 1; j < row.getLastCellNum(); j++) {
				 * org.apache.poi.ss.usermodel.Cell cell = row.getCell(j); //
				 * 设置单元格数据为String类型
				 * cell.setCellType(org.apache.poi.ss.usermodel.Cell.
				 * CELL_TYPE_STRING); tableBody.put(famliyKeyName.get(j-1),
				 * cell.getStringCellValue()); }
				 */
				temp++;
			}
			tableBody.put("familyNumber", temp + "");
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
								if (!StringUtils.isBlank(value)) {
									try {
										f.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(value));
									} catch (Exception e) {
										logger.error("时间格式转化异常");
									}
								}
							} else if ("integer".equalsIgnoreCase(typeReal)) {
								if (!StringUtils.isBlank(value)) {
									f.set(obj, Integer.parseInt(value));
								}
							} else {
								try {
									f.set(obj, value);
								} catch (Exception e) {
									logger.error("参数异常");
								}
							}
						}
						// 将通过反射获取到的数据封装到当前的类型中并添加到指定的列表中
						CarDriver carDriver = (CarDriver) cs.cast(obj);
						List<CarDriverFamilyMember> carDriverFamilyMembers = new ArrayList<CarDriverFamilyMember>();
						if (tableBody.get("familyNumber") != null && !"".equals(tableBody.get("familyNumber"))) {
							Integer size = Integer.parseInt(tableBody.get("familyNumber"));
							if (size > 0) {
								for (int i = 0; i < size; i++) {
									CarDriverFamilyMember carDriverFamilyMember = new CarDriverFamilyMember();
									// 遍历家庭成员每个字段名
									for (String key : famliyKeyName) {
										for (Field field : fields) {
											// 获取属性上面指定的注解
											ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
											// 获取注解指定属性的值
											String name = excelTemplate.name();
											if (key.equals(name)) {
												// 获取当前实体中指定的属性
												Field f = carDriverFamilyMember.getClass()
														.getDeclaredField(field.getName().replace("has_", ""));
												f.setAccessible(true);
												f.set(carDriverFamilyMember, tableBody.get(name + i));
											}
										}
									}
									carDriverFamilyMembers.add(carDriverFamilyMember);
								}
								carDriver.setCarDriverFamilyMembers(carDriverFamilyMembers);
							}
						}

						if (fileType.equals("xls")) {
							// 如果导入的是xls类型的excel表格文件
							// 将工作薄向下转型到xls类型的工作薄
							org.apache.poi.hssf.usermodel.HSSFSheet hsheet = (org.apache.poi.hssf.usermodel.HSSFSheet) sheet1;
							// 获取并遍历工作薄中所有的图片
							for (org.apache.poi.hssf.usermodel.HSSFShape shape : hsheet.getDrawingPatriarch()
									.getChildren()) {
								// 如果是xls类型的工作薄图片
								if (shape instanceof org.apache.poi.hssf.usermodel.HSSFPicture) {
									// 向下转型到xls类型的工作薄图片
									org.apache.poi.hssf.usermodel.HSSFPicture pic = (org.apache.poi.hssf.usermodel.HSSFPicture) shape;
									// 获取图片二进制数据
									byte[] data = pic.getPictureData().getData();
									// 声明图片存放到本地的路径
									String imagePath = File.separator + "image" + File.separator + "upload"
											+ File.separator + System.currentTimeMillis() + "."
											+ pic.getPictureData().suggestFileExtension();
									String savePath = propertiesService.getWindowsRootPath() + imagePath;
									// 保存图片到本地
									File imagefile = new File(savePath);
									File parentFile = imagefile.getParentFile();
									if (!parentFile.exists()) {
										if (!parentFile.mkdirs()) {
										}
									}
									if (!imagefile.exists()) {
										imagefile.createNewFile();
									}
									FileOutputStream fos = new FileOutputStream(imagefile);
									fos.write(data);
									fos.close();
									// 将图片访问路径封装到相应的实体中
									String requestPath = "http://" + propertiesService.getCurrentServerForImage()
											+ imagePath.replaceAll("\\\\", "/");
									carDriver.setHeadPath(requestPath);
								}
							}
						} else if (fileType.equals("xlsx")) {
							org.apache.poi.xssf.usermodel.XSSFDrawing xssfDrawing = (XSSFDrawing) sheet1
									.getDrawingPatriarch();
							if (xssfDrawing != null) {
								// 获取所有的图片内容
								List<org.apache.poi.xssf.usermodel.XSSFShape> listShapes = xssfDrawing.getShapes();
								if (listShapes.size() == 1) {
									for (org.apache.poi.xssf.usermodel.XSSFShape shape : listShapes) {
										if (shape instanceof org.apache.poi.xssf.usermodel.XSSFPicture) {
											org.apache.poi.xssf.usermodel.XSSFPicture pic = (org.apache.poi.xssf.usermodel.XSSFPicture) shape;
											byte[] data = pic.getPictureData().getData();
											String imagePath = File.separator + "image" + File.separator + "upload"
													+ File.separator + System.currentTimeMillis() + "."
													+ pic.getPictureData().suggestFileExtension();
											String savePath = propertiesService.getWindowsRootPath() + imagePath;
											File imagefile = new File(savePath);
											File parentFile = imagefile.getParentFile();
											if (!parentFile.exists()) {
												if (!parentFile.mkdirs()) {

												}
											}
											if (!imagefile.exists()) {
												imagefile.createNewFile();
											}
											FileOutputStream fos = new FileOutputStream(imagefile);
											fos.write(data);
											fos.close();
											String requestPath = "http://"
													+ propertiesService.getCurrentServerForImage()
													+ imagePath.replaceAll("\\\\", "/");
											carDriver.setHeadPath(requestPath);
										}
									}
								}
							}
						} else {
							logger.debug("Excel格式不支持");
						}
						return carDriver;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("数据流异常");
		} catch (ClassNotFoundException e) {
			System.out.println("未找到指定的类");
		} catch (InstantiationException e) {
			System.out.println(1);
		} catch (IllegalAccessException e) {
			System.out.println(2);
		} catch (NoSuchFieldException e) {
			System.out.println("导入驾驶员时，出现属性未找到异常");
		} catch (SecurityException e) {
			System.out.println(4);
		} catch (IllegalArgumentException e) {
			System.out.println("参数异常");
		}
		return null;
	}

	@Override
	public Map<String, Object> createExcelData(BaseExcelQueryTemplate excelTemplate, BaseEntity baseEntity) {
		// 声明一个存放表格数据的Map
		Map<String, Object> tableData = new HashMap<String, Object>();
		// 自定义时间格式
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
					if (typeReal.equalsIgnoreCase("list")) {
						if (name.equals("carDriverFamilyMembers")) {
							// 获取驾驶员的所有家属
							List<CarDriverFamilyMember> carDriverFamilyMembers = (List<CarDriverFamilyMember>) val;
							// 声明一个驾驶员家属表格数据
							List<Map<String, String>> carDriverFamilyMemberExcelData = new ArrayList<Map<String, String>>();
							Map<String, String> carDriverExcelData = null;
							for (CarDriverFamilyMember carDriverFamilyMember : carDriverFamilyMembers) {
								carDriverExcelData = new TreeMap<String, String>();
								// 家庭成员姓名
								carDriverExcelData.put("1", carDriverFamilyMember.getName());
								// 家庭成员关系
								carDriverExcelData.put("2", carDriverFamilyMember.getRelation());
								// 家庭成员联系方式
								carDriverExcelData.put("3", carDriverFamilyMember.getPhone());
								// 家庭成员工作单位
								carDriverExcelData.put("4", carDriverFamilyMember.getWorkUnit());
								carDriverFamilyMemberExcelData.add(carDriverExcelData);
							}
							tableData.put("carDriverFamilyMembers", carDriverFamilyMemberExcelData);
						}
					} else if (typeReal.equalsIgnoreCase("date")) {
						if (name.equals("driverBirth")) {
							Field keyField = cls.getDeclaredField("has_" + name);
							ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
							Date date = (Date) val;
							tableData.put(template.name(), sdf.format(date));
						}
					} else if (typeReal.equalsIgnoreCase("integer")) {

					} else if (typeReal.equalsIgnoreCase("boolean")) {
						if (name.equals("isMarry")) {
							Field keyField = cls.getDeclaredField("has_" + name);
							ExcelTemplate template = keyField.getAnnotation(ExcelTemplate.class);
							Boolean isMarry = (Boolean) val;
							if (isMarry) {
								tableData.put(template.name(), "否");
							} else {
								tableData.put(template.name(), "是");
							}
						}
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

	// =========================以下是将驾驶员列表导出到一个excel表中，一个驾驶员占用一个工作簿的操作===================
	
	
	@Override
	public String createExcelPOI(BaseExcelQueryTemplate excelTemplate, String fileName, List<CarDriver> data,
			String[] water) {
		// 获取模版文件路径
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator
				+ fileName;

		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("导出驾驶员详情列表到一个工作簿中时车辆模版不存在");
			return "";
		}
		if (!file.isFile()) {
			logger.error("导出驾驶员详情列表到一个工作簿中时传入的不是一个模板文件名");
			return "";
		}

		if (data == null | data.size() == 0) {
			return "";
		}
		// 获取excel表格数据，并放到一个list中
		List<Map<String, Object>> listCarDriverMaps = new ArrayList<Map<String, Object>>();
		for (CarDriver carDriver : data) {
			Map<String, Object> map = createExcelData(excelTemplate, carDriver);
			listCarDriverMaps.add(map);
		}
		if (listCarDriverMaps.size() == 0) {
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径
		 * subdirectory 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator + "carDriver"
				+ File.separator + "企业驾驶员情况表" + ".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
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
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
			} else {
				logger.debug("Excel格式不支持");
			}
			// 如果数据总数小于工作簿数量，则删除多余的工作簿
			if (listCarDriverMaps.size() < wb.getNumberOfSheets()) {
				for (int index = listCarDriverMaps.size(); index <= wb.getNumberOfSheets(); index++) {
					wb.removeSheetAt(index);
				}
			}
			Integer temp = 0;
			for (Map<String, Object> map : listCarDriverMaps) {
				if (fileType.equals("xls")) {
					sheet = wb.getSheetAt(temp);
				} else if (fileType.equals("xlsx")) {
					sheet = wb.getSheetAt(temp);
				} else {
					logger.debug("Excel格式不支持");
				}
				// 2表示从第三行开始，9表示到第十行结束，先获取从第三行到第十行的数据
				for (int i = 2; i <= 9; i++) {
					// 获取当前行的数据
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
					// 遍历当前行的每一列数据
					// row.getLastCellNum() 获取当前行的最后一列的列号
					for (int j = 0; j < row.getLastCellNum(); j++) {
						org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
						if (cell != null) {
							// 设置单元格数据为String类型
							cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							// 如果当前单元格里的内容包含在map的key值中
							String cellStringValue = cell.getStringCellValue().replace(" ", "");
							// 根据字段名获取value值
							if (map.containsKey(cellStringValue)) {
								org.apache.poi.ss.usermodel.Cell cellValue = null;
								if ("出生年月".equals(cellStringValue) | "学历".equals(cellStringValue)
										| "驾驶车辆号牌".equals(cellStringValue) | "工作单位".equals(cellStringValue)) {
									cellValue = row.getCell(j + 2);
								} else if ("免冠近照".equals(cellStringValue)) {
									cellValue = cell;
								} else {
									cellValue = row.getCell(j + 1);
								}
								Object value = map.get(cellStringValue);
								if (value != null && value instanceof String) {
									// 获取后缀
									String postfix = value.toString().substring(value.toString().lastIndexOf(".") + 1,
											value.toString().length());
									if (("jpg".equalsIgnoreCase(postfix) | "png".equalsIgnoreCase(postfix)
											| "jpeg".equalsIgnoreCase(postfix) | "gif".equalsIgnoreCase(postfix))) {
										byte[] imageData = FileStreamUtils.getInputStream(
												propertiesService.getWindowsRootPath() + File.separator + "image"
														+ value.toString()
																.substring(value.toString().indexOf("/",
																		value.toString().indexOf("//") + 2))
																.replaceAll("/", "\\\\"));
										if (imageData == null) {
											throw new BizException(StatusCode.UNKNOWN);
										}
										if (fileType.equals("xls")) {
											org.apache.poi.hssf.usermodel.HSSFSheet hSheet = (org.apache.poi.hssf.usermodel.HSSFSheet) sheet;
											org.apache.poi.hssf.usermodel.HSSFPatriarch patriarch = hSheet
													.getDrawingPatriarch();
											org.apache.poi.hssf.usermodel.HSSFClientAnchor anchor = new org.apache.poi.hssf.usermodel.HSSFClientAnchor(
													0, 0, 0, 0, (short) cellValue.getColumnIndex(),
													cellValue.getRowIndex(), (short) (cellValue.getColumnIndex() + 1),
													cellValue.getRowIndex() + 3);
											anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
											org.apache.poi.hssf.usermodel.HSSFWorkbook hwb = (org.apache.poi.hssf.usermodel.HSSFWorkbook) wb;
											Integer picType = null;
											if ("jpg".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("png".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_PNG);
											} else if ("jpeg".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("gif".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_PNG);
											}
											patriarch.createPicture(anchor, picType);
										} else if (fileType.equals("xlsx")) {
											org.apache.poi.xssf.usermodel.XSSFDrawing patriarch = ((org.apache.poi.xssf.usermodel.XSSFSheet) sheet)
													.createDrawingPatriarch();
											org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor = new org.apache.poi.xssf.usermodel.XSSFClientAnchor(
													0, 0, 0, 0, (short) cellValue.getColumnIndex(),
													cellValue.getRowIndex(), (short) cellValue.getColumnIndex() + 1,
													(cellValue.getRowIndex() + 3));
											anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
											org.apache.poi.xssf.usermodel.XSSFWorkbook xwb = (org.apache.poi.xssf.usermodel.XSSFWorkbook) wb;
											Integer picType = null;
											if ("jpg".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("png".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_PNG);
											} else if ("jpeg".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("gif".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_GIF);
											}
											patriarch.createPicture(anchor, picType);
										}
									} else {
										cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
										cellValue.setCellValue(String.valueOf(value));
									}
								}
							}
						}
					}
				}

				if (map.containsKey("carDriverFamilyMembers")) {
					Object value = map.get("carDriverFamilyMembers");
					if (value instanceof List) {
						List<Map<String, String>> carDriverFamilyMembers = (List<Map<String, String>>) value;
						if (carDriverFamilyMembers != null && carDriverFamilyMembers.size() > 0) {
							int tempA = 0;
							// 11表示从第12行开始，到第15行结束
							for (int i = 11; i <= 14; i++) {
								org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
								Map<String, String> rowData = carDriverFamilyMembers.get(temp);
								int cellIndex = 1;
								org.apache.poi.ss.usermodel.Cell cell1 = row.getCell(cellIndex);
								cell1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								cell1.setCellValue(rowData.get("1"));
								org.apache.poi.ss.usermodel.Cell cell2 = row.getCell(cellIndex += 1);
								cell2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								cell2.setCellValue(rowData.get("2"));
								org.apache.poi.ss.usermodel.Cell cell3 = row.getCell(cellIndex += 2);
								cell3.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								cell3.setCellValue(rowData.get("3"));
								org.apache.poi.ss.usermodel.Cell cell4 = row.getCell(cellIndex += 2);
								cell4.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								cell4.setCellValue(rowData.get("4"));
								tempA++;
								if (temp == carDriverFamilyMembers.size()) {
									break;
								}
							}
						}
					}
				}
				if (water != null && water.length > 0) {
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
				temp++;
			}
			wb.close();
			return destPath;
		} catch (FileNotFoundException e) {
			logger.error("文件未找到",e);
		} catch (IOException e) {
			logger.error("文件流异常",e);
		}
		return null;
	}

	@Override
	public String createExcelPOI(String fileName, Map<String, Object> excelData, String[] water) {
		if (StringUtils.isBlank(fileName)) {
			logger.error("传入的文件名为空");
			return "";
		}
		if (excelData == null) {
			logger.error("传入的表格数据为空");
			return "";
		}
		String filePath = propertiesService.getWindowsRootPath() + File.separator + "excelTemplate" + File.separator
				+ fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("模版文件不存在");
			return "";
		}
		if (!file.isFile()) {
			logger.error("模版文件不存在");
			return "";
		}
		/*
		 * 拼接下载和存放到本机的文件子路径 downloadPath 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径
		 * subdirectory 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
		 */
		String downloadPath = File.separator + "excel" + File.separator + "download" + File.separator + "carDriver"
				+ File.separator + new Date().getTime() + ".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
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
			if (fileType.equals("xls")) {
				wb = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				sheet = wb.getSheetAt(0);
			} else {
				logger.debug("Excel格式不支持");
			}
			// 2表示从第三行开始，9表示到第十行结束，先获取从第三行到第十行的数据
			for (int i = 2; i <= 9; i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for (int j = 0; j < row.getLastCellNum(); j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					if (cell != null) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 如果当前单元格里的内容包含在map的key值中
						String cellStringValue = cell.getStringCellValue().replace(" ", "");
						// 根据字段名获取value值
						if (excelData.containsKey(cellStringValue)) {
							org.apache.poi.ss.usermodel.Cell cellValue = null;
							if ("出生年月".equals(cellStringValue) | "学历".equals(cellStringValue)
									| "驾驶车辆号牌".equals(cellStringValue) | "工作单位".equals(cellStringValue)) {
								cellValue = row.getCell(j + 2);
							} else if ("免冠近照".equals(cellStringValue)) {
								cellValue = cell;
							} else {
								cellValue = row.getCell(j + 1);
							}
							Object value = excelData.get(cellStringValue);
							if (value != null && value instanceof String) {
								// 获取后缀
								String postfix = value.toString().substring(value.toString().lastIndexOf(".") + 1,
										value.toString().length());
								if (("jpg".equalsIgnoreCase(postfix) | "png".equalsIgnoreCase(postfix)
										| "jpeg".equalsIgnoreCase(postfix) | "gif".equalsIgnoreCase(postfix))) {
									byte[] imageData = FileStreamUtils.getInputStream(
											propertiesService.getWindowsRootPath() + File.separator + "image"
													+ value.toString()
															.substring(value.toString().indexOf("/",
																	value.toString().indexOf("//") + 2))
															.replaceAll("/", "\\\\"));
									if (imageData != null) {
										if (fileType.equals("xls")) {
											org.apache.poi.hssf.usermodel.HSSFSheet hSheet = (org.apache.poi.hssf.usermodel.HSSFSheet) sheet;
											org.apache.poi.hssf.usermodel.HSSFPatriarch patriarch = hSheet
													.getDrawingPatriarch();
											org.apache.poi.hssf.usermodel.HSSFClientAnchor anchor = new org.apache.poi.hssf.usermodel.HSSFClientAnchor(
													0, 0, 0, 0, (short) cellValue.getColumnIndex(), cellValue.getRowIndex(),
													(short) (cellValue.getColumnIndex() + 1), cellValue.getRowIndex() + 3);
											anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
											org.apache.poi.hssf.usermodel.HSSFWorkbook hwb = (org.apache.poi.hssf.usermodel.HSSFWorkbook) wb;
											Integer picType = null;
											if ("jpg".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("png".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_PNG);
											} else if ("jpeg".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("gif".equalsIgnoreCase(postfix)) {
												picType = hwb.addPicture(imageData,
														org.apache.poi.hssf.usermodel.HSSFWorkbook.PICTURE_TYPE_PNG);
											}
											patriarch.createPicture(anchor, picType);
										} else if (fileType.equals("xlsx")) {
											org.apache.poi.xssf.usermodel.XSSFDrawing patriarch = ((org.apache.poi.xssf.usermodel.XSSFSheet) sheet)
													.createDrawingPatriarch();
											org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor = new org.apache.poi.xssf.usermodel.XSSFClientAnchor(
													0, 0, 0, 0, (short) cellValue.getColumnIndex(), cellValue.getRowIndex(),
													(short) cellValue.getColumnIndex() + 1, (cellValue.getRowIndex() + 3));
											anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
											org.apache.poi.xssf.usermodel.XSSFWorkbook xwb = (org.apache.poi.xssf.usermodel.XSSFWorkbook) wb;
											Integer picType = null;
											if ("jpg".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("png".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_PNG);
											} else if ("jpeg".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_JPEG);
											} else if ("gif".equalsIgnoreCase(postfix)) {
												picType = xwb.addPicture(imageData,
														org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_GIF);
											}
											patriarch.createPicture(anchor, picType);
										}
									}
								} else {
									cellValue.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
									cellValue.setCellValue(String.valueOf(value));
								}
							}
						}
					}
				}
			}

			if (excelData.containsKey("carDriverFamilyMembers")) {
				Object value = excelData.get("carDriverFamilyMembers");
				if (value instanceof List) {
					List<Map<String, String>> carDriverFamilyMembers = (List<Map<String, String>>) value;
					if (carDriverFamilyMembers != null && carDriverFamilyMembers.size() > 0) {
						int temp = 0;
						// 11表示从第12行开始，到第15行结束
						for (int i = 11; i <= 14; i++) {
							org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
							Map<String, String> rowData = carDriverFamilyMembers.get(temp);
							int cellIndex = 1;
							org.apache.poi.ss.usermodel.Cell cell1 = row.getCell(cellIndex);
							cell1.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							cell1.setCellValue(rowData.get("1"));
							org.apache.poi.ss.usermodel.Cell cell2 = row.getCell(cellIndex += 1);
							cell2.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							cell2.setCellValue(rowData.get("2"));
							org.apache.poi.ss.usermodel.Cell cell3 = row.getCell(cellIndex += 2);
							cell3.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							cell3.setCellValue(rowData.get("3"));
							org.apache.poi.ss.usermodel.Cell cell4 = row.getCell(cellIndex += 2);
							cell4.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							cell4.setCellValue(rowData.get("4"));
							temp++;
							if (temp == carDriverFamilyMembers.size()) {
								break;
							}
						}
					}
				}
			}
			if (water != null && water.length > 0) {
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
			// return destPath;
			return "http://" + propertiesService.getCurrentServerForExcel() + downloadPath.replaceAll("\\\\", "/");
		} catch (FileNotFoundException e) {
			logger.error("文件未找到",e);
		} catch (IOException e) {
			logger.error("文件流异常",e);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<CarDriver> list) {
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
			logger.error("导出驾驶员时异常",e);
		} catch (IllegalArgumentException e) {
			logger.error("导出驾驶员时异常",e);
		} catch (IllegalAccessException e) {
			logger.error("导出驾驶员时异常",e);
		}
		// 生成表身数据
		// 遍历传入的数据列表
		if (list != null) {
			try {
				for (CarDriver carDriver : list) {
					tableBody = new TreeMap<String, String>();
					// 获取对象的类的对象
					Class<?> tcls = carDriver.getClass();
					// 遍历表头map的所有的key
					Set<String> keySet = tableHead.keySet();
					// 遍历表头map所有的key
					for (String key : keySet) {
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
							Object obj = filed.get(carDriver);
							// 如果获取到的是时间类型，则转化一下时间格式
							if ("date".equalsIgnoreCase(typeReal)) {
								Date date = (Date) obj;
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								if (date != null) {
									tableBody.put(key, sdf.format(date));
								} else {
									tableBody.put(key, "");
								}
							} else if ("list".equalsIgnoreCase(typeReal)) {
								if ("carDriverFamilyMembers".equals(filed.getName())) {
									List<CarDriverFamilyMember> carDriverFamilyMembers = carDriver
											.getCarDriverFamilyMembers();
									Integer temp = 1;
									if (carDriverFamilyMembers != null) {
										for (CarDriverFamilyMember carDriverFamilyMember : carDriverFamilyMembers) {
											tableBody.put("家庭成员" + temp + "姓名", carDriverFamilyMember.getName());
											tableBody.put("家庭成员" + temp + "联系方式", carDriverFamilyMember.getPhone());
											tableBody.put("与家庭成员" + temp + "关系", carDriverFamilyMember.getRelation());
											tableBody.put("家庭成员" + temp + "工作单位", carDriverFamilyMember.getWorkUnit());
										}
									}
								}
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								if (obj != null) {
									tableBody.put(key, obj.toString());
								} else {
									tableBody.put(key, "");
								}
							}
						}
					}
					tableBody.remove("has_carDriverFamilyMembers");
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
				logger.error("导出驾驶员时出现属性未找到异常");
			} catch (SecurityException e) {
				logger.error("导出驾驶员时异常",e);
			} catch (IllegalArgumentException e) {
				logger.error("导出驾驶员时异常",e);
			} catch (IllegalAccessException e) {
				logger.error("导出驾驶员时异常",e);
			}
		}
		return data;
	}

	@Override
	public List<CarDriver> selectDataFromExcelMapData(BaseExcelQueryTemplate excelTemplate,
			List<Map<String, String>> excelData) {
		// 声明一个存放生成好的实体类对象数据的列表
		List<CarDriver> list = new ArrayList<CarDriver>();
		// 获取本地模版类的对象
		Class<?> cls = excelTemplate.getClass();
		// 获取类的对象的属性列表
		Field[] fs = cls.getDeclaredFields();
		// 遍历获取到的Excel表格数据
		try {
			for (Map<String, String> map : excelData) {
				// 获取当前的实体
				// 获取当前的实体
				Class<?> cs = Class.forName(this.entityClass.getName());
				// 通过字节码获取实例
				Object obj = cs.newInstance();
				// 遍历类的对象的属性列表
				for (Field f : fs) {
					// 获取ExcelTemplate注解
					ExcelTemplate template = f.getAnnotation(ExcelTemplate.class);
					// 获取注解的name属性值
					String name = template.name();
					// 通过name属性值获取map中的对应的值
					String value = map.get(name);
					// 获取当前实体中指定的属性
					Field field = cs.getDeclaredField(f.getName().replace("has_", ""));
					// 设置属性是可以访问的
					field.setAccessible(true);
					String type = field.getGenericType().toString();
					String typeReal = "";
					if (type.contains("<") && type.contains(">")) {
						typeReal = type.substring(type.lastIndexOf(".", type.indexOf("<")) + 1, type.indexOf("<"));
					} else {
						typeReal = type.substring(type.lastIndexOf(".") + 1, type.length());
					}
					if ("date".equalsIgnoreCase(typeReal)) {
						if (!StringUtils.isBlank(value)) {
							field.set(obj, new SimpleDateFormat("yyyy-MM-dd").parse(value));
						}
					} else if ("integer".equalsIgnoreCase(typeReal)) {
						if (!StringUtils.isBlank(value)) {
							field.set(obj, Integer.parseInt(value));
						}
					} else {
						try {
							field.set(obj, value);
						} catch (Exception e) {
							logger.error("参数异常");
						}
					}

				}
				// 将通过反射获取到的数据封装到当前的类型中并添加到指定的列表中
				CarDriver cd = (CarDriver) cs.cast(obj);
				List<CarDriverFamilyMember> carDriverFamilyMembers = new ArrayList<CarDriverFamilyMember>();
				// 获取家庭成员1的信息
				CarDriverFamilyMember family1 = new CarDriverFamilyMember();
				family1.setName(cd.getFamilyName1());
				family1.setPhone(cd.getFamilyPhone1());
				family1.setRelation(cd.getFamilyRelation1());
				family1.setWorkUnit(cd.getFamilyWorkUnit1());
				// 获取家庭成员2的信息
				CarDriverFamilyMember family2 = new CarDriverFamilyMember();
				family2.setName(cd.getFamilyName2());
				family2.setPhone(cd.getFamilyPhone2());
				family2.setRelation(cd.getFamilyRelation2());
				family2.setWorkUnit(cd.getFamilyWorkUnit2());
				// 获取家庭成员3的信息
				CarDriverFamilyMember family3 = new CarDriverFamilyMember();
				family3.setName(cd.getFamilyName3());
				family3.setPhone(cd.getFamilyPhone3());
				family3.setRelation(cd.getFamilyRelation3());
				family3.setWorkUnit(cd.getFamilyWorkUnit3());
				// 获取家庭成员4的信息
				CarDriverFamilyMember family4 = new CarDriverFamilyMember();
				family4.setName(cd.getFamilyName4());
				family4.setPhone(cd.getFamilyPhone4());
				family4.setRelation(cd.getFamilyRelation4());
				family4.setWorkUnit(cd.getFamilyWorkUnit4());

				// 将父亲和母亲添加到家庭成员列表中
				carDriverFamilyMembers.add(family1);
				carDriverFamilyMembers.add(family2);
				carDriverFamilyMembers.add(family3);
				carDriverFamilyMembers.add(family4);
				cd.setCarDriverFamilyMembers(carDriverFamilyMembers);

				// 将驾驶员信息添加到指定的驾驶员列表中
				list.add(cd);
			}
		} catch (NoSuchFieldException e) {
			logger.debug("找不到相应的属性", e);
		} catch (SecurityException e) {
			logger.debug("", e);
		} catch (IllegalArgumentException e) {
			logger.debug("", e);
		} catch (IllegalAccessException e) {
			logger.debug("", e);
		} catch (ClassNotFoundException e) {
			logger.debug("未识别到的类型", e);
		} catch (InstantiationException e) {
			logger.debug("", e);
		} catch (ParseException e) {
			logger.debug("", e);
		}
		return list;
	}

	
	@Override
	public void saveAll(List<CarDriver> list) {
		if(list == null | list.size() ==0){
			throw new BizException(StatusCode.IMPORT_COMPANY_CARDRIVER_NULL);
		}
		// 批量插入数据并返回每条数据的id
		carDriverMapper.insertAll(list);
		// 声明一个存放所有需要被添加的驾驶员亲属数据
		List<CarDriverFamilyMember> listCarDriverFamilyMember = new ArrayList<CarDriverFamilyMember>();
		// 遍历驾驶员列表
		for (CarDriver carDriver : list) {
			List<CarDriverFamilyMember> carDriverFamilyMembers = carDriver.getCarDriverFamilyMembers();
			if(carDriverFamilyMembers != null){
				for (CarDriverFamilyMember carDriverFamilyMember : carDriverFamilyMembers) {
					// 将驾驶员数据放到驾驶员亲属对象中
					carDriverFamilyMember.setCarDriver(carDriver);
				}
				listCarDriverFamilyMember.addAll(carDriverFamilyMembers);
			}
		}
		// 将所有的驾驶员亲属添加到数据库中
		if (listCarDriverFamilyMember.size() > 0) {
			carDriverFamilyMemberMapper.insertAll(listCarDriverFamilyMember);
		}
	}

	
	@Override
	public void setCompanyAndCarDriverRelation() {
		carDriverMapper.setCompanyAndCarDriverRelation();
	}

	@Override
	public void setCarDriverAndCarDriverFamilyMemberRelation() {
		carDriverFamilyMemberMapper.setCarDriverAndCarDriverFamilyMemberRelation();;
	}

	@Override
	public List<CarDriver> queryByCarCode(String carCode) {
		return carDriverMapper.queryByCarCode(carCode);
	}

	@Override
	public CarDriver queryByPhone(String phone) {
		return carDriverMapper.queryByPhone(phone);
	}

	@Override
	public List<CarDriver> queryData(String wherSQL, List<Object> whereParams) {
		// 第一步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? " where 1 = 1 " : " where " + wherSQL;
		// 第二步、拼接sql语句
		String selectSQL = " SELECT " 
				+ "car_driver.id, " 
				+ "car_driver.driver_name," 
				+ "car_driver.driver_sex," 
				+ "car_driver.driver_age," 
				+ "car_driver.driver_birth," 
				+ "car_driver.is_marry," 
				+ "car_driver.driver_nation," 
				+ "car_driver.driver_education," 
				+ "car_driver.id_number," 
				+ "car_driver.id_number_address," 
				+ "car_driver.live_address," 
				+ "car_driver.driver_phone," 
				+ "car_driver.qualification_certificate,"
				+ "car_driver.driver_license_file_number,"
				+ "car_driver_family_member.name,"
				+ "car_driver_family_member.phone,"
				+ "car_driver_family_member.relation,"
				+ "car_driver_family_member.work_unit"
				+ " from "
				+ " t_car_driver car_driver left join t_car_driver_family_member car_driver_family_member on car_driver.id = car_driver_family_member.driver_id " + where;
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}
	
	
	// --------------------------------------------------------


	@Override
	protected BaseMapper<CarDriver> getMapper() {
		return carDriverMapper;
	}

	@Override
	protected String getFields() {
		return "id,driver_name,driver_phone,driver_sex,driver_birth,is_marry,driver_nation,driver_education,id_number_address,id_number,live_address,"
				+ "driver_license_file_number,qualification_certificate,car_code,company_name";
	}
}
