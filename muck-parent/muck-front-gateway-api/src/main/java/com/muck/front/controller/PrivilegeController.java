package com.muck.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Privilege;
import com.muck.page.PageView;
import com.muck.response.ResponseResult;
import com.muck.service.PrivilegeService;

@RestController("FrontPrivilegeController")
@RequestMapping("/front/privilege/")
public class PrivilegeController extends BaseController {
	
	@Autowired
	private PrivilegeService privilegeService;
	
	/**
	 * 根据权限id查询权限
	 * */
	@RequestMapping(value="queryPrivilegeById.action",method=RequestMethod.GET)
	public ResponseResult queryPrivilegeById(String privilegeId){
		Privilege privilege=privilegeService.queryById(privilegeId);
		if(privilege == null){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(privilege);
	}
	
	/***
	* @Description: 查询顶级权限(一级权限)
	 */
	@RequestMapping(value = "queryTopPrivileges.action" , method = RequestMethod.GET)
	public ResponseResult queryTopPrivileges(){
		
		List<Privilege> privileges = privilegeService.queryTopPrivileges();
		if(privileges == null || privileges.isEmpty()){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(privileges);
	}
	
	/***
	* @Description: 根据父权限id查询所有的子权限
	 */
	@RequestMapping(value = "querySubPrivilegesByParentId.action" , method = RequestMethod.GET)
	public ResponseResult querySubPrivilegesByParentId(String parentId){
		
		List<Privilege> privileges = privilegeService.querySubPrivilegesByParentId(parentId);
		
		if(privileges == null || privileges.isEmpty()){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(privileges);
	}
	
	/***
	 * 
	* @Description: 查询所有的权限
	*/
	@RequestMapping(value = "queryAll.action" , method = RequestMethod.GET)
	public ResponseResult queryAll(){
		
		PageView<Privilege> pageView = privilegeService.queryPageData();;

		return ResponseResult.ok(pageView);
	}
	
	
	

}
