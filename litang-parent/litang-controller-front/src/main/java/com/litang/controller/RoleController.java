package com.litang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.litang.domain.Role;
import com.litang.exception.base.BizException;
import com.litang.helper.QueryHelper;
import com.litang.request.RoleForm;
import com.litang.response.ResponseResult;
import com.litang.response.StatusCode;
import com.litang.service.RoleService;

@RestController("FrontRoleController")
@RequestMapping("/front/role")
public class RoleController extends BaseController{

	@Autowired
	private RoleService roleService;
	/**
	 * 添加角色
	 * @param roleForm 传入一个需要被添加的角色
	 * @return 返回添加的操作信息
	 */
	@RequestMapping("save.action")
	public ResponseResult save(RoleForm roleForm){
		//封装需要添加的角色信息
		Role role = new Role();
		role.setRoleName(roleForm.getRoleName());
		roleService.insertRoleWithAuthority(role, roleForm.getAuthorityIds());
		
		return ResponseResult.ok(role.getIdRow());
	}
	
	
	/**
	 * 根据id查询角色
	 * */
	@RequestMapping(value="queryRoleById.action",method=RequestMethod.GET)
	public ResponseResult queryRoleById(String roleId){
		
		Role role=roleService.queryById(roleId);
		if(role==null){
			return ResponseResult.notFound();
		}
		
		return ResponseResult.ok(role);
	}
	
	/**
	 * 根据id修改权限
	 * */
	@RequestMapping(value="editById.action",method=RequestMethod.POST)
	public ResponseResult editById(RoleForm roleForm){
		//构建基本信息
		Role role=new Role();
		String roleId=roleForm.getRoleId();
		String roleName=roleForm.getRoleName();
		role.setId(roleId);
		role.setRoleName(roleName);
		
		roleService.updateRole(role, roleForm.getAuthorityIds());
		
		return ResponseResult.ok();
	}
	
	/**
	 * 根据id删除角色
	 * 真实删除
	 * */
	@RequestMapping(value="deleteById.action",method=RequestMethod.GET)
	public ResponseResult deleteById(String roleId){
		
		//首先需要判断该角色有没有用户在使用
		int num=roleService.queryUserRoleByRoleId(roleId);
		if(num!=0){
			throw new BizException(StatusCode.ROLE_USED_NOW);
		}
		//说明该角色可以删除
		roleService.deleteByIdReal(roleId);
		return ResponseResult.ok();
		
	}
	


	/**
	 * 查询角色列表
	 * 并封装权限
	 * */
	@RequestMapping("queryRoleAll.action")
	public ResponseResult queryRoleAll(){
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "tr.deleted = %d",false);
		List<Role> listRole = roleService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		return ResponseResult.ok(listRole);
	}
}
