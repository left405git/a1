package com.zhu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {
	private SocketChannel client;
	private Selector selector;
	
	private ByteBuffer recBuffer = ByteBuffer.allocate(1024);
	
	private ByteBuffer sndBuffer=ByteBuffer.allocate(1024);
	
	private InetSocketAddress serverAddress = new InetSocketAddress("localhost",8080);
	
	public NIOClient() throws IOException{
		client = SocketChannel.open();
		client.configureBlocking(false);
		client.connect( serverAddress	);
		selector = Selector.open();
		client.register(selector, SelectionKey.OP_CONNECT);
	}
	
	public void session()throws IOException{
		if(client.isConnectionPending()){
			client.finishConnect();
			client.register(selector, SelectionKey.OP_WRITE);
			System.out.println("已连接到服务器， 可以在控制台输入信息");
		}
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			String name =  scan.nextLine();
			if("".equals(name))continue;
			if("finish".equals(name)){
				System.exit(0);
			}
			process(name);
		}
	}
	
	private void process(String name)throws IOException{
		boolean waitHelp = true;
		Iterator<SelectionKey> itr = null;
		Set<SelectionKey> keys = null;
		while(waitHelp){
			try {
				int readys = selector.select();
				if(readys==0)continue;
				
				keys = selector.selectedKeys();
				itr = keys.iterator(); 
				
				while(itr.hasNext()){
					SelectionKey  key = itr.next();
					if(key.isValid()&&key.isWritable()){
						//客户端可写，就代表客户端要向服务端发消息
						sndBuffer.clear();
						sndBuffer.put( name.getBytes() );
						sndBuffer.flip();
						client.write(sndBuffer);
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isValid()&&key.isReadable()){
						recBuffer.clear();
						int len = client.read(recBuffer);
						if(len>0){
							recBuffer.flip();
							System.out.println("服务端发来的信息："+new String(recBuffer.array()) );
							client.register(selector, SelectionKey.OP_WRITE);
							waitHelp = false;
						}
					}
					itr.remove();
				}
			} catch (Exception e) {
				client.socket().close();
				client.close();
				return;
			}
		}
		
	}
	
	
	public static void main(String[] args){
		try {
			new NIOClient().session();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
