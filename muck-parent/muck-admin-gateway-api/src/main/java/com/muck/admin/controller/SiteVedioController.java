package com.muck.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.query.SiteVedioQueryForm;
import com.muck.response.ResponseResult;
import com.muck.service.SiteVedioService;

/**
 * @Description: 查询工地视频Controller
 * @version: v1.0.0
 * @author: 展昭
 * @date: 2018年5月14日 上午11:27:22
 */
@RestController("AdminSiteVedioController")
@RequestMapping("/admin/sitevedio")
public class SiteVedioController extends BaseController {

	@Autowired
	private SiteVedioService siteVedioService; // 工地视频service

	/****
	 * @Description: 查询工地视频
	 * @param: pageNum
	 *             : 当前是第几页 pageSize : 每页显示多少条记录(在工地视频中,每页显示的记录其实就是一屏显示多少个窗口)
	 * @author: 展昭
	 * @date: 2018年5月11日 下午6:30:02
	 */
	@RequestMapping(value = "querySiteVideo.action", method = RequestMethod.POST)
	public ResponseResult querySiteVideo(SiteVedioQueryForm siteVedioQueryForm) {
		return ResponseResult.ok(siteVedioService.querySiteVedio(siteVedioQueryForm));
	}
}