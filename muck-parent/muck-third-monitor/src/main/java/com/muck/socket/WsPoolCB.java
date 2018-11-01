package com.muck.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 将webSocket连接放到set集合中
 * @author 黄福寿
 * @date 2018-07-06
 * */
public class WsPoolCB {
	
    private static final List<Session> wsSet = 
    		Collections.synchronizedList(new ArrayList<Session>());
    static Logger logger = LoggerFactory.getLogger(WsPoolCB.class);

   
  

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
	            ListIterator<Session> iters = wsSet.listIterator();
	            while(iters.hasNext()){
	            	Session conn = iters.next();
	            	if(conn != null && conn.isOpen()){
	            		try {
	            			if(StringUtils.isNotBlank(message)){
	            				conn.getBasicRemote().sendText(message);
	            			}
						} catch (IOException e) {
							logger.error("IOException--发送数据：", e);
						}catch (Exception e) {
							logger.error("发送数据：", e);
						}
	            	}
	            }
			 }
	}
       
    }

}