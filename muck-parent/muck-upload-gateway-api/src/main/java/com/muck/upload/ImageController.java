package com.muck.upload;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.muck.domain.Image;
import com.muck.exception.base.BizException;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.ImageService;
import com.muck.service.UploadFileService;
import com.muck.utils.UploadUtils;

/**
 * 图片Controller
 */
@RestController
@RequestMapping("/upload/image")
public class ImageController {

	@Autowired
	private UploadFileService uploadFileService; // 文件上传service
	
	@Autowired
	private ImageService imageService;
	
	// 抓拍图片上传请求
	@RequestMapping(value = "/upload.action", method = RequestMethod.POST)
	public ResponseResult upload(@RequestParam(value = "imageFile", required = true) MultipartFile imageFile,
			String date, String address, String username) throws IOException {

		// 1、校验是否有上传文件
		if (imageFile == null || imageFile.isEmpty()) {
			throw new BizException(StatusCode.UPLOAD_FILE_CONTENT_BLANK);
		}

		// 2、获取原始文件名
		String fileName = UploadUtils.getSimpleFileName(imageFile.getOriginalFilename());

		// 4、调用文件上传服务根据获取上传的文件路径
		String saveFilePath = uploadFileService.upload(fileName, imageFile.getBytes(),null);

		Image image = new Image();

		image.setCreatedTime(new Date()); // 图片抓拍
		image.setDeleted(true); // 不删除
		image.setImagePath(saveFilePath); // 图片路径

		// 保存抓拍图片
		imageService.save(image);
		
		return ResponseResult.ok(image);
	}

}
