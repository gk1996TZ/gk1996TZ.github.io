package com.muck.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

import org.java_websocket.WebSocket;
/**
 * 将webSocket连接放到set集合中
 * @author 黄福寿
 * @date 2018-06-23
 * */
public class WsPool {
	
    private static final Set<Session> wsSet = Collections.synchronizedSet(new HashSet<Session>());

   
  

    /**
     * 向连接池中添加连接
     * 
     * @param conn websocket连接
     */
    public static void addWebSocket(Session conn) {
    	wsSet.add(conn); // 添加连接
    }

   

    /**
     * 移除连接池中的连接
     * 
     * @param conn websocket连接
     */
    public static boolean removeWebsocket(Session conn) {
        if (wsSet.contains(conn)) {
        	wsSet.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

   

    /**
     * 向所有的用户发送消息
     * 
     * @param message
     */
    public static void sendMessageToAll(String message) {
    	
    		 synchronized (wsSet) {
    			 if(!wsSet.isEmpty()){
    	            for (Session conn : wsSet) {
    	                if (conn != null) {
    	                    try {
    							conn.getBasicRemote().sendText(message);
    						} catch (IOException e) {
    							e.printStackTrace();
    						}
    	                }
    	            }
    	        }
    	}
       
    }

}