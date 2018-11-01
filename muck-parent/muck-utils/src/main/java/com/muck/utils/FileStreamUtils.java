package com.muck.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description: 操作文件流的工具类
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年7月3日 下午2:55:54
 */
public class FileStreamUtils {

	private static Logger logger = LoggerFactory.getLogger(FileStreamUtils.class);
	/**
	 * @Description: 读取文件输入流
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月3日  下午2:59:32
	 * @param: path 传入一个本地的文件目录
	 * @return: byte[] 返回文件的二进制流
	 */
	public static byte [] getInputStream(String path){
		File file = new File(path);
		try {
			return file.exists() && file.isFile() ? IOUtils.toByteArray(new FileInputStream(file)) : null;
		} catch (FileNotFoundException e) {
			logger.error("获取文件流时文件未找到");
		} catch (IOException e) {
			logger.error("获取文件流失败");
		} catch (Exception e){
			logger.error("获取文件流时出现未知异常");
		}
		return null;
	}
}
