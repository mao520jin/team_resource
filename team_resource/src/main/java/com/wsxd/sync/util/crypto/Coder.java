package com.wsxd.sync.util.crypto;

/**
 * 
 * 转码
 * 
 * @author chenfan
 * @version 1.0, 2015/10/07
 *
 */
public class Coder {

	/**
	 * 
	 * 字节数组转十六进制
	 * 
	 * @param b
	 * @return
	 * 
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(String.format("%02x", Integer.valueOf(b[i] & 0xFF)));
		}
		return sb.toString();
	}

	/**
	 * 
	 * 十六进制转字节数组
	 * 
	 * @param s
	 * @return
	 * 
	 */
	public static byte[] hex2byte(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0, j = 0; i < b.length; i++, j = i * 2) {
			b[i] = (byte) Integer.parseInt(s.substring(j, j + 2), 16);
		}
		return b;
	}

}
