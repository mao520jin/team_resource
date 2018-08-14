package com.wsxd.sync.util.crypto;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.wsxd.sync.util.DateUtils;

/**
 * AES 128位<br />
 * 对称加密<br />
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午1:59:04
 */
public class AES {

	public static void main(String[] args) throws Exception {
		String keys = "VSTECS";
		String d = DateUtils.toStr();

		String param = "10000001," + d;
		String rs = encrypt(param, keys);
		System.out.println("加密后：" + rs);

		String drs = decrypt(rs, keys);
		System.out.println("解密后：" + drs);

	}

	private static String encrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); // http://blog.csdn.net/jeamking/article/details/8904080 win 下正常 linux下加密结果随机
		random.setSeed(password.getBytes("UTF-8"));
		// kgen.init(128, new SecureRandom(password.getBytes("UTF-8")));
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = content.getBytes("UTF-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		String rs = parseByte2HexStr(result); // 加密
		// System.out.println("encrypt;content:"+content+";password:"+password+";rs:"+rs);
		return rs;
	}

	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @throws Exception
	 */
	private static String decrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); // http://blog.csdn.net/jeamking/article/details/8904080 win 下正常 linux下加密结果随机
		random.setSeed(password.getBytes("UTF-8"));
		// kgen.init(128, new SecureRandom(password.getBytes("UTF-8")));
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(parseHexStr2Byte(content));
		String rs = new String(result, "UTF-8"); // 解密
		// System.out.println("decrypt;content:"+content+";password:"+password+";rs:"+rs);
		return rs;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}
