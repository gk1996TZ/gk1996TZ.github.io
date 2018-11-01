package com.muck.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 生成excel表格的工具类
 */
public class ExcelUtil {

	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * @Description: 生成Excel表格的方法（jxl方式，在本地会生成一个Excel文件）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月26日 下午6:25:07
	 * @param: currentServerForExcel 当前系统的excel访问ip加端口
	 * @param rootPath 传入存放表格的根目录
	 * @param: subdirectory
	 *             传入存放表格文件的子目录
	 * @param: data
	 *             传入存放到表格中的数据，该数据包含表头信息和表身数据
	 * @return:String 返回下载Excel表格的本机存放目录
	 * @Deprecated @see com.muck.utils.ExcelUtil.createExcelPOI(String
	 *             subdirectory, List<Map<String, String>> data)
	 */
	@Deprecated
	public static String createExcelJXL(String currentServerForExcel,String rootPath,String subdirectory, List<Map<String, String>> data) {
		String downloadPath = "";
		if (!StringUtils.isBlank(subdirectory)) {
			/*
			 * 拼接下载和存放到本机的文件子路径 downloadPath
			 * 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径 subdirectory
			 * 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
			 */
			downloadPath = File.separator+"excel"+File.separator + "download" + File.separator + subdirectory + File.separator
					+ new Date().getTime() + ".xlsx";
		}
		if (data != null && data.size() > 0) {
			try {
				// 声明一个本机存放文件的目录
				String path = "";
				// PathDefine.WINDOWS_UPLOAD_ROOT_EXCEL_PATH
				// 表示配置的存放Excel表格文件的根目录
				path = rootPath + downloadPath;
				// 根据路径创建一个file对象
				File xlsxFile = new File(path);
				// 获取文件的父级目录
				File parentFile = xlsxFile.getParentFile();
				// 判断父级目录是否存在
				if (!parentFile.exists()) {
					// 生成父级目录，判断是否生成成功
					if (!parentFile.mkdirs()) {
						return "";
					}
				}
				// 判断文件是否存在
				if (!xlsxFile.exists()) {
					// 如果文件不存在，创建该文件，并判断是否创建成功
					if (!xlsxFile.createNewFile()) {
						return "";
					}
				}
				// 再次判断文件是否存在
				if (xlsxFile.exists()) {
					// 创建一个工作簿
					WritableWorkbook workbook = Workbook.createWorkbook(xlsxFile);
					// 创建一个工作表
					WritableSheet sheet = workbook.createSheet("sheet1", 0);
					for (int row = 0; row < data.size(); row++) {
						Set<String> key = data.get(row).keySet();
						Iterator<String> iterator = key.iterator();
						int col = 0;
						while (iterator.hasNext()) {
							// 将数据放到excel表中
							sheet.addCell(new Label(col, row, (String) data.get(row).get(iterator.next())));
							col++;
						}
					}
					workbook.write();
					workbook.close();
					//return path;
					return "http://"+currentServerForExcel + downloadPath.replaceAll("\\\\", "/");
				}
			} catch (RowsExceededException e) {
				/*
				 * 当数据量比较大的时候，如果超过了65536条的时候， 导出的时候就会如下的错误：
				 * jxl.write.biff.RowsExceededException
				 */
				logger.debug("生成表格时数据量超过了65536条", e);
			} catch (WriteException e) {
				logger.debug("向表格中添加数据异常", e);
			} catch (IOException e) {
				logger.debug("生成表格文件异常", e);
			}
		}
		return "";
	}

