package com.muck.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.annotation.ExcelTemplate;
import com.muck.domain.Area;
import com.muck.domain.Company;
import com.muck.domain.Device;
import com.muck.domain.DeviceType;
import com.muck.domain.Disposal;
import com.muck.domain.Site;
import com.muck.excelquerytemplate.BaseExcelQueryTemplate;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ChannelMapper;
import com.muck.mapper.DeviceChannelMapper;
import com.muck.mapper.DeviceMapper;
import com.muck.query.DeviceQueryForm;
import com.muck.response.SimpleDeviceAndDisposalInfoResponse;
import com.muck.response.SimpleDeviceAndSiteInfoResponse;
import com.muck.response.SimpleDeviceListResponse;
import com.muck.response.SimpleDeviceTypeInfoResponse;
import com.muck.response.StatusCode;
import com.muck.service.DeviceService;
import com.muck.utils.ArrayUtils;

/**
 * @Description: 设备Service实现
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月16日 上午11:39:26
 */
@Service
public class DeviceServiceImpl extends BaseServiceImpl<Device> implements DeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private DeviceChannelMapper deviceChannelMapper;
	@Autowired
	private ChannelMapper channelMapper;

	@Override
	public Device queryByDeviceCode(String deviceCode) {
		return deviceMapper.queryByDeviceCode(deviceCode);
	}

	@Override
	public String queryDeviceCodeBySiteId(String siteId) {
		List<String> deviceCodes = deviceMapper.queryDeviceCodeBySiteId(siteId);
		if (deviceCodes != null) {
			return ArrayUtils.array2str(deviceCodes.toArray());
		}
		return null;
	}

	@Override
	public String queryDeviceCodeBySiteIds(String siteIds) {
		List<String> deviceCodes = deviceMapper.queryDeviceCodeBySiteIds(siteIds);
		if (deviceCodes != null) {
			return ArrayUtils.array2str(deviceCodes.toArray());
		}
		return null;
	}

	@Override
	public String queryDeviceCodeByDisposalId(String disposalId) {
		List<String> deviceCodes = deviceMapper.queryDeviceCodeByDisposalId(disposalId);
		if (deviceCodes != null) {
			return ArrayUtils.array2str(deviceCodes.toArray());
		}
		return null;
	}

	@Override
	public String queryDeviceCodeByDisposalIds(String disposalIds) {
		List<String> deviceCodes = deviceMapper.queryDeviceCodeByDisposalIds(disposalIds);
		if (deviceCodes != null) {
			return ArrayUtils.array2str(deviceCodes.toArray());
		}
		return null;
	}

	/***
	 * 根据区域id和企业id数组查询设备
	 */
	public List<Device> queryDevicesByCondition(String areaId, String[] companyIds) {
		return deviceMapper.selectByCondition(areaId, companyIds);
	}

	@Override
	public List<Device> queryData(String wherSQL, List<Object> whereParams) {

		// 第一步、获取where条件
		String where = StringUtils.isBlank(wherSQL) ? " where 1 = 1 " : " where " + wherSQL;
		// 第二步、拼接sql语句
		String selectSQL = " SELECT "
				+ "device.id, device.device_name,device.device_site_id,device.device_site_name,device.device_disposal_id,device.device_disposal_name,device.device_channel_nums,device.device_ip,device.device_register_id,device.device_login_name,device.device_code, device.device_login_password,device.device_installed_user,device.device_installed_user_phone,device.device_port,device.device_installed_time, device.device_type, device.device_made_time, device.device_area_id,"
				+ "device.device_area_name, device.device_company_id, device.device_company_name, device.device_factory_name, device.device_car_id,device.device_car_code,"
				+ "device.device_manager_id, device.device_manager_name, device.device_manager_phone, device.is_running, device.device_longitude,"
				+ "device.device_latitude,device.created_time,device.updated_time,device.memo,"
				+ "channel.channel_name,channel.channel_code,channel.channel_status,channel.channel_type,channel.channel_camera_type,channel.channel_latitude,channel.channel_longitude,"
				+ "channel.memo,channel.channel_alarm_type,channel.channel_alarm_level,channel.is_syn_dahua" + " from "
				+ " t_device device,t_device_channel device_channel,t_channel channel " + where
				+ " and device.device_code = device_channel.device_code"
				+ " and device_channel.channel_code = channel.channel_code";
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}

	@Override
	public List<Device> queryData() {
		return queryData(null, null);
	}

	@Override
	public String queryDeviceCodeByChannelCode(String channelCode) {
		return deviceMapper.queryDeviceCodeByChannelCode(channelCode);
	}

	// --------------------------------------
	protected BaseMapper<Device> getMapper() {
		return deviceMapper;
	}

	@Override
	protected String getFields() {
		return "id, device_code, device_name, device_type,"
				+ "device_area_id, device_area_name, device_site_id, device_site_name, "
				+ "device_disposal_id, device_disposal_name, device_register_id, device_channel_nums, "
				+ "device_server_type, device_real_type, device_address, device_company_id, device_company_name, "
				+ "device_factory_name, device_car_id, device_car_code, device_ip, device_port, device_login_name,"
				+ "device_login_password, device_type_dahua, " + "device_status, device_longitude, device_latitude, "
				+ "deleted, is_syn_dahua, memo, operator_id, operator_name, created_time, updated_time,device_installed_user,device_installed_user_phone";
	}

	// ----------------- 前台功能------------

	/**
	 * 根据工地id、处置场id、设备名称查询设备列表(不带分页)
	 */
	public List<SimpleDeviceListResponse> queryDevicesByCondition(DeviceQueryForm queryForm) {
		return deviceMapper.selectDevicesByCondition(queryForm);
	}

	/***
	 * 根据设备id查询设备详情 包括两部分： 1、工地信息 2、设备信息
	 */
	@Override
	public SimpleDeviceAndSiteInfoResponse queryDeviceAndSiteInfoById(String deviceId) {
		return deviceMapper.selectDeviceAndSiteInfoById(deviceId);
	}

	/***
	 * 根据设备id查询设备详情 包括两部分： 1、处置场信息 2、设备信息
	 */
	@Override
	public SimpleDeviceAndDisposalInfoResponse queryDeviceAndDisposalInfoById(String deviceId) {
		return deviceMapper.selectDeviceAndDisposalInfoById(deviceId);
	}

	/**
	 * 查询所有的设备号
	 */
	@Override
	public List<String> queryDeviceCodes() {
		return deviceMapper.selectDeviceCodes();
	}

	/***
	 * 根据设备的注册id查询设备的类型信息(是处置场，还是工地)
	 */
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByRegisterId(String registerId) {
		return deviceMapper.selectDeviceTypeByRegisterId(registerId);
	}

	/**
	 * 导出设备生成表格数据
	 */
	@Override
	public List<Map<String, String>> createExcelData(BaseExcelQueryTemplate excelTemplate, List<Device> list) {
		if (list != null && list.size() > 0) {
			// 声明一个存放表格数据的集合
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 存放到表格中的数据列表，包括表头和表身数据
			// 声明一个表头键值对容器，这里存放所有的表头信息
			Map<String, String> tableHead = new TreeMap<String, String>();

			// 存放表格数据
			Map<String, String> tableBody = null;

			// 获取传入的表格模版的类的对象
			Class<?> cls = excelTemplate.getClass();
			// 获取这个类的属性集合
			Field[] fields = cls.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					// 设置属性是可访问的
					field.setAccessible(true);
					String fieldName = field.getName();
					// 获取属性上的注解
					ExcelTemplate template = field.getAnnotation(ExcelTemplate.class);
					// 将注解为表头字段
					tableHead.put(fieldName, template.name());
				}
			}
			// 将表头放入总集合
			data.add(tableHead);
			// 生成表身数据
			// 遍历传入的数据列表
			for (Device device : list) {
				// 获取类的对象
				Class<?> clazz = device.getClass();
				// 迭代表头获取存放的字段
				Set<String> keySet = tableHead.keySet();
				tableBody = new TreeMap<String, String>();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					try {
						if (key.equals("has_company")) {
							Company company = device.getCompany();
							if (company != null && company.getCompanyName() != null) {
								// 根据工地号查询工地详情
								tableBody.put(key, company.getCompanyName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						} else if (key.equals("has_area")) {
							Area area = device.getArea();
							if (area != null && area.getAreaName() != null) {
								// 根据工地号查询工地详情
								tableBody.put(key, area.getAreaName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						} else if (key.equals("has_site")) {
							// 所属工地
							Site site = device.getSite();
							if (site != null && site.getSiteName() != null) {
								tableBody.put(key, site.getSiteName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						} else if (key.equals("has_disposal")) {
							// 所属处置场
							Disposal disposal = device.getDisposal();
							if (disposal != null && disposal.getDisposalName() != null) {
								tableBody.put(key, disposal.getDisposalName());
							} else {
								tableBody.put(key, null);
							}
							continue;
						}
						else if (key.equals("has_deviceType")) {
							// 所属处置场
							Integer type=device.getDeviceType();
						switch (type) {
						case 1:
							tableBody.put(key, DeviceType.SITE.getName());
							break;
						case 3:
							tableBody.put(key, DeviceType.DISPOSAL.getName());
							break;
						}
						continue;
						}
						Field filed = clazz.getDeclaredField(key.replace("has_", ""));
						filed.setAccessible(true);
						String type = filed.getGenericType().toString();
						// 获取该对象的此属性的值
						Object obj = filed.get(device);
						// 如果获取到的是时间类型，则转化一下时间格式
						if ("date".equalsIgnoreCase(type.substring(type.lastIndexOf(".") + 1, type.length()))) {
							Date date = (Date) obj;
							if (date == null) {
								tableBody.put(key, null);
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key, sdf.format(date));
							}
						} else {
							if (obj == null) {
								tableBody.put(key, null);
							} else {
								// 将该key与属性值添加到当前条数据的Excel表单元格中
								tableBody.put(key, obj.toString());
							}
						}
					} catch (Exception e) {
						throw new BizException(StatusCode.INTERNAL_ERROR);
					}
				}
				data.add(tableBody);
			}
			return data;

		}

		return null;

	}

	@Override
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByChannelCode(String channelCode) {
		return deviceMapper.queryDeviceTypeByChannelCode(channelCode);
	}

	@Override
	public List<String> queryChannelByDeviceCode(String deviceCode) {
		
		return deviceChannelMapper.selectChannelByDeviceCode(deviceCode);
	}

	@Override
	public void updateIsRunningByChannelCode(String channelCode,Integer isRunning) {
		//根据通道号获取设备号，并修改设备运行状态
		updateIsRunningByDeviceCode(channelMapper.selectDeviceCodeByCode(channelCode), isRunning);
		
		//修改通道运行状态
	}

	@Override
	public void updateIsRunningByDeviceCode(String deviceCode,Integer isRunning) {
		deviceMapper.updateIsRunningByDeviceCode(deviceCode, isRunning);
	}
}
