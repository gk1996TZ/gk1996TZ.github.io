package com.muck.request;

/**
 * @Description: 工地负责人
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月15日 下午2:50:56
 */
public class SiteManager {

	// 工地项目负责人
	private String siteManager;

	// 工地项目负责人联系方式
	private String siteManagerPhone;

	public String getSiteManager() {
		return siteManager;
	}

	public void setSiteManager(String siteManager) {
		this.siteManager = siteManager;
	}

	public String getSiteManagerPhone() {
		return siteManagerPhone;
	}

	public void setSiteManagerPhone(String siteManagerPhone) {
		this.siteManagerPhone = siteManagerPhone;
	}
}
