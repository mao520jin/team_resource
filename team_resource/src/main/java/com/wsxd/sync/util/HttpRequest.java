package com.wsxd.sync.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {
	public static String request(String method, String url, String charset, String data) throws MalformedURLException, IOException {
		if ((method == null) || (url == null) || (data == null) || (charset == null)) {
			return null;
		}

		if ((!"GET".equalsIgnoreCase(method)) && (!"POST".equalsIgnoreCase(method))) {
			return null;
		}

		String spec = url;

		HttpURLConnection conn = (HttpURLConnection) new URL(spec).openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setDefaultUseCaches(false);
		conn.setRequestMethod(method);
		conn.connect();

		if ("POST".equalsIgnoreCase(method)) {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			bw.write(data);
			bw.flush();
			bw.close();
		}

		StringBuffer input = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				input.append(line);
			}
		} catch (Exception localException) {
		} finally {
			br.close();

			conn.disconnect();
		}
		return input.toString();
	}
}