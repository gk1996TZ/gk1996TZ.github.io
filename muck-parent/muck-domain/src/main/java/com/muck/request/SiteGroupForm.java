package com.muck.request;

import org.apache.commons.lang3.StringUtils;

import com.muck.response.StatusCode;

/**
 * 	工地组
*/
public class SiteGroupForm extends BaseForm{

	// 工地编码id
	private String siteGroupId;
	
	// 工地组编码
	private String siteGroupCode;

	// 工地组名称
    private String siteGroupName;

    // 工地组描述说明
    private String memo;

    // 工地组所对应的区域id
    private String siteGroupAreaId;

	public String getSiteGroupCode() {
		return siteGroupCode;
	}

	public void setSiteGroupCode(String siteGroupCode) {
		this.siteGroupCode = siteGroupCode;
	}

	public String getSiteGroupName() {
		return siteGroupName;
	}

	public void setSiteGroupName(String siteGroupName) {
		this.siteGroupName = siteGroupName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSiteGroupAreaId() {
		return siteGroupAreaId;
	}

	public void setSiteGroupAreaId(String siteGroupAreaId) {
		this.siteGroupAreaId = siteGroupAreaId;
	}
	
	public String getSiteGroupId() {
		return siteGroupId;
	}

	public void setSiteGroupId(String siteGroupId) {
		this.siteGroupId = siteGroupId;
	}

	@Override
	public StatusCode validate() {
		
		StatusCode statusCode = null;
		if(StringUtils.isBlank(siteGroupName)){
			statusCode = StatusCode.SITE_GROUP_NAME_BLANK;
		}
		return statusCode;
	}
}