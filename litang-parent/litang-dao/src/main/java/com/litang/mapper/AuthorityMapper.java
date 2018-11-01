package com.litang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.litang.domain.Authority;

@Repository
public interface AuthorityMapper extends BaseMapper<Authority> {

	// 查询所有权限
	public List<Authority> getAll();

	// 查询所有顶级权限
	public List<Authority> selectTopAuthorites();

	// 根据父权限查询子权限
	public List<Authority> selectSubAuthoritesByParentId(@Param("parentId")String parentId);
}
