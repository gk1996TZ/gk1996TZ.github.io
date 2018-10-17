package com.xumengba.service;

/**
* @Description: 配置信息常量service
* @version: v1.0.0
* @date: 2018年5月9日 上午10:18:00
 */
public interface PropertiesService {

	
	
	/**
	 * @Description: 获取当前服务器的ip
	 * @version:v1.0.0
	 * @date:2018年7月7日  下午7:05:25
	 * @return:String 返回当前服务器的ip
	 */
	public String getCurrentServer();
	/**
	 * @Description:  获取当前上传图片的套接字
	 * @version:v1.0.0
	 * @date:2018年6月24日  下午3:44:56
	 * @return:String 返回当前上传图片的套接字
	 */
	public String getCurrentServerForImage();
	/**
	 * @Description: 获取当前导出Excel的套接字
	 * @version:v1.0.0
	 * @date:2018年6月24日  下午3:45:03
	 * @return:String 返回当前导出Excel的套接字
	 */
	public String getCurrentServerForExcel();
	
	/**
	 * @Description: 获取本地存放当前项目的资源文件目录
	 * @version:v1.0.0
	 * @date:2018年6月28日  下午8:51:22
	 * @return:String 返回当前项目资源文件目录
	 */
	public String getWindowsRootPath();
	
	/**
	 * @Description: 获取水印图片存放路径
	 * @version:v1.0.0
	 * @date:2018年6月28日  下午8:51:18
	 * @return:String 获取水印图片存放路径
	 */
	public String getWindowsCreateWaterRootPath();
	
}
