package com.muck.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.util.StringUtil;
import com.muck.domain.SnapshotImage;
import com.muck.response.ResponseResult;
import com.muck.service.SnapshotImageService;
import com.muck.service.UploadFileService;

/**
 * @Description:
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月27日 下午9:11:04
 */
@RestController
@RequestMapping("/upload/uploadImageController")
public class UploadImageController {

	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	SnapshotImageService snapshotImageService;
	
	public ResponseResult upload(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		//水印的时间
		String date = request.getParameter("date");
		//水印的地址
		String address = request.getParameter("address");
		//水印的用户名
		String username = request.getParameter("username");
		
		//创建一个通用的多部分解析器  
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
		//设置上传时文件的总大小限制 单位 byte 这里设置为20M
		multipartResolver.setMaxUploadSize(1024*1024*20);
		//判断request是否包含文件上传的请求
		if(multipartResolver.isMultipart(request)){
			List<String> filePaths = new ArrayList<String>(); 
			//将请求转化成文件请求
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			//获取请求中所有的文件名
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			//如果迭代器中包含下一个元素
			while(iterator.hasNext()){
				//获取文件名
				String fileName = iterator.next();
				//通过文件名取得上传文件
				MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
				//拼接水印
				String water = "";
				if(!StringUtil.isEmpty(date)){
					water += "时间:" + date;
					water += "/n";
				}
				if(!StringUtil.isEmpty(address)){
					water += "地点:" + address;
					water += "/n";
				}
				if(!StringUtil.isEmpty(date)){
					water += "管理员:" + username;
					water += "/n";
				}
				if(!"".equals(water)){
					water = water.substring(0, water.length()-2);
				}
				//将图片保存到本地
				String filePath = uploadFileService.upload(fileName, multipartFile.getBytes(), water);
				filePaths.add(filePath);
			}
			
			for(String filePath : filePaths){
				SnapshotImage snapshotImage = new SnapshotImage();

				snapshotImage.setSnapshotImagePath(filePath); // 设置抓拍图片路径
				snapshotImage.setSnapshotTime(new Date()); // 设置抓拍时间

				// 保存抓拍图片到数据库中
				snapshotImageService.save(snapshotImage);
			}
		}
		return ResponseResult.ok();
	}
}
