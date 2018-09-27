package com.zhs.demo.service.notice;

import com.zhs.demo.model.notice.Email;
import com.zhs.demo.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * Created by Zhang on 2018/9/27.
 */

/**
 * 邮件发送服务
 */
@Service
public class EmailService{

    @Autowired
    private JavaMailSender mailSender;


    /**
     * 简单的邮件发送，纯文本内容
     * @param
     */
    public Json sendSimpleEmail(Email email){

        SimpleMailMessage message = new SimpleMailMessage();  //创建简单邮件消息
        message.setFrom("1019263443@qq.com");   //发件人
        message.setTo("www.aiwoo@live.com");   //收件人
        message.setSubject("邮件测试");   //邮件主题
        message.setText("这是一条测试邮件");
        message.setSentDate(new Date());
        message.setCc();   //抄送
        message.setBcc();  //密送
        try {
            mailSender.send(message);
            return Json.ok("邮件发送成功");
        }catch (Exception e){
            return Json.fail("邮件发送失败");
        }
    }


    /**
     * html邮件
     * @param email
     * @return
     */
    public Json sendHtmlEmail(Email email){

        MimeMessage message = mailSender.createMimeMessage();   //创建一个MIME消息

        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom("1019263443@qq.com");
            helper.setTo("www.aiwoo@live.com");
            helper.setSubject("html邮件测试");
            helper.setText(content,true);

            mailSender.send(message);

            return Json.ok("邮件发送成功");

        }catch (MessagingException e){
            return Json.fail("邮件发送失败");
        }
    }


    /**
     * 带附件的邮件
     * @param email
     */
    public Json sendAttachmentsEmail(Email email) {
        MimeMessage message = mailSender.createMimeMessage();  //创建一个MIME消息

        String filePath="E:\\SG";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1019263443@qq.com");
            helper.setTo("www.aiwoo@live.com");
            helper.setSubject("html邮件测试");
            helper.setText("带附件的邮件", true);  // true表示这个邮件是有附件的

            FileSystemResource file = new FileSystemResource(new File(filePath));//创建文件系统资源
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);//添加附件

            mailSender.send(message);

            return Json.ok("邮件发送成功");
        } catch (MessagingException e) {

            return Json.fail("邮件发送失败");
        }

    }


    public Json sendInlineResourceEmail(Email email) {

        MimeMessage message = mailSender.createMimeMessage();

        String rscPath="E:\\SG";
        String rscId="001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1019263443@qq.com");
            helper.setTo("www.aiwoo@live.com");
            helper.setSubject("html邮件测试");
            helper.setText("带附件的邮件", true);  // true表示这个邮件是有附件的

            FileSystemResource res = new FileSystemResource(new File(rscPath));

            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(rscId, res);//添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现

            mailSender.send(message);
            return Json.ok("邮件发送成功");
        } catch (MessagingException e) {
            return Json.fail("邮件发送失败");
        }

    }




}
