package com.litang.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litang.domain.Role;
import com.litang.mapper.BaseMapper;
import com.litang.mapper.RoleMapper;
import com.litang.service.RoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public boolean insertRoleWithAuthority(Role role, String[] authorityIds) {

		// 首先插入数据库返回角色id
		boolean falg = super.insert(role);

		Integer midCount = null;
		if (falg == true) {
			String roleId = role.getId();

			// 批量插入中间表
			midCount = roleMapper.insertRoleAuthority(roleId, authorityIds);
		}
		// 批量操作受影响的行数
		int lenth = authorityIds.length;

		return midCount == lenth;
	}

	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	protected BaseMapper<Role> getMapper() {
		return roleMapper;
	}

	@Override
	public void updateRole(Role role, String[] authorityIds) {

		// 设置修改时间
		role.setUpdatedTime(new Date());
		roleMapper.editById(role);

		// 维护中间表数据
		// 首先通过角色id删除中间表
		roleMapper.deleteRoleAuthorityByRoleId(role.getId());

		// 当id数组为空默认表示删除该角色所有的权限
		if (authorityIds != null && authorityIds.length > 0) {
			// 在将传入的角色对应权限维护
			roleMapper.insertRoleAuthority(role.getId(), authorityIds);
		}

	}

	@Override
	public boolean deleteByIdReal(String roleId) {

		// 首先删除该角色对应的权限
		roleMapper.deleteRoleAuthorityByRoleId(roleId);

		// 执行删除
		return super.deleteByIdReal(roleId);
	}

	@Override
	public int queryUserRoleByRoleId(String roleId) {

		return roleMapper.selectUserRoleByRoleId(roleId);
	}

	@Override
	public List<Role> queryData(String whereSQL, List<Object> whereParams) {

		// 查询条件
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 拼接sql语句
		String selectSQL = " SELECT " + "tr.id, tr.role_name, tr.deleted, tr.created_time, tr.updated_time,"
				+ "ta.id as aId,ta.authority_name,ta.authority_url,ta.authority_url,ta.is_common " + "from "
				+ "t_role tr " + "left join t_role_authority tra " + "on tr.id=tra.role_id "
				+ "left join t_authority ta " + "on tra.authority_id=ta.id where 1=1" + whereSQL;
		// 第五步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);

	}

	@Override
	public Role queryRoleBySimple(String roleId) {

		Role role = roleMapper.queryByIdSimple(roleId);

		return role;
	}

	@Override
	public Role getOrdinaryUserRole() {
		return roleMapper.getOrdinaryUserRole();
	}
}
