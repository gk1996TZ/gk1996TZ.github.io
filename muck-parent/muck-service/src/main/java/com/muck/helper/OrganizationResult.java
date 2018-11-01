package com.muck.helper;

import java.util.List;

import com.muck.domain.Area;
import com.muck.domain.Channel;
import com.muck.domain.Device;
import com.muck.domain.Disposal;
import com.muck.domain.Site;

/**
* @Description: 解析组织结构树的结果
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月10日 下午6:10:40
 */
public class OrganizationResult {

	// 所有的区域,包括区域下面的设备,以及设备下面的通道
	private List<Area> areas;
	
	// 所有的通道的编码集合
	private List<String> channelCodes;
	
	// 所有的通道集合
	private List<Channel> channels;
	
	// 所有的设备集合
	private List<Device> devices;
	
	// 所有的工地集合
	private List<Site> sites;
	
	// 所有处置场集合
	private List<Disposal> disposals;
	
	public OrganizationResult() {

	}

	public OrganizationResult(List<Area> areas, List<String> channelCodes,
				List<Channel> channels,List<Device> devices,List<Site> sites,
				List<Disposal> disposals) {
		this.areas = areas;
		this.channelCodes = channelCodes;
		this.devices = devices;
		this.channels = channels;
		this.sites = sites;
		this.disposals = disposals;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<String> getChannelCodes() {
		return channelCodes;
	}

	public void setChannelCodes(List<String> channelCodes) {
		this.channelCodes = channelCodes;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public List<Disposal> getDisposals() {
		return disposals;
	}

	public void setDisposals(List<Disposal> disposals) {
		this.disposals = disposals;
	}
}
