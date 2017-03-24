package com.efun.filter.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class MyOutputStream extends ServletOutputStream {
    private ServletOutputStream outputStream;
    private ByteArrayOutputStream copy;

    public MyOutputStream(ServletOutputStream outputStream) {
        this.outputStream = outputStream;
        copy = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        copy.write(b);
        System.out.println("output1");
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        copy.write(b, off, len);
        System.out.println("output2");
    }

    @Override
    public void write(byte[] b) throws IOException {
        copy.write(b);
        System.out.println("output3");
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

    public void writeAll(byte[] b) throws IOException {
        outputStream.write(b);
    }

    public void writeClose() throws IOException {
        outputStream.close();
    }

    public void writeFlush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }
}  
