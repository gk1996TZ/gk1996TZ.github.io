package com.muck.service;

import com.muck.domain.Channel;
import com.muck.page.PageView;
import com.muck.query.SiteVedioQueryForm;

/**
* @Description: 查询工地视频
* @version: v1.0.0
* @author: 展昭
* @date: 2018年5月14日 上午11:15:37
 */
public interface SiteVedioService {

	/**
	 * 	根据条件查询工地视频
	*/
	public PageView<Channel> querySiteVedio(SiteVedioQueryForm siteVedioForm);
	
}
