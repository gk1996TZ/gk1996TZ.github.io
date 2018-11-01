package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
* @Description: 工地视频form
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月14日 下午2:32:52
 */
public class SiteVedioQueryForm extends BaseForm {

	// 区域编码
	private String areaCode;
	
	// 企业id
	private String[] companyIds;
	
	// 工地id
	private String[] siteIds;
	
	// 工地名称
	private String siteName;
	
	//负责人姓名
	private String managerName;
	
	//负责人联系方式
	private String managerPhone;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String[] getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(String[] companyIds) {
		this.companyIds = companyIds;
	}
	public String[] getSiteIds() {
		return siteIds;
	}
	public void setSiteIds(String[] siteIds) {
		this.siteIds = siteIds;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
}
