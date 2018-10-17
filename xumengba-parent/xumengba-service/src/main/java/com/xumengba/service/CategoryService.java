package com.xumengba.service;

import java.util.List;

import com.xumengba.domain.Category;

public interface CategoryService extends BaseService<Category>{
	
	
//	//查询所有一级分类
//	public List<Category>queryTopCategorys();
	
//	//根据父类id查询所有的子类
//	public List<Category>queryCategorysByParentId(String parentId);
	
//	//递归查询所有分类
//	public List<Category>queryByDeep();
	
//	public String querySubCategoryIdsByParentId(String parentId);
	/**
	 * @Description: 根据类别名称获取类别id
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 上午9:44:22
	 * @param: categoryName 类别名称
	 * @return: Category 类别
	 */
	public Category queryByName(String categoryName);

}
