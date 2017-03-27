package com.zhu.netty.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//readindex writeindex 不需要flip切换读写
		System.out.println(":server method [channelRead]"); 
		ByteBuf buf = (ByteBuf)msg;
		byte[] requets = new byte[buf.readableBytes()];
		buf.readBytes(requets);
		String body = new String(requets, "UTF-8" );
		System.out.println(" time server : 客户端请求信息为："+body);
		String response =body+"==="+ (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		ByteBuf sndBuf = Unpooled.copiedBuffer(response.getBytes());
		ctx.write(sndBuf);
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println(":server method [channelReadComplete]"); 
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(":server method [exceptionCaught]"); 
		ctx.close();
	}

	
}
