package com.muck.front.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 区域controller
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月17日 下午4:36:51
 */
@RestController
@RequestMapping("/front/area")
public class AreaController extends BaseController{

	/*@RequestMapping(value = "/sss" , method = RequestMethod.POST)
	public void xxx(@RequestParam(value = "file")MultipartFile file){
		
		try{
			String fileName = file.getOriginalFilename();
			
			byte[] datas = file.getBytes();
			
			uploadFileService.upload(fileName, datas, null);
		}catch (Exception e) {
			throw new BizException(new StatusCode());
		}
	}*/
	
	
	
	
	
}
