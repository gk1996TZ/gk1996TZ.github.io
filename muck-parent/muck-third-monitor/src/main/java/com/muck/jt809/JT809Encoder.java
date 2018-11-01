package com.muck.jt809;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JT809Encoder extends MessageToByteEncoder<Message>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		int capacity=msg.getMsgBody().capacity();
		ByteBuf byteb=Unpooled.buffer(capacity+Message.MSG_AVAILABEL_LENGTH);
		//头标识
		out.writeByte(Message.MSG_HEAD);
		//输出头部数据
		//长度
		out.writeInt(msg.getMsgLength());
		byteb.writeInt(msg.getMsgLength());
		//报文序列号
		out.writeInt(msg.getMsgSN());
		byteb.writeInt(msg.getMsgSN());
		//业务类型
		out.writeShort(msg.getMsgId());
		byteb.writeShort(msg.getMsgId());
		//下级平台接入码
		out.writeInt(msg.getMsgGnsscenterid());
		byteb.writeInt(msg.getMsgGnsscenterid());
		//协议版本号标识
		out.writeBytes(msg.getVersionFlag());
		byteb.writeBytes(msg.getVersionFlag());
		//报文是否加密 0标识不加密 1.标识加密
		out.writeByte(msg.getEncryptFlag());
		byteb.writeByte(msg.getEncryptFlag());
		//数据加密的秘钥
		out.writeInt(msg.getEncryptKey());
		byteb.writeInt(msg.getEncryptKey());
		//数据体
		out.writeBytes(msg.getMsgBody().array());
		byteb.writeBytes(msg.getMsgBody().array());
		byte[] b=new byte[5+22];
		byteb.getBytes(0, b);
		//校验码
		//校验码生成规则：去掉头标识、尾标识和校验码
		//out.getBytes(1,byteBuf);
		out.writeInt(msg.getCrc16code());//CRC 校验码
		//尾标识
		out.writeByte(Message.MSG_TAIL);
	}

}
