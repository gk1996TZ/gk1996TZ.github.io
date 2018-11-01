package com.muck.excelquerytemplate;

import java.util.Date;

import com.muck.annotation.ExcelTemplate;

public class CarSnapshotImageTemplate extends BaseExcelQueryTemplate {
	
	
	/** 车牌号*/
	@ExcelTemplate(name="车牌号")
    private String has_carCode;

	@ExcelTemplate(name="车牌颜色")
	private String has_carCardColor;
	
    /** 企业名称*/
	@ExcelTemplate(name="运输企业")
    private String has_companyName;
    
	 /** 抓拍时间*/
	@ExcelTemplate(name="抓拍时间")
    private Date has_snapshotTime;

	/** 所属类型*/
	/*@ExcelTemplate(name="所属类型")
    private String has_snapshotType;*/
	
    /** 抓拍图片路径*/
	/*@ExcelTemplate(name="抓拍大图片")
    private String has_snapshotBigImagePath;*/

	/** 抓拍图片路径*/
	/*@ExcelTemplate(name="抓拍小图片")
	private String has_snapshotSmailImagePath;*/
	
	/*场地名称*/
	@ExcelTemplate(name="场地名称")
	private String has_placeName;

	public String getHas_carCode() {
		return has_carCode;
	}

	public void setHas_carCode(String has_carCode) {
		this.has_carCode = has_carCode;
	}

	public String getHas_carCardColor() {
		return has_carCardColor;
	}

	public void setHas_carCardColor(String has_carCardColor) {
		this.has_carCardColor = has_carCardColor;
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

	/*public String getHas_snapshotBigImagePath() {
		return has_snapshotBigImagePath;
	}

	public void setHas_snapshotBigImagePath(String has_snapshotBigImagePath) {
		this.has_snapshotBigImagePath = has_snapshotBigImagePath;
	}

	public String getHas_snapshotSmailImagePath() {
		return has_snapshotSmailImagePath;
	}

	public void setHas_snapshotSmailImagePath(String has_snapshotSmailImagePath) {
		this.has_snapshotSmailImagePath = has_snapshotSmailImagePath;
	}

	public String getHas_snapshotType() {
		return has_snapshotType;
	}

	public void setHas_snapshotType(String has_snapshotType) {
		this.has_snapshotType = has_snapshotType;
	}*/

	public String getHas_placeName() {
		return has_placeName;
	}

	public void setHas_placeName(String has_placeName) {
		this.has_placeName = has_placeName;
	}

	

	

}
