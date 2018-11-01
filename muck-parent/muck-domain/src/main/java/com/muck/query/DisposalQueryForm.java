package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description: 处置场请求参数封装类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月28日 下午12:47:53
 */
public class DisposalQueryForm extends BaseForm{

	/**处置场id*/
	private String id;
	/**处置场名称*/
	private String disposalName;
	/**处置场渣土容量*/
	private Integer disposalMuckCapacity;
	/**处置场所属区域编号*/
	private String areaCode;
	/**处置场所属区域名称*/
	private String areaName;
	/**处置场所属企业id*/
	private Long companyId;
	/**处置场所属企业名称*/
	private String companyName;
	/**备注信息*/
	private String memo;
	/**处置场负责人1*/
	private String disposalPrincipalName1;
	/**处置场负责人1的联系方式*/
	private String disposalPrincipalPhone1;
	/**处置场负责人2*/
	private String disposalPrincipalName2;
	/**处置场负责人2的联系方式*/
	private String disposalPrincipalPhone2;
	/**处置场负责人3*/
	private String disposalPrincipalName3;
	/**处置场负责人3的联系方式*/
	private String disposalPrincipalPhone3;
	/**处置场负责人4*/
	private String disposalPrincipalName4;
	/**处置场负责人4的联系方式*/
	private String disposalPrincipalPhone4;
	/**处置场负责人5*/
	private String disposalPrincipalName5;
	/**处置场负责人5的联系方式*/
	private String disposalPrincipalPhone5;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisposalName() {
		return disposalName;
	}
	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}
	public Integer getDisposalMuckCapacity() {
		return disposalMuckCapacity;
	}
	public void setDisposalMuckCapacity(Integer disposalMuckCapacity) {
		this.disposalMuckCapacity = disposalMuckCapacity;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDisposalPrincipalName1() {
		return disposalPrincipalName1;
	}
	public void setDisposalPrincipalName1(String disposalPrincipalName1) {
		this.disposalPrincipalName1 = disposalPrincipalName1;
	}
	public String getDisposalPrincipalPhone1() {
		return disposalPrincipalPhone1;
	}
	public void setDisposalPrincipalPhone1(String disposalPrincipalPhone1) {
		this.disposalPrincipalPhone1 = disposalPrincipalPhone1;
	}
	public String getDisposalPrincipalName2() {
		return disposalPrincipalName2;
	}
	public void setDisposalPrincipalName2(String disposalPrincipalName2) {
		this.disposalPrincipalName2 = disposalPrincipalName2;
	}
	public String getDisposalPrincipalPhone2() {
		return disposalPrincipalPhone2;
	}
	public void setDisposalPrincipalPhone2(String disposalPrincipalPhone2) {
		this.disposalPrincipalPhone2 = disposalPrincipalPhone2;
	}
	public String getDisposalPrincipalName3() {
		return disposalPrincipalName3;
	}
	public void setDisposalPrincipalName3(String disposalPrincipalName3) {
		this.disposalPrincipalName3 = disposalPrincipalName3;
	}
	public String getDisposalPrincipalPhone3() {
		return disposalPrincipalPhone3;
	}
	public void setDisposalPrincipalPhone3(String disposalPrincipalPhone3) {
		this.disposalPrincipalPhone3 = disposalPrincipalPhone3;
	}
	public String getDisposalPrincipalName4() {
		return disposalPrincipalName4;
	}
	public void setDisposalPrincipalName4(String disposalPrincipalName4) {
		this.disposalPrincipalName4 = disposalPrincipalName4;
	}
	public String getDisposalPrincipalPhone4() {
		return disposalPrincipalPhone4;
	}
	public void setDisposalPrincipalPhone4(String disposalPrincipalPhone4) {
		this.disposalPrincipalPhone4 = disposalPrincipalPhone4;
	}
	public String getDisposalPrincipalName5() {
		return disposalPrincipalName5;
	}
	public void setDisposalPrincipalName5(String disposalPrincipalName5) {
		this.disposalPrincipalName5 = disposalPrincipalName5;
	}
	public String getDisposalPrincipalPhone5() {
		return disposalPrincipalPhone5;
	}
	public void setDisposalPrincipalPhone5(String disposalPrincipalPhone5) {
		this.disposalPrincipalPhone5 = disposalPrincipalPhone5;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
}
