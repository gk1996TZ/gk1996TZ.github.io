package com.muck.jt809;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyDecoder extends ByteToMessageDecoder {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

		int head = buffer.getByte(0);
		int tail = buffer.getByte(buffer.readableBytes() - 1);
		
		if (!(head == Message.MSG_HEAD && tail == Message.MSG_TAIL)) {
			logger.info("---package wrong ------");
			return;
		}
		
		// 读取报文长度（目前有不一致情况）
		Message message = this.buildMessage(buffer);
		
		out.add(message);
	}
	
	// 创建消息
	private Message buildMessage(ByteBuf in) {

		Message message = new Message();

		ByteBuf transBuf = transfer(in);// 转义之后的字节缓冲区
		message.setMsgLength(transBuf.readInt());// 读取四个字节的长度
		message.setMsgSN(transBuf.readInt());// 4个字节的报文序列
		message.setMsgId(transBuf.readUnsignedShort());// 2个字节的业务id
		message.setMsgGnsscenterid(transBuf.readInt());// 4个字节的平台接入码

		byte[] bt = new byte[3];
		transBuf.readBytes(bt, 0, bt.length);
		message.setVersionFlag(bt);// 读取3个字节的版本号

		message.setEncryptFlag(transBuf.readByte());// 读取加密标识
		message.setEncryptKey(transBuf.readInt());// 读取秘钥

		ByteBuf bb = Unpooled.buffer(transBuf.readableBytes() - 2);
		byte[] be = new byte[transBuf.readableBytes() - 2];
		transBuf.readBytes(be, 0, be.length);
		bb.writeBytes(be);
		message.setMsgBody(bb); // 数据体

		message.setCrcCode(transBuf.readUnsignedShort());// 读取校验码
		transBuf.release();

		return message;
	}

	public ByteBuf transfer(ByteBuf in) {
		ByteBuf out = Unpooled.buffer(in.readableBytes() - 2);
		StringBuffer sb = new StringBuffer();
		in.skipBytes(1);// 跳过头标识
		while (in.readableBytes() > 1) {// 读到尾标识
			sb.append(byteToHex(in.readByte()));
		}
		in.skipBytes(1);// 跳过尾标识
		String str = sb.toString().replace("5a01", "5b").replace("5a02", "5a").replace("5e01", "5d").replace("5e02",
				"5e");
		// 将字符串以4个长度的形式截取存放到字节数组里面
		for (int i = 0; i < str.length(); i = i + 2) {
			if (i + 2 <= str.length()) {
				out.writeByte(Integer.parseInt(str.substring(i, i + 2), 16));
			}
		}
		in.clear();
		return out;
	}

	/** 将字节转换成十六进制 */
	public String byteToHex(byte bt) {
		String hex = Integer.toHexString(bt & 0xff);
		if (hex.length() == 1) {
			hex = "0" + hex;
		}
		return hex;
	}
}
