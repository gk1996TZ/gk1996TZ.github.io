package com.xumengba.query;

import com.xumengba.response.StatusCode;

public class ArticleQueryForm extends BaseForm{

	/**类别id*/
	private String categoryId;
	/**文章序号*/
	private String articleSeq;
	/**类别名称*/
	private String categoryName;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getArticleSeq() {
		return articleSeq;
	}
	public void setArticleSeq(String articleSeq) {
		this.articleSeq = articleSeq;
	}
	@Override
	public StatusCode validate() {
		return null;
	}
}
