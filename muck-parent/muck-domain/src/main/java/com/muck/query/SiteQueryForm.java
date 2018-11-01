package com.muck.query;

import com.muck.request.BaseForm;
import com.muck.response.StatusCode;

/**
 * @Description:工地Form
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月2日 下午8:47:50
 */
public class SiteQueryForm extends BaseForm{

	/**工地id*/
	private String siteId;
	
	/**工地组id*/
	private String siteGroupId;
	
	/**地域编号*/
	private String areaCode;
	
	/**地域id*/
	private String areaId;
	
	/**企业id*/
	private String companyId;
	
	/**工地名称*/
	private String siteName;
	
	/**负责人姓名或联系方式*/
	private String manager;
	
	/**负责人姓名*/
	private String managerName;
	
	/**负责人联系电话*/
	private String managerPhone;
	
	/**	企业id数组	**/
	private String[] companyIds;
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteGroupId() {
		return siteGroupId;
	}
	public void setSiteGroupId(String siteGroupId) {
		this.siteGroupId = siteGroupId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
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
	public String[] getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(String[] companyIds) {
		this.companyIds = companyIds;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
	

}
