package com.muck.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.muck.service.PropertiesService;
import com.muck.service.UploadFileService;
import com.muck.utils.ImageWaterMarkUtils;
import com.muck.utils.OSUtil;
import com.muck.utils.UploadUtils;

/**
* @Description: 文件删除service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月12日 下午3:55:59
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

	@Autowired
	PropertiesService propertiesService;
	/***
	* @Description: 文件上传
	* @param:描述1
	* @return：返回结果描述
	* @throws：异常描述
	* @author: 展昭
	* @date: 2018年4月13日 下午4:58:58
	*/
	public String upload(String fileName,byte[] data,String waterContent){
		
		// 1、根据文件名获取一个唯一的uuid的文件名
		String saveFileName = UploadUtils.generateFileName(fileName);
		
		// 2、生成一个保存文件的路径(home+upload+年+月+日+时)
		String saveFilePath = UploadUtils.generateDirByDate(propertiesService.getWindowsRootPath());
		
		// 3、生成一个带路径+文件名     并根据不同的操作系统上传到不同的路径
		String saveFile = "";
		if(OSUtil.isLinux()){
			saveFile = saveFilePath + File.separator + saveFileName;
		}else if(OSUtil.isWindows()){
			saveFile = "D:"+File.separator+"muck"+File.separator+"resources" + saveFilePath + File.separator +saveFileName;
		}
		try {
			// 4、向磁盘中写文件
			File file = new File(saveFile);
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			FileCopyUtils.copy(data, file);
			
			// 5、判断是否要添加水印
			if(StringUtils.isNotBlank(waterContent)){
				//  表示要添加
				ImageWaterMarkUtils.addWaterMark
						(waterContent, file, saveFile, null);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return "http://"+propertiesService.getCurrentServerForImage()+saveFilePath + "/" + saveFileName;
	}
}
