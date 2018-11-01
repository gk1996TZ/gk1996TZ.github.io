package com.litang.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litang.response.ResponseResult;
import com.litang.response.StatusCode;
import com.litang.service.PropertiesService;

@RestController("FrontClientUpdateController")
@RequestMapping("/front/client")
public class ClientUpdateController {
	
	@Autowired
	private PropertiesService propertiesService;
	
	@RequestMapping("update.action")
	public ResponseResult clientUpdate(String currentVersion){
		
		String updateLink = propertiesService.getUpdateLink();
		String lastVersion = propertiesService.getLastVersion();
		if(!StringUtils.isBlank(currentVersion)){
			Map<String,Object> map = new HashMap<String,Object>();
			//当前版本号
			map.put("currentVersion", currentVersion);
			//最新版本号
			map.put("lastVersion", lastVersion);
			//更新链接
			map.put("updateLink", updateLink);
			if(currentVersion.equals(lastVersion)){
				//是否更新
				map.put("isUpdate",false);
			}else {
				//是否更新
				map.put("isUpdate",true);
			}
			return ResponseResult.ok(map);
		}else {
			StatusCode statusCode = StatusCode.CURRENT_VERSION_NULL;
			return new ResponseResult(statusCode.getCode(),statusCode.getMessage());
		}
	}

}
