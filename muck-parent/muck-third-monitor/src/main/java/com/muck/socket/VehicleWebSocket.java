package com.muck.socket;

import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.muck.response.CarSnapshotResponse;
import com.muck.service.AutoRegister;
import com.muck.service.CarSnapshotInOutImageService;

/**
 * @Description: 车辆识别的socket
 * @Version: v1.0.0
 * @Date: 2018年8月24日 下午3:08:41
 *
 */
@ServerEndpoint("/vehicleWs.ws")
@Component
public class VehicleWebSocket {

	private boolean isInitBean;
	private static ApplicationContext applicationContext;
	private static CarSnapshotInOutImageService snapshot;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		VehicleWebSocket.applicationContext = applicationContext;
	}

	/**
	 * 初始化service的bean对象
	 */
	public void initBean() {
		if (!isInitBean) {
			synchronized (AutoRegister.class) {
				if (!isInitBean) {
					VehicleWebSocket.snapshot = applicationContext.getBean(CarSnapshotInOutImageService.class);
					isInitBean = true;
				}
			}
		}
	}

	public VehicleWebSocket() {
		System.out.println("*****************************************");
	}

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void sendVehicle(Session session) {
		WsPool.addWebSocket(session);
		initBean();
		/*
		 * Map<String, Object> map=new HashMap<>(); map.put("type", "vehicle");
		 */
		List<CarSnapshotResponse> listCarInOut = snapshot.getTopTen();
		/* map.put("data", listCarInOut); */
		System.out.println("推给前台10条数据" + listCarInOut);
		try {
			session.getBasicRemote().sendText(JSONObject.toJSONString(listCarInOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		if(session != null){
			WsPool.removeWebsocket(session); // 从set中删除
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message) {
		// 群发消息
		// WsPool.sendMessageToAll(message);
		System.out.println("收到消息：" + message);
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		System.out.println(error.getMessage());
		error.printStackTrace();
	}
}
