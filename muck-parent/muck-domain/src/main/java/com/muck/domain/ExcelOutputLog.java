package com.muck.domain;

import com.muck.annotation.Table;

/**
 * @Description:
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月26日 下午4:30:26
 */
@Table(name="t_output_excel_log")
public class ExcelOutputLog extends BaseEntity{

	/**导出Excel文件名称*/
	private String outputExcelName;
	/**导出Excel文件访问路径*/
	private String outputExcelPath;
	/**导出Excel文件备注信息*/
	private String memo;
	
	public String getOutputExcelName() {
		return outputExcelName;
	}
	public void setOutputExcelName(String outputExcelName) {
		this.outputExcelName = outputExcelName;
	}
	public String getOutputExcelPath() {
		return outputExcelPath;
	}
	public void setOutputExcelPath(String outputExcelPath) {
		this.outputExcelPath = outputExcelPath;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
