package com.muck.helper;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.muck.domain.Area;
import com.muck.domain.AreaType;
import com.muck.domain.Channel;
import com.muck.domain.Device;
import com.muck.domain.DeviceType;
import com.muck.domain.Disposal;
import com.muck.domain.Site;

/**
 * @Description: 对指定的xml格式的字符串进行解析
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月10日 上午9:58:42
 */
public final class XMLUtils {

	// SAX工厂
	private static SAXParserFactory spf = SAXParserFactory.newInstance();;

	// SAX解析器
	private static SAXParser parser = null;

	// 创建组织处理器
	private static OrganizationHandler handler = null;

	private XMLUtils() {

	}

	/**
	 * @Description: 解析xml格式的字符串
	 * @author: 展昭
	 * @date: 2018年5月10日 上午10:09:06
	 */
	public static OrganizationResult parseXML(String xmlValue) {

		if (StringUtils.isBlank(xmlValue)) {
			return null;
		}

		StringReader reader = null;
		InputSource source = null;
		try {
			// 第一步、创建xml解析器
			parser = spf.newSAXParser();

			// 第二步、创建处理器
			handler = new OrganizationHandler();

			// 第三步、解析
			reader = new StringReader(xmlValue);
			source = new InputSource(reader);

			parser.parse(source, handler);

			List<Area> areas = handler.getAreas();
			List<String> channelCodes = handler.getChannelCodes();
			List<Device> devices = handler.getDevices();
			List<Channel> channels = handler.getChannels();
			List<Site> sites = handler.getSites();
			List<Disposal> disposals = handler.getDisposals();

			return new OrganizationResult(areas, channelCodes, channels, devices, sites, disposals);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}
}

class OrganizationHandler extends DefaultHandler {

	// 要忽略的设备号
	private List<String> ingoreDeviceCodes = Arrays.asList("1000002", "1000003", "1000019", "1000020");

	// 要忽略的通道号
	private List<String> ingoreChannelCodes = Arrays.asList("1000002$7$0$0", "1000003$7$0$0", "1000020$3$0$0",
			"1000020$3$0$1", "1000020$3$0$2", "1000020$3$0$3", "1000020$3$0$4", "1000020$3$0$5", "1000020$3$0$6",
			"1000020$3$0$7");

	private String currentTagName; // 这个属性用来定义当前解析到的开始标签
	private String parentCode; // 父标签的标志,判断当前遍历的是不是父标签，默认为null

	private List<Area> areas = new ArrayList<Area>();
	private Area area = null;

	private boolean isAddDevice = false; // 标志字段,用来判断是否要添加设备
	private List<Device> devices = new ArrayList<Device>(); // 设备
	private Device device;
	private Map<String, Area> deviceAreaMap = new HashMap<String, Area>();

	private boolean isAddChannelForFront = true; // 是否应该添加通道了,这个设置主要是为了给前台使用,前端需要的排序好的按照区域，设备，通道排序好的
	private boolean isAddChannelForBackend = false; // 是否应该添加通道了，这个是为后台计算的。
	private Channel channel = null; // 通道
	private List<String> channelCodes = new ArrayList<String>(); // 通道编码集合
	private List<Channel> channels = new ArrayList<Channel>(); // 通道集合
	private List<Site> sites = new ArrayList<Site>(); // 工地集合
	private Map<String, Device> deviceChannelMap = new HashMap<String, Device>();

	private List<Disposal> disposals = new ArrayList<Disposal>();

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		// 当前标签
		currentTagName = qName;

