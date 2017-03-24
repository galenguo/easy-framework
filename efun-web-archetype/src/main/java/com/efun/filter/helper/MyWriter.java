package com.efun.filter.helper;

import java.io.PrintWriter;  
import java.io.Writer;

public class MyWriter extends PrintWriter{  
    private StringBuilder buffer;  
  
    public MyWriter(Writer out) {  
        super(out);  
        buffer = new StringBuilder();  
    }  
  
    @Override  
    public void write(char[] buf, int off, int len) {  
        // super.write(buf, off, len);  
        char[] dest = new char[len];  
        System.arraycopy(buf, off, dest, 0, len);  
        buffer.append(dest);  
        System.out.println("write1");  
    }  
  
    @Override  
    public void write(char[] buf) {  
        super.write(buf);  
        System.out.println("write2");  
    }  
  
    @Override  
    public void write(int c) {  
        super.write(c);  
        System.out.println("write3"+c);  
    }  
  
    @Override  
    public void write(String s, int off, int len) {  
        super.write(s, off, len);  
        System.out.println("write4"+s+off+len);  
    }  
  
    @Override  
    public void write(String s) {  
        super.write(s);  
        System.out.println("write5");  
    }  
      
    public String getContent(){  
        return buffer.toString();  
    }  
  
}  