package com.efun.filter.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TestWrapper extends HttpServletResponseWrapper {  

      
    public TestWrapper(HttpServletResponse response) {  
        super(response);  
    }  
      
    @Override  
    public PrintWriter getWriter() throws IOException {  
       
        return super.getWriter();  
    }  
      
    @Override  
    public ServletOutputStream getOutputStream() throws IOException {  
        return super.getOutputStream();  
    }  
  
  
      
}  