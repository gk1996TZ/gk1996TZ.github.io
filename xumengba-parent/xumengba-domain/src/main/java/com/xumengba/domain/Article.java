package com.xumengba.domain;

import java.io.Serializable;
import java.util.Date;

import com.xumengba.annotation.Field;
import com.xumengba.annotation.Table;
import com.xumengba.handler.IdTypeHandler;

/**
 * 文章实体类
 */
@Table(name="t_article")
public class Article extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5716917576223969034L;
	/**文章标题*/
	@Field(name="article_title")
	private String articleTitle;
	/**文章内容*/
	@Field(name="article_content")
	private String articleContent;
	/**文章作者*/
	@Field(name="article_author")
	private String articleAuthor;
	/**文章封面图片*/
	@Field(name="article_pic")
	private String articlePic;
	/**文章序号*/
	@Field(name="article_seq")
	private Integer articleSeq;
	/**文章所属分类*/
	@Field(name="category_id")
	private String categoryId;
	/**类别名称*/
	private String categoryName;
	/**文章描述*/
	@Field(name="memo")
	private String memo;
	/**自增id*/
	@Field(name="id")
	private String id;
	/**创建时间*/
	@Field(name="created_time")
	private Date createdTime;
	/**修改时间*/
	@Field(name="updated_time")
	private Date updatedTime;
	/**是否删除true：不删除，false删除*/
	@Field(name="deleted")
	private Boolean deleted;
	/**定义一个idRaw字段*/
	private Long idRaw;
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public String getArticleAuthor() {
		return articleAuthor;
	}
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}
	public String getArticlePic() {
		return articlePic;
	}
	public void setArticlePic(String articlePic) {
		this.articlePic = articlePic;
	}
	public Integer getArticleSeq() {
		return articleSeq;
	}
	public void setArticleSeq(Integer articleSeq) {
		this.articleSeq = articleSeq;
	}
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getId() {
		if(id == null && idRaw > 0){
			id = IdTypeHandler.encode(idRaw);
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Long getIdRaw() {
		return idRaw;
	}
	public void setIdRaw(Long idRaw) {
		this.idRaw = idRaw;
	}
}
