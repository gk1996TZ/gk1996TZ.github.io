package com.litang.service;

/**
* @Description: 配置信息常量service
 */
public interface PropertiesService {

	
	/**
	 * @Description: 获取当前服务器的ip
	 * @version:v1.0.0
	 * @return:String 返回当前服务器的ip
	 */
	public String getCurrentServer();
	/**
	 * @Description:  获取当前上传图片的套接字
	 * @version:v1.0.0
	 * @return:String 返回当前上传图片的套接字
	 */
	public String getCurrentServerForImage();
	/**
	 * @Description: 获取当前导出Excel的套接字
	 * @version:v1.0.0
	 * @return:String 返回当前导出Excel的套接字
	 */
	public String getCurrentServerForExcel();
	
	/**
	 * @Description: 获取本地存放当前项目的资源文件目录
	 * @version:v1.0.0
	 * @return:String 返回当前项目资源文件目录
	 */
	public String getWindowsRootPath();
	/**
	 * @Description: 获取本地存放默认头像的路径
	 * @version:v1.0.0
	 * @return:String 返回当前项目默认头像路径
	 */
	public String getDefaultHeadPath();
	/**
	 * @Description:获取重置后的默认密码
	 * @return:String 返回重置后的默认密码
	 */
	public String getDefaultNewPwd();
	/**
	 * @Description:获取更新客户端的请求链接
	 * @return:返回客户端更新的请求链接
	 */
	public String getUpdateLink();
	/**
	 * @Description:获取客户端最新的版本号
	 * @return:返回客户端最新的版本号
	 */
	public String getLastVersion();
}
