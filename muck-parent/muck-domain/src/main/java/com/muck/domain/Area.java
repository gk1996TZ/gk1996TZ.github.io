package com.muck.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.muck.annotation.Table;

/**
* @Description: 区域实体
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月11日 下午1:34:00
*/
@Table(name="t_area")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Area extends BaseEntity{
	
	// 区域id
	private String areaId;
	
	// 区域编号
	private String areaCode;
	
	// 区域经度
	private BigDecimal areaLongitude;
	
	// 区域纬度
	private BigDecimal areaLatitude;

	// 区域名称
	private String areaName;
	
	// 下级区域
	private List<Area> childAreas = new ArrayList<Area>();
	
	// 上级区域
	private Area parent;
	
	// 区域下面的设备
	private List<Device> devices = new ArrayList<Device>();
	
	// 上级区域的编码
	private String parentCode;

	// 区域备注信息
	private String memo;
	
	// 区域下面的工地
	private List<Site> sites = new ArrayList<Site>();
	// 每个区域下面有多少个工地
	private int siteSize;
	
	// 区域下面的工地组
	private List<SiteGroup> siteGroups = new ArrayList<SiteGroup>();
	
	//区域下面的所有企业
	private List<Company> companys = new ArrayList<Company>();
	
	//区域下面所有的处置场
	private List<Disposal> disposals = new ArrayList<Disposal>();
	
	//区域下面所后的停车场
	private List<CarPark> carParks = new ArrayList<CarPark>();
	
	public int getSiteSize() {
		this.siteSize = this.sites.size();
		if(!this.childAreas.isEmpty()){
			for(Area area : this.childAreas){
				this.siteSize = this.siteSize + area.sites.size();
			}
		}
		return siteSize;
	}
	
	/**
	public int getTotal() {
		if(!this.childAreas.isEmpty()){
			for(Area area : this.childAreas){
				this.total = this.total + area.sites.size();
			}
		}
		return total;
	}*/
	
	public String getAreaName() {
		return areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<Area> getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(List<Area> childAreas) {
		this.childAreas = childAreas;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public BigDecimal getAreaLongitude() {
		return areaLongitude;
	}

	public void setAreaLongitude(BigDecimal areaLongitude) {
		this.areaLongitude = areaLongitude;
	}

	public BigDecimal getAreaLatitude() {
		return areaLatitude;
	}

	public void setAreaLatitude(BigDecimal areaLatitude) {
		this.areaLatitude = areaLatitude;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public List<SiteGroup> getSiteGroups() {
		return siteGroups;
	}

	public void setSiteGroups(List<SiteGroup> siteGroups) {
		this.siteGroups = siteGroups;
	}

	public List<Company> getCompanys() {
		return companys;
	}

	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
	
	public List<Disposal> getDisposals() {
		return disposals;
	}

	public void setDisposals(List<Disposal> disposals) {
		this.disposals = disposals;
	}

	public List<CarPark> getCarParks() {
		return carParks;
	}

	public void setCarParks(List<CarPark> carParks) {
		this.carParks = carParks;
	}

	public void setSiteSize(int siteSize) {
		this.siteSize = siteSize;
	}
	
	// 判断标志(是工地还是处置场 1:工地 2:处置场)
	private Integer validateFlag;

	public Integer getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(Integer validateFlag) {
		this.validateFlag = validateFlag;
	}
}
