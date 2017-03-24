package com.efun.filter.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class MyOutputStream extends ServletOutputStream {
    private ServletOutputStream outputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    public MyOutputStream(ServletOutputStream outputStream) {
        this.outputStream = outputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        byteArrayOutputStream.write(b);
        System.out.println("output1");
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        byteArrayOutputStream.write(b, off, len);
        System.out.println("output2");
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        byteArrayOutputStream.write(b);
        System.out.println("output3");
    }

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		// TODO Auto-generated method stub

	}

    public String getContent() throws UnsupportedEncodingException {
        return new String(byteArrayOutputStream.toByteArray(), "utf-8");
    }

}  
