package com.zhu.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends  ChannelHandlerAdapter{

	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(":client method [channelActive]"); 
		byte[] msg = "客户端请求信息abcde".getBytes("UTF-8") ;
		ByteBuf bf = Unpooled.buffer(msg.length);
		bf.writeBytes(msg);
		ctx.writeAndFlush( bf );
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(":client method [channelRead]"); 
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String serverMsg = new String(req,"UTF-8");
		System.out.println("收到服务器消息："+serverMsg); 
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(":client method [exceptionCaught]"); 
		System.out.println(cause.getMessage());
		ctx.close();
	}
}
