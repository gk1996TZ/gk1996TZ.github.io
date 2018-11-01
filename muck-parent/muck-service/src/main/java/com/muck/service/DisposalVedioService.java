package com.muck.service;

import com.muck.domain.Channel;
import com.muck.page.PageView;
import com.muck.query.DisposalVedioQueryForm;

/**
 * @Description: 处置场视频查看service
 * @version: v1.0.0
 * @author 朱俊亮
 * @date 2018年5月17日 上午10:32:19
 */
public interface DisposalVedioService extends BaseService<Channel>{
	/**
	 * 	根据条件查询处置场视频
	*/
	public PageView<Channel> queryDisposalVedio(DisposalVedioQueryForm disposalVedioQueryForm);
}
