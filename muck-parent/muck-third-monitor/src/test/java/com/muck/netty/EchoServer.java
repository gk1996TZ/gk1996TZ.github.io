package com.muck.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		super();
		this.port = port;
	}
	public void start(){
		NioEventLoopGroup group=new NioEventLoopGroup();
		ServerBootstrap serverBootstrap=new ServerBootstrap();
		serverBootstrap.group(group);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.localAddress(new InetSocketAddress(port));
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline channelPipeline=ch.pipeline();
				channelPipeline.addLast(new EchoServerHander());
			}
			
		});
		try {
			ChannelFuture cf=serverBootstrap.bind().sync();
			System.out.println(EchoServer.class.getName()+"started on linstener "+cf.channel().localAddress());
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
            try {
				group.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}            //10
        }
		
		
	}
	
    public static void main(String[] args) throws Exception {
      /*  if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                    " <port>");
            return;
        }*/
        int port = Integer.parseInt("8989");        //1
        new EchoServer(port).start();                //2
    }
	
}
