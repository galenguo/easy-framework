package com.efun.core.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * FileUtils
 *
 * @author Galen
 * @since 2016/05/30.
 */
public class FileUtils {

    /**
     * add separator for dir path
     *
     * @param dirName
     * @return
     */
    public static String addSeparatorIfNec(String dirName) {
        if (StringUtils.isNotBlank(dirName)
                && !dirName.endsWith(File.separator)) {
            dirName += File.separator;
        }
        return dirName;
    }


    // TODO: 2017/5/16 待优化 
    /**
     * 下载网络文件保存在本地
     * @param savePath	文件保存路径
     * @param fileName	文件名
     * @param fileUrl	网络文件地址
     * @throws Exception
     */
    public static void downloadNet(String savePath,String fileName, String fileUrl) throws Exception {
        System.out.println("downloadNet start>>"+fileUrl);
        File file=new File(savePath) ;
        if(!file.exists()||!file.isDirectory()){
            file.mkdirs();
        }
        if(fileName==null||fileName.trim().length()==0){
            fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        }
        file=new File(savePath,fileName);
        if(!file.exists()||!file.isFile()){
            file.createNewFile();
        }else if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        URL url = new URL(fileUrl);
        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
        conn.connect();

        long fileLength=conn.getContentLength();
        System.out.println("downloadNet file size(KB)>>>"+Math.round(fileLength*1.00/1024));
//		DecimalFormat df=new DecimalFormat("0.00");

        InputStream inStream = conn.getInputStream();
        BufferedInputStream bis=new BufferedInputStream(inStream);
        FileOutputStream fs = new FileOutputStream(file);
        int byteread = -1;
//		long bytesum=0;
        byte[] buffer = new byte[1024];
        while ((byteread = bis.read(buffer)) != -1) {
//			bytesum+=byteread;
//			System.out.println(df.format(bytesum*100.0/fileLength)+"%");
            fs.write(buffer, 0, byteread);
        }
        fs.flush();
        fs.close();
        bis.close();
        inStream.close();
        conn.disconnect();
        System.out.println("downloadNet finish>>"+fileUrl);
    }

    public static void main(String[] args) {
        try {
            long startTime=System.currentTimeMillis();
            //GeoLite2-City.mmdb.gz
            downloadNet("D:/mydownloads", "GeoLite2-City.mmdb.gz", "http://geolite.maxmind.com/download/geoip/database/GeoLite2-City.mmdb.gz");
            System.out.println("download time(ms):"+(System.currentTimeMillis()-startTime));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
