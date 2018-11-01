package com.muck.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Car;
import com.muck.domain.CarGroup;
import com.muck.domain.Company;
import com.muck.handler.IdTypeHandler;
import com.muck.mapper.BaseMapper;
import com.muck.mapper.CarGroupMapper;
import com.muck.service.CarGroupService;

/**
 * @Description: 车辆组Service实现
 * @version: v1.0.0
 * @author: 朱俊亮
 * @date: 2018年5月30日 上午10:37:32
 */
@Service
public class CarGroupServiceImpl extends BaseServiceImpl<CarGroup> implements CarGroupService{

	@Autowired
	private CarGroupMapper carGroupMapper;
	
	@Override
	public List<Company> queryCarGroupTree(String companyId) {
		//获取第一级车辆组
		List<Company> companys = carGroupMapper.queryCarGroupTree(companyId);
		StringBuilder carGroupIds = new StringBuilder("");
		//  遍历企业列表
		if(companys != null){
			for(Company company : companys){
				List<CarGroup> carGroups = company.getCarGroups();
				if(carGroups != null){
					//遍历企业下面的一级车辆组
					for(CarGroup carGroup : carGroups){
						if(!StringUtils.isBlank(carGroup.getId())){
							carGroupIds.append(IdTypeHandler.decode(carGroup.getId()));
							carGroupIds.append(",");
						}
					}
				}
			}
		}
		//截取删除最后一个逗号
		if(carGroupIds.length() > 0){
			carGroupIds.deleteCharAt(carGroupIds.length()-1);
			//根据车辆组ids查询第二级的车辆组
			List<CarGroup> carGroupsAccess = carGroupMapper.queryCarGroupAccess(carGroupIds.toString());
			if(carGroupsAccess != null){
				for(Company company : companys){
					List<CarGroup> carGroupsStair = company.getCarGroups();
					if(carGroupsStair != null){
						//遍历企业下面的一级车辆组
						for(CarGroup carGroupStair : carGroupsStair){
							//声明一个存放二级车辆组的集合
							List<CarGroup> carGroupStairs = new ArrayList<CarGroup>();
							if(!StringUtils.isBlank(carGroupStair.getId())){
								//遍历二级车辆组
								for(CarGroup carGroupAccess : carGroupsAccess){
									if(!StringUtils.isBlank(carGroupAccess.getParentId())){
										//如果二级车辆组的父级id与一级车辆组的id相同，则将该二级车辆组放到相应的二级车辆组容器中
										if(carGroupAccess.getParentId().equals(carGroupStair.getId())){
											carGroupStairs.add(carGroupAccess);
										}
									}
								}
							}
							//将第二级车辆组放到第一级车辆组里
							carGroupStair.setCarGroups(carGroupStairs);
						}
					}
				}
			}
		}
		return companys;
	}
	@Override
	public List<Car> queryCarByGroupId(String carGroupId) {
		return carGroupMapper.queryCarByGroupId(carGroupId);
	}

	@Override
	public CarGroup queryByCarGroupName(String carGroupName) {
		return carGroupMapper.queryByCarGroupName(carGroupName);
	}
	// ----------------------------------------------------
	@Override
	protected BaseMapper<CarGroup> getMapper() {
		return carGroupMapper;
	}
	@Override
	protected String getFields() {
		return " * ";
	}
}
