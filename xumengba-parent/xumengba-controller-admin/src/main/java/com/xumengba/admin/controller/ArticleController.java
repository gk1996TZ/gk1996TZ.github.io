package com.xumengba.admin.controller;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Article;
import com.xumengba.domain.Category;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.query.ArticleQueryForm;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.ArticleService;
import com.xumengba.service.CategoryService;

/**
 * 文章控制层
 */
@RestController("AdminArticleController")
@RequestMapping("/admin/article")
public class ArticleController extends BaseController{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * @Description: 添加文章的方法
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:32:39
	 * @param: article 传入一个文章对象
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("save.action")
	public ResponseResult save(Article article){
		Date date = new Date();
		article.setCreatedTime(date);
		article.setUpdatedTime(date);
		article.setDeleted(true);
		articleService.save(article);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id真实删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:33:14
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteByIdReal.action")
	public ResponseResult deleteByIdReal(String id){
		articleService.deleteByIdReal(id);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id逻辑删除
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:33:51
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("deleteById.action")
	public ResponseResult deleteById(String id){
		articleService.deleteById(id);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id修改文章
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:34:16
	 * @param: article 传入一个需要被修改的文章
	 * @return: ResponseResult 返回操作信息
	 */
	@RequestMapping("editById.action")
	public ResponseResult editById(Article article){
		Date date = new Date();
		article.setUpdatedTime(date);
		article.setCreatedTime(date);
		articleService.editById(article);
		return ResponseResult.ok();
	}
	/**
	 * @Description: 根据id查询数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:34:48
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回查询到的数据
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String id){
		return ResponseResult.ok(articleService.queryById(id));
	}
	/**
	 * @Description: 根据查询条件查询文章列表
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:35:16
	 * @param: articleQueryForm 传入一个查询条件
	 * @return: ResponseResult 返回文章列表
	 */
	@RequestMapping("getArticles.action")
	public ResponseResult getArticles(ArticleQueryForm articleQueryForm){
		QueryHelper queryHelper = new QueryHelper();
		String categoryName = articleQueryForm.getCategoryName();
		if(!StringUtils.isBlank(categoryName)){
			Category category = categoryService.queryByName(categoryName);
			if(category != null){
				queryHelper.addCondition(category.getId(),"a.category_id = %d " , true);
			}
		}
		queryHelper.addCondition(articleQueryForm.getCategoryId(),"a.category_id = %d " , true);
		queryHelper.addCondition(articleQueryForm.getArticleSeq(), "a.article_seq = %d", false);
		queryHelper.addCondition(1, "a.deleted = %d", false);
		return ResponseResult.ok(articleService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams()));
	}
	/**
	 * @Description: 获取文章分页列表
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:36:45
	 * @param: articleQueryForm 传入一个查询条件
	 * @return: ResponseResult 返回分页数据
	 */
	@RequestMapping("getArticlePage.action")
	public ResponseResult getArticlePage(ArticleQueryForm articleQueryForm){
		// 创建分页对象
		PageView<Article> pageView = null;
		// 1、组装orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("a.created_time", "desc");
		// 如果是要查询,那么就创建一个QueryHelper的查询帮助对象
		QueryHelper queryHelper = new QueryHelper();
		String categoryName = articleQueryForm.getCategoryName();
		if(!StringUtils.isBlank(categoryName)){
			Category category = categoryService.queryByName(categoryName);
			if(category != null){
				queryHelper.addCondition(category.getId(),"a.category_id = %d " , true);
			}
		}
		queryHelper.addCondition(articleQueryForm.getCategoryId(),"a.category_id = %d " , true);
		queryHelper.addCondition(articleQueryForm.getArticleSeq(), "a.article_seq = %d", false);
		queryHelper.addCondition(1, "a.deleted = %d", false);
		
		pageView = articleService.queryPageData(articleQueryForm.getPageNum(), articleQueryForm.getPageSize(),
				queryHelper.getWhereSQL(), queryHelper.getWhereParams(), orderby);
		return ResponseResult.ok(pageView);
	}
}
