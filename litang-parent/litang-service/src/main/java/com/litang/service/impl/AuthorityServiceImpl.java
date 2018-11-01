package com.litang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.Authority;
import com.litang.mapper.AuthorityMapper;
import com.litang.mapper.BaseMapper;
import com.litang.service.AuthorityService;
import com.litang.utils.CollectionUtils;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	protected BaseMapper<Authority> getMapper() {
		return authorityMapper;
	}
	/***
	 * 查询顶级权限
	 */
	public List<Authority> queryTopPrivileges() {

		List<Authority> privileges = authorityMapper.selectTopAuthorites();

		return CollectionUtils.toList(privileges);
	}

	/***
	 * 根据父区域id查询下面所有的子区域
	 */
	public List<Authority> querySubPrivilegesByParentId(String parentId) {
		List<Authority> privileges = authorityMapper.selectSubAuthoritesByParentId(parentId);

		return CollectionUtils.toList(privileges);
	}


	
	@Override
	public List<Authority> queryAll() {
		List<Authority> authorities=authorityMapper.getAll();
		//查询所有的一级权限
		List<Authority> Topauthorities=authorityMapper.selectTopAuthorites();
		
		
		
		return CollectionUtils.toList(authorities);
	}
	
	
	public List<Authority> queryPrivilegeByDeep() {
		//1.查询一级权限
		List<Authority>topPrivileges=this.queryTopPrivileges();
		
		if(topPrivileges!=null&&!topPrivileges.isEmpty()){
			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
			List<Authority>allPrivileges=new ArrayList<Authority>();
			allPrivileges.addAll(topPrivileges); // 首先将所有的父节点添加到总的结果集
			deep(allPrivileges,topPrivileges);
			return allPrivileges;
		}
		return topPrivileges;
	}

	private void deep(List<Authority> allPrivileges, List<Authority> topPrivileges) {
		
		// 循环遍历一级分类
		for(Authority privilege:topPrivileges){
			//查询一级分类下的子分类
			List<Authority>subPrivileges=this.querySubPrivilegesByParentId(privilege.getId());
			if(subPrivileges!=null&&!subPrivileges.isEmpty()){
				deep(allPrivileges, subPrivileges);
				privilege.setAuthorities(subPrivileges);
			}
		}
		
	}

	

}
