package com.xumengba.service;

import java.util.List;

import com.xumengba.domain.Article;

public interface ArticleService extends BaseService<Article>{

	/**
	 * @Description: 根据id获取上一篇文章
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 下午3:08:15
	 * @param: id 传入一个id
	 * @param: categoryId 传入一个类别id
	 * @return: Article 返回一个文章对象
	 */
	public Article queryAritcleUpById(String id,String categoryId);
	/**
	 * @Description: 根据id获取下一篇文章
	 * @author: 朱俊亮
	 * @date: 2018年10月12日 下午3:09:03
	 * @param: id 传入一个id
	 * @param: categoryId 传入一个类别id
	 * @return: Article 返回一个文章对象
	 */
	public Article queryAritcleDownById(String id,String categoryId);
	public List<Article> queryArticlesByCategoryId(String categoryId);
}
