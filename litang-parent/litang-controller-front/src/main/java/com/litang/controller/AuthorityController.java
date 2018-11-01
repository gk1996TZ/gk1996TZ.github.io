package com.litang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.litang.domain.Authority;
import com.litang.response.ResponseResult;
import com.litang.service.AuthorityService;

@RestController("FrontAuthorityController")
@RequestMapping("/front/authority/")
public class AuthorityController extends BaseController {
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 *前台获取前台列表 
	 * */
	@RequestMapping(value="getAllPrivileges.action",method=RequestMethod.GET)
	public ResponseResult getAllPrivileges(){
		List<Authority> authorities=authorityService.queryPrivilegeByDeep();
		return ResponseResult.ok(authorities);
	}
	

}
