package com.muck.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muck.jt809.JT809Server;
import com.muck.socket.LocationServer;
@Service
public class InitializingData implements InitializingBean{
	Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private CarService carService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//将所有的车辆状态改为掉线状态
		carService.updateRunning(false);
		logger.info("初始化状态为0成功");
		new Thread(new Runnable() {
			@Override
			public void run() {
				JT809Server.start();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("netty websocket 启动");
				LocationServer.run();
			}
		}).start();
	}

}
