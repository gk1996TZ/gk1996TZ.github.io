package com.muck.front.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Area;
import com.muck.domain.Disposal;
import com.muck.response.ResponseResult;
import com.muck.service.DisposalService;

/**
 * 	前台处置场Controller
*/
@RestController("FrontDisposalController")
@RequestMapping("/front/disposal")
public class DisposalController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DisposalService disposalService;	// 处置场service
	
	/***
	 * 	 处置场树
	*/
	@RequestMapping(value = "initTreeDisposals.action", method = RequestMethod.GET)
	public ResponseResult initTreeDisposals() {

		List<Area> areas = disposalService.queryDisposalTree();

		return ResponseResult.ok(areas);
	}
	
	/***
	 * 	根据通道号查询处置场详情
	*/
	@RequestMapping(value = "queryDisposalInfoByChannelCode.action", method = RequestMethod.GET)
	public ResponseResult queryDisposalInfoByChannelCode(String channelCode) {

		if (StringUtils.isBlank(channelCode)) {
			logger.error("调用queryDisposalInfoByChannelCode接口时channelCode为空");
		}

		try {
			Disposal disposal= disposalService.queryDisposalInfoByChannelCode(channelCode);

			return ResponseResult.ok(disposal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}
	
	/***
	 * 	根据处置场id查询处置场详情
	*/
	@RequestMapping(value = "queryDisposalInfoById.action", method = RequestMethod.GET)
	public ResponseResult queryDisposalInfoById(String disposalId) {

		if (StringUtils.isBlank(disposalId)) {
			logger.error("调用queryDisposalInfoById接口时disposalId为空");
		}

		try {
			Disposal disposal= disposalService.queryById(disposalId);

			return ResponseResult.ok(disposal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseResult.notFound();
	}
	
	
}
