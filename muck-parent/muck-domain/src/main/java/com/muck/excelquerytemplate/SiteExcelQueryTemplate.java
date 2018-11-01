package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

public class SiteExcelQueryTemplate extends BaseExcelQueryTemplate {

	
	// 工地名称
	@ExcelTemplate(name="工地名称")
	private String has_siteName;
	// 工地地址
	@ExcelTemplate(name="工地地址")
	private String has_siteAddress;
	/**项目详情*/
	@ExcelTemplate(name="项目名称")
	private String has_siteProjectInfo;
	/**所属区域*/
	@ExcelTemplate(name="所属区域")
	private String has_area;
	/**所属企业*/
	@ExcelTemplate(name="所属企业")
	private String has_company;
	/*工程项目进度**/
	@ExcelTemplate(name="工程项目进度描述")
	private String has_siteProcessMemo;
	

	public String getHas_siteName() {
		return has_siteName;
	}
	public void setHas_siteName(String has_siteName) {
		this.has_siteName = has_siteName;
	}
	public String getHas_siteAddress() {
		return has_siteAddress;
	}
	public void setHas_siteAddress(String has_siteAddress) {
		this.has_siteAddress = has_siteAddress;
	}
	public String getHas_siteProjectInfo() {
		return has_siteProjectInfo;
	}
	public void setHas_siteProjectInfo(String has_siteProjectInfo) {
		this.has_siteProjectInfo = has_siteProjectInfo;
	}
	public String getHas_area() {
		return has_area;
	}
	public void setHas_area(String has_area) {
		this.has_area = has_area;
	}
	public String getHas_company() {
		return has_company;
	}
	public void setHas_company(String has_company) {
		this.has_company = has_company;
	}
	public String getHas_siteProcessMemo() {
		return has_siteProcessMemo;
	}
	public void setHas_siteProcessMemo(String has_siteProcessMemo) {
		this.has_siteProcessMemo = has_siteProcessMemo;
	}
	
	
	
}
