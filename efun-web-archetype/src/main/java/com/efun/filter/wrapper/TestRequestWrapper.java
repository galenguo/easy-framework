package com.efun.filter.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.efun.filter.helper.AESHelper;

public class TestRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletRequest request;
	private Map params;
	private String queryString;
	private InputStream inputStream;
	private static final String charset = "utf-8";

	/**
	 * 
	 * @param request
	 * @param method
	 *            请求方法,只处理POST/GET
	 * @param key
	 *            AES加密key
	 * @throws IOException
	 */
	public TestRequestWrapper(HttpServletRequest request, String method,
			byte[] key, byte[] iv) throws IOException {
		super(request);
		this.request = request;
		params = new HashMap<String, Objects>();
		String encrytedStr;
		// 针对不同请求使用不同方法获取参数
		if ("get".equalsIgnoreCase(method)) {
			encrytedStr = request.getQueryString();
			if (encrytedStr != null) {
				byte[] content = Base64.getDecoder().decode(encrytedStr);
				queryString = AESHelper.decrypt(content, key, iv);
				params.putAll(parseForm(queryString));
			}

			// aesDectryte(encrytedStr);
		} else if ("post".equalsIgnoreCase(method)) {
			byte[] data = getRequestPostBytes(request);
			data = Base64.getDecoder().decode(data);
			queryString = AESHelper.decrypt(data, key, iv);
			params.putAll(parseForm(queryString));
			inputStream = new ByteArrayInputStream(data);
		}
		// this.params = newParams;

	}

	private Map<String, Object> parseForm(String form) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (form.trim().length() > 0) {
			String[] kvAarry = form.split("&");
			for (String item : kvAarry) {
				String[] kv = item.split("=");
				if (kv.length > 1) {
					map.put(kv[0], kv[1]);
				}
			}
		}
		return map;
	}

	@Override
	public Map getParameterMap() {
		return queryString == null ?  params : request.getParameterMap();
	}

	@Override
	public String getQueryString() {
		return queryString == null ?  queryString : request.getQueryString();
	}

	public Enumeration getParameterNames() {
		if (queryString == null) {
			Vector l = new Vector(params.keySet());
			return l.elements();
		} else {
			return request.getParameterNames();
		}
	}

	public String[] getParameterValues(String name) {
		if (queryString == null) {
			return request.getParameterValues(name);
		} else {
			Object v = params.get(name);
			if (v == null) {
				return null;
			} else if (v instanceof String[]) {
				return (String[]) v;
			} else if (v instanceof String) {
				return new String[] { (String) v };
			} else {
				return new String[] { v.toString() };
			}
		}
	}

	public String getParameter(String name) {
		if (queryString == null) {
			return request.getParameter(name);
		} else {
			Object v = params.get(name);
			if (v == null) {
				return null;
			} else if (v instanceof String[]) {
				String[] strArr = (String[]) v;
				if (strArr.length > 0) {
					return strArr[0];
				} else {
					return null;
				}
			} else if (v instanceof String) {
				return (String) v;
			} else {
				return v.toString();
			}
		}
	}

	/***
	 * post方法获取输入流字节数组
	 * 
	 * @param request
	 * @return byte[]
	 * @throws IOException
	 */
	private static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/***
	 * Post方法请求体转字串(类queryString)
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = charset;
		}
		return new String(buffer, charEncoding);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new EncryteInputStream (this.inputStream);
	}

	public class EncryteInputStream extends ServletInputStream {

		InputStream _is = null;

		public EncryteInputStream(InputStream sourceInputStream) {
			_is = sourceInputStream;
		}

		public int available() throws IOException {
			return this._is == null?-1:this._is.available();
		}

		public int read() throws IOException {
			return this._is == null?-1:this._is.read();
		}

		public int read(byte[] buf, int offset, int len) throws IOException {
			return this._is == null?-1:this._is.read(buf, offset, len);
		}

		public long skip(long n) throws IOException {
			return this._is == null?-1L:this._is.skip(n);
		}

		public void close() throws IOException {
		}

		public void free() {
			this._is = null;
		}

		public String toString() {
			return this.getClass().getSimpleName() + "[" + this._is + "]";
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {

		}
	}
}
