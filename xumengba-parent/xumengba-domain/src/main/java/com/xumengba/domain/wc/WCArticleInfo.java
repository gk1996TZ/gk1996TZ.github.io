package com.xumengba.domain.wc;

import java.io.Serializable;

import com.xumengba.domain.Article;


/**
 * 包含上下篇文章的封装类
 */
public class WCArticleInfo implements Serializable{
	private static final long serialVersionUID = 7043843960734105118L;
	/**当前的文章详情*/
	private Article article;
	/**上一篇文章的详情*/
	private Article articleUp;
	/**下一篇文章的类型*/
	private Article articleDown;
	public WCArticleInfo() {
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Article getArticleUp() {
		return articleUp;
	}
	public void setArticleUp(Article articleUp) {
		this.articleUp = articleUp;
	}
	public Article getArticleDown() {
		return articleDown;
	}
	public void setArticleDown(Article articleDown) {
		this.articleDown = articleDown;
	}
}
