package com.muck.upload;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.muck.domain.SnapshotImage;
import com.muck.exception.base.BizException;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.SnapshotImageService;
import com.muck.service.UploadFileService;
import com.muck.utils.UploadUtils;


/**
 * @Description: 抓拍图片Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月12日 下午3:41:54
*/
@RestController
@RequestMapping("/upload/SnapshotImage/")
public class SnapshotImageController {

	@Autowired
	private SnapshotImageService snapshotImageService; // 视频抓拍service

	@Autowired
	private UploadFileService uploadFileService; // 文件上传service

	// 抓拍图片上传请求
	@RequestMapping(value = "/upload.action", method = RequestMethod.POST)
	public ResponseResult upload(@RequestParam(value = "imageFile", required = true) MultipartFile imageFile,String date,String address,String username) throws IOException {

		// 1、校验是否有上传文件
		if (imageFile == null || imageFile.isEmpty()) {
			throw new BizException(StatusCode.UPLOAD_FILE_CONTENT_BLANK);
		}

		// 2、获取原始文件名
		String fileName = UploadUtils.getSimpleFileName(imageFile.getOriginalFilename());
		
		// 3、拼接水印
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
		// 4、调用文件上传服务根据获取上传的文件路径
		String saveFilePath = uploadFileService.upload(fileName, imageFile.getBytes(), water);

		SnapshotImage snapshotImage = new SnapshotImage();

		snapshotImage.setSnapshotImagePath(saveFilePath); // 设置抓拍图片路径
		snapshotImage.setSnapshotTime(new Date()); // 设置抓拍时间

		// 保存抓拍图片
		snapshotImageService.save(snapshotImage);
		return ResponseResult.ok(snapshotImage);
	}

	// 抓拍图片上传请求
	@RequestMapping(value = "/save.action")
	public void save() {

		SnapshotImage image = new SnapshotImage();
		image.setSnapshotTime(new Date());
		image.setSnapshotImagePath("qq");

		snapshotImageService.save(image);
	}

	// 抓拍图片上传请求
	@RequestMapping(value = "/findById.action")
	public SnapshotImage findById(String id) {

		SnapshotImage image = snapshotImageService.queryById(id);

		return image;
	}

	// 抓拍图片上传请求
	@RequestMapping(value = "/testById.action")
	public SnapshotImage testById() {

		SnapshotImage image = snapshotImageService.queryById("");

		return image;
	}

}
