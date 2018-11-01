package com.muck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.muck.domain.Device;
import com.muck.query.DeviceQueryForm;
import com.muck.response.SimpleDeviceAndDisposalInfoResponse;
import com.muck.response.SimpleDeviceAndSiteInfoResponse;
import com.muck.response.SimpleDeviceListResponse;
import com.muck.response.SimpleDeviceTypeInfoResponse;

/**
 * @Description: 设备Mapper
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月11日 下午2:15:38
 */
@Repository
public interface DeviceMapper extends BaseMapper<Device>{

	/**
	 * @Description: 根据设备编号查询设备信息
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月9日 下午7:24:18
	 * @param: deviceCode 传入一个设备编号
	 * @return: Device 返回设备信息
	 */
	public Device queryByDeviceCode(@Param("deviceCode")String deviceCode);
	/**
	 * @Description: 根据工地id查询这些工地下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:26:33
	 * @param: siteId 传入工地id
	 * @return: List<String> 返回含有设备编号的列表
	 */
	public List<String> queryDeviceCodeBySiteId(@Param("siteId")String siteId);
	
	/**
	 * @Description: 根据工地id列表查询这些工地下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午10:31:18
	 * @param: siteIds 传入一个工地id列表
	 * @return: List<String> 返回含有设备编号的列表
	 */
	public List<String> queryDeviceCodeBySiteIds(@Param("siteIds")String siteIds);
	
	/**
	 * @Description: 根据处置场id查询这些处置场下所有的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午9:27:13
	 * @param: disposalId 传入处置场id
	 * @return: List<String> 返回含有设备编号的列表
	 */
	public List<String> queryDeviceCodeByDisposalId(@Param("disposalId")String disposalId);
	
	/**
	 * @Description: 通过处置场id列表获取这些处置场下面的设备编号
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月10日 上午11:13:28
	 * @param: disposalIds 传入处置场的id列表
	 * @return: List<String> 返回含有设备编号的列表
	 */
	public List<String> queryDeviceCodeByDisposalIds(@Param("disposalIds")String disposalIds);
	
	/***
	* @Description: 批量添加设备信息
	* @author: 展昭
	* @date: 2018年5月11日 下午12:24:02
	 */
	public void insertBatch(@Param("devices")List<Device> devices);
	
	/***
	 * 根据区域id和企业id数组查询设备 
	 * @author: 展昭
	 * @date: 2018年5月16日 下午6:14:33
	 * 
	 */
	public List<Device> selectByCondition(@Param("areaId")String areaId, @Param("companyIds")String[] companyIds);
	/**
	 * @Description: 通过通道号来查询设备号 
	 * @version: v1.0.0
	 * @author 朱俊亮
	 * @date: 2018年5月18日 下午2:49:55
	 * @param: channelCode 传入一个通道号
	 * @return: String 返回设备号
	 */
	public String queryDeviceCodeByChannelCode(String channelCode);
	
	/**
	 * 	将设备的工地信息维护到设备表中
	*/
	public void setDeviceAndSiteRelation();
	
	/**
	 * 	查询所有的设备号
	*/
	public List<String> selectDeviceCodes();
	
	/**
	 * 	将设备的处置场信息维护到设备表中
	*/
	public void setDeviceAndDisposalRelation();
	
	// ---------------  前台	 --------------------------
	
	/**
	 * 	根据工地id、处置场id、设备名称查询设备列表(不带分页)
	*/
	public List<SimpleDeviceListResponse> selectDevicesByCondition(DeviceQueryForm queryForm);
	
	/***
	 * 	根据设备id查询设备详情
	 * 		包括两部分：
	 * 			1、工地信息
	 * 			2、设备信息
	*/
	public SimpleDeviceAndSiteInfoResponse selectDeviceAndSiteInfoById(@Param("deviceId")String deviceId);
	
	/***
	 * 	根据设备id查询设备详情
	 * 		包括两部分：
	 * 			1、处置场信息
	 * 			2、设备信息
	*/
	public SimpleDeviceAndDisposalInfoResponse selectDeviceAndDisposalInfoById(@Param("deviceId")String deviceId);
	
	/***
	 * 	根据设备的注册id查询设备的类型信息(是处置场，还是工地)
	*/
	public SimpleDeviceTypeInfoResponse selectDeviceTypeByRegisterId(@Param("registerId")String registerId);

	/**
	 * @Description: 根据设备的通道号查询设备的类型信息(是处置场，还是工地)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月29日  上午2:30:07
	 * @param:channelCode 传入一个通道号
	 * @return:SimpleDeviceTypeInfoResponse 返回设备类型1:工地2:车辆3:处置场
	 */
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByChannelCode(@Param("channelCode")String channelCode);
	
	/**
	 * @Description:根据设备的注册id查询设备的类型信息(是处置场，还是工地)
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月7日  下午11:51:30
	 * @param: registerId 传入一个注册id
	 * @return:SimpleDeviceTypeInfoResponse 返回设备类型1:工地2:车辆3:处置场
	 */
	public SimpleDeviceTypeInfoResponse queryDeviceTypeByRegisterId(@Param("registerId")String registerId);


	public void updateIsRunningByDeviceCode(@Param("deviceCode")String deviceCode,@Param("isRunning")Integer isRunning);
}
