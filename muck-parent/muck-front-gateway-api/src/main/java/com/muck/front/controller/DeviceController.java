package com.muck.front.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.query.DeviceQueryForm;
import com.muck.response.ResponseResult;
import com.muck.response.SimpleDeviceAndDisposalInfoResponse;
import com.muck.response.SimpleDeviceAndSiteInfoResponse;
import com.muck.response.SimpleDeviceListResponse;
import com.muck.response.SimpleDeviceTypeInfoResponse;
import com.muck.service.DeviceService;
import com.muck.utils.CollectionUtils;

/**
 * 设备Controller
 */
@RestController("FrontDeviceController")
@RequestMapping("/front/device")
public class DeviceController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DeviceService deviceService; // 设备service

	/***
	 * 根据工地id、处置场id、设备名称查询设备列表(不带分页)
	 */
	@RequestMapping(value = "queryDevicesByCondition.action", method = RequestMethod.POST)
	public ResponseResult queryDevicesByCondition(DeviceQueryForm queryForm) {

		// 查询列表(不分页)
		List<SimpleDeviceListResponse> devices = deviceService.queryDevicesByCondition(queryForm);

		return ResponseResult.ok(CollectionUtils.toList(devices));
	}

	/***
	 * 根据设备id查询设备详情 包括两部分： 1、工地信息 2、设备信息
	 */
	@RequestMapping(value = "queryDeviceAndSiteInfoById.action", method = RequestMethod.GET)
	public ResponseResult queryDeviceAndSiteInfoById(String deviceId) {
		if (StringUtils.isBlank(deviceId)) {
			logger.error("调用queryDeviceAndSiteInfoById接口时deviceId为空");
		}
		try {

			SimpleDeviceAndSiteInfoResponse deviceInfoResponse = deviceService.queryDeviceAndSiteInfoById(deviceId);
			return ResponseResult.ok(deviceInfoResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}

	/***
	 * 根据设备id查询设备详情 包括两部分： 1、处置场信息 2、设备信息
	 */
	@RequestMapping(value = "queryDeviceAndDisposalInfoById.action", method = RequestMethod.GET)
	public ResponseResult queryDeviceAndDisposalInfoById(String deviceId) {
		if (StringUtils.isBlank(deviceId)) {
			logger.error("调用queryDeviceAndDisposalInfoById接口时deviceId为空");
		}

		try {
			SimpleDeviceAndDisposalInfoResponse deviceInfoResponse = deviceService.queryDeviceAndDisposalInfoById(deviceId);
			return ResponseResult.ok(deviceInfoResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}

	/***
	 * 查询所有的设备编号
	 */
	@RequestMapping(value = "queryDeviceCodes.action", method = RequestMethod.GET)
	public List<String> queryDeviceCodes() {

		List<String> deviceCodes = deviceService.queryDeviceCodes();

		return deviceCodes;
	}

	/***
	 * 根据设备的注册id获取设备所在的区域或者处置场
	 */
	@RequestMapping(value = "queryDeviceTypeByRegisterId.action", method = RequestMethod.GET)
	public ResponseResult queryDeviceTypeByRegisterId(String registerId) {

		if (StringUtils.isBlank(registerId)) {
			logger.error("调用queryDeviceTypeByRegisterId接口时registerId为空");
		}

		try {
			SimpleDeviceTypeInfoResponse deviceTypeInfoResponse = deviceService.queryDeviceTypeByRegisterId(registerId);

			return ResponseResult.ok(deviceTypeInfoResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}
}
