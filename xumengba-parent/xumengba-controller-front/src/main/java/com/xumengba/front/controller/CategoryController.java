package com.xumengba.front.controller;


import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Category;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.CategoryService;

@RestController("FrontCategoryController")
@RequestMapping("/front/category")
public class CategoryController extends BaseController {
	
	
	@Autowired
	private CategoryService categoryService;
//	/**查询所有分类
//	 * 递归
//	 * */
//	@RequestMapping(value="queryTopCategories.action",method=RequestMethod.GET)
//	public ResponseResult queryTopCategories(){
//		
//		List<Category>categories=categoryService.queryByDeep();
//		return ResponseResult.ok(CollectionUtils.toList(categories));
//	}
	
	/**
	 * @Description:查询所有分类带分页
	 * @author:甘坤
	 * @date: 2018年10月10日 下午3:44:39
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value = "queryAll.action", method = RequestMethod.GET)
	public ResponseResult queryAll(Long pageSize,Long pageNum) {
		QueryHelper queryHelper = new QueryHelper();

		queryHelper.addCondition(1, "deleted=%d", false);
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("created_time", "desc");
		PageView< Category> pageView= categoryService.queryPageData(pageNum, pageSize, queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}
	
	/**
	 * @Description:查询所有分类
	 * @author:甘坤
	 * @date: 2018年10月10日 下午5:10:22
	 * @param: @param pageSize
	 * @param: @param pageNum
	 * @param: @return
	 * @return: ResponseResult
	 */
	@RequestMapping(value = "queryAllNoPage.action", method = RequestMethod.GET)
	public ResponseResult queryAllNoPage() {
		QueryHelper queryHelper = new QueryHelper();

		queryHelper.addCondition(1, "deleted=%d", false);
		// 1、组装orderby
		
		List<Category>categories=categoryService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		return ResponseResult.ok(categories);
	}
	
	

}
