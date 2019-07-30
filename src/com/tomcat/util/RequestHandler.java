package com.tomcat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler implements Runnable {
	public RequestHandler(InputStream in,OutputStream out ,String root) {
		this.in = in;
		this.out=out;
		this.ROOT=root;
	}

	// 默认访问路径
	private static String url = "/ROOT/index.html";

	private InputStream in = null;
	
	private OutputStream out = null;
	
	private String ROOT=null;
	@Override
	public void run() {
		System.out.println("开始处理请求");
		StringBuffer buffer = new StringBuffer();
		byte[] b = new byte[2048];
		int i;
		try {
			i = in.read(b);
			for (byte c : b) {
				buffer.append((char)c);
			}
			String temp = buffer.toString();
			//正则匹配url
			Pattern regx =Pattern.compile("\\s/\\S*\\s");
			Matcher matcher = regx.matcher(temp);
			if(matcher.find()) {
				SendResponse send = new SendResponse(out,ROOT+matcher.group().trim());
				send.send();
			}else {
				SendResponse send = new SendResponse(out,ROOT+url);
				send.send();
			}
		} catch (IOException e) {
			System.err.println("读取失败");
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
