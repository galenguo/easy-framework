package com.efun.filter;

import com.efun.filter.helper.*;
import com.efun.filter.wrapper.TestRequestWrapper;
import com.efun.filter.wrapper.TestResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class EncryteFilter implements Filter {

	private byte[] iv;
	private RSAPrivateKey private_key;
	
	private static final String charset = "utf-8";

	/**
	 * 针对iv、privatekey做初始化
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		/*byte[] ivBytes = arg0.getInitParameter("iv").getBytes();
		iv = Arrays.copyOf(ivBytes, 16);*/
		try {
			private_key = getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String encrytkey = req.getHeader("requestID");
		// 这里进行key的rsa解密，iv使用固定iv
		if (null != encrytkey) {
			byte[] md5_key = getAesKeyByRSA(encrytkey);
			System.out.println(new String(md5_key, charset));
			byte[] key = new byte[16];
			byte[] iv = new byte[16];
			System.arraycopy(md5_key, 0, key, 0, 16);
			System.arraycopy(md5_key, 16, iv, 0, 16);
			//解密request
			TestRequestWrapper requestWrapper = new TestRequestWrapper(
					(HttpServletRequest) req, req.getMethod(), key, iv);
			TestResponseWrapper responseWrapper = new TestResponseWrapper(
					(HttpServletResponse) response);
			chain.doFilter(requestWrapper, responseWrapper);

			//加密response

			MyOutputStream myOutputStream = responseWrapper.getMyOutputStream();
			byte[] data = myOutputStream.getCopy();
			if (data != null) {
				String content = new String(data, charset);
				String md5_String = MD5Util.MD5(content);
				key = new byte[16];
				iv = new byte[16];
				System.arraycopy(md5_key, 0, key, 0, 16);
				System.arraycopy(md5_key, 16, iv, 0, 16);
				myOutputStream.writeAll(encrypt(content, key, iv).getBytes("utf-8"));
				responseWrapper.setHeader("responseID", Base64.getEncoder().encodeToString(RSAAlgorithm.encrypt(md5_String.getBytes(charset))));
			}
			myOutputStream.flush();
		} else {
			// 不符合规则可以放行
			chain.doFilter(request, response);
		}

	}

	private boolean rule(HttpServletRequest request) {
		boolean result = false;
		return result;
	}

	private String encrypt(String content, byte[] key, byte[] iv) {
		String str = content;
		try {
			str = AESHelper.encrypt(content.getBytes(charset), key, iv);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 根据rsa算法获取aes加密key
	 * 
	 * @param encrytKey
	 * @return
	 */
	private byte[] getAesKeyByRSA(String encrytKey) {
		byte[] decSource = Base64.getDecoder().decode(encrytKey);
		return RSAAlgorithm.decrypt(decSource);
	}

	/**
	 * 文件中读取privatekey
	 * 
	 * @return
	 * @throws IOException
	 */
	private RSAPrivateKey getPrivateKey() throws IOException {
		char[] buffer = new char[2048];
		StringBuilder sb = new StringBuilder();
		FileReader fr;
		File privateFile = new File(this.getClass().getClassLoader()
				.getResource("private.txt").getFile());
		fr = new FileReader(privateFile);
		try {
			int count;
			while ((count = fr.read(buffer)) > 0) {
				sb.append(new String(buffer, 0, count));
			}
			String privateKey = sb.toString();
			System.out.println("private key : " + privateKey);
			byte[] privatekeyBytes = Base64.getDecoder().decode(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					privatekeyBytes);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			fr.close();
		}

	}

}



