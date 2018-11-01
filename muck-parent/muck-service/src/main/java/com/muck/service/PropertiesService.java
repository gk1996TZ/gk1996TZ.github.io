package com.muck.service;

import com.muck.response.DaHuaPlatFormConfig;
import com.muck.response.GPSDataConfig;

/**
* @Description: 配置信息常量service
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 上午10:18:00
 */
public interface PropertiesService {

	/***
	* @Description: 获取大华登录平台的配置信息
	* @author: 展昭
	* @date: 2018年5月9日 上午10:41:16
	 */
	public DaHuaPlatFormConfig getDaHuaPlatFormConfig();
	
	/**
	 * 	获取定时删除GPS车载数据的配置时间信息
	*/
	public GPSDataConfig getGPSDataConfig();
	
	/***
	 * 	长宝
	 * 		远程调用获取车辆gps的数据webservice url
	*/
	public String getChangbaoWebserviceUrl();

	/**
	 *	长宝
	 *		远程调用获取车辆gps的数据webservice url所需要的key
	*/
	public String getChangbaoUserKey();
	
	/***
	 * 	北斗
	 * 		远程调用获取车辆gps的数据webservice url
	*/
	public String getBeidouWebserviceUrl();

	/***
	 *  北斗
	 *  	远程调用获取车辆gps的数据webservice url所需要的key
	*/
	public String getBeidouUserKey();
	/**
	 * @Description: 获取当前服务器的ip
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月7日  下午7:05:25
	 * @return:String 返回当前服务器的ip
	 */
	public String getCurrentServer();
	/**
	 * @Description:  获取当前上传图片的套接字
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月24日  下午3:44:56
	 * @return:String 返回当前上传图片的套接字
	 */
	public String getCurrentServerForImage();
	/**
	 * @Description: 获取当前导出Excel的套接字
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月24日  下午3:45:03
	 * @return:String 返回当前导出Excel的套接字
	 */
	public String getCurrentServerForExcel();
	
	/**
	 * @Description: 获取本地存放当前项目的资源文件目录
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月28日  下午8:51:22
	 * @return:String 返回当前项目资源文件目录
	 */
	public String getWindowsRootPath();
	
	/**
	 * @Description: 获取水印图片存放路径
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年6月28日  下午8:51:18
	 * @return:String 获取水印图片存放路径
	 */
	public String getWindowsCreateWaterRootPath();
	
	
}
