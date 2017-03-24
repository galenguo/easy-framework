package com.efun.filter.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ServletOutputStreamCopier
 *
 * @author Galen
 * @since 2017/3/24
 */
public class ServletOutputStreamCopier extends ServletOutputStream {

    private OutputStream outputStream;
    private ByteArrayOutputStream copy;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        copy.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        copy.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        copy.write(b);
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
