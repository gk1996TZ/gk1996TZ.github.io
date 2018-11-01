package com.muck.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.muck.response.CarSnapshotResponse;
import com.muck.socket.WsPool;
import com.netsdk.lib.NetSDKLib;
import com.netsdk.lib.NetSDKLib.fMessCallBack;
import com.netsdk.lib.NetSDKLib.fServiceCallBack;
import com.netsdk.lib.ToolKits;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public class AutoRegister {
	static Logger logger = LoggerFactory.getLogger(AutoRegister.class);
	// 标志变量,表示是否已经初始化了bean对象,默认为false
	private static boolean isInitBean;
	private static ApplicationContext applicationContext;
	private static CarSnapshotInOutImageService snapshot;
	private static PropertiesService propertiesService;
	
	static NetSDKLib NetSdk        = NetSDKLib.NETSDK_INSTANCE;
    static NetSDKLib ConfigSdk     = NetSDKLib.CONFIG_INSTANCE;
    static NetSDKLib.NET_DEVICEINFO_Ex deviceinfo = new NetSDKLib.NET_DEVICEINFO_Ex();
    public AutoRegister(){
    	
	}
    public static void setApplicationContext(ApplicationContext applicationContext) {
    	AutoRegister.applicationContext = applicationContext;
	}
    /**
	 * 初始化service的bean对象
	 */
	public static void initBean() {
		if (!isInitBean) {
			synchronized (AutoRegister.class) {
				if (!isInitBean) {
					AutoRegister.snapshot = applicationContext.getBean(CarSnapshotInOutImageService.class);
					AutoRegister.propertiesService = applicationContext.getBean(PropertiesService.class);
					isInitBean = true;
				}
			}
		}
	}
	/**
	 * NetSDK 库初始化
	 */
	private class SDKEnvironment { 
	    private boolean bInit = false;
	    private boolean bLogopen = false;
	    private DisConnect 		 disConnect    = new DisConnect();    // 设备断线通知回调
	    // 初始化
	    public boolean init() {    	
			// SDK 库初始化, 并设置断线回调
			bInit = NetSdk.CLIENT_Init(disConnect, new NativeLong(0));
			if (!bInit) {
				logger.error("Initialize SDK failed");
				return false;
			}
			
			// 打开日志，可选
			NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();
			
			File path = new File(".");			
			String logPath = path.getAbsoluteFile().getParent() + "\\sdk_log\\sdk.log";
			
			setLog.bSetFilePath = 1;
			System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);
			
			setLog.bSetPrintStrategy = 1;
			setLog.nPrintStrategy = 0;
			bLogopen = NetSdk.CLIENT_LogOpen(setLog);
			if (!bLogopen) {
				logger.error("Failed to open NetSDK log !!!");
			}
			
			// 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime 
			// 接口设置的登录设备超时时间和尝试次数意义相同
			// 此操作为可选操作
			NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
			netParam.nConnectTime = 5000; // 登录时尝试建立链接的超时时间
			NetSdk.CLIENT_SetNetworkParam(netParam);
	    	
	    	return true;
	    }
	    
	    // 清除环境
	    public void cleanup() {
	    	if (bLogopen) {
	    		NetSdk.CLIENT_LogClose();
	    	}
	    	
	    	if (bInit) {
	    		NetSdk.CLIENT_Cleanup();
	    	}
	    }
	    
	    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
	    public class DisConnect implements NetSDKLib.fDisConnect  {
	        public void invoke(NativeLong lLoginID, String pchDVRIP, int nDVRPort, NativeLong dwUser) {
	        	System.out.printf("Device[%s] Port[%d] Disconnect!\n" , pchDVRIP , nDVRPort);
	        }
	    }
	}
	
	private static NativeLong serverHandler; 	// 本地服务器句柄
	
	// 设备列表
	private static List<Device> deviceList = new ArrayList<Device>();
	
	private static Map<NativeLong, Device> deviceMap = new HashMap<NativeLong, AutoRegister.Device>();
	
	private static class Device {
		public Device(String address, int port, String username,
				String password, String serialNumber) {
			super();
			this.address = address;
			this.port = port;
			this.username = username;
			this.password = password;
			this.serialNumber = serialNumber;
			
			this.handle = null;
			loginSuccess = false;
		}

		public NativeLong handle; 	// 设备句柄, 标识唯一的设备
		public String address; 		// 设备地址
		public int port; 			// 设备端口
		public String username; 	// 用户名
		public String password; 	// 密码
		public String serialNumber; // 序列号
		
		public boolean loginSuccess; // 登录状态

		@Override
		public String toString() {
			return "Device [address=" + address + ", serialNumber="
					+ serialNumber + "]";
		}
	} 
	
	// 侦听服务器回调函数, 建议写成单例模式
	public static class ServiceCB implements fServiceCallBack {
		private ServiceCB() {
		}
		private static ServiceCB serviceCB = new ServiceCB();
		
		public static ServiceCB getInstance() {
			return serviceCB;
		} 
		
		@Override
		public int invoke(NativeLong lHandle, final String pIp, final int wPort,
				NativeLong lCommand, Pointer pParam, int dwParamLen,
				NativeLong dwUserData) {
			logger.info("注册设备信息  Device address " + pIp + ", port " + wPort);
			
			// 将 pParam 转化为序列号
			byte[] buf = new byte[dwParamLen];
			pParam.read(0, buf, 0, dwParamLen);
			final String sn = new String(buf).trim();
			
			final Device device = new Device(pIp, wPort, "admin", "jh1234567", sn);
			
			new Thread(new Business(device)).start();
			
			return 0;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	public synchronized static void addDevice(final Device device) {
		if (device != null) {
			deviceList.add(device);
			deviceMap.put(device.handle, device);
		}
	}
	
	public synchronized static Device getDevice(NativeLong handle) {
		return deviceMap.get(handle);
	}
	
	///////////////////////////////////////////////////////////////////////////
	private SDKEnvironment environment = new SDKEnvironment();
	public void init() {
		environment.init();
	}
	
	public void cleanup() {
		environment.cleanup();
	}

	/**
	 * 开启服务
	 * @param address 本地IP地址
	 * @param port 本地端口, 可以任意
	 * @return
	 */
	public boolean startServer(String address, int port) {
		
		init();
		
		serverHandler = NetSdk.CLIENT_ListenServer(address, port, 1000, ServiceCB.getInstance(), null);
		if (0 == serverHandler.longValue()) {
			logger.error("Failed to start server. " + getErrorCode());
		}
		return serverHandler.longValue() != 0;
	}
	
	/**
	 * 结束服务
	 * @return
	 */
	public boolean stopServer() {
	
		boolean stopSuccess = NetSdk.CLIENT_StopListenServer(AutoRegister.serverHandler);		
		
		finishBusiness();
		
		cleanup();
		
		return stopSuccess;
	}
	
	/**
	 * 主动注册方式登录
	 * @param address
	 * @param port
	 * @param userName
	 * @param password
	 * @param sn 序列号, 必填
	 * @return
	 */
	public static NativeLong login(String address, int port, String userName, String password, String sn) {
		logger.info("Function: login " + address + " sn " + sn + "]");
		final int tcpSpecCap = 2;// 主动注册方式
		final IntByReference errorReference = new IntByReference(0);
		
		
		// 将 序列号 转化为 pointer 类型
		com.netsdk.lib.NativeString serial = new com.netsdk.lib.NativeString(sn);
		NativeLong handle = NetSdk.CLIENT_LoginEx2(address, port, userName, password, tcpSpecCap, serial.getPointer(), deviceinfo, errorReference);
		//logger.info("CLIENT_LoginEx2"+handle);
		if (0 == handle.longValue()) {
			logger.error("Failed to Login " + address + getErrorCode());
			return null;
		}
		//logger.info("Success to Login " + address);
		return handle;
	}
	
	public static void logout(NativeLong handle) {
		logger.info("[Function: Logout device.");
		NetSdk.CLIENT_Logout(handle);
	}
	
	/**
	 * 开门
	 * @param handle
	 * @return
	 */
	public static boolean openDoor(NativeLong handle) {
		logger.info("[Function: openDoor.]");
		
		NetSDKLib.NET_CTRL_ACCESS_OPEN open = new NetSDKLib.NET_CTRL_ACCESS_OPEN();
		open.nChannelID = 0;
		
		open.write();
		boolean openSuccess = NetSdk.CLIENT_ControlDeviceEx(handle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_ACCESS_OPEN, open.getPointer(), null, 3000);
		if (!openSuccess) {
			logger.error("Failed to open door." + getErrorCode());
		}
		open.read();
		
		return openSuccess;
	}
	
	// 回调建议写成单例模式
	private static class MessCallBack implements fMessCallBack {
		private MessCallBack() {}
		static MessCallBack msgCallBack = new MessCallBack();
		
		public static MessCallBack getInstance() {
			return msgCallBack;
		}

		public boolean invoke(int lCommand, final NativeLong lLoginID,
				Pointer pStuEvent, int dwBufLen, String strDeviceIP,
				NativeLong nDevicePort, NativeLong dwUser) {
			
			logger.info(">> Event invoke. alarm command 0x" + Integer.toHexString(lCommand));
			
			final Device device = getDevice(lLoginID);
			if (device != null) {
				logger.info("\t" + device.toString());
			} 
			
			/// 具体事件类, 
			switch (lCommand) {
				case NetSDKLib.NET_ALARM_ACCESS_CTL_EVENT: {
					NetSDKLib.ALARM_ACCESS_CTL_EVENT_INFO info = new NetSDKLib.ALARM_ACCESS_CTL_EVENT_INFO();
					ToolKits.GetPointerData(pStuEvent, info);
					//logger.info(info.toString());
					
					if (info.nErrorCode == 0x10) {
						// 密码开门
						if (info.emOpenMethod == NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_PWD_ONLY) {
							logger.info("密码开门失败");
						}
						else if (info.emOpenMethod == NetSDKLib.NET_ACCESS_DOOROPEN_METHOD.NET_ACCESS_DOOROPEN_METHOD_CARD) { 
						// 刷卡开门  - (1202B-D 的 二维码方式)
							logger.info("刷卡方式失败");
						}
					}
					

					/// 触发开门
					new Thread(new Runnable() {
						@Override
						public void run() {
							openDoor(lLoginID);
						}
					}).start();
					
					break;
				}
				case NetSDKLib.NET_ALARM_ALARMCLEAR: {
					break;
				}
				case NetSDKLib.NET_ALARM_ALARM_EX: {
					break;
				}
				case NetSDKLib.NET_ALARM_TALKING_INVITE : //  设备请求对方发起对讲事件
		  			NetSDKLib.ALARM_TALKING_INVITE_INFO stu_Invite_Info = new NetSDKLib.ALARM_TALKING_INVITE_INFO();
		  			ToolKits.GetPointerData(pStuEvent, stu_Invite_Info);
		  			
//		  			logger.info("\temCaller :" + stu_Invite_Info.emCaller);
//		  			logger.info("\tstuTime :" + stu_Invite_Info.stuTime);
//		  			logger.info("\tszCallID :" + new String(stu_Invite_Info.szCallID).trim());
//		  			logger.info("\tnLevel :" + stu_Invite_Info.nLevel);
		  			
		  			break;
				case NetSDKLib.NET_ALARM_ALARM_EX2: // 本地报警事件
		  			NetSDKLib.ALARM_ALARM_INFO_EX2 stuALARM_ALARM_INFO_EX2 = new NetSDKLib.ALARM_ALARM_INFO_EX2();
		  			ToolKits.GetPointerData(pStuEvent, stuALARM_ALARM_INFO_EX2);
		  			
		  			logger.info("\tChannel is " + stuALARM_ALARM_INFO_EX2.nChannelID);
		  			logger.info("\tAction is " + stuALARM_ALARM_INFO_EX2.nAction);
		  			logger.info("\tHappend time is " + stuALARM_ALARM_INFO_EX2.stuTime.toStringTime());
		  			logger.info("\tSense type is " + stuALARM_ALARM_INFO_EX2.emSenseType + "\r\n");
		  			logger.info("\tDefence area type is " + stuALARM_ALARM_INFO_EX2.emDefenceAreaType);
		  			
		  			if(stuALARM_ALARM_INFO_EX2.emSenseType == 1) 
		  			{
		  				logger.info("\t被动红外对射入侵报警");
		  			} else if(stuALARM_ALARM_INFO_EX2.emSenseType == 5) 
		  			{
		  				logger.info("\t主动红外对射入侵报警");
		  			}

		  			break;
				default:
					break;
			}
			
			return true;
		}
	}
	
	/**
	 * 监听事件
	 */
	public static boolean startListenAlarm(NativeLong handle) {
		
		/// 设置报警事件回调
		NetSdk.CLIENT_SetDVRMessCallBack(MessCallBack.getInstance(), null);
		return NetSdk.CLIENT_StartListenEx(handle);
	}
	
	/**
	 * 结束监听事件
	 */
	public static void stopListenAlarm(NativeLong handle) {
		NetSdk.CLIENT_StopListen(handle);
	}
	
	/**
	 * 获取接口错误码
	 * @return
	 */
	public static String getErrorCode() {
		return " { error code: ( 0x80000000|" + (NetSdk.CLIENT_GetLastError() & 0x7fffffff) +" ). 参考  NetSDKLib.java }";
	}
	
	/**
	 * 完成业务
	 */
	public void finishBusiness() {
		
		//logger.info("[finishBusiness]");
		
		for (Device device : deviceList) {
			if (device.loginSuccess) {
				stopListenAlarm(device.handle);
				
				logout(device.handle);
			}
		}
	}/**/
	
	/**
	 * 业务接口
	 */
	public static class Business implements Runnable {
		private Device device;
		
		public Business(Device device) {
			this.device = device;
		}
		
		public void run() {
			logger.info("Begin of doing business.");
			
			System.out.println("--------------------Business-----------------");
			
			/// 登录设备
			device.handle = login(device.address, device.port, device.username, device.password, device.serialNumber);
			if (device.handle == null || device.handle.longValue() == 0) {
				logger.error("Failed to Login." + getErrorCode());
				return;
			}
			device.loginSuccess = true;
			/// 监听事件
			startListenAlarm(device.handle);
					
			//调用订阅方法
			int chNum=deviceinfo.byChanNum;
			logger.info(deviceinfo+"：一共有"+chNum+"个通道");
			//subscrible(0, 1,device);
			
			/*if(chNum==0||chNum==1){
				subscrible(0, 1,device);
				logger.info("开始订阅第0个通道！");
			}else if(chNum>1){
				for(int i=0;i<chNum;i++){
					subscrible(i, 1,device);
					logger.info("开始订阅第"+i+"个通道！");
				}
			}*/
			//subscrible(0, 1,device);
			
			subscrible(2, 1,device);
		
			
			/// 下发卡, 请查考 JNADemoCommon.java
			// RecordSetAccess() 函数	
			
			logger.info("End of doing business.");
		}
	} 

public static  void test(){
		//InetAddress address = InetAddress.getByName("122.226.50.246");
		InetAddress address=null;
		try {
			initBean();
			address = InetAddress.getByName(propertiesService.getCurrentServer());
			logger.info("当前服务器ip："+propertiesService.getCurrentServer());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		Integer port = Integer.valueOf(8885);
		
		final AutoRegister demo = new AutoRegister();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				demo.stopServer();		
				synchronized (demo) {
					demo.notify();
				}
				
				logger.info("Stop Server Success");
			}
		}));	
				
		try {
			boolean retVal = demo.startServer(address.getHostAddress(), port);
			if (!retVal) {
				logger.error("Failed to Start Service " + address.getHostAddress());
			}
			
			logger.info("Start Server Success. [" + address.getHostAddress() + ":" + port + "]");
			
			synchronized (demo) {
				demo.wait();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 		
	}
	public static void main(String[] args) {
		test();
	}
	
	/**订阅
	 * 说明：
	 *通道数可以在有登录是返回的信息 m_stDeviceInfo.byChanNum 获取
	 *下列仅订阅了0通道的智能事件.
	 * @param channelId 通道号
	 * @param bNeedPicture 是否需要图片 1.表示需要 0.表示不需要
	 * @param loginHandle 登录句柄
	 * */
	public static  void subscrible(int channelId,int bNeedPicture,Device device){
		  attachHandle =  NetSdk.CLIENT_RealLoadPictureEx(device.handle, channelId,  NetSDKLib.EVENT_IVS_ALL , bNeedPicture , m_AnalyzerDataCB , null , null);
		  if( attachHandle.longValue() != 0  )
	        {
			  device.handle=attachHandle;
			  addDevice(device);
	    		logger.info("[通道" + channelId + "] 订阅成功！");
	        }
	        else
	        {
	            System.out.printf("CLIENT_RealLoadPictureEx Failed!LastError = %x\n", NetSdk.CLIENT_GetLastError() );
	            return;
	        }
	}
	
	
	/* 智能报警事件回调 */
    public static class fAnalyzerDataCB implements NetSDKLib.fAnalyzerDataCallBack, StdCallCallback {
        private String m_imagePath;
    	 String EventMsg; // 事件信息
    	 NetSDKLib.NET_MSG_OBJECT plateObject; // 车牌信息
	     NetSDKLib.NET_MSG_OBJECT vehicleObject; // 车辆信息
	     NetSDKLib.NET_TIME_EX utc; // 事件时间
	     NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO deviceInfo; //设备信息
	     //NetSDKLib.NET_VEHICLE_INFO vehicleInfo;//车辆信息
	        String bigPicture; // 大图
	        String smallPicture; // 小图
	              
	        public fAnalyzerDataCB() {
	        	m_imagePath = "./PlateNumber/";
	            File path = new File(m_imagePath);
	            if (!path.exists()) {
	                path.mkdir();
	            }
	        }
		@Override
		public int invoke(NativeLong lAnalyzerHandle, int dwAlarmType, Pointer pAlarmInfo, Pointer pBuffer,
				int dwBufSize, Pointer dwUser, int nSequence, Pointer reserved) {
			//logger.info("开始扫描。。。。。。。。");
			// private String m_imagePath;
		     //logger.info("nativeLOng："+lAnalyzerHandle);
		    
			  // 获取事件信息        
            plateObject = new NetSDKLib.NET_MSG_OBJECT(); // 车牌信息
            vehicleObject = new NetSDKLib.NET_MSG_OBJECT(); // 车辆信息
            utc = new NetSDKLib.NET_TIME_EX(); // 事件时间
            deviceInfo=new NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO();//设备信息
            EventMsg = ""; // 事件信息
            //车辆识别
            GetStuObject(dwAlarmType, pAlarmInfo);
            
            Calendar calendar = Calendar.getInstance();
            String path = File.separator + "image" + File.separator + "snapshot"+
            			File.separator+calendar.get(Calendar.YEAR) +
            			File.separator+(calendar.get(Calendar.MONTH)+1) +
            			File.separator+calendar.get(Calendar.DAY_OF_MONTH) +
            			File.separator;
            
            String bPath = path + "B_" + UUID.randomUUID().toString() + ".jpg";
            String sPath = path+ "S_" + UUID.randomUUID().toString() + ".jpg";
            //本地存放图片的路径
            String bigPath = propertiesService.getWindowsRootPath() + bPath;
            String smailPath = propertiesService.getWindowsRootPath() + sPath; 
            //在页面上请求并保存到数据库的图片路径
            String queryBigPath  = "http://" + propertiesService.getCurrentServerForImage()+
            		bPath.replaceAll("\\\\", "/");
            String querySmailPath  = "http://" + propertiesService.getCurrentServerForImage()+
            		sPath.replaceAll("\\\\", "/");
            SavePlatePic(bigPath,smailPath,plateObject, pBuffer, dwBufSize);
            try {
            	//车牌号
            	String carCode = new String(plateObject.szText, "GBK").trim();
            	//车牌颜色
            	String carCardColor = null;
            	try {
					if (1 == plateObject.bColor) {
						carCardColor = GetColorString(plateObject.rgbaMainColor);
					}
				} catch (Exception e) {
					logger.info("获取车牌颜色失败");
				}
            	//String carCardColor = new String(plateObject.,"GBK").trim();
            	//Integer red = vehicleInfo.stuPlateColor.nRed;
            	//Integer green = vehicleInfo.stuPlateColor.nGreen;
            	//Integer blue = vehicleInfo.stuPlateColor.nBlue;
            	// 保存大图片
            	//抓拍时间
            	String time = String.format("%02d:%02d:%02d",utc.dwHour, utc.dwMinute, utc.dwSecond);
				logger.info("车牌号："+carCode);
				logger.info("进出时间："+time);
				//logger.info(getDevice(lAnalyzerHandle));
				//logger.info("注册id:"+lAnalyzerHandle);
				String vehicleJson=new String(plateObject.szText, "GBK").trim();
				//WsPool.sendMessageToAll(vehicleJson);
				//根据设备注册id查询设备
				initBean();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
				CarSnapshotResponse snapshotInOutImage = snapshot.saveCarSnapshotOutInfoByCondition(carCode,carCardColor,lAnalyzerHandle.toString(), df.format(new Date()), queryBigPath,querySmailPath);

				/*Map<String, Object> map=new HashMap<>();
		    	map.put("type", "vehicle");
		        map.put("data", snapshotInOutImage);*/

				//用通用的socket返回车辆识别的数据
				//WsPool.sendMessageToAll(JSONObject.toJSONString(map));

				WsPool.sendMessageToAll(JSONObject.toJSONString(snapshotInOutImage));
				//查询车辆所属企业
				//获取通道号和获取设备地址
				//logger.info("设备地址："+new String(deviceInfo., "GBK").trim());
				//	logger.info("设备ID："+new String(deviceInfo.szDeviceID, "GBK").trim());
				
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
			return 0;
		}

		private boolean GetStuObject(int dwAlarmType, Pointer pAlarmInfo) {
			if(pAlarmInfo == null) {
        		return false;
        	}
        	NetSDKLib.NET_EVENT_FILE_INFO fileInfo = new NetSDKLib.NET_EVENT_FILE_INFO(); 
        	
        	boolean isAlarmTypeParsed = true;
        	//logger.info("dwAlarmType:"+dwAlarmType);
        	switch(dwAlarmType)
            {
	        	
	        	case NetSDKLib.EVENT_IVS_VEHICLE_RECOGNITION:  ///< 车牌对比事件
	        	{	
	        		NetSDKLib.DEV_EVENT_VEHICLE_RECOGNITION_INFO msg = new NetSDKLib.DEV_EVENT_VEHICLE_RECOGNITION_INFO();
	            	ToolKits.GetPointerData(pAlarmInfo, msg);
	            	
	                plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    
                    utc = msg.UTC;
                    
                    
                    //获取车辆信息
                    //vehicleInfo = new NetSDKLib.NET_VEHICLE_INFO();
                    
                    
                    
                   // logger.info("channel : " + msg.nChannel);
                    
                    // 对比信息在   msg.stuCarCandidate数组里
	
	                EventMsg = "[ "+ "车辆动作:" + msg.nVehicleAction + ";" + utc.toStringTime() + " ] " + "车牌对比事件";   
	        		
	        		break;
	        	}
	          
        		case NetSDKLib.EVENT_IVS_TRAFFIC_TURNRIGHT: ///<违章右转
        		{
        			NetSDKLib.DEV_EVENT_TRAFFIC_TURNRIGHT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_TURNRIGHT_INFO();
        			ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                 /*   lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "违章右转事件";
        			break;
        		}
        		case NetSDKLib.EVENT_IVS_TRAFFIC_UTURN: ///<违章掉头
        		{
        			NetSDKLib.DEV_EVENT_TRAFFIC_UTURN_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_UTURN_INFO();
        			ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
               /*     lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "违章掉头事件";
        			break;
        		}
        		case NetSDKLib.EVENT_IVS_TRAFFIC_RUNYELLOWLIGHT: ///<闯黄灯
        		{
        			NetSDKLib.DEV_EVENT_TRAFFIC_RUNYELLOWLIGHT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_RUNYELLOWLIGHT_INFO();
        			ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                 /*   lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "闯黄灯事件";
        			break;
        		}
                case NetSDKLib.EVENT_IVS_TRAFFICJUNCTION: ///< 路口事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFICJUNCTION_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                	ToolKits.GetPointerData(pAlarmInfo, msg);//设备信息
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    deviceInfo=msg.stTrafficCar;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "路口事件";
                   // logger.info("设备："+deviceInfo.szDeviceAddress);
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_RUNREDLIGHT: ///< 交通违章-闯红灯事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_RUNREDLIGHT_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                 /*   lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "闯红灯交通违章-闯红灯事件";
                    
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_OVERLINE: ///< 交通违章-压车道线事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_OVERLINE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERLINE_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "压车道线事件";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_OVERYELLOWLINE: ///< 交通违章-压黄线事件
                {
                	 NetSDKLib.DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERYELLOWLINE_INFO();
                	 ToolKits.GetPointerData(pAlarmInfo, msg);
                     plateObject = msg.stuObject;
                     vehicleObject = msg.stuVehicle;
                     utc = msg.UTC;
                   /*  lane = msg.nLane;                 
                     nConfide = msg.stuObject.nConfidence;
                     fileInfo = msg.stuFileInfo;*/
                     EventMsg = "[ "+ utc.toStringTime() + " ] " + "压黄线事件";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_OVERSPEED: ///< 交通违章-超速
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_OVERSPEED_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_OVERSPEED_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                /*    lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "超速事件";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_PARKING: ///< 交通违章-违章停车
                {
                	 NetSDKLib.DEV_EVENT_TRAFFIC_PARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKING_INFO();
                	 ToolKits.GetPointerData(pAlarmInfo, msg);
                     plateObject = msg.stuObject;
                     vehicleObject = msg.stuVehicle;
                     utc = msg.UTC;
                   /*  lane = msg.nLane;                 
                     nConfide = msg.stuObject.nConfidence;
                     fileInfo = msg.stuFileInfo;*/
                     EventMsg = "[ "+ utc.toStringTime() + " ] " + "违章停车";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_WRONGROUTE: ///< 交通违章-不按车道行驶
                {
                	 NetSDKLib.DEV_EVENT_TRAFFIC_WRONGROUTE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_WRONGROUTE_INFO();
                	 ToolKits.GetPointerData(pAlarmInfo, msg);
                     plateObject = msg.stuObject;
                     vehicleObject = msg.stuVehicle;
                     utc = msg.UTC;
                  /*   lane = msg.nLane;                 
                     nConfide = msg.stuObject.nConfidence;
                     fileInfo = msg.stuFileInfo;*/
                     EventMsg = "[ "+ utc.toStringTime() + " ] " + "不按车道行驶";
                     break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_YELLOWPLATEINLANE: ///< 交通违章-黄牌车占道事件
                {
                	 NetSDKLib.DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_YELLOWPLATEINLANE_INFO();
                	 ToolKits.GetPointerData(pAlarmInfo, msg);
                     
                     plateObject = msg.stuObject;
                     vehicleObject = msg.stuVehicle;
                     utc = msg.UTC;
                   /*  lane = msg.nLane;                 
                     nConfide = msg.stuObject.nConfidence;
                     fileInfo = msg.stuFileInfo;*/
                     EventMsg = "[ "+ utc.toStringTime() + " ] " + "黄牌车占道事件";
                     break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_VEHICLEINROUTE: ///< 有车占道事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_VEHICLEINROUTE_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "有车占道事件";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_MANUALSNAP: /// 手动抓拍事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_MANUALSNAP_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);              	     	
                	
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "手动抓拍事件";
                    
                	JOptionPane.showMessageDialog(null, "手动抓拍成功！");   
                	
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_THROW: /// 抛洒物
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_THROW_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_THROW_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "抛洒物";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_PEDESTRAIN: /// 交通行人
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_PEDESTRAIN_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PEDESTRAIN_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    nConfide = msg.stuObject.nConfidence;
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "交通行人";
                	break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFICJAM: /// 交通拥堵
                {
                	NetSDKLib.DEV_EVENT_TRAFFICJAM_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFICJAM_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    utc = msg.UTC;
                  /*  lane = msg.nLane;                 
                    fileInfo = msg.stuFileInfo;*/
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "交通拥堵";
                    break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACEPARKING: /// 车位有车事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                 /*   lane = msg.nLane;               */  
                    fileInfo = msg.stuFileInfo;
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "车位有车事件";
                    
//                    NetSDKLib.DEV_EVENT_TRAFFIC_TRAFFICCAR_INFO trafficCar = msg.stTrafficCar;                  
//                    logger.info("停车场车位号：" + new String(trafficCar.szCustomParkNo).trim()
//                    		 +"\n行驶方向 " + trafficCar.byDirection 
//                    		 +"\nVehicleSize "+ trafficCar.nVehicleSize
//                    		 +"\nszPlateColor "+ new String(trafficCar.szPlateColor).trim()
//                    		 +"\nszVehicleColor "+ new String(trafficCar.szVehicleColor).trim()
//                    		);
                    
                	break;
                }
                case NetSDKLib.EVENT_IVS_TRAFFIC_PARKINGSPACENOPARKING: /// 车位无车事件
                {
                	NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO msg = new NetSDKLib.DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO();
                	ToolKits.GetPointerData(pAlarmInfo, msg);
                    plateObject = msg.stuObject;
                    vehicleObject = msg.stuVehicle;
                    utc = msg.UTC;
                 /*   lane = msg.nLane;                 */
                    fileInfo = msg.stuFileInfo;
                    EventMsg = "[ "+ utc.toStringTime() + " ] " + "车位无车事件";
                	break;
                }
                default:
                	isAlarmTypeParsed = false;
                    System.out.printf("Get Event 0x%x\n", dwAlarmType);
                    EventMsg = "未处理事件 dwAlarmType = " + String.format("0x%x", dwAlarmType); 
                    break;
            }
        	
        	if (isAlarmTypeParsed) {
        		EventMsg = EventMsg + ";组编号 GroupID = " + fileInfo.nGroupId 
        							+ ";图片组总数 bount = " + fileInfo.bCount 
        							+ ";当前图片序号 bIndex = " + fileInfo.bIndex 
        							+ ";置信度 = ";
        		
        		// 车牌
        		String PlateNumber = null;
				try {
					PlateNumber = new String(plateObject.szText, "GBK").trim();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		if (PlateNumber.length() > 0) {
        			EventMsg += ";车牌号 = " + PlateNumber;
        		}
        		
        		// 车牌类型
        		String plateType =  new String(plateObject.szObjectSubType).trim();
        		if (plateType.length() > 0) {
        			EventMsg += ";车牌类型 = " + plateType;
        		}
        		
        		// 车标
        		String vehicleType = new String(vehicleObject.szText).trim();
        		if (vehicleType.length() > 0) {
        			EventMsg += ";车标 = " + vehicleType;
        		}
        		
        		//　车辆类型
        		String vehicleSubType = new String(vehicleObject.szObjectSubType).trim();
        		if (vehicleSubType.length() > 0) {
        			EventMsg += ";车辆类型  = " + vehicleSubType;
        		}
        		
        		
        		EventMsg += "\n";
        		return true;
        	}
        	else {
        		return false;
        	}
			
		}
		 // 颜色对照表渣土项目数据库存放 1:蓝色,2:黄色,3:白色,4:黑色,9:其他
        private String GetColorString(int RGBColor) {
        	String strColor = new String("");
        	int Color = (RGBColor >> 8) & 0x00ffffff;
        	switch(Color) {
        	case 0x000000:
        		strColor = "4";
        		break;
        	case 0xFFFFFF:
        		strColor = "3";
        		break;
        	case 0xFF0000:
        		strColor = "红色";
        		break;
        	case 0x0000FF:
        		strColor = "1";
        		break;
        	case 0x00FF00:
        		strColor = "绿色";
        		break;
        	case 0xFFFF00:
        		strColor = "2";
        		break;
        	case 0x808080:
        		strColor = "灰色";
        		break;
        	case 0xFFA500:
        		strColor = "橙色";
        		break;
        	}
        	
        	return strColor;
        }
		 // 显示车牌小图:大华早期交通抓拍机，设备不传单独的车牌小图文件，只传车牌在大图中的坐标；由应用来自行裁剪。
        // 2014年后，陆续有设备版本，支持单独传车牌小图，小图附录在pBuffer后面。
        private void SavePlatePic(String bigPath,String smailPath,NetSDKLib.NET_MSG_OBJECT plateObject, Pointer pBuffer, int dwBufSize) {
        	// 清空
        	
			BufferedImage snapImage = null;
        	
        	if (pBuffer != null && dwBufSize > 0) {
        		File file = new File(bigPath);
        		File parentFile = file.getParentFile();
        		if(!parentFile.exists()){
        			if(!parentFile.mkdirs()){
        				bigPath = "B_" + UUID.randomUUID().toString() + ".jpg";
        			}
        		}
        		if(!file.exists()){
        			try {
						if(!file.createNewFile()){
							bigPath = "B_" + UUID.randomUUID().toString() + ".jpg";
						}
					} catch (IOException e) {
					}
        		}
            	bigPicture = bigPath;
            	ToolKits.savePicture(pBuffer, 0, dwBufSize, bigPicture);
        	}
        	
        	if (plateObject.bPicEnble == 1) {
        		//根据pBuffer中数据偏移保存小图图片文件
        		int picLength = plateObject.stPicInfo.dwFileLenth;
        		if (picLength > 0) {
        			File file = new File(smailPath);
            		File parentFile = file.getParentFile();
            		if(!parentFile.exists()){
            			if(!parentFile.mkdirs()){
            				smailPath = "S_" + UUID.randomUUID().toString() + ".jpg";
            			}
            		}
            		if(!file.exists()){
            			try {
    						if(!file.createNewFile()){
    							smailPath = "S_" + UUID.randomUUID().toString() + ".jpg";
    						}
    					} catch (IOException e) {
    					}
            		}
            		smallPicture = smailPath;
            		ToolKits.savePicture(pBuffer, plateObject.stPicInfo.dwOffSet, picLength, smallPicture);
        		}
        		
    			if(smallPicture == null) {
    				return;
    			}
    			
        	}	
        	else {
        		if(plateObject.BoundingBox == null) {
        			return;
        		}
        		//根据大图中的坐标偏移计算显示车牌小图
                if (plateObject.BoundingBox.bottom.longValue() == 0 
                		&& plateObject.BoundingBox.top.longValue() == 0) {
                    return ;
                }

                NetSDKLib.DH_RECT dhRect = plateObject.BoundingBox;
        		//1.BoundingBox的值是在8192*8192坐标系下的值，必须转化为图片中的坐标
                //2.OSD在图片中占了64行,如果没有OSD，下面的关于OSD的处理需要去掉(把OSD_HEIGHT置为0)
        		final int OSD_HEIGHT = 0;
        		
                long nWidth = snapImage.getWidth(null);
                long nHeight = snapImage.getHeight(null);
                
                nHeight = nHeight - OSD_HEIGHT;
                if ((nWidth <= 0) || (nHeight <= 0)) {
                    return ;
                }
                
                NetSDKLib.DH_RECT dstRect = new NetSDKLib.DH_RECT();
                
                dstRect.left.setValue((long)((nWidth * dhRect.left.longValue()) / 8192.0));
                dstRect.right.setValue((long)((nWidth * dhRect.right.longValue()) / 8192.0));
                dstRect.bottom.setValue((long)((nHeight * dhRect.bottom.longValue()) / 8192.0));
                dstRect.top.setValue((long)((nHeight * dhRect.top.longValue()) / 8192.0));

                int x = dstRect.left.intValue();
                int y = dstRect.top.intValue() + OSD_HEIGHT;
                int w = dstRect.right.intValue() - dstRect.left.intValue();
                int h = dstRect.bottom.intValue() - dstRect.top.intValue();
                //logger.info(" x =" + x + ", y =" + y + "; w = "+ w +"; h = "+ h);
                try {
                    BufferedImage plateImage = snapImage.getSubimage(x, y, w, h);
                    smallPicture = m_imagePath + "small_" + UUID.randomUUID().toString() + ".jpg";
                    ImageIO.write(plateImage, "jpg", new File(smallPicture));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }
    
    }
    private static  NativeLong attachHandle = new NativeLong(0); // 智能事件订阅句柄
	
	private static  fAnalyzerDataCB    m_AnalyzerDataCB = new fAnalyzerDataCB(); // 智能事件回调 
	
	
}
