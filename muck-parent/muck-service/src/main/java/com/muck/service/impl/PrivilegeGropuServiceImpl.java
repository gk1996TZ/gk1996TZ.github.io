package com.muck.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.PrivilegeGroup;
import com.muck.exception.base.BizException;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.PrivilegeGroupMapper;
import com.muck.page.PageView;
import com.muck.response.StatusCode;
import com.muck.service.PrivilegeGropuService;

/**
 * @Description: 权限组设置
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月24日 下午4:32:02
 */
@Service
public class PrivilegeGropuServiceImpl extends BaseServiceImpl<PrivilegeGroup> implements PrivilegeGropuService {

	@Autowired
	private PrivilegeGroupMapper privilegeGroupMapper;

	/**
	 * 增加全选组
	 */
	public void save(PrivilegeGroup privilegeGroup) {

		// 1、增加之前判断此权限组是否存在...根据权限名称判断
		Long count = privilegeGroupMapper.validateExistPrivilegeGroup(privilegeGroup.getPrivilegeGroupName());

		// 2、如果大于0则表示存在
		if (count != null && count > 0) {
			throw new BizException(StatusCode.PRIVILEGE_GROUP_HAS_EXIST_ADD);
		}
		// 3、否则就添加
		super.save(privilegeGroup);
	}

	/**
	 * 修改权限
	 */
	public void editById(PrivilegeGroup privilegeGroup) {

		// 1、增加之前判断此权限组是否存在...根据权限名称判断
		Long count = privilegeGroupMapper.validateExistPrivilegeGroup(privilegeGroup.getPrivilegeGroupName());

		// 2、如果大于0则表示存在
		if (count != null && count > 0) {
			throw new BizException(StatusCode.PRIVILEGE_GROUP_HAS_EXIST_EDIT);
		}
		super.editById(privilegeGroup);
	}

	@Override
	public PageView<PrivilegeGroup> queryPageData(Long currentPageNum, Long pageSize, String whereSQL,
			List<Object> whereParams, LinkedHashMap<String, String> orderBy) {

		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";

		// 用来查询数据总数的sql
		String sql = "select count(tpg.id) from t_privilege_group tpg where 1 = 1 " + whereSQL;

		String selectSQL = "select "
				+ "tpg.id, tpg.privilege_group_name,tpg.privilege_group_degree, tpg.memo as tpgmemo,"
				+ "tpg.created_time, tpg.updated_time,"
				+ "tp.id as tpId,tp.privilege_name,tp.privilege_url,tp.is_common,tp.privilege_model,tp.memo as tpmemo "
				+ "from " + "t_privilege_group tpg "
				+ "left join t_privilegegroup_privilege tpp on tpg.id=tpp.privilege_group_id "
				+ "left join t_privilege tp on tpp.privilege_id=tp.id " + "where 1 = 1 " + whereSQL + limit;

		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = privilegeGroupMapper.selectTotalRecoreds(sql);

		PageView<PrivilegeGroup> pageView = new PageView<>(count, currentPageNum, pageSize);

		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		List<PrivilegeGroup> privilegeGroups = privilegeGroupMapper.selectPageData(selectSQL);

		pageView.setDatas(privilegeGroups);

		return pageView;
	}

	// -------------------------------------

	protected BaseMapper<PrivilegeGroup> getMapper() {
		return privilegeGroupMapper;
	}

	@Override
	protected String getFields() {
		return "id, privilege_group_name";
	}
}
