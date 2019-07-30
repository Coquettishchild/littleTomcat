package com.tomcat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SendResponse {
	private String url;
	private OutputStream out;

	public SendResponse(OutputStream out, String url) {
		this.out = out;
		this.url = url;
	}
	public void send() throws IOException {
		File file = new File(url);
		// 判断请求文件是否存在，如果存在返回
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			byte[] temp = new byte[2048];
			int endflag = in.read(temp);
			while (endflag != -1) {
				out.write(temp);
				endflag = in.read(temp);
			}
			in.close();
			// 如果不存在报404
		} else {
			out.write("404 file not found".getBytes("utf8"));
			System.out.println("响应成功");
		}
	}
}
