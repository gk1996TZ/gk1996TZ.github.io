package com.muck.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.domain.Area;
import com.muck.exception.base.BizException;
import com.muck.mapper.AreaMapper;
import com.muck.mapper.BaseMapper;
import com.muck.response.StatusCode;
import com.muck.service.AreaService;
import com.muck.utils.CollectionUtils;

/**
 * @Description: 区域Service
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年4月16日 上午11:17:51
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {
	@Autowired
	private AreaMapper areaMapper;

	/**
	 * 查询顶级区域(一级区域)
	 */
	public List<Area> queryTopAreas() {
		List<Area> areas = areaMapper.selectTopAreas();
		return CollectionUtils.toList(areas);
	}

	/**
	 * 根据父区域的id查询此区域下面所有的子区域
	 */
	public List<Area> querySubAreasByParentId(String parentId,Integer areaType) {
		if (StringUtils.isBlank(parentId)) {
			throw new BizException(StatusCode.AREA_ID_BLANK);
		}

		// 根据区域id查询一级子区域
		List<Area> areas = areaMapper.selectSubAreasByParentId(parentId,areaType);

		if (areas != null && !areas.isEmpty()) {
			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
			List<Area> allAreas = new ArrayList<Area>();
			allAreas.addAll(areas); // 首先将所有的父节点添加到总的结果集
			deep(allAreas, areas,areaType);
			return allAreas;
		}

		return CollectionUtils.toList(areas);
	}

	@Override
	public String querySubAreaIdsByParentId(String parentId) {
		return areaMapper.selectSubAreaIdsByParentId(parentId);
	}

	/**
	 * 根据区域id查询此区域下面的子区域的个数
	 */
	public Long querySubAreaCount(String areaId) {
		if (StringUtils.isBlank(areaId)) {
			throw new BizException(StatusCode.AREA_ID_BLANK);
		}

		Long count = areaMapper.selectSubAreaCount(areaId);

		return count == null ? 0L : count;
	}

	@Override
	public String querySubAreaCodesByParentCode(String parentCode) {
		return areaMapper.querySubAreaCodesByParentCode(parentCode);
	}

	/**
	 * @Description: 通过递归查询所有的区域(查询出来的是一颗树状结构的数)
	 * @author: 展昭
	 * @date: 2018年4月17日 下午5:08:36
	 */
	public List<Area> queryAreasByDeep(Integer areaType) {

		// 1、首先查询所有的一级分类
		List<Area> topAreas = this.queryTopAreas();
		if (topAreas != null && !topAreas.isEmpty()) {
			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
			List<Area> allAreas = new ArrayList<Area>();
			allAreas.addAll(topAreas); // 首先将所有的父节点添加到总的结果集
			deep(allAreas, topAreas,areaType);
			return allAreas;
		}
		return CollectionUtils.toList(topAreas);
	}

	// 递归查询
	private void deep(List<Area> allAreas, List<Area> parentAreas,Integer areaType) {
		// 循环遍历一级分类
		for (Area area : parentAreas) {
			// 拿着主分类去查询此分类下面的子分类
			List<Area> subAreas = this.querySubAreasByParentId(area.getId(),areaType);
			if (subAreas != null && !subAreas.isEmpty()) {
				deep(allAreas, subAreas,areaType);
				area.setChildAreas(subAreas);
			}
		}
	}

	@Override
	public Area queryByAreaCode(String areaCode) {
		return areaMapper.queryByAreaCode(areaCode);
	}

	/**
	 * 	
	*/
	public List<Area> queryAllAreas() {
		return areaMapper.selectAllAreas();
	}

	// -----------------------------------------
	protected BaseMapper<Area> getMapper() {
		return areaMapper;
	}

	@Override
	protected String getFields() {
		return "id,area_name,area_code,parent_code,created_time,updated_time";
	}
}
