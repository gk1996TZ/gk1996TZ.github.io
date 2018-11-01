package com.muck.front.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.PrivilegeGroup;
import com.muck.helper.QueryHelper;
import com.muck.page.PageView;
import com.muck.request.PrivilegeGroupForm;
import com.muck.response.ResponseResult;
import com.muck.service.PrivilegeGropuService;

@RestController("FrontPrivilegeGroupController")
@RequestMapping(value = "/front/PrivilegeGroup/")
public class PrivilegeGroupController extends BaseController {

	@Autowired
	private PrivilegeGropuService privilegeGroupService;

	/**
	 * 根据名字模糊查询返回分页数据
	 * 
	 */
	@RequestMapping(value = "queryByRoughlyName.action")
	public ResponseResult queryByRoughlyName(PrivilegeGroupForm privilegeGroupForm) {

		QueryHelper queryHelper = new QueryHelper();
		if (StringUtils.isNotBlank(privilegeGroupForm.getPrivilegeGroupName())) {
			String name = privilegeGroupForm.getPrivilegeGroupName();
			queryHelper.addCondition("%" + name + "%", "privilege_group_name like 's%'", false);
		}
		queryHelper.addCondition(1, "deleted=d%", false);
		PageView<PrivilegeGroup> pageView = privilegeGroupService.queryPageData(privilegeGroupForm.getPageNum(),
				privilegeGroupForm.getPageSize(), queryHelper.getWhereSQL(), queryHelper.getWhereParams(), null);
		if (pageView == null) {
			return ResponseResult.notFound();
		}

		return ResponseResult.ok(pageView);
	}

	/**
	 * 查询所有权限组
	 */
	@RequestMapping(value = "queryAll.action", method = RequestMethod.GET)
	public ResponseResult queryAll() {

		PageView<PrivilegeGroup> pageView = privilegeGroupService.queryPageData();
		;

		return ResponseResult.ok(pageView);
	}
}
