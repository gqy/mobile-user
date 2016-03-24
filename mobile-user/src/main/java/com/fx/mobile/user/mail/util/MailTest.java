package com.fx.mobile.user.mail.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailTest {
	public static void main(String[] args) throws NoSuchProviderException{  
		MailSenderInfo mailInfo = new MailSenderInfo();   
//          mailInfo.setMailServerHost("smtp.163.com");   
//	      mailInfo.setUserName("18251901682@163.com");   
//	      mailInfo.setPassword("aibeihoudeshang");//您的邮箱密码 
//	      mailInfo.setFromAddress("18251901682@163.com"); 
	      mailInfo.setMailServerHost("mail.cn.phicomm.com"); 
	      mailInfo.setMailServerPort("25");   
	      mailInfo.setValidate(true); 
	      mailInfo.setUserName("noreply@phicomm.com.cn");   
	      mailInfo.setPassword("feixun!123");//您的邮箱密码   
	      mailInfo.setFromAddress("noreply@phicomm.com.cn");   
	      mailInfo.setToAddress("qinyang.ge@feixun.com.cn");   
	      mailInfo.setSubject("测试");   
	      mailInfo.setContent("'www.baidu.com'sdfsd测试内容");   
	         //这个类主要来发送邮件  
	      SimpleMailSender sms = new SimpleMailSender();  
	      sms.sendTextMail(mailInfo);//发送文体格式   
	      //    sms.sendHtmlMail(mailInfo);//发送html格式
	}
//		 Properties prop = new Properties();
//		 prop.setProperty("mail.host", "smtp.sohu.com");
//		 prop.setProperty("mail.transport.protocol", "smtp");
//		 prop.setProperty("mail.smtp.auth", "true");
//		 //使用JavaMail发送邮件的5个步骤
//		 //1、创建session 
//		 Session session = Session.getInstance(prop);
//		 //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态 
//		 session.setDebug(true);
//		 //2、通过session得到transport对象 
//		 Transport ts;
//		try {
//			ts = session.getTransport();
//			 //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。 
//			 ts.connect("smtp.163.com", "18251901682", "aibeihoudeshang");
//			 //4、创建邮件 
//			 Message message = createSimpleMail(session);
//			 //5、发送邮件
//			 ts.sendMessage(message, message.getAllRecipients());
//			 ts.close();
//		} catch ( Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	 public static MimeMessage createSimpleMail(Session session)
//	            throws Exception {
//	        //创建邮件对象
//	        MimeMessage message = new MimeMessage(session);
//	        //指明邮件的发件人
//	        message.setFrom(new InternetAddress("18251901682@163.com"));
//	        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
//	        message.setRecipient(Message.RecipientType.TO, new InternetAddress("1271269755@qq.com"));
//	        //邮件的标题
//	        message.setSubject("只包含文本的简单邮件");
//	        //邮件的文本内容
//	        message.setContent("你好啊！", "text/html;charset=UTF-8");
//	        //返回创建好的邮件对象
//	        return message;
//	    }
}