	/**
	 * @Description: 生成Excel表格的方法（POI方式，在本地会生成一个Excel文件）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月28日 下午5:23:56
	 * @param: currentServerForExcel 当前系统的excel访问ip加端口
	 * @param: rootPath 传入存放表格文件的根目录
	 * @param: subdirectory
	 *             传入存放表格文件的子目录
	 * @param: data
	 *             传入存放到表格中的数据，该数据包含表头信息和表身数据
	 * @param: waterPath 传入存放水印文件的根目录
	 * @param: water
	 *             文字水印
	 * @return:String 返回下载Excel表格的访问目录和存放到本机的文件子路径
	 */
	public static String createExcelPOI(String currentServerForExcel,String rootPath,String subdirectory,List<Map<String, String>> data,String waterPath, String[] water) {
		String downloadPath = "";
		if (!StringUtils.isBlank(subdirectory)) {
			/*
			 * 拼接下载和存放到本机的文件子路径 downloadPath
			 * 该路径是存放到数据库中和返回给前台用来下载此次生成的Excel表格的路径 subdirectory
			 * 该路径是调用该生成Excel表格方法时传出的子级目录， 用来区分表格的类别，可以传入为空，表示在配置好的根目录下
			 */
			downloadPath =File.separator+ "excel"+ File.separator + "download" + File.separator + subdirectory + File.separator
					+ new Date().getTime() + ".xlsx";
		}
		// 声明一个本机存放文件的目录
		String path = "";
		// PathDefine.WINDOWS_UPLOAD_ROOT_EXCEL_PATH
		// 表示配置的存放Excel表格文件的根目录
		path = rootPath + downloadPath;

		try {
			// 根据路径创建一个file对象
			File xlsxFile = new File(path);
			// 获取文件的父级目录
			File parentFile = xlsxFile.getParentFile();
			// 判断父级目录是否存在
			if (!parentFile.exists()) {
				// 生成父级目录，判断是否生成成功
				if (!parentFile.mkdirs()) {
					return "";
				}
			}
			// 判断文件是否存在
			if (!xlsxFile.exists()) {
				// 如果文件不存在，创建该文件，并判断是否创建成功
				if (!xlsxFile.createNewFile()) {
					return "";
				}
			}
			if (xlsxFile.exists() && xlsxFile.isFile()) {
				// 创建Excel
				org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
				// 创建Sheet
				org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("导出数据");
				org.apache.poi.xssf.usermodel.XSSFDrawing patriarch = sheet.createDrawingPatriarch();
				//
				for (int row = 0; row < data.size(); row++) {
					org.apache.poi.xssf.usermodel.XSSFRow newrow = sheet.createRow(row);
					Set<String> keySet = data.get(row).keySet();
					Iterator<String> iterator = keySet.iterator();
					int col = 0;
					while (iterator.hasNext()) {
						String key = iterator.next();
						String value = data.get(row).get(key);
						// 将数据放到excel表中
						// 创建一个列单元格
						org.apache.poi.xssf.usermodel.XSSFCell cell = newrow.createCell(col);
						if (value != null && (value.contains("http") | value.contains("https"))) {
							// 获取后缀
							String postfix = value.substring(value.lastIndexOf(".") + 1, value.length());
							if (("jpg".equalsIgnoreCase(postfix) | "png".equalsIgnoreCase(postfix)
									| "jpeg".equalsIgnoreCase(postfix) | "gif".equalsIgnoreCase(postfix))) {
								byte[] imageData = FileStreamUtils.getInputStream(rootPath+value.substring(value.indexOf("/",value.indexOf("//")+2)).replaceAll("/","\\\\"));
								org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor = new org.apache.poi.xssf.usermodel.XSSFClientAnchor(
										0, 0, 0, 0, (short) col, row, (short) (col + 1), row + 1);
								anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
								if(imageData !=null){
									patriarch.createPicture(anchor, workbook.addPicture(imageData,
											org.apache.poi.xssf.usermodel.XSSFWorkbook.PICTURE_TYPE_PNG));
								}
							}
						} else {
							cell.setCellType(org.apache.poi.xssf.usermodel.XSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(value);
						}
						col++;
					}
				}
				if(water != null && water.length > 0){
					// 水印文件存放目录
					String imgPath = waterPath + File.separator + "水印.png";
					// 生成水印图片
					ImageUtils.createWaterMark(water, imgPath);
					// String result = ImageUtil.outputImage(water);
					// ImageUtil.write2Pic(result,imgPath);
					// 将水印图片加载到表格中
					ExcelWaterMarkUtils.setWaterMarkToExcel(workbook, sheet, imgPath, 0, 10, 9, 52, 3, 50, 0, 0);
				}
				// 创建一个文件输出流
				FileOutputStream fos = new FileOutputStream(xlsxFile);
				workbook.write(fos);
				fos.close();
				workbook.close();
				//return path;
				return "http://"+currentServerForExcel + downloadPath.replaceAll("\\\\", "/");
			}
		} catch (FileNotFoundException e) {
			logger.debug("文件不存在", e);
		} catch (IOException e) {
			logger.debug("生成" + subdirectory + "表格文件失败", e);
		}
		return "";
	}

	/**
	 * @Description: 读取指定Excel表格文件的内容，该方法使用jxl方式读取，不支持.xlsx文件格式
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月26日 下午6:26:02
	 * @param:@param file
	 *                   传入一个Excel表格文件
	 * @return:List<Map<String,String>> 返回读取到的数据列表
	 * @deprecated @see com.muck.utils.ExcelUtil.read(String path)
	 */
	@Deprecated
	public static List<Map<String, String>> readExcel(String path) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			File file = new File(path);
			// 判断如果文件不存在则返回null;
			if (!file.exists()) {
				return null;
			}
			// 判断如果不是文件，则返回null;
			if (!file.isFile()) {
				return null;
			}
			// 判断文件是否存在
			// 通过指定的Excel表格文件创建工作簿
			Workbook workbook = Workbook.getWorkbook(file);
			// 获得第一个工作表sheet1
			Sheet sheet = workbook.getSheet(0);
			// 获得数据
			for (int i = 1; i < sheet.getRows(); i++) {// sheet.getRows():获得表格文件行数
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < sheet.getColumns(); j++) {// sheet.getColumns():获得表格文件列数
					Cell cell = sheet.getCell(j, i);
					map.put(sheet.getCell(j, 0).getContents(), cell.getContents());
				}
				list.add(map);
			}
			workbook.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Description: 读取指定Excel表格文件的内容，该方法使用POI方式读取，支持.xls和.xlsx文件格式
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月27日 上午12:19:02
	 * @param: currentServerForImage 当前系统的图片访问ip加端口
	 * @param: path
	 *                   传入一个Excel表格文件路径
	 * @param: rootPath 传入本机存放文件的根目录
	 * @return:List<Map<String,String>> 返回读取到的数据列表，不包含表头
	 */
	public static List<Map<String, String>> read(String currentServerForImage,String path,String rootPath) {
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
				sheet1 = wb.getSheetAt(0);
			} else if (fileType.equals("xlsx")) {
				wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
				sheet1 = wb.getSheetAt(0);
			} else {
				logger.debug("Excel格式不支持");
			}
			// 循环遍历工作薄中的数据
			// 获取表头数据
			Map<String, String> tableHead = new HashMap<String, String>();
			// 表头每列的字段名
			List<String> tableHeadName = new ArrayList<String>();
			//存放每一列对应的表头名，key为列号，value为表头名
			Map<String,String> tableHeadNameCell = new HashMap<String,String>();
			// 如果工作薄对象不为空
			if (sheet1 != null) {
				// 获取第一行表头数据
				org.apache.poi.ss.usermodel.Row row = sheet1.getRow(0);
				if (row != null) {
					// 遍历表头数据
					Integer temp = 0;
					for (org.apache.poi.ss.usermodel.Cell cell : row) {
						// 设置单元格数据为String类型
						cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
						// 将表头数据添加到map中
						tableHead.put(cell.getStringCellValue(), cell.getStringCellValue());
						// 将表头名称添加到相应的容器中
						tableHeadName.add(cell.getStringCellValue());
						//将表头放到表头名对应列号的容器中
						tableHeadNameCell.put(""+(temp++), cell.getStringCellValue());
					}
				}
			}
			// 遍历除表头之外的每一行数据
			Map<String, String> tableBody = null;
			// sheet1.getLastRowNum() 获取最后一行的行号
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
						if(cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
							if(XSSFDateUtil.isCellDateFormatted(cell)){
								tableBody.put(tableHeadName.get(j),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
							}
						}else {
							cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
							// 将表头数据添加到map中
							tableBody.put(tableHeadName.get(j), cell.getStringCellValue());
						}
					}
				}
				list.add(tableBody);
			}
			//声明一个存放图片数据的数据
			byte [] data = null;
			//声明一个存放图片的子目录
			String imagePath = "";
			//声明一个存放图片的本地全路径
			String savePath = "";
			//声明图片所在的行和列
			int row = 0;
			int col = 0;
			if (fileType.equals("xls")) {
				//如果导入的是xls类型的excel表格
				org.apache.poi.hssf.usermodel.HSSFSheet hSheet = (org.apache.poi.hssf.usermodel.HSSFSheet)sheet1;
				//获取并遍历当前工作薄所有的图片
				for(org.apache.poi.hssf.usermodel.HSSFShape shape : hSheet.getDrawingPatriarch().getChildren()){
					//判断图片是xls类型的工作簿图片
					if(shape instanceof org.apache.poi.hssf.usermodel.HSSFPicture){
						//将图片向下转型到xls类型的工作薄图片
						org.apache.poi.hssf.usermodel.HSSFPicture pic=(org.apache.poi.hssf.usermodel.HSSFPicture) shape;
						//获取图片单元格信息
						org.apache.poi.hssf.usermodel.HSSFClientAnchor xssfClientAnchor=pic.getClientAnchor();
						row=xssfClientAnchor.getRow1();
						col=xssfClientAnchor.getCol1();
						//获取图片二进制数据
						data=pic.getPictureData().getData();
						//声明保存图片的路径
						imagePath = File.separator+"image"+File.separator + "upload"+ File.separator +System.currentTimeMillis()+"."+pic.getPictureData().suggestFileExtension();
						savePath = rootPath+imagePath;
					}
				}
			} else if (fileType.equals("xlsx")) {
				org.apache.poi.xssf.usermodel.XSSFDrawing xssfDrawing=(org.apache.poi.xssf.usermodel.XSSFDrawing) sheet1.getDrawingPatriarch();
				if(xssfDrawing != null){
					// 获取所有的图片内容
					List<org.apache.poi.xssf.usermodel.XSSFShape> listShapes=xssfDrawing.getShapes();
					if(listShapes.size()>0){
						for(org.apache.poi.xssf.usermodel.XSSFShape shape:listShapes){
							if(shape instanceof org.apache.poi.xssf.usermodel.XSSFPicture){
								org.apache.poi.xssf.usermodel.XSSFPicture pic=(org.apache.poi.xssf.usermodel.XSSFPicture) shape;
								org.apache.poi.xssf.usermodel.XSSFClientAnchor xssfClientAnchor=pic.getClientAnchor();
								row=xssfClientAnchor.getRow1();
								col=xssfClientAnchor.getCol1();
								data=pic.getPictureData().getData();
								imagePath = File.separator+"image"+File.separator + "upload"+ File.separator +System.currentTimeMillis()+"."+pic.getPictureData().suggestFileExtension();
								savePath = rootPath+imagePath;
							}
						}
					}
				}
			} else {
				logger.debug("Excel格式不支持");
			}
			
			File imagefile = new File(savePath);
			File parentFile = imagefile.getParentFile();
			if(!parentFile.exists()){
				if(!parentFile.mkdirs()){
				}
			}
			if(!imagefile.exists()){
				imagefile.createNewFile();
			}
			//将图片保存到本地
			FileOutputStream fos=new FileOutputStream(imagefile);
			fos.write(data);
			fos.close();
			String requestPath = "http://" + currentServerForImage + imagePath.replaceAll("\\\\", "/");
			//获取指定行的数据
			Map<String,String> rowData = list.get(row-1);
			//获取指定列的表头名
			String colName = tableHeadNameCell.get(col+"");
			if(rowData != null){
				rowData.put(colName, requestPath);
			}
		} catch (IOException e) {
		}
		return list;
	}
}
