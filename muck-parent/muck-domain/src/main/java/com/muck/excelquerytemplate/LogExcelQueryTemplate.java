package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 日志Excel模版
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月4日 下午2:01:37
 */
public class LogExcelQueryTemplate extends BaseExcelQueryTemplate{

	@ExcelTemplate(name="账号名称")
	private String has_operatorAccount;
	@ExcelTemplate(name="联系电话")
	private String has_operatorPhone;
	@ExcelTemplate(name="操作人名称")
	private String has_operatorName;
	@ExcelTemplate(name="操作模块")
	private String has_operatorModel;
	@ExcelTemplate(name="操作时间")
	private String has_operatorTime;
	@ExcelTemplate(name="备注")
	private String has_memo;
	
	public LogExcelQueryTemplate() {
	}
	public LogExcelQueryTemplate(String has_operatorAccount, String has_operatorPhone, String has_operatorName,
			String has_operatorModel, String has_operatorTime, String has_memo) {
		super();
		this.has_operatorAccount = has_operatorAccount;
		this.has_operatorPhone = has_operatorPhone;
		this.has_operatorName = has_operatorName;
		this.has_operatorModel = has_operatorModel;
		this.has_operatorTime = has_operatorTime;
		this.has_memo = has_memo;
	}
	public String getHas_operatorAccount() {
		return has_operatorAccount;
	}
	public void setHas_operatorAccount(String has_operatorAccount) {
		this.has_operatorAccount = has_operatorAccount;
	}
	public String getHas_operatorPhone() {
		return has_operatorPhone;
	}
	public void setHas_operatorPhone(String has_operatorPhone) {
		this.has_operatorPhone = has_operatorPhone;
	}
	public String getHas_operatorName() {
		return has_operatorName;
	}
	public void setHas_operatorName(String has_operatorName) {
		this.has_operatorName = has_operatorName;
	}
	public String getHas_operatorModel() {
		return has_operatorModel;
	}
	public void setHas_operatorModel(String has_operatorModel) {
		this.has_operatorModel = has_operatorModel;
	}
	public String getHas_operatorTime() {
		return has_operatorTime;
	}
	public void setHas_operatorTime(String has_operatorTime) {
		this.has_operatorTime = has_operatorTime;
	}
	public String getHas_memo() {
		return has_memo;
	}
	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}
}
