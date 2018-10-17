package com.xumengba.admin.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Article;
import com.xumengba.domain.Category;
import com.xumengba.exception.BizException;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.request.CategoryForm;
import com.xumengba.response.ResponseResult;
import com.xumengba.response.StatusCode;
import com.xumengba.service.ArticleService;
import com.xumengba.service.CategoryService;

@RestController("AdminCategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	// /**查询所有分类
	// * 递归
	// * */
	// @RequestMapping(value="queryTopCategories.action",method=RequestMethod.GET)
	// public ResponseResult queryTopCategories(){
	//
	// List<Category>categories=categoryService.queryByDeep();
	// return ResponseResult.ok(CollectionUtils.toList(categories));
	// }
	//
	/**
	 * 添加分类
	 */
	@RequestMapping(value = "addCategory.action", method = RequestMethod.POST)
	public ResponseResult addCategory(CategoryForm categoryForm) {

		Category category = new Category();
		category.setCategoryName(categoryForm.getCategoryName());
		category.setMemo(categoryForm.getMemo());
		Date date = new Date();
		category.setDeleted(true);
		category.setCreatedTime(date);
		category.setUpdatedTime(date);

		categoryService.save(category);
		return ResponseResult.ok();
	}

	/**
	 * 删除分类
	 */
	@RequestMapping(value = "deleteCategory.action", method = RequestMethod.GET)
	public ResponseResult deleteCategory(String categoryId) {

		// 1.查询这个分类有没有对应的文章
		List<Article> articles = articleService.queryArticlesByCategoryId(categoryId);
		if (articles != null && articles.size() > 0) {
			throw new BizException(StatusCode.CATEGORY_USED);
		}

		categoryService.deleteByIdReal(categoryId);
		return ResponseResult.ok();
	}

	/**
	 * 修改分类
	 */
	@RequestMapping(value="editById",method=RequestMethod.POST)
	public ResponseResult editById(CategoryForm categoryForm) {

		Category category = new Category();
		if (StringUtils.isBlank(categoryForm.getId())) {
			throw new BizException(StatusCode.ID_BLANK);
		}
		category.setId(categoryForm.getId());
		category.setCategoryName(categoryForm.getCategoryName());
		category.setMemo(categoryForm.getMemo());
		category.setUpdatedTime(new Date());

		categoryService.editById(category);
		return ResponseResult.ok();
	}

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