		// 如果当前解析的标签是部门(区域)
		if ("Department".equals(currentTagName)) {

			// 创建一个区域对象
			area = new Area();
			area.setCreatedTime(new Date()); // 创建时间
			area.setUpdatedTime(area.getCreatedTime()); // 修改时间，默认就是创建时间
			area.setSynDaHua(true); // 表示是大华同步的数据

			// 判断当前的标签是否有属性,如果有属性,则拿出属性的值
			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					String attrName = attributes.getQName(i); // 获取属性名
					String attrValue = attributes.getValue(attrName); // 获取属性值

					if ("coding".equals(attrName)) {
						// 区域编码
						area.setAreaCode(attrValue);
						if (parentCode == null) {
							parentCode = attrValue;
						} else {
							if (attrValue.startsWith(parentCode)) {
								area.setParentCode(parentCode);
							}
						}
						if ("001005".equals(attrValue)) {
							area.setValidateFlag(AreaType.DISPOSAL.getType());
						} else {
							area.setValidateFlag(AreaType.SITE.getType());
						}
					}
					if ("name".equals(attrName)) {
						// 区域名称
						area.setAreaName(attrValue);
					}
				}
			}
			areas.add(area);
		}

		// 如果当前解析的是设备
		if ("Device".equals(currentTagName)) {

			// 创建设备对象
			device = new Device();

			// 判断当前的标签是否有属性,如果有属性,则拿出属性的值
			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					String attrName = attributes.getQName(i); // 获取属性名
					String attrValue = attributes.getValue(attrName); // 获取属性值

					if ("id".equals(attrName)) {

						// 设备编码
						device.setDeviceCode(attrValue);
						if (area != null) {
							deviceAreaMap.put(attrValue, area);
						}
					}
					if ("name".equals(attrName)) {
						// 设备名称
						device.setDeviceName(attrValue);
					}
					if ("type".equals(attrName)) {
						// 设备类型(大华提供)
						device.setDeviceTypeDaHua(attrValue);
					}
					if ("manufacturer".equals(attrName)) {
						// 设备厂商
						device.setDeviceFactoryName(attrValue);
					}
					if ("user".equals(attrName)) {
						// 设备登录用户名
						device.setDeviceLoginName(attrValue);
					}
					if ("password".equals(attrName)) {
						// 设备登录密码
						device.setDeviceLoginPassword(attrValue);
					}
					if ("status".equals(attrName)) {
						// 设备状态(大华提供)
						device.setDeviceStatus(attrValue);
					}
					if ("deviceIp".equals(attrName)) {
						// 设备真实ip
						device.setDeviceIp(attrValue);
					}
					if ("devicePort".equals(attrName)) {
						// 设备端口
						device.setDevicePort(attrValue);
					}
				}
			}
		}

		// 如果当前解析的是通道
		if ("Channel".equals(currentTagName)) {

			// 创建一个管道对象
			channel = new Channel();

			// 判断当前的标签是否有属性,如果有属性,则拿出属性的值
			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					String attrName = attributes.getQName(i); // 获取属性名
					String attrValue = attributes.getValue(attrName); // 获取属性值

					if ("id".equals(attrName)) {
						// 通道编码
						channel.setChannelCode(attrValue);
						if (device != null) {
							deviceChannelMap.put(attrValue, device);
						}
					}
					if ("name".equals(attrName)) {
						// 通道名称
						channel.setChannelName(attrValue);
					}
					if ("status".equals(attrName)) {
						// 通道状态
						channel.setChannelStatus(attrValue);
					}
					if ("channelType".equals(attrName)) {
						// 通道类型
						channel.setChannelType(attrValue);
					}
					if ("cameraType".equals(attrName)) {
						// 通道摄像头类型
						channel.setChannelCameraType(attrValue);
					}
					if ("latitude".equals(attrName)) {
						// 通道纬度
						try {
							channel.setChannelLatitude(Double.parseDouble(attrValue));
						} catch (Exception e) {

						}
					}
					if ("longitude".equals(attrName)) {
						// 通道经度
						try {
							channel.setChannelLongitude(Double.parseDouble(attrValue));
						} catch (Exception e) {

						}
					}
					if ("alarmType".equals(attrName)) {
						// 通道报警类型
						channel.setChannelAlarmType(attrValue);
					}
					if ("alarmLevel".equals(attrName)) {
						// 通道报警级别
						channel.setChannelAlarmLevel(attrValue);
					}
				}
			}
			// 设置设备和通道的关联关系
			// device.getChannels().add(channel);
			// channel.setDevice(device);
		}

		if ("Devices".equals(currentTagName)) {
			area = null;
			device = null;
			channel = null;
			isAddDevice = true;
			isAddChannelForFront = false; // 之所以默认是指是为true,则主要是因为给前台返回的时候是直接按照区域，设备，通道给排序好的
			isAddChannelForBackend = true;
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		currentTagName = null;

		if ("Device".equals(qName)) {
			if (isAddDevice) {

				String deviceCode = device.getDeviceCode();
				if (!ingoreDeviceCodes.contains(deviceCode)) {
					Area a = deviceAreaMap.get(device.getDeviceCode());
					a.getDevices().remove(new Device(device.getDeviceCode()));
					// 设置管理关系
					a.getDevices().add(device);
					device.setArea(a);

					// 设置所有的设备信息
					// 设置基础信息
					device.setCreatedTime(new Date()); // 创建时间
					device.setUpdatedTime(device.getCreatedTime()); // 修改时间
					device.setSynDaHua(true); // 表示数据是从大华那边拿到的

					// 区域的类型包括,工地,处置场
					Integer areaType = a.getValidateFlag();
					if (areaType != null) {
						switch (areaType) {
						case 1:
							// 表示工地
							device.setDeviceType(DeviceType.SITE.getType());
							break;
						case 2:
							// 表示车辆
							device.setDeviceType(DeviceType.CAR.getType());
							break;
						case 3:
							// 表示处置场
							device.setDeviceType(DeviceType.DISPOSAL.getType());
							break;
						}
					}
					devices.add(device);
				}
			}
		}

		if ("Channel".equals(qName)) {

			if (isAddChannelForBackend) {

				String channelCode = channel.getChannelCode();

				if (!ingoreChannelCodes.contains(channelCode)) {
					Device d = deviceChannelMap.get(channel.getChannelCode());

					// 设置管理关系
					d.getChannels().remove(new Channel(channel.getChannelCode()));
					d.getChannels().add(channel);
					channel.setDevice(d);

					// 设置基础信息
					channel.setCreatedTime(new Date()); // 创建时间
					channel.setUpdatedTime(channel.getCreatedTime()); // 修改时间
					channel.setSynDaHua(true); // 表示数据是从大华那边拿到的

					channels.add(channel);
				}
			}

			if (isAddChannelForFront) {
				channelCodes.add(channel.getChannelCode());
			}
		}
	}

	// 区域集合
	public List<Area> getAreas() {
		return areas;
	}

	// 通道编码集合
	public List<String> getChannelCodes() {
		return channelCodes;
	}

	// 所有通道集合
	public List<Channel> getChannels() {
		return channels;
	}

	// 设备集合
	public List<Device> getDevices() {
		return devices;
	}

	// 获取工地集合
	public List<Site> getSites() {
		return sites;
	}

	public List<Disposal> getDisposals() {
		return disposals;
	}
}
