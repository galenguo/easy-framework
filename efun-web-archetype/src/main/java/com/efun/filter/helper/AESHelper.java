package com.efun.filter.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
//	public static void main(String[] args) throws UnsupportedEncodingException {
//		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		byte[] secret = "875F8EA63F4DBBE977ABBDADB5D382AA".getBytes("UTF-8");
//		byte[] key = new byte[16];
//		System.arraycopy(secret, 0, key, 0, 16);
//		byte[] iv = new byte[16];
//		System.arraycopy(secret, 16, iv, 0, 16);
//
//		System.out.println("AES加密key，UTF-8编码：" + new String(key, "UTF-8"));
//		System.out.println("AES加密iv，UTF-8编码：" + new String(iv, "UTF-8"));
//
//		String source = "3A323E9AB21A9ADCA69FF9D794CD49EA";
////		System.out.println("原文：" + source);
//		byte[] contentBytes = source.getBytes(Charset.forName("UTF-8"));
//		String result = encrypt(contentBytes, key, iv);
//		System.out.println(ALGORITHM + "加密后密文：" + result);
//
//		contentBytes = Base64.getDecoder().decode("pcEdEVcmVDrTCpb4HzLw49gZ/pINcY6IVup70GZsgAaG/7QvEWh7qlTReGIYFybc");
//		result = decrypt(contentBytes, key, iv);
//		System.out.println(ALGORITHM + "还原文：" + result);
//	}


	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	// 加密
	public static String encrypt(byte[] srcData, byte[] key, byte[] iv) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
			byte[] encData = cipher.doFinal(srcData);
			return Base64.getEncoder().encodeToString(encData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// 解密
	public static String decrypt(byte[] encData, byte[] key, byte[] iv) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
			byte[] decbbdt = cipher.doFinal(encData);
			return new String(decbbdt, Charset.forName("UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}