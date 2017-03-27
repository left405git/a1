package com.zhu.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	public void connect(int port, String host)throws Exception{
		EventLoopGroup clientGroup =  new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(clientGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler( new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeClientHandler());	
				}
			} );
			
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		}finally{
			clientGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args){
		try {
			new TimeClient().connect(8081,"localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
