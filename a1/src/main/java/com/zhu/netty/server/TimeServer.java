package com.zhu.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

	//构建需要
	//处理客户端连接 和 服务业务调度的两大线程组
	public void bind(int port)throws Exception{
		// reactor 线程模型
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap  = new ServerBootstrap();
			//group 方法设置线程组，通道类型， 绑定网络参数，调度类
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler( new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());
				}
			} );
			//异步调用封装返回对象  sync 同步返回
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
/*	
	//职责链模块
	private class ChildChannelHanndler extends ChannelInitializer<SocketChannel>{
		
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new TimeServerHandler());
		}
		
	}*/
	
	public static void main(String[] args){
		try {
			new TimeServer().bind(8081);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
