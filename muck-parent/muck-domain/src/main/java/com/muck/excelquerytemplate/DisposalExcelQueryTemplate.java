package com.muck.excelquerytemplate;

import com.muck.annotation.ExcelTemplate;

/**
 * @Description: 处置场Excel请求模版参数
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月28日 下午12:02:10
 */
public class DisposalExcelQueryTemplate extends BaseExcelQueryTemplate{

	/**处置场名称*/
	@ExcelTemplate(name="处置场名称")
	private String has_disposalName;
	/**处置场渣土容量*/
	@ExcelTemplate(name="处置场渣土容量")
	private String has_disposalMuckCapacity;
	/**处置场所属区域名称*/
	@ExcelTemplate(name="所属区域")
	private String has_areaName;
	
	/**处置场地址*/
	@ExcelTemplate(name="处置场地址")
	private String has_disposalAddress;
	
	/**处置场所属企业名称*/
	@ExcelTemplate(name="所属企业")
	private String has_companyName;
	/**处置场负责人1*/
	@ExcelTemplate(name="处置场负责人1")
	private String has_disposalPrincipalName1;
	/**处置场负责人1的联系方式*/
	@ExcelTemplate(name="处置场负责人1联系方式")
	private String has_disposalPrincipalPhone1;
	/**处置场负责人2*/
	@ExcelTemplate(name="处置场负责人2")
	private String has_disposalPrincipalName2;
	/**处置场负责人2的联系方式*/
	@ExcelTemplate(name="处置场负责人2联系方式")
	private String has_disposalPrincipalPhone2;
	/**处置场负责人3*/
	@ExcelTemplate(name="处置场负责人3")
	private String has_disposalPrincipalName3;
	/**处置场负责人3的联系方式*/
	@ExcelTemplate(name="处置场负责人3联系方式")
	private String has_disposalPrincipalPhone3;
	/**处置场负责人4*/
	@ExcelTemplate(name="处置场负责人4")
	private String has_disposalPrincipalName4;
	/**处置场负责人4的联系方式*/
	@ExcelTemplate(name="处置场负责人4联系方式")
	private String has_disposalPrincipalPhone4;
	/**处置场负责人5*/
	@ExcelTemplate(name="处置场负责人5")
	private String has_disposalPrincipalName5;
	/**处置场负责人5的联系方式*/
	@ExcelTemplate(name="处置场负责人5联系方式")
	private String has_disposalPrincipalPhone5;
	/**备注信息*/
	@ExcelTemplate(name="处置场备注信息")
	private String has_memo;
	
	public DisposalExcelQueryTemplate() {
	}
	
	public DisposalExcelQueryTemplate(String has_disposalName, String has_disposalMuckCapacity, String has_areaName,
			String has_disposalAddress,String has_companyName, String has_disposalPrincipalName1, String has_disposalPrincipalPhone1,
			String has_memo) {
		super();
		this.has_disposalName = has_disposalName;
		this.has_disposalMuckCapacity = has_disposalMuckCapacity;
		this.has_areaName = has_areaName;
		this.has_disposalAddress = has_disposalAddress;
		this.has_companyName = has_companyName;
		this.has_disposalPrincipalName1 = has_disposalPrincipalName1;
		this.has_disposalPrincipalPhone1 = has_disposalPrincipalPhone1;
		this.has_memo = has_memo;
	}

	public DisposalExcelQueryTemplate(String has_disposalName, String has_disposalMuckCapacity, String has_areaName,
			String has_disposalAddress, String has_companyName, String has_disposalPrincipalName1,
			String has_disposalPrincipalPhone1, String has_disposalPrincipalName2, String has_disposalPrincipalPhone2,
			String has_disposalPrincipalName3, String has_disposalPrincipalPhone3, String has_disposalPrincipalName4,
			String has_disposalPrincipalPhone4, String has_disposalPrincipalName5, String has_disposalPrincipalPhone5,
			String has_memo) {
		super();
		this.has_disposalName = has_disposalName;
		this.has_disposalMuckCapacity = has_disposalMuckCapacity;
		this.has_areaName = has_areaName;
		this.has_disposalAddress = has_disposalAddress;
		this.has_companyName = has_companyName;
		this.has_disposalPrincipalName1 = has_disposalPrincipalName1;
		this.has_disposalPrincipalPhone1 = has_disposalPrincipalPhone1;
		this.has_disposalPrincipalName2 = has_disposalPrincipalName2;
		this.has_disposalPrincipalPhone2 = has_disposalPrincipalPhone2;
		this.has_disposalPrincipalName3 = has_disposalPrincipalName3;
		this.has_disposalPrincipalPhone3 = has_disposalPrincipalPhone3;
		this.has_disposalPrincipalName4 = has_disposalPrincipalName4;
		this.has_disposalPrincipalPhone4 = has_disposalPrincipalPhone4;
		this.has_disposalPrincipalName5 = has_disposalPrincipalName5;
		this.has_disposalPrincipalPhone5 = has_disposalPrincipalPhone5;
		this.has_memo = has_memo;
	}

