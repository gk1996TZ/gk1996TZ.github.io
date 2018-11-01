package com.muck.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Manager;
import com.muck.domain.PrivilegeGroup;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.ManagerMapper;
import com.muck.mapper.ManagerPrivilegeGroupMapper;
import com.muck.mapper.PrivilegeGroupMapper;
import com.muck.service.ManagerService;
import com.muck.utils.MD5Utils;

/**
* @Description: 系统用户Service实现
* @version: v1.0.0
* @author: 展昭
* @date: 2018年4月16日 上午11:14:15
 */
@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager> 
								implements ManagerService {

	@Autowired
	private ManagerMapper managerMapper; // 用户mapper
	
	@Autowired
	private ManagerPrivilegeGroupMapper managerPrivilegeGroupMapper; // 权限组中间表管理
	
	@Autowired
	private PrivilegeGroupMapper privilegeGroupMapper; // 权限组管理

	/**
	 * 	为用户授权权限组
	 * 	managerIds:用户组ids
	 * 	privilegeGroupIds : 权限组ids
	*/
	public void authorizedPrivilegeGroup(String[] managerIds, String[] privilegeGroupIds) {
		
		// 1、首先把当前所选择的用户权限组给清空
		managerPrivilegeGroupMapper.deleteByManagerIds(managerIds);
		
		// 2、再为用户组赋予权限组
		if(privilegeGroupIds != null && privilegeGroupIds.length > 0){
			Map<String,String[]> map = new HashMap<String,String[]>();
			map.put("managerIds", managerIds);
			map.put("privilegeGroupIds", privilegeGroupIds);
			managerPrivilegeGroupMapper.insertBatch(map);
		}
	}
	

	/**
	 *	用户登录 
	*/
	public Manager login(String managerPhone, String managerPassword) {
		
		Map<String,String> conditions = new HashMap<String,String>();
		conditions.put("managerPhone", managerPhone);
		conditions.put("managerPassword", MD5Utils.encode(managerPassword)); // 一定要加密比较
		
		Manager manager = managerMapper.selectOneByCondition(conditions);
		
		return manager;
	}

	/**
	 * 	根据用户查询用户信息(用户所属企业,用户的权限组)
	*/
	public Manager queryManagerCenterInfo(Manager manager) {
		
		// 1、查询用户所属于的企业
		manager = managerMapper.selectById(manager.getId());
		
		// 2、查询用户所属于的权限组
		List<PrivilegeGroup> pgs = privilegeGroupMapper.selectByManagerId(manager.getId());
		
		manager.setPrivilegeGroups(pgs);
		
		return manager;
	}
	

	/**
	 * 	个人中心修改
	*/
	public void editManagerCenterInfo(Manager manager,String[] privilegeGropuIds) {
		
		// 1、更新用户
		managerMapper.updateById(manager);
		
		// 2、设置权限组
		this.authorizedPrivilegeGroup(new String[]{manager.getId()}, privilegeGropuIds);
	}
	
	@Override
	public List<Manager> queryByName(String managerName) {
		if(!StringUtils.isBlank(managerName)){
			managerName = "%"+managerName+"%";
		}
		return managerMapper.selectByName(managerName);
	}
	
	
	@Override
	public List<Manager> queryByPhone(String managerPhone) {
		if(!StringUtils.isBlank(managerPhone)){
			managerPhone = "%"+managerPhone+"%";
		}
		return managerMapper.selectByPhone(managerPhone);
	}
	
	//--------------------------
	


	@Override
	protected BaseMapper<Manager> getMapper() {
		return managerMapper;
	}
	
	
	// 姓名、手机号码、证件号码（身份证）、电子邮箱、备注、企  业、权限组
	@Override
	protected String getFields() {
		return "id,manager_name,manager_phone,manager_company_name,manager_site_name,manager_disposal_name,manager_area_name,manager_email,memo";
	}
}
