package com.wsxd.sync.util.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 字符串加密、文件完整性检验
 * 
 * <ul>
 * <li>MD2
 * <li>MD5
 * <li>SHA-1
 * <li>SHA-256
 * <li>SHA-384
 * <li>SHA-512
 * </ul>
 * 
 * @author chenfan
 * @version 1.0, 2015/10/07
 *
 */
public class MD {

	/**
	 * 
	 * MD2(32位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String md2(String input) throws NoSuchAlgorithmException, IOException {
		return digest("MD2", input);
	}

	/**
	 * 
	 * MD5(32位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String md5(String input) throws NoSuchAlgorithmException, IOException {
		return digest("MD5", input);
	}

	/**
	 * 
	 * SHA-1(40位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String sha1(String input) throws NoSuchAlgorithmException, IOException {
		return digest("SHA-1", input);
	}

	/**
	 * 
	 * SHA-256(64位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String sha256(String input) throws NoSuchAlgorithmException, IOException {
		return digest("SHA-256", input);
	}

	/**
	 * 
	 * SHA-384(96位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String sha384(String input) throws NoSuchAlgorithmException, IOException {
		return digest("SHA-384", input);
	}

	/**
	 * 
	 * SHA-512(128位)
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * 
	 */
	public static String sha512(String input) throws NoSuchAlgorithmException, IOException {
		return digest("SHA-512", input);
	}

	public static String digest(String algorithm, String input) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		File file = new File(input);
		if (file.isFile()) {
			InputStream in = new FileInputStream(input);
			int len = 0;
			byte[] b = new byte[2048];
			while ((len = in.read(b)) != -1) {
				md.update(b, 0, len);
			}
			in.close();
		} else {
			md.update(input.getBytes("UTF-8"));
		}
		return Coder.byte2hex(md.digest());
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String input = "message"; // 字符串
		System.out.println(MD.md2(input)); // 243ef9d20377745273db12632da8ce19
		System.out.println(MD.md5(input)); // 78e731027d8fd50ed642340b7c9a63b3
		System.out.println(MD.sha1(input)); // 6f9b9af3cd6e8b8a73c2cdced37fe9f59226e27d
		System.out.println(MD.sha256(input)); // ab530a13e45914982b79f9b7e3fba994cfd1f3fb22f71cea1afbf02b460c6d1d
		System.out.println(MD.sha384(input)); // 353eb7516a27ef92e96d1a319712d84b902eaa828819e53a8b09af7028103a9978ba8feb6161e33c3619c5da4c4666a5
		System.out.println(MD.sha512(input)); // f8daf57a3347cc4d6b9d575b31fe6077e2cb487f60a96233c08cb479dbf31538cc915ec6d48bdbaa96ddc1a16db4f4f96f37276cfcb3510b8246241770d5952c
	}

}
