package com.muck.jt809;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.muck.domain.Car;
import com.muck.domain.GpsData;
import com.muck.service.CarService;
import com.muck.service.GpsDataService;
import com.muck.socket.ChannelHanderMap;
import com.muck.utils.map.CoordinateConverter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/*@Component*/
public class JT809BusiHander extends SimpleChannelInboundHandler<List<Message>> {
	Logger logger = LoggerFactory.getLogger(getClass());
	// 静态的上下文对象
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		JT809BusiHander.applicationContext = applicationContext;
	}

	// 标志变量,表示是否已经初始化了bean对象,默认为false
	private boolean isInitBean;
	private CarService carService;
	private GpsDataService gpsDataService; // 车辆

	java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(150);

	public void initBean() {
		if (!isInitBean) {
			synchronized (JT809BusiHander.class) {
				if (!isInitBean) {
					this.carService = applicationContext.getBean(CarService.class);
					this.gpsDataService = applicationContext.getBean(GpsDataService.class);
					isInitBean = true;
				}
			}
		}
	}

	public JT809BusiHander() {

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, List<Message> messageList) throws Exception {
		for (Message msg : messageList) {
			try {
				if (msg != null) {
					logger.info("msgID:" + msg.getMsgId());
					switch (msg.getMsgId()) {
					case JT809BusinessType.UP_CONNECT_REQ:
						// System.out.println("获取登录信息：" +
						// ChangBaoPro.DOWN_LINK_IP);
						// System.out.println("登录请求");
						// System.out.println("长度" + msg.getMsgLength());
						logger.info("登录 msgID:" + msg.getMsgId());
						logger.info("登录");
						loginAndRes(ctx, msg);
						// System.out.println("登录应答");
						break;
					case JT809BusinessType.UP_DISCONNECT_REQ:
						// System.out.println("断开链接请求");
						logger.info("断开 msgID:" + msg.getMsgId());
						break;
					case JT809BusinessType.UP_LINKTEST_REQ:
						logger.info("保持连接msgID:" + msg.getMsgId());
						// System.out.println("保持连接请求");
						logger.info("保持心跳");
						keepConnection(ctx, msg);// 保持心跳
						break;
					case JT809BusinessType.UP_EXG_MSG:
						logger.info("交换车辆定位信息msgID:" + msg.getMsgId());
						// System.out.println("交换车辆定位信息");
						connection_gps(ctx, msg);
						break;
					case JT809BusinessType.UP_EXG_MSG_REAL_LOCATION:
						logger.info("实时上传车辆定位信息msgID:" + msg.getMsgId());
						// System.out.println("实时上传车辆定位信息");
						// connection_gps(ctx, msg);
						break;
					case JT809BusinessType.UP_EXG_MSG_HISTORY_LOCATION:
						logger.info("车辆定位信息自动补报msgID:" + msg.getMsgId());
						// System.out.println("车辆定位信息自动补报");
						break;
					case JT809BusinessType.UP_EXG_MSG_REGISTER:
						logger.info("上传车辆注册msgID:" + msg.getMsgId());
						// System.out.println("上传车辆注册");
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
				logger.error("读异常", e);
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

	}

	/**
	 * 登录请求并应答请求
	 */
	private void loginAndRes(ChannelHandlerContext ctx, Message msg) {
		ByteBuf byteBuf = msg.getMsgBody();
		long userid = byteBuf.readUnsignedInt();// 用户登录id
		byte[] by = new byte[8];
		byteBuf.readBytes(by, 0, 8);
		String password = new String(by, Charset.forName("GBK"));
		byteBuf.release();
		// String ip = byteBuf.readBytes(32).toString(Charset.forName("GBK"));
		// int port=byteBuf.readBytes(2).getUnsignedShort(0);
		msg.setMsgLength(JT809BusinessType.UP_CONNECT_RSP_LENGTH);
		msg.setMsgId(JT809BusinessType.UP_CONNECT_RSP);// 登录应答
		// ByteBuf msgBody=Unpooled.buffer(5);
		ByteBuf msgBody = Unpooled.buffer(5);
		if (msg.getMsgGnsscenterid() != JT809User.MSG_GNSSCENTERID) {// 接入码不相等
			msgBody.writeByte(JT809ResultType.CONNECTION_GNSS_INCORRECT);
		} else if (userid != JT809User.USERID) {// 登录id不正确
			msgBody.writeByte(JT809ResultType.CONNECTION_USERID_NOT_REGISTER);
		} /*
			 * else if(!JT809User.DOWN_LINK_IP.equals(ip)){//ip不正确
			 * msgBody.writeByte(JT809ResultType.CONNECTION_IP_INCORRECT); }
			 */else if (!JT809User.PASSWORD.equals(password)) {// 密码不正确
			msgBody.writeByte(JT809ResultType.CONNECTION_PASSWORD_INCORRECT);
		} else {
			msgBody.writeByte(JT809ResultType.CONNECTION_SUCCESS);// 登录成功
		}

		msgBody.writeInt(msg.getCrcCode());// 校验码
		// msgBody.writeInt(38041);//校验码

		msg.setMsgBody(msgBody);
		ByteBuf bb = Unpooled.directBuffer(27);
		// 数据头
		bb.writeInt(msgBody.capacity() + 26);
		bb.writeInt(msg.getMsgSN());
		bb.writeShort(msg.getMsgId());
		bb.writeInt(msg.getMsgGnsscenterid());
		bb.writeBytes(msg.getVersionFlag());
		bb.writeByte(msg.getEncryptFlag());
		bb.writeInt(msg.getEncryptKey());

		// 数据体
		bb.writeBytes(msgBody.array());
		byte[] bt = new byte[5 + 22];
		bb.getBytes(0, bt);
		// System.out.println("应答校验码：" + CRC16CCITT.crc16(bt));
		msg.setCrc16code(CRC16CCITT.crc16(bt));
		// bb.release();
		ctx.write(msg);
		ctx.flush();
	}

	/** 保持连接应答 */
	private void keepConnection(ChannelHandlerContext ctx, Message msg) {
		msg.setMsgBody(null);
		msg.setMsgLength(JT809BusinessType.UP_LINKTEST_RSP_LENGTH);
		msg.setMsgId(JT809BusinessType.UP_LINKTEST_RSP);

		ctx.write(msg);
		ctx.flush();
	}

	/**
	 * 实时获取车辆定位数据
	 */
	private void connection_gps(ChannelHandlerContext ctx, Message msg) {

		try {
			ByteBuf body = msg.getMsgBody();
			logger.info("开始获取车辆定位数据");
			if (body.readableBytes() > 0) {
				logger.info("进入获取车辆定位数据");
				Charset charset = Charset.forName("gbk");
				JT809Car car = new JT809Car();
				// ByteBuf buffer = Unpooled.copiedBuffer(body.readBytes(21));
				byte[] rb = new byte[21];
				body.readBytes(rb, 0, 21);
				String str = new String(rb, charset);
				car.setVehicle_no(str.trim());
				car.setVehicle_color(body.readByte());
				car.setData_type(body.readShort());
				car.setData_length(body.readInt());
				logger.info("进入获取车辆定位数据car:" + car);
				logger.info("子业务类型id:" + car.getData_type() + ";实时上传id:" + JT809BusinessType.UP_EXG_MSG_REAL_LOCATION);
				// buffer.release(); // 关闭
				if (car.getData_type() == JT809BusinessType.UP_EXG_MSG_REAL_LOCATION
						|| car.getData_type() == JT809BusinessType.UP_EXG_MSG_HISTORY_LOCATION) {
					initBean();
					if (car.getData_type() == JT809BusinessType.UP_EXG_MSG_HISTORY_LOCATION) {
						int Gnss_cnt = body.readByte();
						logger.info("车辆" + car.getVehicle_no() + "的补报数量" + Gnss_cnt);
						logger.info("车辆" + car.getVehicle_no() + "的补报字节数" + body.readableBytes());
						for (int i = 0; i < Gnss_cnt; i++) {
							readCarData(body, car);
						}
					} else {
						logger.info("车辆" + car.getVehicle_no() + "的上传定位数据");
						logger.info("车辆" + car.getVehicle_no() + "的上传定位字节数" + body.readableBytes());
						readCarData(body, car);
					}

				}

				// System.out.println("获取到的车辆为：" + car);
			}
		} catch (Exception e) {
			logger.info("----------GPS Car push Exception-----------------");
			// e.printStackTrace();
			logger.error("gps异常", e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	private void readCarData(ByteBuf body, JT809Car car) {
		final GpsData gpsData = realCar(car, body);
		// 更新数据库状态
		GpsData rcar = JT809Map.getByNo(gpsData.getVehicleNo());
		if (rcar == null) {
			/*
			 * Car cars =
			 * carService.selectCarByCarCodeAndCarCardColor(gpsData.getVehicleNo
			 * (), gpsData.getVehicleColor() + "");
			 */
			Car cars = carService.queryByCarCode(gpsData.getVehicleNo());
			if (cars != null) {
				gpsData.setCompanyId(cars.getCompanyId());
				// gpsData.setCarGroupId(cars.getCarGroupId());
				gpsData.setCompanyName(cars.getCompanyName().trim());
			} else {
				logger.error("车辆的企业没有查询到：" + gpsData.getVehicleNo());
				// break;
			}
		} else {
			gpsData.setCompanyId(rcar.getCompanyId());
			// gpsData.setCarGroupId(rcar.getCarGroupId());
			gpsData.setCompanyName(rcar.getCompanyName());
		}

		JSONObject result = new JSONObject();
		GpsData cGpsData = JT809Map.getByNo(gpsData.getVehicleNo());
		if (cGpsData == null) {
			result.put("isChange", true);
		} else if (cGpsData.getLongitude() != gpsData.getLongitude()
				|| cGpsData.getLatitude() != gpsData.getLatitude()) {
			result.put("isChange", true);

		} else if (gpsData.getStatus() == 0) {
			result.put("isChange", true);
		} else {
			result.put("isChange", false);
		}

		/**
		 * if ((boolean) result.get("isChange") == true) { // 添加轨迹
		 * 
		 * }
		 **/

		// 接收到数据就直接保存
		/**
		 * executorService.execute(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 *           // logger.info("添加轨迹："+gpsData); } });
		 **/

		// 集合中没有 表示状态为1
		// 集合中有说明集合中的状态为1
		/**
		 * if ((cGpsData == null && gpsData.getStatus() == 1) || (cGpsData !=
		 * null && gpsData.getStatus() == 0)) { // 更改数据库中的状态
		 * executorService.execute(new Runnable() {
		 * 
		 * @Override public void run() { Car car = new Car();
		 *           car.setCarCode(gpsData.getVehicleNo()); boolean running =
		 *           gpsData.getStatus() == 1 ? true : false;
		 *           car.setRunning(running); car.setUpdatedTime(new Date());
		 *           car.setCarCardColor(gpsData.getVehicleColor()+ "");
		 *           carService.editByCarCode(car);
		 *           logger.info("修改了数据中的车辆状态和车牌颜色："+car); } }); }
		 **/

		boolean flag = JT809Map.addOrRemoveCars(gpsData);
		result.put("gpsData", gpsData);
		if (flag) {
			/* result.put("type", "gps"); */
			try {
				result.put("companyId", gpsData.getCompanyId());
				result.put("companyName", JT809Map.getCompanyNameById(gpsData.getCompanyId()).trim());
				result.put("companyCount", JT809Map.getCountById(gpsData.getCompanyId(), (byte) 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// result.put("groupId", gpsData.getCarGroupId());
			// result.put("groupIdCount",
			// JT809Map.getCountById(gpsData.getCompanyId(),
			// (byte)1));
			// System.out.println("目前的车辆情况："+contentResult);
			// logger.info(contentResult);
			// System.out.println(" contentResult -->
			// "+contentResult);//
			String contentResult = JSONObject.toJSONString(result);

			logger.info("-------------长宝推送数据开始啦Begin----------");

			// WsPoolCB.sendMessageToAll(contentResult);
			ChannelHanderMap.sendMessage(contentResult);

			logger.info("-------------长宝推送数据结束啦End----------");

		}
		// body.release();
		gpsDataService.save(gpsData);
		
		// 表示运行
		Byte carIsRunning = gpsData.getStatus();
		Long gpsTimeStamp = gpsData.getGpsTime().getTime();
		Long currentTimeStamp = new Date().getTime();
		if (carIsRunning != null) {
			// 更改数据库中的状态
			Car car1 = new Car();
			car1.setCarCode(gpsData.getVehicleNo());
			if ((carIsRunning == 1) && (currentTimeStamp - gpsTimeStamp <= 1000 * 60 * 10)) {
				car1.setRunning(true);
			} else {
				car1.setRunning(false);
			}
			car1.setUpdatedTime(new Date());
			car1.setCarCardColor(gpsData.getVehicleColor() + "");
			carService.editByCarCode(car1);
			logger.info("修改了数据中的车辆状态和车牌颜色：" + car);
		}
	}

	/**
	 * private void connection_gps(ChannelHandlerContext ctx, Message msg) {
	 * ByteBuf body = msg.getMsgBody(); if (body.readableBytes() > 0) { Charset
	 * charset = Charset.forName("gbk"); JT809Car car = new JT809Car();
	 * car.setVehicle_no(body.readBytes(21).toString(charset));
	 * car.setVehicle_color(body.readByte());
	 * car.setData_type(body.readShort()); car.setData_length(body.readInt());
	 * switch (car.getData_type()) { case
	 * JT809BusinessType.UP_EXG_MSG_REAL_LOCATION: final GpsData gpsData =
	 * realCar(car, body);
	 * 
	 * System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	 * 
	 * System.out.println("原始坐标:-->" + gpsData.getLongitude() + ",  " +
	 * gpsData.getLatitude());
	 * 
	 * System.out.println("String类型坐标:-->" + gpsData.getLnglatStrs()[0] + ", " +
	 * gpsData.getLnglatStrs()[1]);
	 * 
	 * System.out.println("Double类型坐标:-->" + gpsData.getLnglat()[0] + ", " +
	 * gpsData.getLnglat()[1]);
	 * 
	 * System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	 * 
	 * JSONObject result = new JSONObject(); result.put("gpsData", gpsData);
	 * result.put("type", "gps"); result.put("isChange", true);
	 * 
	 * String contentResult = JSONObject.toJSONString(result);
	 * WsPoolCB.sendMessageToAll(contentResult); break; } } }
	 */
	/**
	 * 实时上传车辆数据
	 */
	private GpsData realCar(JT809Car car, ByteBuf body) {
		car.setEncrypt(body.readByte());
		// ByteBuf bb = body.readBytes(4).copy();
		byte[] bt = new byte[4];
		// bb.getBytes(0, bt);
		body.readBytes(bt, 0, bt.length);
		car.setDate(bt);
		ByteBuf bb = Unpooled.buffer(4);
		bb.writeBytes(bt);
		byte day = bb.readByte();// d 日
		byte month = bb.readByte();// m 月
		short year = bb.readShort();// yy 年
		bb.release();
		// ByteBuf bbf = body.readBytes(3).copy();
		byte[] btf = new byte[3];
		body.readBytes(btf, 0, 3);
		// bbf.getBytes(0, btf);
		byte hour = btf[0];
		byte minute = btf[1];
		byte seconds = btf[2];
		// bbf.release();
		car.setTime(btf);
		Integer x1 = body.readInt();
		Integer x2 = body.readInt();
		// System.out.println(" x1 ==== > " + x1 + "\n" + " x2 ==== > " + x2);
		car.setLon(x1);
		car.setLat(x2);
		car.setVec1(body.readShort());
		car.setVec2(body.readShort());
		car.setVec3(body.readInt());
		car.setDirection(body.readShort());
		car.setAltitude(body.readShort());
		car.setState(body.readInt());
		car.setAlarm(body.readInt());
		GpsData gpsData = new GpsData();

		// dmyy 日月年
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();// 默认为当前时间
		try {
			date = sdf.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seconds);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// hms 时分秒
		gpsData.setGpsTime(date);
		gpsData.setDirection(car.getDirection() + "度");
		gpsData.setVehicleColor((int) car.getVehicle_color());
		gpsData.setLatitude((long) car.getLat());
		gpsData.setLongitude((long) car.getLon());

		double latDouble = ((double) gpsData.getLatitude()) / 1000000;
		double lonDouble = ((double) gpsData.getLongitude()) / 1000000;
		double[] lonlat = CoordinateConverter.wgs2GCJ(latDouble, lonDouble);
		gpsData.setLnglat(lonlat);

		Short vec1 = car.getVec1();
		Short vec2 = car.getVec2();
		if (vec1 != null && vec1 > 0) {
			gpsData.setSpeed((double) vec1);
		} else if (vec2 != null && vec2 > 0) {
			gpsData.setSpeed((double) vec2);
		} else {
			gpsData.setSpeed((double) 0);
		}
		gpsData.setMileage(car.getVec3() + "");
		gpsData.setVehicleNo(car.getVehicle_no().trim());
		gpsData.setDeleted(true);
		String str = Integer.toBinaryString(car.getState());
		StringBuffer sb = new StringBuffer(str);
		if (str.length() < 32) {
			for (int i = 0; i < 32 - str.length(); i++) {
				sb.insert(0, '0');
			}
		}
		if (Byte.parseByte(sb.charAt(4) + "") == 1) {// 二进制状态位 1表示停止，0.表示运行
			gpsData.setStatus((byte) 0);
		} else {
			gpsData.setStatus((byte) 1);
		}
		// body.release();
		return gpsData;
	}
}
