package com.tomcat.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tomcat.util.RequestHandler;

public class Server {
	
	public Server(int port,String address) {
		this.prot = port;
		this.address = address;
	}
	public Server(int port) {
		this.prot = port;
	}
	public Server(String address) {
		this.address = address;
	}
	public Server() {
	}
	//初始目录
	private final String ROOT = System.getProperty("user.dir")+File.separator+"webapps";
	//监听地址
	private  String address ="127.0.0.1";
	//监听端口
	private  int prot=8080;
	
	public void await() {
		ServerSocket server = null;
		ThreadPoolExecutor  pool = new  ThreadPoolExecutor(10, 30, 30, TimeUnit.SECONDS, new LinkedTransferQueue<Runnable>());
		try {
			server = new ServerSocket(prot,30,InetAddress.getByName(address));
			System.out.println("开始监听");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ServerSocket 初始化出错");
		}
		while(true) {
			try {
				Socket socket = server.accept();
				RequestHandler handler = new RequestHandler(socket.getInputStream(),socket.getOutputStream(),ROOT);
				pool.execute(handler);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println();
			}
		}
	}
	
}
