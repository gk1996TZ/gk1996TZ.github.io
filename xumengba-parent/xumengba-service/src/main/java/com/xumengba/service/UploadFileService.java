package com.xumengba.service;

/**
* @Description: 文件上传service接口
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月12日 下午3:55:00
 */
public interface UploadFileService{

	/**
	 * 
	* @Description: 文件上传
	* @param:	filePath : 文件名称
	* 			isWater : 是否添加水印
	* @return：返回结果描述
	* @throws：异常描述
	* @author: Administrator
	* @date: 2018年4月13日 下午4:39:41
	 */
	public String upload(String fileName,byte[] data,String waterContent);
}
