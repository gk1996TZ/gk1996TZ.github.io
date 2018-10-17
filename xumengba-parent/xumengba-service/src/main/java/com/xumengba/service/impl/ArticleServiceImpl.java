package com.xumengba.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumengba.domain.Article;
import com.xumengba.mapper.ArticleMapper;
import com.xumengba.mapper.BaseMapper;
import com.xumengba.page.PageView;
import com.xumengba.service.ArticleService;
import com.xumengba.utils.CollectionUtils;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService{

	@Autowired
	ArticleMapper articleMapper;
	
	@Override
	public PageView<Article> queryPageData(Long currentPageNum, Long pageSize, String whereSQL, List<Object> whereParams,
			LinkedHashMap<String, String> orderBy) {
		String limit = super.buildLimit(currentPageNum, pageSize);
		whereSQL = StringUtils.isNotBlank(whereSQL) ? " and " + whereSQL : "";
		// 用来查询数据总数的sql
		String sql = "select count(id) from t_article a where 1 = 1 " + whereSQL;
		// 用来查询数据的sql
		String selectSQL = "select a.*,c.category_name "
				+ " from t_article a left join t_category c on a.category_id = c.id "
				+ " where 1 = 1 " + whereSQL + limit;
		// 查询总数
		sql = super.setQueryParams(sql, whereParams, null);
		Long count = articleMapper.selectTotalRecoreds(sql);
		PageView<Article> pageView = new PageView<>(count, currentPageNum, pageSize);
		// 查询记录
		selectSQL = super.setQueryParams(selectSQL, whereParams, pageView);
		pageView.setDatas(articleMapper.selectPageData(selectSQL));
		return pageView;
	}
	@Override
	public List<Article> queryData(String whereSQL, List<Object> whereParams) {
		// 第一步、获取where条件
		String where = StringUtils.isBlank(whereSQL) ? " where 1 = 1 " : " where " + whereSQL;
		// 第二步、拼接查询sql
		String selectSQL = "select a.*,c.category_name "
				+ " from t_article a left join t_category c on a.category_id = c.id "
			    + where;
		// 第三步、设置参数
		selectSQL = setQueryParams(selectSQL, whereParams, null);
		return getMapper().queryData(selectSQL);
	}

	@Override
	public Article queryAritcleUpById(String id,String categoryId) {
		return articleMapper.queryAritcleUpById(id,categoryId);
	}
	@Override
	public Article queryAritcleDownById(String id,String categoryId) {
		return articleMapper.queryAritcleDownById(id,categoryId);
	}
	//===================
	@Override
	protected String getFields() {
		return " * ";
	}

	@Override
	protected BaseMapper<Article> getMapper() {
		return articleMapper;
	}

	@Override
	public List<Article> queryArticlesByCategoryId(String categoryId) {
		return CollectionUtils.toList(articleMapper.getArticlesByCategoryId(categoryId));
	}

}
