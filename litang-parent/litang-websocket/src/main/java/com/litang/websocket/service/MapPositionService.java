package com.litang.websocket.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 地图定位的websocket服务
 */
@ServerEndpoint("/mapPositionWs")
@Component
public class MapPositionService {

	// 保存客户端的连接
	private static final Set<Session> webSockets = Collections.synchronizedSet(new HashSet<Session>());

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException {
		System.out.println("打开连接");
		webSockets.add(session);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("连接关闭啦");
		webSockets.remove(session);
	}

	/**
	 * 发生错误时调用,同样需要将session给移除
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		System.out.println(error.getMessage());
		System.out.println(error.getLocalizedMessage());
		webSockets.remove(session);
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(String message) throws IOException {

		if (StringUtils.isNotBlank(message)) {
			
			System.out.println("message--->" + message);
			
			// 第一步 : 接收用户登录之后的位置信息. 格式形如:
			// {'userId':80,'longitude':120.005738,'latitude':30.292039}
			JSONObject result = JSONObject.parseObject(message);

			result.put("name", "spring");
			result.put("phone", "13282439901");

			// 第二步 : 广播消息
			for (Session session : webSockets) {
				
				synchronized (session) {
					session.getBasicRemote().sendText(JSONObject.toJSONString(result));
				}
			}
		}
	}
}
