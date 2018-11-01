package com.muck.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Color;
import com.muck.response.ResponseResult;
import com.muck.service.ColorService;

/**
 *	颜色Controller 
*/
@RestController("AdminColorController")
@RequestMapping("/admin/color")
public class ColorController extends BaseController{

	@Autowired
	private ColorService colorService;
	
	/**
	 * 	查询所有的颜色
	*/
	@RequestMapping(value = "queryAllColors.action" , method = RequestMethod.GET)
	public ResponseResult queryAllColors(){
		
		List<Color> colors = colorService.queryData();
		
		return ResponseResult.ok(colors);
	}
	
}
