package com.muck.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.muck.response.DaHuaPlatFormConfig;
import com.muck.response.GPSDataConfig;
import com.muck.service.PropertiesService;

/***
* @Description: 属性service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 上午10:25:50
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

	@Value("${dahua_ip}")
	private String dahua_ip;	// 大华平台登录ip
	
	@Value("${dahua_port}")
	private String dahua_port;	// 大华平台登录port
	
	@Value("${dahua_login_name}")
	private String	dahua_login_name;  // 大华登录平台登录名
 	
	@Value("${dahua_login_pwd}")
	private String dahua_login_pwd;		// 大华登录平台密码
	
	//------------------------------------------------
	
	@Value("${delete_gps_data_hour}")
	private String delete_gps_data_hour; // 指定定时删除gps数据在几点
	
	@Value("${delete_gps_data_minute}")
	private String delete_gps_data_minute; // 指定定时上删除gps数据在几分
	
	@Value("${reserved_gps_data_month}")
	private String reserved_gps_data_month; // 设置要保留的GPS车载数据的月份
	
	//-----------------------------------------------------
	
	//  长宝
	@Value("${changbao_car_gps_data_webservice_url}")
	private String changbao_car_gps_data_webservice_url; // 远程调用获取车辆gps的数据webservice url
	
	@Value("${changbao_car_gps_data_webservice_url_user_key}")
	private String changbao_car_gps_data_webservice_url_user_key; // 远程调用获取车辆gps的数据webservice url所需要的key
	
	// 北斗
	@Value("${beidou_car_gps_data_webservice_url}")
	private String beidou_car_gps_data_webservice_url; // 远程调用获取车辆gps的数据webservice url
	
	@Value("${beidou_car_gps_data_webservice_url_user_key}")
	private String beidou_car_gps_data_webservice_url_user_key; // 远程调用获取车辆gps的数据webservice url所需要的key
	/**
	 * 当前服务器的ip
	 */
	@Value("${CURRENT_SERVER}")
	private String CURRENT_SERVER;
	/**
	 * 上传图片到服务器时使用到的套接字
	 */
	@Value("${CURRENT_SERVER_FOR_IMAGE}")
	private String CURRENT_SERVER_FOR_IMAGE;
	/**
	 * 导出Excel时使用到的套接字
	 */
	@Value("${CURRENT_SERVER_FOR_EXCEL}")
	private String CURRENT_SERVER_FOR_EXCEL;
	/**
	 * 本地保存上传文件的路径，文件存放的路径
	 */
	@Value("${WINDOWS_ROOT_PATH}")
	private String WINDOWS_ROOT_PATH;
	/**
	 * 导出Excel时生成水印过程中生成的水印图片的路径，图片路径
	 */
	@Value("${WINDOWS_CREATE_WATER_ROOT_PATH}")
	private String WINDOWS_CREATE_WATER_ROOT_PATH;
	
	/***
	* @Description: 获取大华登录平台的配置信息
	* @author: 展昭
	* @date: 2018年5月9日 上午10:41:16
	 */
	public DaHuaPlatFormConfig getDaHuaPlatFormConfig(){
		return new DaHuaPlatFormConfig(dahua_ip, dahua_port, dahua_login_name, dahua_login_pwd);
	}

	//-----------------------------------

	/**
	 * 	获取定时删除GPS车载数据的配置时间信息
	*/
	public GPSDataConfig getGPSDataConfig(){
		
		Integer hour = 22;
		Integer minute = 30;
		Integer month = 6;
		try {
			if(StringUtils.isNotBlank(delete_gps_data_hour)){
				hour = Integer.parseInt(delete_gps_data_hour);
			}
			if(StringUtils.isNotBlank(delete_gps_data_minute)){
				minute = Integer.parseInt(delete_gps_data_minute);
			}
			if(StringUtils.isNotBlank(reserved_gps_data_month)){
				month = Integer.parseInt(reserved_gps_data_month);
			}
		} catch (Exception e) {
			/***
			 *  假如时间的配置信息在转换的过程中抛出了异常则默认直接捕获,不要影响程序运行,那么就采用默认的时间去定时执行即可
			*/
			e.printStackTrace();
		}
		return new GPSDataConfig(hour, minute,month);
	}
	
	//----------------------------
	
	/**
	 * 	长宝
	*/
	public String getChangbaoWebserviceUrl() {
		return changbao_car_gps_data_webservice_url;
	}

	public String getChangbaoUserKey() {
		return changbao_car_gps_data_webservice_url_user_key;
	}

	/**
	 *  北斗
	*/
	public String getBeidouWebserviceUrl() {
		return beidou_car_gps_data_webservice_url;
	}
	@Override
	public String getBeidouUserKey(){
		return beidou_car_gps_data_webservice_url_user_key;
	}
	
	@Override
	public String getCurrentServer() {
		return CURRENT_SERVER;
	}

	@Override
	public String getCurrentServerForImage(){
		return CURRENT_SERVER_FOR_IMAGE;
	}
	
	/**
	 * @Description: 获取当前导出Excel的套接字
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月24日  下午3:45:03
	 * @return:String 返回当前导出Excel的套接字
	 */
	public String getCurrentServerForExcel(){
		return CURRENT_SERVER_FOR_EXCEL;
	}
	public String getWindowsRootPath(){
		return WINDOWS_ROOT_PATH;
	}
	public String getWindowsCreateWaterRootPath(){
		return WINDOWS_CREATE_WATER_ROOT_PATH;
	}

}
