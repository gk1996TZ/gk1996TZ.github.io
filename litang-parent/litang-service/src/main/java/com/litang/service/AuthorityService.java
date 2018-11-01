package com.litang.service;

import java.util.List;

import com.litang.domain.Authority;

public interface AuthorityService extends BaseService<Authority> {
	
	public List<Authority> queryAll();
	
	public List<Authority> queryPrivilegeByDeep();

}
