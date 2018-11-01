package com.muck.admin.controller;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.PrivilegeGroup;
import com.muck.exception.base.BizException;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.request.PrivilegeGroupForm;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.AccreditService;
import com.muck.service.PrivilegeGropuService;
import com.muck.service.PrivilegeGroupPrivilegeService;

/**
* @Description: 权限组
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月24日 下午4:43:11
 */
@RestController("AdminPrivilegeGroupController")
@RequestMapping("/admin/privilegegroup")
public class PrivilegeGroupController extends BaseController{

	@Autowired
	private PrivilegeGropuService privilegeGropuService;
	
	@Autowired
	private PrivilegeGroupPrivilegeService privilegeGroupPrivilegeService;
	
	@Autowired
	AccreditService accreditService;
	
	/**
	* @Description: 增加权限组
	* @author: 展昭
	* @date: 2018年4月24日 下午4:44:09
	*/
	@RequestMapping(value = "save.action" , method = RequestMethod.POST)
	public ResponseResult save(PrivilegeGroupForm privilegeGroupForm,String[] privilegeIds){
		
		// 第一步、组装基本信息
		PrivilegeGroup privilegeGroup = new PrivilegeGroup();
		privilegeGroup.setPrivilegeGroupDegree(privilegeGroupForm.getPrivilegeGrouDegree());//权限组等级
		privilegeGroup.setPrivilegeGroupName(privilegeGroupForm.getPrivilegeGroupName());// 权限组名
		privilegeGroup.setMemo(privilegeGroupForm.getMemo());
		// 第二步、保存添加
		privilegeGropuService.save(privilegeGroup);
		//为权限组添加权限
		if(privilegeIds!=null&&privilegeIds.length>0){
			privilegeGroupPrivilegeService.addBatch(privilegeGroup.getId(), Arrays.asList(privilegeIds));
		}
		return ResponseResult.ok();
	}
	
	/**
	* @Description: 修改权限组
	* @author: 展昭
	* @date: 2018年4月24日 下午4:44:09
	*/
	@RequestMapping( value = "editById.action" , method = RequestMethod.POST)
	public ResponseResult editById(PrivilegeGroupForm privilegeGroupForm,String[] privilegeIds){
		
		// 第一步、根据id查询新
		PrivilegeGroup privilegeGroup = privilegeGropuService.queryById(privilegeGroupForm.getPrivilegeGroupId());
		
		// 第二步、设置信息
		privilegeGroup.setPrivilegeGroupDegree(privilegeGroupForm.getPrivilegeGrouDegree());//权限组等级
		privilegeGroup.setPrivilegeGroupName(privilegeGroupForm.getPrivilegeGroupName());// 权限组名
		privilegeGroup.setMemo(privilegeGroupForm.getMemo());
		
		// 第二步、保存修改
		privilegeGropuService.editById(privilegeGroup);
		
		//首先清空该权限组所有权限,在添加
		privilegeGroupPrivilegeService.deletedByPrivielgeGroupId(privilegeGroupForm.getPrivilegeGroupId());
		
		if(privilegeIds!=null&&privilegeIds.length>0){
			privilegeGroupPrivilegeService.addBatch(privilegeGroup.getId(), Arrays.asList(privilegeIds));
		}
		
		return ResponseResult.ok();
	}
	
	/**
	* @Description: 查询所有的权限组,不包含权限组下面的权限,包括模糊查询
	* @author: 展昭
	* @date: 2018年4月25日 上午10:51:32
	 */
	@RequestMapping(value = "queryAll.action" , method = RequestMethod.GET)
	public ResponseResult queryAll(Long pageNum,Long pageSize,String roughName){
		
		QueryHelper queryHelper=new QueryHelper();
		if(StringUtils.isNotBlank(roughName)){
		queryHelper.addCondition("%"+roughName+"%", "tpg.privilege_group_name like '%s'", false);
		}
		//创建一个查询对象
		queryHelper.addCondition(1, " tpg.deleted = %d", false);
		
		//查询分页数据
		PageView<PrivilegeGroup> pageView = privilegeGropuService.queryPageData(pageNum,
				pageSize,queryHelper.getWhereSQL(),queryHelper.getWhereParams(),null);
		if(pageView==null){
			return ResponseResult.notFound();
			}

		return ResponseResult.ok(pageView);
	}
	
	/**
	 * 根据权限组id查询单个详情
	 * */
	@RequestMapping(value="queryById.action",method=RequestMethod.GET)
	public ResponseResult queryById(String privilegeGroupId){
		// 第一步、根据id查询新
		if(StringUtils.isNotBlank(privilegeGroupId)){
			PrivilegeGroup privilegeGroup = privilegeGropuService.queryById(privilegeGroupId);
			if(privilegeGroup==null){
				return ResponseResult.notFound();
			}
			return ResponseResult.ok(privilegeGroup);
		}
		throw new BizException(StatusCode.PRIVILEGE_GROUP_ID_BLANK);
		
	}
	
	/**
	 * 根据权限组id删除权限组
	 * */
	@RequestMapping(value="deleteById.action",method=RequestMethod.GET)
	public ResponseResult deleteById(String privilegeGroupId){
		
		if(StringUtils.isNotBlank(privilegeGroupId)){
			//首先查询这个权限组有没有被赋予给用户
			int count=accreditService.queryByPrivilegeGroupId(privilegeGroupId);
			
			if(count!=0){
				throw new RuntimeException("该权限组有用户在使用无法删除");
			}
			//删除对应的权限组
			privilegeGropuService.deleteByIdReal(privilegeGroupId);
			
			//根据权限组id清空权限组所有权限
			privilegeGroupPrivilegeService.deletedByPrivielgeGroupId(privilegeGroupId);
			return ResponseResult.ok();
		}
		throw new BizException(StatusCode.PRIVILEGE_GROUP_ID_BLANK);
		
	}
	
	//----------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
}