	public String getHas_disposalName() {
		return has_disposalName;
	}
	public void setHas_disposalName(String has_disposalName) {
		this.has_disposalName = has_disposalName;
	}
	public String getHas_disposalMuckCapacity() {
		return has_disposalMuckCapacity;
	}
	public void setHas_disposalMuckCapacity(String has_disposalMuckCapacity) {
		this.has_disposalMuckCapacity = has_disposalMuckCapacity;
	}
	public String getHas_areaName() {
		return has_areaName;
	}
	public void setHas_areaName(String has_areaName) {
		this.has_areaName = has_areaName;
	}
	public String getHas_disposalAddress() {
		return has_disposalAddress;
	}
	public void setHas_disposalAddress(String has_disposalAddress) {
		this.has_disposalAddress = has_disposalAddress;
	}
	public String getHas_companyName() {
		return has_companyName;
	}
	public void setHas_companyName(String has_companyName) {
		this.has_companyName = has_companyName;
	}
	public String getHas_disposalPrincipalName1() {
		return has_disposalPrincipalName1;
	}
	public void setHas_disposalPrincipalName1(String has_disposalPrincipalName1) {
		this.has_disposalPrincipalName1 = has_disposalPrincipalName1;
	}
	public String getHas_disposalPrincipalPhone1() {
		return has_disposalPrincipalPhone1;
	}
	public void setHas_disposalPrincipalPhone1(String has_disposalPrincipalPhone1) {
		this.has_disposalPrincipalPhone1 = has_disposalPrincipalPhone1;
	}
	public String getHas_disposalPrincipalName2() {
		return has_disposalPrincipalName2;
	}
	public void setHas_disposalPrincipalName2(String has_disposalPrincipalName2) {
		this.has_disposalPrincipalName2 = has_disposalPrincipalName2;
	}
	public String getHas_disposalPrincipalPhone2() {
		return has_disposalPrincipalPhone2;
	}
	public void setHas_disposalPrincipalPhone2(String has_disposalPrincipalPhone2) {
		this.has_disposalPrincipalPhone2 = has_disposalPrincipalPhone2;
	}
	public String getHas_disposalPrincipalName3() {
		return has_disposalPrincipalName3;
	}
	public void setHas_disposalPrincipalName3(String has_disposalPrincipalName3) {
		this.has_disposalPrincipalName3 = has_disposalPrincipalName3;
	}
	public String getHas_disposalPrincipalPhone3() {
		return has_disposalPrincipalPhone3;
	}
	public void setHas_disposalPrincipalPhone3(String has_disposalPrincipalPhone3) {
		this.has_disposalPrincipalPhone3 = has_disposalPrincipalPhone3;
	}
	public String getHas_disposalPrincipalName4() {
		return has_disposalPrincipalName4;
	}
	public void setHas_disposalPrincipalName4(String has_disposalPrincipalName4) {
		this.has_disposalPrincipalName4 = has_disposalPrincipalName4;
	}
	public String getHas_disposalPrincipalPhone4() {
		return has_disposalPrincipalPhone4;
	}
	public void setHas_disposalPrincipalPhone4(String has_disposalPrincipalPhone4) {
		this.has_disposalPrincipalPhone4 = has_disposalPrincipalPhone4;
	}
	public String getHas_disposalPrincipalName5() {
		return has_disposalPrincipalName5;
	}
	public void setHas_disposalPrincipalName5(String has_disposalPrincipalName5) {
		this.has_disposalPrincipalName5 = has_disposalPrincipalName5;
	}
	public String getHas_disposalPrincipalPhone5() {
		return has_disposalPrincipalPhone5;
	}
	public void setHas_disposalPrincipalPhone5(String has_disposalPrincipalPhone5) {
		this.has_disposalPrincipalPhone5 = has_disposalPrincipalPhone5;
	}
	public String getHas_memo() {
		return has_memo;
	}
	public void setHas_memo(String has_memo) {
		this.has_memo = has_memo;
	}
}
