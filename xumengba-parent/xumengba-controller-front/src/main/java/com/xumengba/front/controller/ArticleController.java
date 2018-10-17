package com.xumengba.front.controller;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xumengba.domain.Article;
import com.xumengba.domain.Category;
import com.xumengba.domain.wc.WCArticleInfo;
import com.xumengba.helper.QueryHelper;
import com.xumengba.page.PageView;
import com.xumengba.query.ArticleQueryForm;
import com.xumengba.response.ResponseResult;
import com.xumengba.service.ArticleService;
import com.xumengba.service.CategoryService;

/**
 * 文章控制层
 */
@RestController("FrontArticleController")
@RequestMapping("/front/article")
public class ArticleController extends BaseController{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	/**
	 * @Description: 根据id查询数据
	 * @author: 朱俊亮
	 * @date: 2018年10月10日 下午1:34:48
	 * @param: id 传入一个id
	 * @return: ResponseResult 返回查询到的数据
	 */
	@RequestMapping("queryById.action")
	public ResponseResult queryById(String id,String categoryName){
		WCArticleInfo articleInfo = new WCArticleInfo();
		Category category = categoryService.queryByName(categoryName);
		String categoryId = null;
		if(category != null){
			categoryId = category.getId();
		}
		articleInfo.setArticleUp(articleService.queryAritcleUpById(id,categoryId));
		articleInfo.setArticle(articleService.queryById(id));
		articleInfo.setArticleDown(articleService.queryAritcleDownById(id,categoryId));
		return ResponseResult.ok(articleInfo);
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
