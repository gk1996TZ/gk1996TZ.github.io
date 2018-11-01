package com.muck.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.muck.domain.GpsData;
import com.muck.jt809.JT809Map;



/**
 * @Description:长宝的gps数据的socket
 * @Version: v1.0.0
 * @Date: 2018年8月24日 下午3:06:48
*/
//@ServerEndpoint("/chaobaoWs.ws")
//@Component
public class ChaobaoWebSocket {
	
	Logger logger=LoggerFactory.getLogger(getClass());

/*	private boolean isInitBean;
	private static ApplicationContext applicationContext;
	private static CarSnapshotInOutImageService snapshot;
	public static void setApplicationContext(ApplicationContext applicationContext) {
		ChaobaoWebSocket.applicationContext = applicationContext;
	}
	*//**
	 * 初始化service的bean对象
	 *//*
	public void initBean() {
		if (!isInitBean) {
			synchronized (AutoRegister.class) {
				if (!isInitBean) {
					ChaobaoWebSocket.snapshot = applicationContext.getBean(CarSnapshotInOutImageService.class);
					isInitBean = true;
				}
			}
		}
	}*/
	/*@Autowired
	private WsPoolCB wsPoolCB;*/

	
	 public ChaobaoWebSocket() {
		System.out.println("*****************************************");
		
	}
	 /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
    	WsPoolCB.addWebSocket(session);     //加入set中
    	
    	//VehicleData(session);
    }
	private void firstConnectData(Session session) {
		
		
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
    	//获取车辆识别前十条数据
    	/*initBean();
    	Map<String, Object> map=new HashMap<>();
    	map.put("type", "vehicle");
        List<CarSnapshotResponse> listCarInOut = snapshot.getTopTen();
        map.put("data", listCarInOut);*/
        //gps数据
    	String contentResultGps = JSONObject.toJSONString(resultGps);
    	//车辆识别的数据
    	/*String contentResultVehicle = JSONObject.toJSONString(map);*/
    	
    	try {
//    		logger.info("建立连接成功:"+session);
//    		logger.info("第一次传送的数据："+contentResult);
//    		logger.info("第一次传送的数据总数："+allMap.size());
    		
//    		System.out.println("contentResult--->" + contentResult);
    		logger.info("第一次连接将以前加载的车辆定位数据返回："+contentResultGps);
    		//向前台发送长宝的gps数据
			session.getBasicRemote().sendText(contentResultGps);
			/*logger.info("第一次连接将以前加载的车辆识别前十条数据返回："+contentResultVehicle);
			session.getBasicRemote().sendText(contentResultVehicle);*/
			//向前台发送车辆识别的数据
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("第一次连接返回数据出错", e);
		}
	}
    /**
     * 获取车辆识别数据
     * */
	/*private void VehicleData(Session session) {
		vehicleWebSocket.sendVehicle(session); 
	}*/

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session){
    	try {
    		if(session != null && !session.isOpen()){
    			WsPoolCB.removeWebsocket(session); //从set中删除
    			session.close();
    			logger.info("移除连接成功:"+session);
    		}
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error("关闭session", e);
		}
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        //群发消息
    	//WsPool.sendMessageToAll(message);
    	logger.info(message,session);
    	if("first_connection".equals(message)){
    		/*System.out.println("第一次连接");*/
    		firstConnectData(session);
    	}else if("reconnection".equals(message)){
    		/*System.out.println("重新连接");*/
    	}
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
     //   try {
        	logger.error("连接出错："+session, error);
        	logger.info("这里会执行吗？");
    		//WsPoolCB.removeWebsocket(session); //从set中删除
			//session.close();
		//	logger.info("移除连接成功:"+session);
		/*} catch (IOException e) {
			//e.printStackTrace();
			logger.error("连接出错时关闭session", e);
		}*/
    }


}
