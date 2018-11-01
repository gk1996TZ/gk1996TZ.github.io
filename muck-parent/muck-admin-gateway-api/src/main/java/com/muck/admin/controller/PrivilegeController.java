package com.muck.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Privilege;
import com.muck.page.PageView;
import com.muck.request.PrivilegeForm;
import com.muck.response.ResponseResult;
import com.muck.service.PrivilegeService;
import com.muck.utils.CollectionUtils;

/**
* @Description: 权限Controller
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月27日 下午2:23:49
 */
@RestController("AdminPrivilegeController")
@RequestMapping("/admin/privilege")
public class PrivilegeController extends BaseController{

	@Autowired
	private PrivilegeService privilegeService;
	
	/***
	* @Description: 增加权限
	* @author: 展昭
	* @date: 2018年4月27日 下午2:27:49
	 */
	@RequestMapping( value = "save.action" , method = RequestMethod.POST)
	public ResponseResult save(PrivilegeForm privilegeForm){
		
		// 第一步、组装基本信息
		Privilege privilege = new Privilege();
		privilege.setPrivilegeName(privilegeForm.getPrivilegeName());
		privilege.setPrivilegeUrl(privilegeForm.getPrivilegeUrl());
		privilege.setCommon(privilegeForm.isCommon());
		privilege.setPrivilegeModel(privilegeForm.getPrivilegeModel());
		privilege.setMemo(privilegeForm.getMemo());
		
		// 第二步、保存添加
		privilegeService.save(privilege);
		
		return ResponseResult.ok();
	}
	
	/**
	* @Description: 修改权限
	* @author: 展昭
	* @date: 2018年4月24日 下午4:44:09
	*/
	@RequestMapping( value = "editById.action" , method = RequestMethod.POST)
	public ResponseResult editById(PrivilegeForm privilegeForm){
		
		// 第一步、根据id查询
		Privilege privilege = privilegeService.queryById(privilegeForm.getPrivilegeId());
		
		// 第二步、设置信息
		privilege.setPrivilegeName(privilegeForm.getPrivilegeName());
		privilege.setPrivilegeUrl(privilegeForm.getPrivilegeUrl());
		privilege.setCommon(privilegeForm.isCommon());
		privilege.setPrivilegeModel(privilegeForm.getPrivilegeModel());
		privilege.setMemo(privilegeForm.getMemo());
		
		// 第二步、保存修改
		privilegeService.editById(privilege);
		
		return ResponseResult.ok();
	}
	
	/***
	* @Description: 根据权限id删除权限
	* @param: privilegeId:权限id
	* @author: 展昭
	* @date: 2018年4月27日 下午3:49:57
	 */
	@RequestMapping(value = "deleteById.action" , method = RequestMethod.GET)
	public ResponseResult deleteById(String privilegeId){
		
		privilegeService.deleteById(privilegeId);
		
		return ResponseResult.ok();
	}
	
	/***
	* @Description: 根据权限id查询权限
	* @param: privilegeId:权限id
	* @author: 展昭
	* @date: 2018年4月27日 下午3:49:57
	 */
	@RequestMapping(value = "queryById.action" , method = RequestMethod.GET)
	public ResponseResult queryById(String privilegeId){
		
		Privilege privilege = privilegeService.queryById(privilegeId);
		if(privilege == null){
			return ResponseResult.notFound();
		}
		return ResponseResult.ok(privilege);
	}
	
	/***
	* @Description: 查询顶级权限(一级权限)
	* @author: 展昭
	* @date: 2018年4月27日 下午4:49:03
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
	* @author: 展昭
	* @date: 2018年4月27日 下午5:28:08
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
	* @author: 展昭
	* @date: 2018年4月27日 下午3:05:44
	*/
	@RequestMapping(value = "queryAll.action" , method = RequestMethod.GET)
	public ResponseResult queryAll(){
		
		PageView<Privilege> pageView = privilegeService.queryPageData();;

		return ResponseResult.ok(pageView);
	}
	
	/***
	 * 
	* @Description: 权限树
	* @author: 甘坤
	*/
	@RequestMapping(value = "queryAllPrivilegeTree.action" , method = RequestMethod.GET)
	public ResponseResult queryAllPrivilegeTree(){
		
		//递归查询所有权限
		List<Privilege>privileges=privilegeService.queryPrivilegeByDeep();
		return ResponseResult.ok(CollectionUtils.toList(privileges));
	}
	
	
	
	
	
}
