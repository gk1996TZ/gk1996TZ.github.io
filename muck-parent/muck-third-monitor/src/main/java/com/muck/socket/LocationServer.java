package com.muck.socket;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muck.jt809.JT809Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class LocationServer {

	static Logger logger = LoggerFactory.getLogger(LocationServer.class);
	private static int port=8899;
	public static void run(){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketChannelInitializer());
            logger.info("netty websocket 启动 端口号"+port);
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(port)).sync();
            channelFuture.channel().closeFuture().sync();
            logger.info("netty websocket 关闭"+port);
        } catch (InterruptedException e) {
			//e.printStackTrace();
        	logger.error("netty websocket", e);
		}finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }

	}
	
}
