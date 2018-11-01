package com.muck.admin.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.annotation.Logger;
import com.muck.domain.CarGroup;
import com.muck.domain.Company;
import com.muck.exception.base.BizException;
import com.muck.helper.QueryHelper;
import com.muck.request.CarGroupForm;
import com.muck.response.CarGroupTree;
import com.muck.response.ResponseResult;
import com.muck.response.StatusCode;
import com.muck.service.CarGroupService;
import com.muck.service.CompanyService;

/**
 * @Description: 车辆组controller
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月29日 下午10:43:35
 */
@RestController("AdminCarGroupController")
@RequestMapping("/admin/carGroup")
public class CarGroupController extends BaseController{

	@Autowired
	private CarGroupService carGroupService;
	@Autowired
	private CompanyService companyService;
	/**
	 * @Description: 添加车辆组操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午2:40:34
	 * @param: carGroupForm 提交的车辆组数据
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("save.action")
	@Logger(operatorModel = "车辆组管理", operatorDesc = "添加车辆组")
	public ResponseResult save(CarGroupForm carGroupForm){
		if(carGroupService.queryByCarGroupName(carGroupForm.getGroupName()) != null){
			throw new BizException(StatusCode.CAR_GROUP_NAME_EXISTS);
		}
		CarGroup carGroup = new CarGroup();
		String parentId = carGroupForm.getParentId();
		if(!StringUtils.isBlank(parentId)){
			carGroup.setParentId(parentId);
		}
		carGroup.setGroupCode(carGroupForm.getGroupCode());
		carGroup.setGroupName(carGroupForm.getGroupName());
		carGroup.setCompanyId(carGroupForm.getCompanyId());
		String companyId = carGroupForm.getCompanyId();
		if(!StringUtils.isBlank(companyId)){
			Company company = companyService.queryById(companyId);
			if(company != null){
				carGroup.setCompanyName(company.getCompanyName());
			}
		}
		carGroupService.save(carGroup);
		return ResponseResult.ok(carGroup);
	}
	/**
	 * @Description: 根据id删除（逻辑删除）数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午2:49:37
	 * @param:carGroupId 传入一个车辆组id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	@Logger(operatorModel = "车辆组管理", operatorDesc = "根据id逻辑删除区域")
	public ResponseResult deleteById(String carGroupId){
		carGroupService.deleteById(carGroupId);;
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id删除（真实删除）数据的操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午2:49:37
	 * @param:carGroupId 传入一个车辆组id
	 * @return:ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String carGroupId){
		carGroupService.deleteByIdReal(carGroupId);;
		return ResponseResult.ok();
	}
	/**
	 * @Description: 修改车辆组信息操作
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午2:55:34
	 * @param: carGroupForm 提交的车辆组数据
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("edit.action")
	public ResponseResult edit(CarGroupForm carGroupForm){
		if(carGroupService.queryByCarGroupName(carGroupForm.getGroupName()) != null){
			throw new BizException(StatusCode.CAR_GROUP_NAME_EXISTS);
		}
		CarGroup carGroup = new CarGroup();
		String parentId = carGroupForm.getParentId();
		if(!StringUtils.isBlank(parentId)){
			carGroup.setParentId(parentId);
		}
		carGroup.setId(carGroupForm.getCarGroupId());
		carGroup.setGroupCode(carGroupForm.getGroupCode());
		carGroup.setGroupName(carGroupForm.getGroupName());
		carGroup.setCompanyId(carGroupForm.getCompanyId());
		String companyId = carGroupForm.getCompanyId();
		if(!StringUtils.isBlank(companyId)){
			Company company = companyService.queryById(companyId);
			if(company != null){
				carGroup.setCompanyName(company.getCompanyName());
			}
		}
		carGroupService.editById(carGroup);
		return ResponseResult.ok(carGroup);
	}
	/**
	 * @Description: 获取车辆组树
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月29日  下午11:17:04
	 * @return:ResponseResult 返回车辆组树
	 */
	@RequestMapping(value = "queryCarGroupTree.action" , method = RequestMethod.GET)
	public ResponseResult queryCarGroupTree(String companyId){
		List<CarGroupTree> listCarGroupTree = new ArrayList<CarGroupTree>();
		CarGroupTree carGroupTree = new CarGroupTree();
		List<Company> list=carGroupService.queryCarGroupTree(companyId);
		carGroupTree.setRootName("根节点");
		carGroupTree.setListCompany(list);
		listCarGroupTree.add(carGroupTree);
		//		for(Company cp:list){
//			List<CarGroup> carGrop=cp.getCarGroups();
//			if(carGrop.size()==1&&carGrop.get(0).getId()==null){
//				cp.setCarGroups(null);
//			}
//		}
//		System.out.println(list);
		return ResponseResult.ok(listCarGroupTree);
	}
	/**
	 * @Description: 根据车辆组id获取车辆列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月30日  上午11:45:06
	 * @param:@param carGroupId 传入一个车辆组id
	 * @return:ResponseResult 返回车辆组里的车辆列表
	 */
	@RequestMapping("queryCarByCarGroupId.action")
	public ResponseResult queryCarByCarGroupId(String carGroupId){
		return ResponseResult.ok(carGroupService.queryCarByGroupId(carGroupId));
	}
	/**
	 * @Description: 查询车辆组列表
	 * @version:v1.0.0
	 * @author:朱俊亮
	 * @date:2018年5月31日  下午3:07:54
	 * @return:ResponseResult 返回车辆组列表
	 */
	@RequestMapping("queryCarGroups.action")
	public ResponseResult queryCarGroups(CarGroupForm carGroupFrom){
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1, "deleted = %d", false)
		.addCondition(carGroupFrom.getCompanyId(), "company_id = %d", true);
		return ResponseResult.ok(carGroupService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}
}
