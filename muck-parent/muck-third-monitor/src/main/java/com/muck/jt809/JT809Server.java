package com.muck.jt809;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务器端的JT809 主链路
 */
public class JT809Server {
	static Logger logger = LoggerFactory.getLogger(JT809Server.class);
	private static int port = 1080;
	
	public static void start() {
		logger.info("netty 车辆定位 启动 端口号" + port);
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		EventLoopGroup bossGroup = new NioEventLoopGroup(10);
		EventLoopGroup workerGroup = new NioEventLoopGroup(10);
		
		final byte[] delimiter2 = new byte[]{0x5d};
		
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_RCVBUF, 32 * 1024)
				// .childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline channelPipeline = ch.pipeline();
						channelPipeline.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
						// 添加解码器
						// channelPipeline.addLast("decoder", new JT809Decoder());
						channelPipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 300,false,Unpooled.copiedBuffer(delimiter2)));
						channelPipeline.addLast("decoder", new MyDecoder());
						
						// 添加编码器
						channelPipeline.addLast("encoder", new JT809Encoder());
						// 业务处理
						channelPipeline.addLast("busiHander", new MyJT809BusiHander());
						// channelPipeline.addLast("busiHander", new JT809BusiHander());
					}
				});

		try {
			// ChannelFuture
			// channelFuture=serverBootstrap.bind(InetAddress.getByName("DESKTOP-37DDR6S"),
			// port).sync();
			// ChannelFuture
			// channelFuture=serverBootstrap.bind(InetAddress.getByName("192.168.0.22"),
			// port).sync();
			// ChannelFuture
			// channelFuture=serverBootstrap.bind(InetAddress.getLocalHost(),
			// port).sync();
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			logger.info(port + "启动了......");
			channelFuture.channel().closeFuture().sync();
			channelFuture.addListener(ChannelFutureListener.CLOSE);
		} catch (InterruptedException e) {
			// e.printStackTrace();
			logger.error("netty出现异常", e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			logger.info("NioEventLoopGroup 关闭");
		}
	}

}
