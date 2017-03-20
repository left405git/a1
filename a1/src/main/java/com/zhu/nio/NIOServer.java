package com.zhu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NIOServer {
	
	private ServerSocketChannel server;
	
	private int port  = 8080;
	
	private Selector selector;
	
	ByteBuffer recBuffer = ByteBuffer.allocate(1024);
	
	ByteBuffer sndBuffer = ByteBuffer.allocate(1024);
	
	public NIOServer(int port) throws IOException{
		this.port = port;
		server = ServerSocketChannel.open();
		server.socket().bind( new InetSocketAddress(port) );
		server.configureBlocking(false);
		selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	
}
