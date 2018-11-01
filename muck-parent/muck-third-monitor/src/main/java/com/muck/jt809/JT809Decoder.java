package com.muck.jt809;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class JT809Decoder extends ByteToMessageDecoder{
	
	 Logger logger = LoggerFactory.getLogger(getClass());
	 static int count=0;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//System.out.println("开始decoder...");
		count++;
	
		//in.resetReaderIndex();
		//in.setIndex(0, in.capacity());
		//int capacity=in.capacity();//容量大小
		/*System.out.println("容量："+capacity);
		System.out.println(in.readableBytes());
		System.out.println(in.getByte(0));
		System.out.println(in.getByte(in.readableBytes()-1));
		System.out.println("数据接收");
		System.out.println(Message.MSG_HEAD);
		System.out.println(Message.MSG_TAIL);*/
	/*	byte[] be=new byte[in.readableBytes()];
		in.getBytes(0, be);
		StringBuffer sb=new StringBuffer();
		for(byte b:be){
			sb.append(byteToHex(b));
		}
		logger.info(sb.toString());
		logger.info(count+"开头："+in.getByte(0)+"");
		logger.info(count+"结尾："+in.getByte(in.readableBytes()-1)+"");
		int startIndex=in.readerIndex();
		if(sb.length()>(index_5d+2)){
			 startIndex=in.readerIndex();//读取的下标
		}
		int index_5d=sb.lastIndexOf("5d");
		logger.info(count+"读到尾标识了:"+index_5d);
		if(Message.MSG_HEAD==in.getByte(in.readerIndex())&&index_5d!=-1){
			logger.info(count+"读到尾标识了");
			logger.info("index_5d:"+index_5d/2+";startIndex:"+startIndex);
			int end_index=sb.indexOf("5d");
			int s_index=0;
			while(end_index!=index_5d){
				end_index=sb.indexOf("5d");
				ByteBuf bbf=Unpooled.buffer(end_index/2+1-s_index);
				byte[] by=new byte[end_index/2+1-s_index];
				in.readBytes(by, 0, by.length);
				bbf.writeBytes(by);
				buildMessage(message,bbf);
				bbf.release();
				logger.info(count+"循环读取到的内容：结束下标"+end_index+"开始下标"+s_index+";内容："+sb.substring(s_index,end_index/2+1));
				s_index=end_index/2+1;
				out.add(message);
			}*/
			/*ByteBuf bbf=Unpooled.buffer(index_5d/2+1-startIndex);
			byte[] by=new byte[index_5d/2+1-startIndex];
			in.readBytes(by, 0, by.length);
			bbf.writeBytes(by);
			buildMessage(message,bbf);
			bbf.release();
			out.add(message);*/
			
		/*}*/
		if(!(Message.MSG_HEAD==in.getByte(0)&&Message.MSG_TAIL==in.getByte(in.readableBytes()-1))){
			logger.info("数据包不完整");
			//in.skipBytes(in.readableBytes());
		//	System.out.println("数据传输不正确");
		}else{//数据传输不争取
			logger.info("数据包完整");
			//buildMessage(message,in);
			List<Message> list=listMessage(in);
			logger.info("数据包完整:"+list);
			out.add(list);
		}
		
		
		//处理粘包和拆包问题
		/*if(in.readableBytes()>=Message.MSG_LENGTH){//读取的数据包要全
			logger.info(count+"数据包长度>"+Message.MSG_LENGTH);
			//解析数据头
			if(in.getByte(0)==Message.MSG_HEAD){//头标识
				logger.info(count+"数据包读到了头标识");
				in.readByte();//读头标识
				logger.info(count+"数据包开始转义数据头：");
				ByteBuf header=transfer(in.readBytes(Message.MSG_AVAILABEL_LENGTH));//数据头转义
				message.setMsgLength(header.readInt());//读取四个字节的长度
				message.setMsgSN(header.readInt());//4个字节的报文序列
				message.setMsgId(header.readUnsignedShort());//2个字节的业务id
				message.setMsgGnsscenterid(header.readInt());//4个字节的平台接入码
				byte[] bt=new byte[3];
				header.readBytes(bt, 0, bt.length);
				message.setVersionFlag(bt);//读取3个字节的版本号
				message.setEncryptFlag(header.readByte());//读取加密标识
				message.setEncryptKey(header.readInt());//读取秘钥
				int lg=message.getMsgLength()-24;//去掉头标识长度、数据头长度 、尾标识长度、剩下是数据体
				header.release();//释放头缓存
				logger.info(count+"去掉头标识长度、数据头长度 、、校验码、尾标识长度、剩下是数据体："+lg);
				logger.info(count+"可读字节数："+in.readableBytes()+";剩余字节数："+(lg+3));
				if(in.readableBytes()>=lg+3){
					logger.info(count+"数据包开始转义数据体：");
					ByteBuf body=transfer(in.readBytes(lg));//数据体
					message.setCrcCode(in.readUnsignedShort());//读取校验码
					if(in.readByte()==Message.MSG_TAIL){
						logger.info(count+"有尾标识：写数据体");
						ByteBuf bb=Unpooled.buffer(body.readableBytes());
						byte[] bd=new byte[body.readableBytes()];
						body.readBytes(bd, 0, bd.length);
						bb.writeBytes(bd);
						message.setMsgBody(bb);
						out.add(message);
					}else{//没有尾巴，说明数据包不全
						in.resetReaderIndex();
						logger.info(count+"数据包没有读到尾标识");
					}
					body.release();
				}else{
					in.resetReaderIndex();
					logger.info(count+"数据包没有读到数据体");
				}
			}else{
				logger.info(count+"数据包没有读到头标识");
			}
		}else{
			logger.info(count+"数据包长度没有达到"+Message.MSG_LENGTH);
		}*/
		
		
	
	}
	
	private void buildMessage(Message message,ByteBuf in){
		
		
		
		ByteBuf transBuf=transfer(in);//转义之后的字节缓冲区
		message.setMsgLength(transBuf.readInt());//读取四个字节的长度
		message.setMsgSN(transBuf.readInt());//4个字节的报文序列
		message.setMsgId(transBuf.readUnsignedShort());//2个字节的业务id
		message.setMsgGnsscenterid(transBuf.readInt());//4个字节的平台接入码
		
		//处理粘包和拆包问题
		
		/*if(message.getMsgGnsscenterid()==1618){
			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("业务类型："+message.getMsgId());
			logger.info("业务类型："+message.getMsgId());
		}*/
		//ByteBuf bbf=Unpooled.directBuffer(3);
		//ByteBuf bbf=Unpooled.buffer(3);
		//bbf.writeBytes(transBuf.readBytes(3));
		byte[] bt=new byte[3];
		//transBuf.readBytes(3).getBytes(0, bt);
		transBuf.readBytes(bt, 0, bt.length);
		//transBuf.getBytes(0, bt);
		//bbf.release();
		message.setVersionFlag(bt);//读取3个字节的版本号
		message.setEncryptFlag(transBuf.readByte());//读取加密标识
		message.setEncryptKey(transBuf.readInt());//读取秘钥
	//	ByteBuf byteBuf=Unpooled.copiedBuffer(transBuf.readBytes(transBuf.readableBytes()-2));//读取数据体
		
		//检测校验码
	//	byte[] b = new byte[byteBuf.capacity()+22];  
	//	transBuf.getBytes(1, b);
       // System.out.println("校验码："+CRC16CCITT.crc16(b));	
		ByteBuf bb=Unpooled.buffer(transBuf.readableBytes()-2);
		byte[] be=new byte[transBuf.readableBytes()-2];
		transBuf.readBytes(be, 0, be.length);
		bb.writeBytes(be);
		message.setMsgBody(bb);
		message.setCrcCode(transBuf.readUnsignedShort());//读取校验码
		//System.out.println("还有读取的吗："+transBuf.readableBytes());
		//System.out.println(message);
		//byteBuf.release();
		transBuf.release();
	}

	private List<Message> listMessage(ByteBuf in) {
		List<Message> list=new ArrayList<>();
		byte[] be=new byte[in.readableBytes()];
		in.getBytes(0, be);
		StringBuffer sb=new StringBuffer();
		for(byte b:be){
			sb.append(byteToHex(b));
		}
		logger.info(sb.toString());
		logger.info(count+"开头："+in.getByte(0)+"");
		logger.info(count+"结尾："+in.getByte(in.readableBytes()-1)+"");
		/*if(sb.length()>(index_5d+2)){
			 startIndex=in.readerIndex();//读取的下标
		}*/
		//logger.info(count+"读到尾标识了:"+index_5d);
		int end_index=0;//每次读取的位置
		int s_index=0;//上一次的位置
		while(end_index!=sb.lastIndexOf("5d")){
			logger.info(count+"开始循环读取到的内容：结束下标"+end_index+"开始下标"+s_index+";内容："+sb.substring(s_index,end_index+2));
			Message mess=new Message();
				end_index=sb.indexOf("5d5b",end_index+4);//重新获取下一次
				logger.info("5d5b位置："+end_index);
			if(end_index==-1){//没有5d5b了
					end_index=sb.lastIndexOf("5d");
					logger.info("没有5d5ble,定位到最后位置："+end_index);
			}
			
			int length=end_index/2-s_index/2;//数组长度
			if(in.readerIndex()==0){
				length++;
			}
			/*if(length>in.readableBytes()){
				length=in.readableBytes();
			}*/
			ByteBuf bbf=Unpooled.buffer(length);
			byte[] by=new byte[length];
			logger.info("数组长度为：end_index/2+1="+(end_index/2+1)+";-s_index/2="+s_index/2+";结果是="+by.length);
			logger.info("目前的位置:"+in.readerIndex()+"没读之前还剩多少个字节可读："+in.readableBytes());
			logger.info("开始字节:"+Integer.toHexString(in.getByte(in.readerIndex()))+";结束字节:"+Integer.toHexString(in.getByte(in.readerIndex()+by.length-1)));
			in.readBytes(by, 0, by.length);
			logger.info("目前的位置:"+in.readerIndex()+"读了之后还剩多少个字节可读："+in.readableBytes());
			bbf.writeBytes(by);
			buildMessage(mess,bbf);
			list.add(mess);
		//	bbf.release();
			logger.info(count+"循环读取到的内容：结束下标"+end_index+"开始下标"+s_index+";内容："+sb.substring((s_index+2),end_index+2));
			s_index=end_index;//保存位置作为上一次
		}
		return list;
	}
	
	/**将字节读取出来重新排列，进行转义
	 * @param ByteBuf 
	 * @return ByteBuf
	 * */
	public ByteBuf transfer(ByteBuf in){
		//ByteBuf out=Unpooled.directBuffer(in.readableBytes()-2);
		ByteBuf out=Unpooled.buffer(in.readableBytes()-2);
		//ByteBuf out=Unpooled.buffer(in.readableBytes());
		StringBuffer sb=new StringBuffer();
		in.skipBytes(1);//跳过头标识
		while(in.readableBytes()>1){//读到尾标识
			sb.append(byteToHex(in.readByte()));
		}
		/*while(in.readableBytes()>=1){
			sb.append(byteToHex(in.readByte()));
		}*/
		logger.info(count+"数据包转义前数据：");
		logger.info(count+":"+sb.toString());
		in.skipBytes(1);//跳过尾标识
		String str=sb.toString()
		.replace("5a01", "5b")
		.replace("5a02", "5a")
		.replace("5e01", "5d")
		.replace("5e02", "5e")
		;
	/*	int osbl=sb.length();
		int strl=str.length();
		if(osbl>strl){
			for(int i=0;i<osbl-strl;i++){
				str+="0";
			}
		}*/
		logger.info(count+"数据包转义后数据：");
		logger.info(count+":"+str);
		//将字符串以4个长度的形式截取存放到字节数组里面
		for(int i=0;i<str.length();i=i+2){
			if(i+2<=str.length()){
				out.writeByte(Integer.parseInt(str.substring(i, i+2),16));
			}
		}
		in.release();
		return out;
	}
	/**将字节转换成十六进制*/
	public String byteToHex(byte bt){
		String hex= Integer.toHexString(bt & 0xff);
		if(hex.length()==1){
			hex="0"+hex;
		}
		return hex;
	}
}
