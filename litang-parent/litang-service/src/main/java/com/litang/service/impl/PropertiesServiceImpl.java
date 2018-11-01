package com.litang.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.litang.service.PropertiesService;

/***
* @Description: 属性service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月9日 上午10:25:50
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

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
	 * 服务器端存放默认头像的路径
	 * */
	@Value("${DEFAULT_HEAD_PATH}")
	private String DEFAULT_HEAD_PATH;
	
	@Value("${DEFAULT_NEW_PWD}")
	private String DEFAULT_NEW_PWD;
	
	/**客户端更新的访问链接*/
	@Value("${updateLink}")
	private String updateLink;
	
	/**最新版的版本号*/
	@Value("${lastVersion}")
	private String lastVersion;
	
	@Override
	public String getCurrentServer() {
		return CURRENT_SERVER;
	}
	@Override
	public String getCurrentServerForImage(){
		return CURRENT_SERVER_FOR_IMAGE;
	}
	public String getCurrentServerForExcel(){
		return CURRENT_SERVER_FOR_EXCEL;
	}
	public String getWindowsRootPath(){
		return WINDOWS_ROOT_PATH;
	}
	@Override
	public String getDefaultHeadPath() {
		return DEFAULT_HEAD_PATH;
	}
	@Override
	public String getDefaultNewPwd() {
		return DEFAULT_NEW_PWD;
	}
	@Override
	public String getUpdateLink() {
		return updateLink;
	}
	@Override
	public String getLastVersion() {
		return lastVersion;
	}
}
