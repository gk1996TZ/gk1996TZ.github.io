package com.xumengba.upload;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xumengba.domain.Image;
import com.xumengba.exception.BizException;
import com.xumengba.response.ResponseResult;
import com.xumengba.response.StatusCode;
import com.xumengba.service.ImageService;
import com.xumengba.service.UploadFileService;
import com.xumengba.utils.UploadUtils;



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
	
	// 图片上传请求
	@RequestMapping(value = "/upload.action", method = RequestMethod.POST)
	public ResponseResult upload(@RequestParam(value = "imageFile", required = true) MultipartFile imageFile,
			Integer imageSeq,String imageTitle, String categoryId, String memo) throws IOException {

		// 1、校验是否有上传文件
		if (imageFile == null || imageFile.isEmpty()) {
			throw new BizException(StatusCode.UPLOAD_FILE_CONTENT_BLANK);
		}

		// 2、获取原始文件名
		String fileName = UploadUtils.getSimpleFileName(imageFile.getOriginalFilename());

		// 4、调用文件上传服务根据获取上传的文件路径
		String saveFilePath = uploadFileService.upload(fileName, imageFile.getBytes(),null);

		Image image = new Image();
		image.setImageSeq(imageSeq);
		image.setImageTitle(imageTitle);//图片标题
		image.setCategoryId(categoryId);//图片类别id
		image.setImageUrl(saveFilePath); // 图片路径
		image.setMemo(memo);//图片描述
		Date date = new Date();
		image.setCreatedTime(date); // 图片抓拍
		image.setUpdatedTime(date);
		image.setDeleted(true); // 不删除

		// 保存抓拍图片
		imageService.save(image);
		
		return ResponseResult.ok(image);
	}

}
