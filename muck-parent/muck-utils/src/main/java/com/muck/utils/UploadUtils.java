package com.muck.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
* @Description: 文件上传工具类
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月12日 下午3:24:43
 */
public final class UploadUtils {

	private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);
	private UploadUtils(){
		
	}

	/**
	 * 	随机生成唯一的文件名
	*/
	public static String generateFileName(String fileName){
		fileName = StringUtils.isBlank(fileName) ? "" : fileName;
		return UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
	}
	
	/**
	 * 	生成目录
	 * 		根据一个文件名来去计算这个文件名的hash值(根据一个字符串来去计算一个字符串的hash值)
	 * 
	 * 		1、通过文件名计算hashcode
	 * 		2、我拿hashcode值的低4位作为一级目录
	 * 		3、我拿hashcode值的5-8位作为二级目录
	*/
	public static String generateDirByHashCode(String filePathDir , String fileName){
		
		int hashCode = fileName.hashCode();
		
		int dirLevel1 = hashCode & 0X0F;
		int dirLevel2 = (hashCode & 0XF0) >> 4;
		
		String realDir = filePathDir + "\\" + dirLevel1 + "\\" + dirLevel2;
		File file = new File(realDir);
		if(!file.exists()){
			file.mkdirs();
		}
		return realDir;
	}
	
	/**
	 * 	生成目录
	 * 		通过日期
	 * 			年
	 * 				月
	 * 					日
	 * 						时
	*/
	public static String generateDirByDate(String filePathDir){
		
		// 当前时间
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH");
		String formatDate = sdf.format(date);
		
		String realDir = "/" + "home" + "/" + formatDate;
		File file = new File(realDir);
		if(!file.exists()){
			file.mkdirs();
		}
		
		return realDir;
	}
	
	/**
	 * 	根据文件名获取扩展名 
	 * 		xxx.txt
	*/
	public static String getFileExt(String fileName){
		String ext = "";
		if(StringUtils.isNotBlank(fileName)){
			ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return ext;
	}
	
	/**
	 * 	校验上传的文件是图片文件
	*/
	public static boolean validateFileTypeIsImage(String ext,String type){
		
		boolean flag = true;
		List<String> exts = Arrays.asList("gif","png","bmp","jpeg","jpg");
		
		if(!exts.contains(ext)){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据路径和servlet请求中的文件上传请求上传单个文件
	 * @param rootPath 将文件存放在本地的根目录
	 * @param path
	 *            传入上传的文件需要放入的文件夹目录，该目录为服务器系统目录，
	 *            同时用来区分文件的类型
	 * @param request
	 *            传入一个request请求
	 * @return 返回需要存放到数据库的文件路径
	 */
	public static String uploadFile(String rootPath,String path, HttpServletRequest request) {
		//将request请求强制转化为文件请求
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		if(request instanceof MultipartHttpServletRequest){
			 multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		}
		//获取文件请求中所有的文件
		List<MultipartFile> list = null;
		if(multipartHttpServletRequest!=null){
			list = multipartHttpServletRequest.getFiles("file");
		}
		MultipartFile mul = null;
		if(list!=null && list.size() > 0){
			//一般是单个文件上传，获取第一个图片即可，多个的话再添加判断
			mul = list.get(0);
		}
		// 存放图片的相对路径
		String file_path = "";
		if(mul != null){
			if (path != null && !path.isEmpty()) {
				//如果参数传入有文件子目录，则将文件存放到相应子目录下
				file_path =File.separator+"excel"+File.separator + "upload" + File.separator + path + File.separator + getSimpleFileName(mul.getOriginalFilename());
			} else {
				//如果参数传入没有存放的路径，则将文件存放到根目录下
				file_path =File.separator+"excel"+File.separator+ "upload" + File.separator + getSimpleFileName(mul.getOriginalFilename());
			}
			// 判断当前的服务器系统类型，给path前面追加服务器本地的存放路径
			path = rootPath+ file_path;
			try {
				File file = new File(path);
				FileUtils.copyInputStreamToFile(mul.getInputStream(), file);
			} catch (IOException e) {
				logger.error("文件上传路径不存在！");
			}
		}else {
			//否则返回默认的服务器本地图片
			return "";
		}
		// 返回文件在本地存放的路径
		return path;
	}
	
	/***
	 * 	根据一个文件的全路径截取文件名(不包括上传路径)
	 * 		filePath两种情况:
	 * 			1、包含路径  eg:  c:\a\b\c\aa.txt
	 * 			2、仅仅包含文件名  eg:  aa.txt
	 * 		
	*/
	public static String getSimpleFileName(String filePath){
		
		if(StringUtils.isBlank(filePath)){
			return null;
		}
		int index = filePath.lastIndexOf("\\");
		
		if(index != -1){
			filePath = filePath.substring(index + 1);
		}
		return filePath;
	}
}
