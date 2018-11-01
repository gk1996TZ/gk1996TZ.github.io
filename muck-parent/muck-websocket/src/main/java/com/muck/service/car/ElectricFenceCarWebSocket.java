package com.muck.service.car;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;



/**
 * @Description: 车辆驶出电子围栏告警的socket
 * @Version: v1.0.0
 * @Date: 2018年8月24日 下午3:10:47
 *
 */
@ServerEndpoint("/eleVehicleWs.ws")
@Component
public class ElectricFenceCarWebSocket {
	
	 public ElectricFenceCarWebSocket() {
		System.out.println("*****************************************");
	}
	 /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
    	System.out.println("建立连接成功");
        EFPool.addWebSocket(session);     //加入set中
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session){
    	EFPool.removeWebsocket(session); //从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        //群发消息
    	//WsPool.sendMessageToAll(message);
    	System.out.println("收到消息："+message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }


}
