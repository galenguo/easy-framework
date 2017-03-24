package com.efun.filter.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.efun.filter.helper.MyOutputStream;
import com.efun.filter.helper.MyWriter;

public class TestResponseWrapper extends HttpServletResponseWrapper {

	private MyWriter myWriter;
	private MyOutputStream myOutputStream;

	public TestResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (myWriter == null) {
			myWriter = new MyWriter(super.getWriter());
		}
		return myWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (myOutputStream == null) {
			myOutputStream = new MyOutputStream(super.getOutputStream());
		}
		return myOutputStream;
	}

	public MyWriter getMyWriter() {
		return myWriter;
	}

	public MyOutputStream getMyOutputStream() {
		return myOutputStream;
	}

}