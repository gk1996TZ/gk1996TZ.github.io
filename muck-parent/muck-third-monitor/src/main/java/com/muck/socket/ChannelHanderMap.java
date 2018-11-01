package com.muck.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.muck.domain.GpsData;
import com.muck.jt809.JT809Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChannelHanderMap {
	static Logger logger = LoggerFactory.getLogger(ChannelHanderMap.class);
	private static Map<String,ChannelHandlerContext> channelMap=new HashMap<String, ChannelHandlerContext>();
	
	public static void add(ChannelHandlerContext cc){
		synchronized (channelMap) {
			channelMap.put(cc.channel().id().asLongText(), cc);
			logger.info("add channel id:"+cc.channel().id().asLongText());
		}
	}
	public static void remove(ChannelHandlerContext cc){
		synchronized (channelMap) {
			String id=cc.channel().id().asLongText();
			if(channelMap.containsKey(id)){
				channelMap.remove(id);
				logger.info("remove channel id:"+id);
			}
		}
	}
	public static void sendMessage(String message){
		try {
			synchronized (channelMap) {
				Set<String> setKey=channelMap.keySet();
				Iterator<String> ids=setKey.iterator();
				while(ids.hasNext()){
					String id=ids.next();
					ChannelHandlerContext cc=channelMap.get(id);
					cc.channel().writeAndFlush(new TextWebSocketFrame(message));
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("netty 发送消息", e);
		}
	}
	
	
	public static void firstConnectData(ChannelHandlerContext cc) {
		
		
		//第一次进入的时候将所有数据回传
    	Map<String,GpsData> allMap=JT809Map.getAllMap();
    	Map<String,Integer> countMap=JT809Map.getCountByComp();
    	JSONObject resultGps = new JSONObject();
    	
    	resultGps.put("dataList", allMap.values());
    	/*resultGps.put("type", "gps");*/
    	Set<String> set=countMap.keySet();
    	List<Map<String,Object>> countList=new ArrayList<>();
    	if(set!=null&&!set.isEmpty()){
    		for(String str:set){
        		Map<String,Object> map=new HashMap<>();
        		map.put("companyId", str);
        		map.put("companyName", JT809Map.getCompanyNameById(str));
        		map.put("companyCount", countMap.get(str));
        		countList.add(map);
        	}
    	}
    	resultGps.put("compCList", countList);
    	String contentResultGps = JSONObject.toJSONString(resultGps);
    	try {
    		logger.info("第一次连接将以前加载的车辆定位数据返回："+contentResultGps);
    		//向前台发送车辆定位数据
    		cc.channel().writeAndFlush(new TextWebSocketFrame(contentResultGps));
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("第一次连接返回数据出错", e);
		}
	}
}
