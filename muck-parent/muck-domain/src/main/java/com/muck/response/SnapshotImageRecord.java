package com.muck.response;

import java.util.ArrayList;
import java.util.List;

import com.muck.domain.Area;

/**
 * @Description: 抓拍图片记录返回树
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年6月1日 上午10:36:18
 */
public class SnapshotImageRecord {

	/**工地区域列表*/
	List<Area> sites = new ArrayList<Area>();
	/**处置场区域列表*/
	List<Area> disposals = new ArrayList<Area>();
	/**停车场区域列表*/
	List<Area> carParks = new ArrayList<Area>();
	public List<Area> getSites() {
		return sites;
	}
	public void setSites(List<Area> sites) {
		this.sites = sites;
	}
	public List<Area> getDisposals() {
		return disposals;
	}
	public void setDisposals(List<Area> disposals) {
		this.disposals = disposals;
	}
	public List<Area> getCarParks() {
		return carParks;
	}
	public void setCarParks(List<Area> carParks) {
		this.carParks = carParks;
	}
}
