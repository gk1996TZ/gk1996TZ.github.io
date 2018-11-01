package com.muck.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 	工地组
*/
public class SiteGroup extends BaseEntity{

	// 工地组编码
	private String siteGroupCode;

	// 工地组名称
    private String siteGroupName;

    // 工地组描述说明
    private String memo;

    // 工地组所对应的区域
    private Area area;
    
    // 每个工地组下面的工地
    private List<Site> sites = new ArrayList<Site>();
    // 每个工地组下面有工地数量
    private int siteSize;
    
	public int getSiteSize() {
		
		this.siteSize = sites.size();
	
		return siteSize;
	}

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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
}