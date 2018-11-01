package com.muck.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Privilege;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.PrivilegeMapper;
import com.muck.service.PrivilegeService;
import com.muck.utils.CollectionUtils;

/**
 * @Description: 权限service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月27日 下午2:24:53
 */
@Service
public class PrivilegeServiceImpl extends BaseServiceImpl<Privilege> implements PrivilegeService {

	@Autowired
	private PrivilegeMapper privilegeMapper; // 权限

	/***
	 * 查询顶级权限
	 */
	public List<Privilege> queryTopPrivileges() {

		List<Privilege> privileges = privilegeMapper.selectTopPrivileges();

		return CollectionUtils.toList(privileges);
	}

	/***
	 * 根据父区域id查询下面所有的子区域
	 */
	public List<Privilege> querySubPrivilegesByParentId(String parentId) {
		List<Privilege> privileges = privilegeMapper.selectSubPrivilegesByParentId(parentId);

		return CollectionUtils.toList(privileges);
	}

	/**
	 * @Description: 通过递归查询所有的权限(查询出来的是一颗树状结构的数)
	 * */
	public List<Privilege> queryPrivilegeByDeep() {
		//1.查询一级权限
		List<Privilege>topPrivileges=this.queryTopPrivileges();
		
		if(topPrivileges!=null&&!topPrivileges.isEmpty()){
			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
			List<Privilege>allPrivileges=new ArrayList<Privilege>();
			allPrivileges.addAll(topPrivileges); // 首先将所有的父节点添加到总的结果集
			deep(allPrivileges,topPrivileges);
			return allPrivileges;
		}
		return topPrivileges;
	}

	private void deep(List<Privilege> allPrivileges, List<Privilege> topPrivileges) {
		
		// 循环遍历一级分类
		for(Privilege privilege:topPrivileges){
			//查询一级分类下的子分类
			List<Privilege>subPrivileges=this.querySubPrivilegesByParentId(privilege.getId());
			if(subPrivileges!=null&&!subPrivileges.isEmpty()){
				deep(allPrivileges, subPrivileges);
				privilege.setChildPrivileges(subPrivileges);
			}
		}
		
	}

	// -----------------------------------

	@Override
	protected BaseMapper<Privilege> getMapper() {
		return privilegeMapper;
	}

	@Override
	protected String getFields() {
		return "id,privilege_name,privilege_url,is_common,privilege_model,memo";
	}

}
