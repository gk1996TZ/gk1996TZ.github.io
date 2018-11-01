package com.muck.excelquerytemplate;

import java.util.Date;

import com.muck.annotation.ExcelTemplate;

public class SnapshotImageTemplate extends BaseExcelQueryTemplate {

	/** 项目名称 */
	@ExcelTemplate(name = "项目名称")
	private String has_projectName;

	/** 所属区域 */
	@ExcelTemplate(name = "所属区域")
	private String has_areaName;

	/** 所属企业 */
	@ExcelTemplate(name = "所属企业")
	private String has_companyName;

	/** 抓拍时间 */
	@ExcelTemplate(name = "抓拍时间")
	private Date has_snapshotTime;

	/** 抓拍图片路径 */
	@ExcelTemplate(name = "抓拍图片")
	private String has_snapshotImagePath;

	/* 抓拍人 */
	@ExcelTemplate(name = "抓拍人")
	private String has_operatorName;

	public String getHas_projectName() {
		return has_projectName;
	}

	public void setHas_projectName(String has_projectName) {
		this.has_projectName = has_projectName;
	}

	public String getHas_areaName() {
		return has_areaName;
	}

	public void setHas_areaName(String has_areaName) {
		this.has_areaName = has_areaName;
	}

	public String getHas_companyName() {
		return has_companyName;
	}

	public void setHas_companyName(String has_companyName) {
		this.has_companyName = has_companyName;
	}

	public Date getHas_snapshotTime() {
		return has_snapshotTime;
	}

	public void setHas_snapshotTime(Date has_snapshotTime) {
		this.has_snapshotTime = has_snapshotTime;
	}

	public String getHas_snapshotImagePath() {
		return has_snapshotImagePath;
	}

	public void setHas_snapshotImagePath(String has_snapshotImagePath) {
		this.has_snapshotImagePath = has_snapshotImagePath;
	}

	public String getHas_operatorName() {
		return has_operatorName;
	}

	public void setHas_operatorName(String has_operatorName) {
		this.has_operatorName = has_operatorName;
	}

}
