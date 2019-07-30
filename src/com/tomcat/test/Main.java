package com.tomcat.test;

import com.tomcat.server.Server;

public class Main {
	public static void main(String[] args) {
		Server server = new Server();
		server.await();
	}
}	
