package com.clei.Y2019.M04.D05;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDateTime;
import java.util.Properties;

public class JavaMailTest {
    private final static String MAIL_TO = "1406723908@qq.com";
    //异常直接抛出了。。。
    public static void main(String[] args){
        try {
            MailSender ms = new MailSender();
            ms.sendTextMail(MAIL_TO);
            //ms.sendImageMail(MAIL_TO);
            // ms.sendAttachMail(MAIL_TO);
            // ms.sendMixedMail(MAIL_TO);
        } catch (Exception e) {
            PrintUtil.dateLine("邮件发送失败！！！");
            e.printStackTrace();
        }
    }
}

class MailSender{
    //用你自己的邮箱和授权码去。。。
    private final static String FROM = "";
    private final static String AUTHORIZATION_CODE = "";

    public void sendTextMail(String mailTo) throws Exception{
        Session session = createSession();
        MimeMessage message = createTextMessage(session, mailTo);
        send(session, message);
    }

    public void sendImageMail(String mailTo) throws Exception{
        Session session = createSession();
        MimeMessage message = createImageMessage(session, mailTo);
        send(session, message);
    }

    // 普通发送附件邮件
    public void sendAttachMail(String mailTo) throws Exception{
        Session session = createSession();
        MimeMessage message = createAttachMessage(session, mailTo);
        send(session, message);
    }

    public void sendMixedMail(String mailTo) throws Exception{
        Session session = createSession();
        MimeMessage message = createMixedMessage(session, mailTo);
        send(session, message);
    }

    public void send(Session session, MimeMessage message) throws Exception{
        //2 通过session获得transport对象，即邮件传输对象
        Transport ts = session.getTransport();
        ts.connect();
        //3 使用邮箱的用户名和密码连上邮件服务器，发送邮件时，
        // 发件人需要提交邮箱的用户名和密码给smtp服务器，
        // 用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        //上面我们getSession时用了授权码就不需这步了
        //ts.connect(FROM,"邮箱密码");
        //4 创建邮件
        //MimeMessage message = createTextMessage(session, mailTo);
        //发送。。
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
        PrintUtil.println("{} -- 邮件发送成功！！！", DateUtil.format(LocalDateTime.now(),DateUtil.getDefaultPattern()));
    }

    public Session createSession(){
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.263.net");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用javaMail发送邮件的5个步骤
        //1 创建session
        Session session = Session.getInstance(prop, new MyAuthenticator());
        //开启session的debug模式，这样可以看到程序发送邮件的运行状态
        session.setDebug(true);
        return session;
    }

    public MimeMessage createTextMessage(Session session, String mailTo) throws Exception{
        MimeMessage message = createMessage(session, mailTo);
        //主题
        message.setSubject("JavaMail Text Mail Test!!!");
        //内容
        message.setContent("中国，我爱你，不用千言和万语","text/html;charset=UTF-8");
        return message;
    }

    public MimeMessage createImageMessage(Session session, String mailTo) throws Exception{
        MimeMessage message = createMessage(session, mailTo);
        //主题
        message.setSubject("JavaMail Image Mail Test!!!");
        //内容
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent("中国，我爱你，不用千言和万语<br/> <img src='cid:linkinpark.jpg' />","text/html;charset=UTF-8");
        //图片数据
        MimeBodyPart imagePart = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(JavaMailTest.class.getResource("/linkinpark.jpg").getPath()));
        imagePart.setDataHandler(dh);
        //这里的contendID要与上面text里的cid对应哦
        imagePart.setContentID("linkinpark.jpg");
        //描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(textPart);
        mm.addBodyPart(imagePart);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        return message;
    }

    public MimeMessage createAttachMessage(Session session, String mailTo) throws Exception{
        MimeMessage message = createMessage(session, mailTo);
        //主题
        message.setSubject("JavaMail Attach Mail Test!!!");
        //内容
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent("中国，我爱你，不用千言和万语","text/html;charset=UTF-8");
        //附件数据
        MimeBodyPart attachPart = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(JavaMailTest.class.getResource("/linkinpark.jpg").getPath()));
        attachPart.setDataHandler(dh);
        attachPart.setContentID("linkinpark.jpg");
        //描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(textPart);
        mm.addBodyPart(attachPart);
        mm.setSubType("mixed");

        message.setContent(mm);
        message.saveChanges();
        return message;
    }

    public MimeMessage createMixedMessage(Session session, String mailTo) throws Exception{
        MimeMessage message = createMessage(session, mailTo);
        //主题
        message.setSubject("JavaMail Mixed Mail Test!!!");
        //内容
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent("中国，我爱你，不用千言和万语<br/> <img src='cid:linkinpark.jpg' />","text/html;charset=UTF-8");
        //图片数据
        MimeBodyPart imagePart = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(JavaMailTest.class.getResource("/linkinpark.jpg").getPath()));
        imagePart.setDataHandler(dh);
        imagePart.setContentID("linkinpark.jpg");
        //附件数据
        MimeBodyPart attachPart = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(JavaMailTest.class.getResource("/BreakingTheHabit.txt").getPath()));
        attachPart.setDataHandler(dh2);
        attachPart.setFileName(dh2.getName());
        //描述正文和图片数据关系
        MimeMultipart mm1 = new MimeMultipart();
        mm1.addBodyPart(textPart);
        mm1.addBodyPart(imagePart);
        mm1.setSubType("related");
        //正文 bodyPart
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(mm1);
        //描述正文与附件关系
        MimeMultipart mm2 = new MimeMultipart();
        mm2.addBodyPart(attachPart);
        mm2.addBodyPart(bodyPart);
        mm2.setSubType("mixed");

        message.setContent(mm2);
        message.saveChanges();
        return message;
    }

    public MimeMessage createMessage(Session session, String mailTo) throws Exception{
        MimeMessage message = new MimeMessage(session);
        //发送人
        message.setFrom(new InternetAddress(FROM));
        //接受者 ,可以添加抄送，密送，多个接受者。。
        Address[] addresses = new Address[]{new InternetAddress(mailTo),new InternetAddress("lanziseyueya@163.com")};
        message.setRecipients(Message.RecipientType.TO, addresses);

        return message;
    }

    class MyAuthenticator extends Authenticator{
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM,AUTHORIZATION_CODE);
        }
    }
    
}
