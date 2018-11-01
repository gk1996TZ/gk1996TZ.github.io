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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.CompanyManager;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CompanyManagerMapper;
import com.muck.service.CompanyManagerService;
import com.muck.service.PropertiesService;
import com.muck.utils.ExcelWaterMarkUtils;
import com.muck.utils.ImageUtils;
import com.muck.utils.XSSFDateUtil;

/**
 * @Description: 企业管理人员Service实现
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月20日 下午10:32:03
 */
@Service
public class CompanyManagerServiceImpl extends BaseServiceImpl<CompanyManager> implements CompanyManagerService {

	@Autowired
	CompanyManagerMapper companyManagerMapper;

	@Autowired
	PropertiesService propertiesService;
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
				sheet1 = wb.getSheet("企业管理人员汇总");
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
				sheet1 = wb.getSheet("企业管理人员汇总");
			} else {
				logger.debug("Excel格式不支持");
			}
			// 循环遍历工作薄中的数据
			// 表头每列的字段名
			List<String> tableHeadName = new ArrayList<String>();
			// 如果工作薄对象不为空
			if (sheet1 != null) {
				// 获取表头数据
				//企业管理人员汇总表头数据在第三行，下标为2
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(2);
				if (row != null) {
					// 遍历表头数据
					for (org.apache.poi.ss.usermodel.Cell cell : row) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 将表头名称添加到相应的容器中
						tableHeadName.add(cell.getStringCellValue().replaceAll(" ", ""));
					}
				}
			}
			// 遍历除表头之外的每一行数据
			Map<String, String> tableBody = null;
			// sheet1.getLastRowNum() 获取最后一行的行号
			//表身数据从第4行开始，下标为3
			for (int i = 3; i <= sheet1.getLastRowNum(); i++) {
				// 获取当前行的数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(i);
				if(isNullRow(row)){
					continue;
				}
				tableBody = new HashMap<String, String>();
				
				// 遍历当前行的每一列数据
				// row.getLastCellNum() 获取当前行的最后一列的列号
				for (int j = 0; j < row.getLastCellNum(); j++) {
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					// 设置单元格数据为String类型
					if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
						//如果是数值型
						if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
							//判断当前单元格是否进行时间格式化了
							if (XSSFDateUtil.isCellDateFormatted(cell)) {
								tableBody.put(tableHeadName.get(j),
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
							}else {
								//将单元格设置为字符串类型
								cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
								// 将表头数据添加到map中
								tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
							}
						} else {
							//将单元格设置为字符串类型
							cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
						}
					}
				}
				list.add(tableBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<CompanyManager> selectDataFromExcelMapData(BaseExcelQueryTemplate excelTemplate,
			List<Map<String, String>> excelData) {

		// 声明一个存放生成好的实体类对象数据的列表
		List<CompanyManager> list = new ArrayList<CompanyManager>();
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
					String name = template.name().trim();
					// 通过name属性值获取map中的对应的值
					String value = map.get(name);
					if (StringUtils.isNotBlank(value)) {
						// 获取指定的属性
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
								field.set(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
							}
						} else if ("integer".equalsIgnoreCase(typeReal)) {
							field.set(obj, Integer.parseInt(value));
						} else {
							field.set(obj, value);
						}
					}
				}
				// 将通过反射获取到的数据封装到当前的类型中并添加到指定的列表中
				list.add((CompanyManager) cs.cast(obj));
			}
		} catch (NoSuchFieldException e) {
			logger.debug("导入企业时导入企业管理人员找不到相应的属性", e);
		} catch (SecurityException e) {
			logger.debug("导入企业时导入企业管理人员异常", e);
		} catch (IllegalArgumentException e) {
			logger.debug("导入企业时导入企业管理人员异常", e);
		} catch (IllegalAccessException e) {
			logger.debug("导入企业时导入企业管理人员异常", e);
		} catch (ClassNotFoundException e) {
			logger.debug("导入企业时导入企业管理人员出现未识别到的类型异常", e);
		} catch (InstantiationException e) {
			logger.debug("导入企业时导入企业管理人员异常", e);
		} catch (ParseException e) {
			logger.debug("导入企业时导入企业管理人员异常", e);
		}
		return list;
	}

	
	//=======================以下是导出的操作========================

	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<CompanyManager> list) {
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
			//data.add(tableHead);
		} catch (SecurityException e) {
			logger.error("导入企业时出现的异常",e);
		} catch (IllegalArgumentException e) {
			logger.error("导入企业时出现的异常",e);
		} catch (IllegalAccessException e) {
			logger.error("导入企业时出现的异常",e);
		}
		
		
		// 生成表身数据
		// 遍历传入的数据列表
		if (list != null) {
			try {
				for (CompanyManager companyManager : list) {
					// 获取对象的类的对象
					Class<?> tcls = companyManager.getClass();
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
							Object obj = filed.get(companyManager);
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
				/*if (data.size() > 0) {
					data.set(0, tableHead);
				}*/

			} catch (Exception e) {
				logger.error("导出企业时的异常",e);
			}
		}
		return data;
	}
	
	@Override
	public String createExcelPOI(String filePath, List<Map<String, String>> data, String[] water) {
		if(data == null | data.size() == 0){
			return "";
		}
		if(StringUtils.isBlank(filePath)){
			return "";
		}
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
				+ File.separator +"企业基本情况表"+".xlsx";
		String destPath = propertiesService.getWindowsRootPath() + downloadPath;
		// 根据路径创建一个file对象
		File destFile = new File(destPath);
		try {
			//将资源文件拷贝到目标文件下
			FileUtils.copyFile(file, destFile);
			// 判断文件是否存在
			if (!destFile.exists()) {
				return "";
			}
			if (destFile.exists() && destFile.isFile()) {
				// 获取文件输入流
				InputStream is = new FileInputStream(destFile);
				// 创建Excel
				org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);
				// 创建Sheet
				org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.getSheet("企业管理人员汇总");
				//企业管理人员数据从第四行开始
				for (int row = 3,index = 0; index < data.size(); row++,index++) {
					org.apache.poi.xssf.usermodel.XSSFRow nowrow = sheet.getRow(row);
					Map<String,String> map = data.get(index);
					if(map != null){
						//序号
						org.apache.poi.xssf.usermodel.XSSFCell cell1 = nowrow.getCell(0);
						if(cell1 != null){
							cell1.setCellValue((index+1)+"");
						}
						//姓名
						org.apache.poi.xssf.usermodel.XSSFCell cell2 = nowrow.getCell(1);
						if(cell2 != null){
							cell2.setCellType(Cell.CELL_TYPE_STRING);
							cell2.setCellValue(map.get("has_companyManagerName"));
						}
						//性别
						org.apache.poi.xssf.usermodel.XSSFCell cell3 = nowrow.getCell(2);
						if(cell3 != null){
							cell3.setCellType(Cell.CELL_TYPE_STRING);
							cell3.setCellValue(map.get("has_companyManagerSex"));
						}
						//岗位
						org.apache.poi.xssf.usermodel.XSSFCell cell4 = nowrow.getCell(3);
						if(cell4 != null){
							cell4.setCellType(Cell.CELL_TYPE_STRING);
							cell4.setCellValue(map.get("has_companyManagerPost"));
						}
						//联系电话
						org.apache.poi.xssf.usermodel.XSSFCell cell5 = nowrow.getCell(4);
						if(cell5 != null){
							cell5.setCellType(Cell.CELL_TYPE_STRING);
							cell5.setCellValue(map.get("has_companyManagerPhone"));
						}
						//身份证号码
						org.apache.poi.xssf.usermodel.XSSFCell cell6 = nowrow.getCell(5);
						if(cell6 != null){
							cell6.setCellType(Cell.CELL_TYPE_STRING);
							cell6.setCellValue(map.get("has_companyManagerIdNumber"));
						}
						//备注
						org.apache.poi.xssf.usermodel.XSSFCell cell7 = nowrow.getCell(6);
						if(cell7 != null){
							cell7.setCellType(Cell.CELL_TYPE_STRING);
							cell7.setCellValue(map.get("has_memo"));
						}
						
					}
				}
				if(water != null && water.length > 0){
					// 水印文件存放目录
					String imgPath = propertiesService.getWindowsCreateWaterRootPath() + File.separator + "水印.png";
					// 生成水印图片
					ImageUtils.createWaterMark(water, imgPath);
					// String result = ImageUtil.outputImage(water);
					// ImageUtil.write2Pic(result,imgPath);
					// 将水印图片加载到表格中
					ExcelWaterMarkUtils.setWaterMarkToExcel(workbook, sheet, imgPath, 0, 10, 9, 52, 3, 50, 0, 0);
				}
				// 创建一个文件输出流
				FileOutputStream fos = new FileOutputStream(destFile);
				workbook.write(fos);
				fos.flush();
				fos.close();
				workbook.close();
				return destPath;
			}
		} catch (FileNotFoundException e) {
			logger.error("文件不存在", e);
		} catch (IOException e) {
			logger.error("导出企业管理人员数据流异常",e);
		}
		return null;
	}
	
	
	
	//=======================以上是导出的操作========================
	
	

	// --------------------------------------------------------------------
	@Override
	protected BaseMapper<CompanyManager> getMapper() {
		return companyManagerMapper;
	}

	@Override
	protected String getFields() {
		return "*";
	}

}
