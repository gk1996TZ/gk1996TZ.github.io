package com.muck.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DownloadFileByIO {
	/**
	 * @Description: 从本地以流的方式下载文件
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月6日  下午12:53:01
	 * @param: filepath 传入文件在服务器本地的路径
	 * @param: response 传入一个HttpServletResponse 请求
	 * @param: filename 传入一个文件名
	 */
	public static void downloadFileFromServer(String filepath, HttpServletResponse response, String filename){
		try {
			byte[] content = FileUtils.readFileToByteArray(new File(filepath));
			response.setCharacterEncoding("gbk");
			String header = "attachment;filename=" + new String((filename).getBytes("gbk"), "ISO-8859-1");
			if (filepath.endsWith(".xlsx")) {
				response.setHeader("Content-Disposition", header + ".xlsx");
				resIO(response, content, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=gbk");
			} else if(filepath.endsWith(".xls")){
				response.setHeader("Content-Disposition", header + ".xls");
				resIO(response, content, "application/vnd.ms-excel;charset=gbk");
			}else if(filepath.endsWith(".jpg")){
				response.setHeader("Content-Disposition", header + ".jpg");
				resIO(response, content, "image/jpeg;charset=gbk");
			}else if(filepath.endsWith(".jpeg")){
				response.setHeader("Content-Disposition", header + ".jpeg");
				resIO(response, content, "image/jpeg;charset=gbk");
			}else if(filepath.endsWith(".img")){
				response.setHeader("Content-Disposition", header + ".img");
				resIO(response, content, "application/x-img;charset=gbk");
			}else if(filepath.endsWith(".gif")){
				response.setHeader("Content-Disposition", header + ".gif");
				resIO(response, content, "image/gif;charset=gbk");
			}else if(filepath.endsWith(".ico")){
				response.setHeader("Content-Disposition", header + ".ico");
				resIO(response, content, "image/x-icon;charset=gbk");
			}else if(filepath.endsWith(".png")){
				response.setHeader("Content-Disposition", header + ".png");
				resIO(response, content, "image/png;charset=gbk");
			}else if(filepath.endsWith(".txt")){
				response.setHeader("Content-Disposition", header + ".txt");
				resIO(response, content, "text/plain;charset=gbk");
			}else if(filepath.endsWith(".mp4")){
				response.setHeader("Content-Disposition", header + ".mp4");
				resIO(response, content, "video/mpeg4;charset=gbk");
			}
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
	}
	/**
	 * @Description: 下载文件的通用方法（不支持断点续传）
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年7月6日 下午12:26:26
	 * @param: response 传入一个Response请求
	 * @param: bytes 传入文件字节流
	 * @param: mimeType 传入文件类型
	 */
	public static void resIO(HttpServletResponse response, byte[] bytes, String mimeType) {
		response.setContentType(mimeType);
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(bytes);
			outputStream.flush();
			response.setStatus(200);
		} catch (IOException e) {
		} finally {
			// 关闭流
			IOUtils.closeQuietly(outputStream);
		}
	}
}
