package com.muck.front.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muck.domain.Channel;
import com.muck.helper.QueryHelper;
import com.muck.query.ChannelQueryForm;
import com.muck.response.ResponseResult;
import com.muck.service.ChannelService;
import com.muck.utils.PolygonSelectBoxUtil;

@RestController("FrontPolygonSelectBoxController")
@RequestMapping("/front/polygonSelectBox")
public class PolygonSelectBoxController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ChannelService channelService;
	@RequestMapping("polygonSelectBox.action")
	public ResponseResult polygonSelectBox(String[] points,ChannelQueryForm channelQueryForm) {
		//如果传入的点为空
		if(points == null){
			logger.error("调用polygonSelectBox接口时points为空");
		}
		//如果传入的坐标点数组长度不是偶数，说明传入的有可能不是一个坐标
		if(points.length % 2 != 0){
			logger.error("调用polygonSelectBox接口时POINT_FORMAT_ERROR为空");
		}
		//如果长度小于六，则说明小于三个坐标，不能构成一个多边形
		if(points.length < 6){
			logger.error("调用polygonSelectBox接口时POINT_SIZE_LESS_THREE为空");
		}
		//创建一个存放多边形各个点的列表
		List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
		//将传入的点放到列表中
		for(int i = 0 ;i<points.length;i++){
			//判断下标是否是偶数
			if(i%2==0){
				pts.add(new Point2D.Double(Double.parseDouble(points[i]),Double.parseDouble(points[i+1])));
			}
		}
		//查询数据库设备数据
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.addCondition(1,"deleted = %d", false)
		.addCondition(channelQueryForm.getChannelType(), "channel_type = %d", false);
		List<Channel> channels = new ArrayList<Channel>();
		List<Channel> listChannel = channelService.queryData(queryHelper.getWhereSQL(), queryHelper.getWhereParams());
		for(Channel channel : listChannel){
			if(channel.getChannelLatitude() != null && channel.getChannelLongitude() !=null){
				Point2D.Double point = new Point2D.Double(Double.parseDouble(String.valueOf(channel.getChannelLongitude())), Double.parseDouble(String.valueOf(channel.getChannelLatitude())));
				if (PolygonSelectBoxUtil.IsPtInPoly(point, pts)) {
					channels.add(channel);
				}
			}
		}
		return ResponseResult.ok(channels);
	}
}
