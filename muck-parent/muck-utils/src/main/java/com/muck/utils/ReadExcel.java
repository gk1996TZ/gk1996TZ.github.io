package com.muck.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.muck.utils.map.CoordinateConverter;

public class ReadExcel {
	
	
	public static void main(String[] args) throws Exception {
		
		File file=new File("d:/muck/丰艺企业-浙G61533车辆数据(2018-08-18号之后)(1).xls");
		if(file!=null){
			InputStream is = new FileInputStream(file);
			
			Workbook workbook  = new HSSFWorkbook(is);
			
			Sheet sheet=workbook.getSheetAt(0);
			
			int lastNum=sheet.getLastRowNum();
			
			for(int i=1;i<=lastNum;i++){
				Row row=sheet.getRow(i);
				
				//经度
				String lati=row.getCell(2).getStringCellValue();
				//纬度
				String lonti=row.getCell(3).getStringCellValue();
				
				double[] coordinate=CoordinateConverter.wgs2GCJ(Double.parseDouble(lati)/1000000, Double.parseDouble(lonti)/1000000);
				
				//国测局经度
				double la=coordinate[0];
				double lo=coordinate[1];
				
				Cell guola=row.createCell(10);
				
				Cell guolo=row.createCell(11);
				
				guola.setCellType(Cell.CELL_TYPE_NUMERIC);
				guola.setCellValue(la);
				
				guolo.setCellType(Cell.CELL_TYPE_NUMERIC);
				guolo.setCellValue(lo);
	
			}
			
			FileOutputStream fos=new FileOutputStream(file);
			workbook.write(fos);
			workbook.close();
			fos.close();
			is.close();
		}
	}

}
