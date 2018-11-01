package com.muck.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 操作文件的工具类
 * @Version: v1.0.0
 * @Author: 朱俊亮
 * @Date: 2018年8月10日 下午4:45:19
 */
/**
 * @Description:
 * @Version: v1.0.0
 * @Author: 朱俊亮
 * @Date: 2018年8月10日 下午9:23:16
 *
 */
public class FileUtils {

	/**
	 * @Description: 获取一个文件夹下所有的子文件(不包括文件夹)
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午4:57:43
	 * @Param: path 传入一个文件夹
	 * @Return: Map<String,String> 返回文件夹下所有的文件,格式:key为文件名(不包含后缀);value为文件的绝对路径,windows系统下包括盘符
	 */
	public static Map<String,String> getSubFilePaths(String path){
		Map<String,String> map = new HashMap<String,String>();
		if(!"".equals(path)){
			File file = new  File(path);
			if(file.exists()){
				if(file.isDirectory()){
					File[] files = file.listFiles();
					if(files != null && files.length > 0){
						for(File fi : files){
							if(fi.isFile()){
								map.put(fi.getName().substring(0,fi.getName().lastIndexOf(".")), fi.getAbsolutePath());
							}
						}
					}
				}
			}
		}
		return map;
	}
	/**
	 * @Description: 递归删除文件夹或文件的方法
	 * @Version: v1.0.0
	 * @Author: 朱俊亮
	 * @Date: 2018年8月10日 下午9:23:18
	 * @Param: path 传入一个文件路径
	 */
	public static void deleteFile (String path){
		if(path != null && !"".equals(path)){
			File file = new File(path);
			if(file.isFile()){
				file.delete();
			}else {
				File [] files = file.listFiles();
				if(files != null){
					for(File fs : files){
						//递归调用删除文件方法
						if(fs.isDirectory()){
							deleteFile(fs.getAbsolutePath());
						}else fs.delete();
					}
				}
				file.delete();
			}
		}
	}
}
