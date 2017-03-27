package com.zhu.nio;
//1:04
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NIOServer {
	
	private ServerSocketChannel server;
	
	private int port  = 8080;
	
	private Selector selector;
	
	ByteBuffer recBuffer = ByteBuffer.allocate(1024);
	
	ByteBuffer sndBuffer = ByteBuffer.allocate(1024);
	
	private Map<SelectionKey,String> sessionMaps = new HashMap<SelectionKey,String>();
	
	public NIOServer(int port) throws IOException{
		this.port = port;
		server = ServerSocketChannel.open();
		server.socket().bind( new InetSocketAddress(this.port) );
		server.configureBlocking(false);
		selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void listen()throws IOException	{
		while(true){
			int events = selector.select();
			if(events==0) continue;
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> itr = keys.iterator();
			while(itr.hasNext()){
				process(itr.next());
				itr.remove();
			}
		}
		
	}
	private void process(SelectionKey key){
		SocketChannel client = null;
		try {
			if(key.isValid()&& key.isAcceptable()){
				client = server.accept();
				client.configureBlocking(false);
				System.out.println("客户端连接"); 
				client.register(selector, SelectionKey.OP_READ);
			}else if(key.isValid()&&key.isReadable()){
				recBuffer.clear();
				client = (SocketChannel)key.channel();
				int len = client.read(recBuffer);
				if(len>0){
					String msg = new String(recBuffer.array(),0,len);
					sessionMaps.put(key, msg);
					System.out.println("client send msg "+Thread.currentThread().getId()+msg); 
					client.register(selector, SelectionKey.OP_WRITE);
				}
				
			}else if(key.isValid()&&key.isWritable()){
				if(!sessionMaps.containsKey(key)){
					return;
				}
				client =(SocketChannel) key.channel();
				sndBuffer.clear();
				sndBuffer.put( new String( sessionMaps.get(key)  +" === "+(new Date()).getTime() ).getBytes() );
				sndBuffer.flip();
				client.write(sndBuffer);
				client.register(selector, SelectionKey.OP_READ);
			}
		} catch (IOException e) {
			key.cancel();
			try {
				client.socket().close();
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		try {
			new NIOServer(8080).listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
