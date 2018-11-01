package com.muck.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Area;
import com.muck.domain.Channel;
import com.muck.domain.Device;
import com.muck.domain.Disposal;
import com.muck.domain.Site;
import com.muck.exception.base.BizException;
import com.muck.helper.OrganizationResult;
import com.muck.helper.XMLUtils;
import com.muck.mapper.AreaMapper;
import com.muck.mapper.ChannelMapper;
import com.muck.mapper.DeviceChannelMapper;
import com.muck.mapper.DeviceMapper;
import com.muck.mapper.DisposalMapper;
import com.muck.mapper.SiteMapper;
import com.muck.response.StatusCode;
import com.muck.service.OrganizationService;

/**
 * @Description: 组织结构处理Service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月10日 上午10:52:55
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private DeviceChannelMapper deviceChannelMapper; // 设备和通道Mapper

	@Autowired
	private AreaMapper areaMapper; // 区域Mapper

	@Autowired
	private DeviceMapper deviceMapper; // 设备Mapper

	@Autowired
	private ChannelMapper channelMapper; // 通道Mapper

	@Autowired
	private SiteMapper siteMapper; // 工地Mapper

	@Autowired
	private DisposalMapper disposalMapper; // 处置场Mapper

	/**
	 * @Description: 解析组织结构树
	 * @author: 展昭
	 * @date: 2018年5月10日 下午5:42:07
	 */
	public OrganizationResult analyzeOrganization(String xmlValue) {

		if (StringUtils.isBlank(xmlValue)) {
			throw new BizException(StatusCode.ORGANIZATION_BLANK);
		}

		// 解析组织结构
		OrganizationResult result = XMLUtils.parseXML(xmlValue);

		return result;
	}

	/**
	 * @Description: 添加区域、设备信息、通道信息
	 * 
	 *               递归解析大华组织结构的时候做了几件事： 1、首先把所有的通道传递给前台
	 *               2、将解析好的设备、通道、区域同时保存到我们系统的数据库中
	 *               3、在保存的时候，由于这边是大华的数据，所以我们这边还需要去鉴别数据的来源,
	 *               因为我们自己的系统有可能会添加自己的设备和区域,所以要区分开
	 *               4、区分开的目的就是在数据同步的时候不会覆盖掉我们自己系统的添加的数据。
	 * 
	 *               所以这个组织结构的解析是一个非常慎重的一个操作,也就是说我们一般情况下只需要同步一次(就是在用户登录到我们系统之后)，
	 *               以后我们所拿的数据都是走的是我们系统的数据。
	 *               但是这个地方还是需要给用户这么一个操作,或者是一个按钮(同步拉取大华的数据),但是这个操作的话,前台需要格外注意:
	 *               多加谨慎操作的提示。
	 * 
	 * @author: 展昭
	 * @date: 2018年5月10日 下午6:55:44
	 * @version 1.0
	 * 
	 *          public void addOrganization(OrganizationResult result) {
	 * 
	 *          if(result == null){ throw new
	 *          BizException(StatusCode.ORGANIZATION_BLANK); }
	 * 
	 *          // 第一大步、添加区域 List<Area> areas = result.getAreas(); if(areas ==
	 *          null || areas.size() <= 0){ throw new
	 *          BizException(StatusCode.ORGANIZATION_AREA_BLANK); }
	 * 
	 *          // 1、批量删除大华已经同步过的数据--->区域数据 //
	 *          areaMapper.deleteBatchDaHuaSynData(); //
	 *          2、同步更新组织结构的信息,将组织结构的区域信息添加到区域表中---> 区域数据
	 *          areaMapper.insertBatch(areas);
	 * 
	 *          // 区域添加完毕之后将这个List转换为Map,方便以后根据区域名称查询id Map
	 *          <String,String> areasMap = this.area2map(areas);
	 * 
	 *          // 第二大步、添加设备 List<Device> devices = result.getDevices();
	 *          if(devices == null || devices.size() <= 0){ throw new
	 *          BizException(StatusCode.ORGANIZATION_DEVICES_BLANK); }
	 * 
	 *          // 1、批量删除大华已经同步过的数据--->设备数据 //
	 *          deviceMapper.deleteBatchDaHuaSynData(); //
	 *          2、同步更新组织结构的信息,将组织结构的设备信息添加到设备表中---> 设备数据 for(Device device :
	 *          devices){ Area area = device.getArea(); if(area != null){ String
	 *          areaName = area.getAreaName();
	 *          if(areasMap.containsKey(areaName)){
	 *          area.setAreaId(areasMap.get(areaName)); } } }
	 *          deviceMapper.insertBatch(devices);
	 * 
	 *          // 第三大步、设置设备和区域的关系(不需要了,因为已经在前面的第二步已经在Device表中设置好了关联关系) //
	 *          1、首先将区域和设备的关联中的大华的数据批量删除(逻辑删除)[t_area_device表]--->区域和设备关联数据 //
	 *          areaDeviceMapper.deleteBatchDaHuaSynData();
	 * 
	 *          // 2：插入区域和设备的关系 // areaDeviceMapper.insertBatch(devices);
	 * 
	 *          // 第四大步、添加通道 List<Channel> channels = result.getChannels();
	 *          if(devices == null || devices.size() <= 0){ throw new
	 *          BizException(StatusCode.ORGANIZATION_CHANNEL_BLANK); }
	 * 
	 *          // 1、批量删除大华已经同步过的数据--->通道数据 //
	 *          channelMapper.deleteBatchDaHuaSynData(); //
	 *          2、同步更新组织结构的信息,将组织结构的通道信息添加到通道表中---> 通道数据
	 *          channelMapper.insertBatch(channels);
	 * 
	 *          // 第五大步、添加设备和通道的关联信息
	 * 
	 *          // 1、首先将设备和区域的关联中的大华的数据批量删除(逻辑删除)[t_device_channel表]--->设备和通道关系表
	 *          // deviceChannelMapper.deleteBatchDaHuaSynData(); //
	 *          2：插入设备和通道的关系 deviceChannelMapper.insertBatch(channels);
	 * 
	 *          // 第六大步、添加工地信息 //List<Site> sites = result.getSites();
	 *          //if(sites == null || sites.size() <= 0){ //throw new
	 *          BizException(StatusCode.ORGANIZATION_DEVICES_BLANK); //} //
	 *          将组织结构的设备信息添加到工地表中---> 工地数据 //siteMapper.insertBatch(sites);
	 * 
	 *          // 第七步、将区域的树形结构维护 areaMapper.setAreaRelation();
	 * 
	 *          // 第八步、将设备的工地信息维护到设备表中 deviceMapper.setDeviceAndSiteRelation();
	 *          }
	 */

	public void addOrganization(OrganizationResult result) {

		if (result == null) {
			throw new BizException(StatusCode.ORGANIZATION_BLANK);
		}

		// 第一大步、添加区域
		List<Area> areas = result.getAreas();
		if (areas == null || areas.size() <= 0) {
			throw new BizException(StatusCode.ORGANIZATION_AREA_BLANK);
		}

		// 批量添加---> 区域数据
		areaMapper.insertBatch(areas);

		// 第二大步、添加设备
		List<Device> devices = result.getDevices();
		if (devices == null || devices.size() <= 0) {
			throw new BizException(StatusCode.ORGANIZATION_DEVICES_BLANK);
		}

		// 批量添加---> 设备数据
		deviceMapper.insertBatch(devices);

		// 第三大步、添加通道
		List<Channel> channels = result.getChannels();
		if (devices == null || devices.size() <= 0) {
			throw new BizException(StatusCode.ORGANIZATION_CHANNEL_BLANK);
		}

		// 批量添加---> 通道信息(通道信息的添加,工地信息的添加,处置场信息的添加)
		channelMapper.insertBatch(channels);

		// 第四大步、添加设备和通道的关联信息

		// 批量添加---> 设备和通道的关联信息
		deviceChannelMapper.insertBatch(channels);

		// 工地
		List<Site> sites = new ArrayList<Site>();
		// 处置场
		List<Disposal> disposals = new ArrayList<Disposal>();

		// 第五大步、添加工地信息
		this.channel2siteAndDisposal(channels, sites, disposals);
		if (sites.size() > 0) {
			siteMapper.insertBatch(sites);
		}

		// 第六大步、添加处置场信息
		if (disposals.size() > 0) {
			disposalMapper.insertBatch(disposals);
		}

		// ----------------- 数据维护 -----------------

		// 1、将区域的树形结构维护(主要是维护父节点和子节点的关系)
		areaMapper.setAreaRelation();

		// 2、将工地中的区域维护(主要是维护区域的id)
		siteMapper.setSiteAndAreaRelation();

		// 3、将设备的工地信息维护到设备表中(同时将区域的id也一并维护[这里的区域id的取值使用的是工地表中的区域])
		deviceMapper.setDeviceAndSiteRelation();

		// 4、将处置场中的区域维护(主要是维护区域的id)
		disposalMapper.setDisposalAndAreaRelation();

		// 5、将设备的处置场信息维护到设备表中(同时将区域的id也一并维护[这里的区域id的取值使用的是处置场表中的区域])
		deviceMapper.setDeviceAndDisposalRelation();
	}

	private void channel2siteAndDisposal(List<Channel> channels, List<Site> sites, List<Disposal> disposals) {

		// 工地
		Site site = null;

		// 处置场
		Disposal disposal = null;

		for (Channel channel : channels) {
			String channelName = channel.getChannelName();
			if (channelName != null && !channelName.contains("处置场")) {
				site = new Site();
				// 设置工地信息
				site.setSiteId(channel.getChannelCode()); // 通道号
				site.setSiteName(channelName);// 通道名称<==>工地名称
				site.setCreatedTime(new Date()); // 创建时间
				site.setArea(channel.getDevice().getArea());
				site.setUpdatedTime(site.getCreatedTime()); // 修改时间
				site.setDeleted(true); // 默认不删除
				site.setSynDaHua(true); // 表示数据是从大华那边拿到的
				site.setArea(channel.getDevice().getArea());
				sites.add(site);
			} else {
				// 表示是处置场
				disposal = new Disposal();
				disposal.setDisposalId(channel.getChannelCode()); // 通道号
				disposal.setDisposalName(channelName); // 通道名称
				disposal.setCreatedTime(new Date()); // 创建时间
				disposal.setAreaCode(channel.getDevice().getArea().getAreaCode());
				disposal.setUpdatedTime(disposal.getCreatedTime()); // 修改时间
				disposal.setDeleted(true); // 默认不删除
				disposal.setSynDaHua(true); // 表示数据是从大华那边拿到的
				Area a = channel.getDevice().getArea();
				if (a != null) {
					disposal.setAreaId(a.getId());
					disposal.setAreaName(a.getAreaName());
					disposal.setAreaCode(a.getAreaCode());
				}
				disposals.add(disposal);
			}
		}
	}
}
