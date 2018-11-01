package com.muck.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	static Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		ChannelHanderMap.add(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		ChannelHanderMap.remove(ctx);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    try {
			logger.error("连接出错 id:"+ctx.channel().id().asLongText(), cause);
			ChannelHanderMap.remove(ctx);
			ctx.close();
		} catch (Exception e) {
			logger.error("连接出错 异常",e);
		}

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		if("first_connection".equals(msg.text())){
    		logger.info("第一次连接 id："+ctx.channel().id().asLongText());
			ChannelHanderMap.firstConnectData(ctx);
    	}else if("reconnection".equals(msg.text())){
    		logger.info("重新连接 id："+ctx.channel().id().asLongText());
    	}else if("HeartBeat".equals(msg.text())){
    		logger.info("心跳连接 id："+ctx.channel().id().asLongText());
    		ctx.channel().writeAndFlush(new TextWebSocketFrame("ok"));
    	}
		
		
	}

}
