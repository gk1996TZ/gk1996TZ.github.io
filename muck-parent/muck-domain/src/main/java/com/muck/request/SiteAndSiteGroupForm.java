package com.muck.request;

import com.muck.response.StatusCode;

/**
 * 	Site和SiteGroup的Form
*/
public class SiteAndSiteGroupForm extends BaseForm{

	private String siteId;
	
	private String siteGroupId;
	
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

	@Override
	public StatusCode validate() {
		return null;
	}
}
