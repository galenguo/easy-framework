package com.efun.core.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * EmailUtils
 *
 * @author Galen
 * @since 2017/5/22
 */
public class EmailUtils {

    public static final boolean sendEmail(Boolean isAutheticate, String protocol, String host, Integer port,
                                          String user, String password, String from, String timeOut, String connectionTimeOut, String[] to,
                                          String subject, String content, Map<String, String> filePathMap) throws Exception {
        boolean bool = false;
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties p = new Properties();
        p.put("mail.smtp.auth", isAutheticate.toString());
        p.put("mail.transport.protocol", protocol);
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.port", port);
        p.put("mail.smtp.timeout", timeOut);
        p.put("mail.smtp.connectiontimeout", connectionTimeOut);
        final String username = user;
        final String pwd = password;
        // 建立会话
        Session session = Session.getInstance(p, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, pwd);
            }
        });
        // 建立消息
        MimeMessage msg = new MimeMessage(session);
        // 设置发件人
        msg.setFrom(new InternetAddress(from));
        // 收件人
        InternetAddress[] sendTo = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            sendTo[i] = new InternetAddress(to[i]);
        }
        msg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
        // 发送日期
        msg.setSentDate(new Date());
        // 主题
        msg.setSubject(subject, "utf-8");
        // 设置邮件内容，作为Multipart对象的一部分
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(content, "text/html;charset=utf-8");
        Multipart mulp = new MimeMultipart();
        mulp.addBodyPart(mbp);
        // 文件件名
        String fileName = null;
        // 全路径
        String fileFullPath = null;
        DataSource source = null;
        if (filePathMap != null && filePathMap.size() > 0) {
            Iterator it = filePathMap.entrySet().iterator();
            while (it.hasNext()) {
                // 为每个附件做为Multipart对象的一部分
                mbp = new MimeBodyPart();
                Map.Entry<String, String> entry = (Map.Entry) it.next();
                fileName = entry.getKey();
                fileFullPath = entry.getValue();
                if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileFullPath)) {
                    continue;
                }
                File f = new File(fileFullPath);
                if (!f.exists()) {
                    continue;
                }
                source = new FileDataSource(fileFullPath);
                mbp.setDataHandler(new DataHandler(source));
                mbp.setFileName(fileName);
                mulp.addBodyPart(mbp);
            }
        }
        // 设置信息内容，将Multipart 对象加入信息中
        msg.setContent(mulp);
        // 登陆邮件服务器进行用户验证
        Transport tran = session.getTransport(protocol);
        tran.connect(host, port, user, password);
        // 发送
        tran.sendMessage(msg, msg.getAllRecipients());
        bool = true;
        tran.close();
        return bool;
    }


}

