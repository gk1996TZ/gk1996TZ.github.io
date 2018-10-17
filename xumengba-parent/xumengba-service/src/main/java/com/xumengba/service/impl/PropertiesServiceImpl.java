package com.xumengba.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xumengba.service.PropertiesService;


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
	 * 导出Excel时生成水印过程中生成的水印图片的路径，图片路径
	 */
	@Value("${WINDOWS_CREATE_WATER_ROOT_PATH}")
	private String WINDOWS_CREATE_WATER_ROOT_PATH;
	
	
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
