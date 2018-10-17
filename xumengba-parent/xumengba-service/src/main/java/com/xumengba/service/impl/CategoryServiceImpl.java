package com.xumengba.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.Category;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.mapper.CategoryMapper;
import com.xumengba.service.CategoryService;
import com.xumengba.utils.CollectionUtils;
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

	

	@Autowired
	private CategoryMapper categoryMapper;
	

//	@Override
//	public List<Category> queryTopCategorys() {
//		List<Category> categories=categoryMapper.queryTopCategory();
//		return CollectionUtils.toList(categories);
//	}

//	//根据父类id查询子类
//	public List<Category> queryCategorysByParentId(String parentId) {
//		
//		List<Category> categories=categoryMapper.querySubCategorysById(parentId);
//		if (categories != null && !categories.isEmpty()) {
//			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
//			List<Category> allAreas = new ArrayList<Category>();
//			allAreas.addAll(categories); // 首先将所有的父节点添加到总的结果集
//			deep(allAreas, categories);
//			return allAreas;
//		}
//		
//		return CollectionUtils.toList(categories);
//	}
	
	
//	//递归查询
//	private void deep(List<Category> allCategories, List<Category> parentCategories){
//		//查询下面的子分类
//		for(Category category:parentCategories){
//			List<Category>subCategories=this.queryCategorysByParentId(category.getId());
//			if(subCategories!=null&&subCategories.size()>0){
//				deep(allCategories, subCategories);
//				category.setSubCategories(subCategories);
//			}
//		}
//		
//	}

	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	public Category queryByName(String categoryName) {
		return categoryMapper.queryByName(categoryName);
	}

	@Override
	protected BaseMapper<Category> getMapper() {
		return categoryMapper;
	}

//	@Override
//	public List<Category> queryByDeep() {
//		// 1、首先查询所有的一级分类
//		List<Category> topCategories = this.queryTopCategorys();
//		if (topCategories != null && !topCategories.isEmpty()) {
//			// 创建一个总的结果集(这个总的结果集就包括了父节点和子节点)
//			List<Category> allAreas = new ArrayList<Category>();
//			allAreas.addAll(topCategories); // 首先将所有的父节点添加到总的结果集
//			deep(allAreas, topCategories);
//			return allAreas;
//		}
//		return CollectionUtils.toList(topCategories);
//		
//	}

//	@Override
//	public String querySubCategoryIdsByParentId(String parentId) {
//		return categoryMapper.getSubCategoryIdsByParentId(parentId);
//	}

}
