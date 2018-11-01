package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 企业管理人员模版类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月20日 下午9:57:24
 */
public class CompanyManagerExcelQueryTemplate extends BaseExcelQueryTemplate{

	/**表格标题*/
	@ExcelTemplate(name="企业管理人员汇总")
	private String title;
	@ExcelTemplate(name="序号")
	private String has_companyManagerId;
	@ExcelTemplate(name="姓名")
	private String has_companyManagerName;
	@ExcelTemplate(name="性别")
	private String has_companyManagerSex;
	@ExcelTemplate(name="岗位")
	private String has_companyManagerPost;
	@ExcelTemplate(name="联系电话")
	private String has_companyManagerPhone;
	@ExcelTemplate(name="身份证号码")
	private String has_companyManagerIdNumber;
	@ExcelTemplate(name="备注")
	private String has_memo;
	public CompanyManagerExcelQueryTemplate() {
	}
	public CompanyManagerExcelQueryTemplate(String title, String has_companyManagerId, String has_companyManagerName,
			String has_companyManagerSex, String has_companyManagerPost, String has_companyManagerPhone,
			String has_companyManagerIdNumber, String has_memo) {
		super();
		this.title = title;
		this.has_companyManagerId = has_companyManagerId;
		this.has_companyManagerName = has_companyManagerName;
		this.has_companyManagerSex = has_companyManagerSex;
		this.has_companyManagerPost = has_companyManagerPost;
		this.has_companyManagerPhone = has_companyManagerPhone;
		this.has_companyManagerIdNumber = has_companyManagerIdNumber;
		this.has_memo = has_memo;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHas_companyManagerId() {
		return has_companyManagerId;
	}
	public void setHas_companyManagerId(String has_companyManagerId) {
		this.has_companyManagerId = has_companyManagerId;
	}
	public String getHas_companyManagerName() {
		return has_companyManagerName;
	}
	public void setHas_companyManagerName(String has_companyManagerName) {
		this.has_companyManagerName = has_companyManagerName;
	}
	public String getHas_companyManagerSex() {
		return has_companyManagerSex;
	}
	public void setHas_companyManagerSex(String has_companyManagerSex) {
		this.has_companyManagerSex = has_companyManagerSex;
	}
	public String getHas_companyManagerPost() {
		return has_companyManagerPost;
	}
	public void setHas_companyManagerPost(String has_companyManagerPost) {
		this.has_companyManagerPost = has_companyManagerPost;
	}
	public String getHas_companyManagerPhone() {
		return has_companyManagerPhone;
	}
	public void setHas_companyManagerPhone(String has_companyManagerPhone) {
		this.has_companyManagerPhone = has_companyManagerPhone;
	}
	public String getHas_companyManagerIdNumber() {
		return has_companyManagerIdNumber;
	}
	public void setHas_companyManagerIdNumber(String has_companyManagerIdNumber) {
		this.has_companyManagerIdNumber = has_companyManagerIdNumber;
	}
	public String getHas_memo() {
		return has_memo;
	}
	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}
}
