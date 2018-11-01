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
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.CompanyMember;
import com.muck.domain.CompanyMemberType;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CompanyMemberMapper;
import com.muck.service.CompanyMemberService;
import com.muck.service.PropertiesService;
@Service
public class CompanyMemberServiceImpl extends BaseServiceImpl<CompanyMember> implements CompanyMemberService {
	@Autowired
	CompanyMemberMapper companyMemberMapper;

	@Autowired
	PropertiesService propertiesService;
	@SuppressWarnings("resource")
	@Override
	public CompanyMember readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path) {
		File file = new File(path);
		CompanyMember companyMember = null;
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
			// 获取文件输入流
			InputStream stream;
			try {
				stream = new FileInputStream(path);
		
			// 创建一个工作薄
			Workbook workbook = null;
			if (fileType.equals("xls")) {
				workbook = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				workbook = new XSSFWorkbook(stream);
			} else {
				throw new RuntimeException("不是Excel类型");
			}
			Sheet sheet = workbook.getSheetAt(0);

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
			Map<String, String> map = new HashMap<String, String>();
			// 先读取第二行到第六行的数据
			for (int i = 1; i <= 5; i++) {
				// 获取当前行的数据
				Row row = sheet.getRow(i);
				// 遍历当前行的每一列的内容
				for (int j = 0; j <= 5; j++) {
					// 拿到当前单元格的内容
					Cell cell1 = row.getCell(j);
					if (cell1 != null) {
						cell1.setCellType(Cell.CELL_TYPE_STRING);
						if (cell1.getStringCellValue() != null && !cell1.getStringCellValue().equals("")) {
							// 设置单元格数据为String类型
							String stringField = cell1.getStringCellValue().replace(" ", "");
							if (tableKeyName.contains(stringField)) {
								if (stringField.equals("出生年月")) {
									Cell cell2 = row.getCell(j + 2);
									if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
										Date date=cell2.getDateCellValue();
										if(date!=null){
											SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
											map.put(stringField,sdf.format(date) );
										}
									}
								}
								else if (stringField.equals("姓名") || stringField.equals("何时何校何专业毕业")
										|| stringField.equals("企业管理资历")) {
									// 如果包含在表格字段列表中，就去拿它右2格的数据
									Cell cell2 = row.getCell(j + 2);
									cell2.setCellType(Cell.CELL_TYPE_STRING);
									map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
								} else {
									Cell cell2 = row.getCell(j + 1);
									cell2.setCellType(Cell.CELL_TYPE_STRING);
									map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
								}
							}
						}

					}

				}
			}

			// 从第七行开始
			Row row1 = sheet.getRow(6);
			// 遍历当前行的每一列的内容
			for (int j = 1; j <= 5; j++) {
				Cell cell1 = row1.getCell(j);
				cell1.setCellType(Cell.CELL_TYPE_STRING);
				String stringField = cell1.getStringCellValue().replace(" ", "");
				if (tableKeyName.contains(stringField)) {
					// 获取到它的下一行的数据
					Row row2 = sheet.getRow(7);
					Cell cell2 = row2.getCell(j);
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
				}
			}

			// 获取当前的实体
			Class<CompanyMember> cs = this.entityClass;
			// 通过字节码获取实例
			 companyMember = cs.newInstance();
			Class<?> clazz = baseExcelQueryTemplate.getClass();
			if (clazz != null) {
				// 获取模板数据的属性集合
				Field[] fileds = clazz.getDeclaredFields();
				for (Field field : fileds) {
					// 获取属性上对应的注解
					ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
					String name = excelTemplate.name();
					// 在Excel读取的内容
					String value = map.get(name);
					Field f = cs.getDeclaredField(field.getName().replaceAll("has_", ""));
					// 设置属性是可访问的
					f.setAccessible(true);

					Class<?> cla = f.getType();
					if (cla.equals(Date.class)) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						f.set(companyMember, sdf.parse(value));
					} else if (cla.equals(Integer.class)) {
						f.set(companyMember, Integer.parseInt(value));
					} else {
						f.set(companyMember, value);
					}
				}
			}

			// 读取Excel中的所有图片
			XSSFDrawing drawing = (XSSFDrawing) sheet.getDrawingPatriarch();

			if (drawing != null) {
				List<XSSFShape> shapes = drawing.getShapes();
				XSSFShape shape = shapes.get(0);
				if (shape instanceof XSSFPicture) {
					XSSFPicture picture = (XSSFPicture) shape;
					byte[] data = picture.getPictureData().getData();
					String imagePath = File.separator + "image" + File.separator + "upload" + File.separator
							+ System.currentTimeMillis() + "." + picture.getPictureData().suggestFileExtension();
					String savePath = propertiesService.getWindowsRootPath() + imagePath;
					File imagefile = new File(savePath);
					File parentFile = imagefile.getParentFile();
					if (!parentFile.exists()) {
						parentFile.mkdirs();
					}
					if (!imagefile.exists()) {
						imagefile.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(imagefile);
					fos.write(data);
					fos.close();
					String requestPath = "http://" + propertiesService.getCurrentServerForImage() + imagePath.replaceAll("\\\\", "/");
					companyMember.setImageUrl(requestPath);
				}
			}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
			return companyMember;
			
	}
	
	
	
	//传递类型查询并封装实体
		@SuppressWarnings("resource")
		@Override
		public CompanyMember readExcelData(BaseExcelQueryTemplate baseExcelQueryTemplate, String path,Integer type) {
			String tableName="";
			
			switch (type) {
			case 1:
			    tableName=CompanyMemberType.CORPORATEREPRESENTATIVE.getName();
				break;
			case 2:
			    tableName=CompanyMemberType.COMPANYMANAGE.getName();
				break;
			case 3:
			    tableName=CompanyMemberType.VEHICLERESPONSIBLEPERSON.getName();
				break;
			}
			CompanyMember companyMember=null;
			if(path!=null){
				File file=new File(path);
				if (!file.exists()||!file.isFile()) {
					return null;
				}
				//获取文件类型
				String fileType=path.substring(path.lastIndexOf(".")+1);
				InputStream is=null;
				try {
					is=new FileInputStream(path);
					// 创建一个工作薄
					Workbook workbook = null;
					if (fileType.equals("xls")) {
						workbook = new HSSFWorkbook(is);
					} else if (fileType.equals("xlsx")) {
						workbook = new XSSFWorkbook(is);
					} else {
						throw new RuntimeException("不是Excel类型");
					}
					companyMember=new CompanyMember();
					 
				Sheet	sheet =workbook.getSheet(tableName);
				if(sheet==null){
					sheet=workbook.getSheetAt(0);
				}
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
				Map<String, String> map = new HashMap<String, String>();
				// 先读取第二行到第六行的数据
				for (int i = 1; i <= 5; i++) {
					// 获取当前行的数据
					Row row = sheet.getRow(i);
					// 遍历当前行的每一列的内容
					for (int j = 0; j <= 5; j++) {
						// 拿到当前单元格的内容
						Cell cell1 = row.getCell(j);
						if (cell1 != null) {
							cell1.setCellType(Cell.CELL_TYPE_STRING);
							if (cell1.getStringCellValue() != null && !cell1.getStringCellValue().equals("")) {
								// 设置单元格数据为String类型
								String stringField = cell1.getStringCellValue().replace(" ", "");
								if (tableKeyName.contains(stringField)) {
									if (stringField.equals("出生年月")) {
										Cell cell2 = row.getCell(j + 2);
										if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
											Date date=cell2.getDateCellValue();
											if(date!=null){
												SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
												map.put(stringField,sdf.format(date) );
											}
										}
									}
									else if (stringField.equals("姓名") || stringField.equals("何时何校何专业毕业")
											|| stringField.equals("企业管理资历")) {
										// 如果包含在表格字段列表中，就去拿它右2格的数据
										Cell cell2 = row.getCell(j + 2);
										cell2.setCellType(Cell.CELL_TYPE_STRING);
										map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
									} else {
										Cell cell2 = row.getCell(j + 1);
										cell2.setCellType(Cell.CELL_TYPE_STRING);
										map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
									}
								}
							}

						}

					}
				}

				// 从第七行开始
				Row row1 = sheet.getRow(6);
				// 遍历当前行的每一列的内容
				for (int j = 1; j <= 5; j++) {
					Cell cell1 = row1.getCell(j);
					cell1.setCellType(Cell.CELL_TYPE_STRING);
					String stringField = cell1.getStringCellValue().replace(" ", "");
					if (tableKeyName.contains(stringField)) {
						// 获取到它的下一行的数据
						Row row2 = sheet.getRow(7);
						Cell cell2 = row2.getCell(j);
						cell2.setCellType(Cell.CELL_TYPE_STRING);
						map.put(stringField, cell2.getStringCellValue().replace(" ", ""));
					}
				}

				// 获取当前的实体
				Class<CompanyMember> cs = this.entityClass;
				// 通过字节码获取实例
				 companyMember = cs.newInstance();
				 companyMember.setMemberType(type);
				Class<?> clazz = baseExcelQueryTemplate.getClass();
				if (clazz != null) {
					// 获取模板数据的属性集合
					Field[] fileds = clazz.getDeclaredFields();
					for (Field field : fileds) {
						// 获取属性上对应的注解
						ExcelTemplate excelTemplate = field.getAnnotation(ExcelTemplate.class);
						String name = excelTemplate.name();
						// 在Excel读取的内容
						String value = map.get(name);
						if(value!=null&&!value.equals("")){
							Field f = cs.getDeclaredField(field.getName().replaceAll("has_", ""));
							// 设置属性是可访问的
							f.setAccessible(true);
							
							Class<?> cla = f.getType();
							if (cla.equals(Date.class)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								f.set(companyMember, sdf.parse(value));
							} else if (cla.equals(Integer.class)) {
								f.set(companyMember, Integer.parseInt(value));
							} else {
								f.set(companyMember, value);
							}
						}
					}
				}
				

		
				if (fileType.equals("xls")) {
					HSSFSheet hSheet=(HSSFSheet)sheet;
					List<HSSFShape> shapes=hSheet.getDrawingPatriarch().getChildren();
					HSSFShape	 shape=shapes.get(0);
				   if(shape instanceof HSSFShape){
					   HSSFPicture picture=(HSSFPicture)shape;
					   byte[] data = picture.getPictureData().getData();
						String imagePath = File.separator + "image" + File.separator + "upload" + File.separator
								+ System.currentTimeMillis() + "." + picture.getPictureData().suggestFileExtension();
						String savePath = propertiesService.getWindowsRootPath() + imagePath;
						File imagefile = new File(savePath);
						File parentFile = imagefile.getParentFile();
						if (!parentFile.exists()) {
							parentFile.mkdirs();
						}
						if (!imagefile.exists()) {
							imagefile.createNewFile();
						}
						FileOutputStream fos = new FileOutputStream(imagefile);
						fos.write(data);
						fos.close();
						String requestPath = "http://" + propertiesService.getCurrentServerForImage() + imagePath.replaceAll("\\\\", "/");
						companyMember.setImageUrl(requestPath);
				   }
				   
					
				}else if(fileType.equals("xlsx")){
					// 读取Excel中的所有图片
					XSSFDrawing drawing = (XSSFDrawing) sheet.getDrawingPatriarch();

					if (drawing != null) {
						List<XSSFShape> shapes = drawing.getShapes();
						XSSFShape shape = shapes.get(0);
						if (shape instanceof XSSFPicture) {
							XSSFPicture picture = (XSSFPicture) shape;
							byte[] data = picture.getPictureData().getData();
							String imagePath = File.separator + "image" + File.separator + "upload" + File.separator
									+ System.currentTimeMillis() + "." + picture.getPictureData().suggestFileExtension();
							String savePath = propertiesService.getWindowsRootPath() + imagePath;
							File imagefile = new File(savePath);
							File parentFile = imagefile.getParentFile();
							if (!parentFile.exists()) {
								parentFile.mkdirs();
							}
							if (!imagefile.exists()) {
								imagefile.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(imagefile);
							fos.write(data);
							fos.close();
							String requestPath = "http://" + propertiesService.getCurrentServerForImage() + imagePath.replaceAll("\\\\", "/");
							companyMember.setImageUrl(requestPath);
						}
					}
				}
			
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			
			}
			return companyMember;
		}
		

			
		
		
	

	@Override
	protected BaseMapper<CompanyMember> getMapper() {
		return companyMemberMapper;
	}

	@Override
	protected String getFields() {
		return null;
	}

}
