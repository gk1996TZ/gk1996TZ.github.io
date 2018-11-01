package com.muck.service;

import java.util.List;

import com.muck.domain.Device;
import com.muck.query.DeviceQueryForm;
import com.muck.response.SimpleDeviceAndDisposalInfoResponse;
import com.muck.response.SimpleDeviceAndSiteInfoResponse;
import com.muck.response.SimpleDeviceListResponse;
import com.muck.response.SimpleDeviceTypeInfoResponse;

/**
* @Description: 设备Service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:38:11
 */
public interface DeviceService extends BaseService<Device>{
	
	/**
	 * @Description: 根据设备编号查询设备信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月9日 下午7:24:18
	 * @param: deviceCode 传入一个设备编号
	 * @return: Device 返回设备信息
	 */
	public Device queryByDeviceCode(String deviceCode);
	
	/**
	 * @Description: 根据工地id查询这个工地下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:26:33
	 * @param: siteIds 传入工地id
	 * @return: String 返回含有设备编号的列表，多个设备编号用逗号隔开
	 */
	public String queryDeviceCodeBySiteId(String siteId);
	
	/**
	 * @Description: 根据工地id列表查询这些工地下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:26:33
	 * @param: siteIds 传入工地id列表
	 * @return: String 返回含有设备编号的列表，多个设备编号用逗号隔开
	 */
	public String queryDeviceCodeBySiteIds(String siteIds);
	/**
	 * @Description: 根据处置场id查询这个处置场下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:27:13
	 * @param: disposalIds 传入处置场id
	 * @return: String 返回含有设备编号的列表，多个设备编号用逗号隔开
	 */
	public String queryDeviceCodeByDisposalId(String disposalId);
	/**
	 * @Description: 根据处置场id列表获取这些处置场下面所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午11:11:27
	 * @param: disposalIds 传入处置场id列表
	 * @return: String 返回含有设备编号的列表，多个设备编号用逗号隔开
	 */
	public String queryDeviceCodeByDisposalIds(String disposalIds);

	/**
	* @Description: 根据区域id和企业id数组查询设备
	* @param:	
	* 			areaId : 区域id
	* 			companyIds : 企业id数组
	* @author: 展昭
	* @date: 2018年5月16日 下午6:12:03
	 */
	public List<Device> queryDevicesByCondition(String areaId, String[] companyIds);
	/**
	 * @Description: 通过通道号来查询设备号 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午2:49:55
	 * @param: channelCode 传入一个通道号
	 * @return: String 返回设备号
	 */
	public String queryDeviceCodeByChannelCode(String channelCode);

	// --------------- 前台功能------------------------------------
	
	/***
	 * 	根据工地id、处置场id、设备名称查询设备列表(不带分页)
	*/
	public List<SimpleDeviceListResponse> queryDevicesByCondition(DeviceQueryForm queryForm);

	/***
	 * 	根据设备id查询设备详情
	 * 		包括两部分：
	 * 			1、工地信息
	 * 			2、设备信息
	*/
	public SimpleDeviceAndSiteInfoResponse queryDeviceAndSiteInfoById(String deviceId);

	/***
	 * 	根据设备id查询设备详情
	 * 		包括两部分：
	 * 			1、处置场信息
	 * 			2、设备信息
	*/
	public SimpleDeviceAndDisposalInfoResponse queryDeviceAndDisposalInfoById(String deviceId);

	/***
	 * 	查询所有的设备号
	*/
	public List<String> queryDeviceCodes();

	/***
	 * 	根据设备的注册id查询设备的类型信息(是处置场，还是工地)
	*/
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByRegisterId(String registerId);

	/**
	 * @Description: 根据设备的通道号查询设备的类型信息(是处置场，还是工地)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月29日  上午2:30:07
	 * @param:channelCode 传入一个通道号
	 * @return:SimpleDeviceTypeInfoResponse 返回设备类型1:工地2:车辆3:处置场
	 */
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByChannelCode(String channelCode);
	
	/**
	 * 根据设备号查询通道号
	 * */
	public List<String> queryChannelByDeviceCode(String deviceCode);
	
	/**
	 * @Description: 通过通道号修改设备运行状态 
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年9月29日 下午6:38:50
	 * @Param: channelCode 通道编号
	 * @Param: isRunning 传入一个运行状态
	 */
	public void updateIsRunningByChannelCode(String channelCode,Integer isRunning);
	
	/**
	 * @Description: 通过设备编号修改设备运行状态
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年9月29日 下午6:49:53
	 * @Param: deviceCode 传入一个设备编号
	 * @Param: isRunning 传入一个运行状态
	 */
	public void updateIsRunningByDeviceCode(String deviceCode,Integer isRunning);
}
