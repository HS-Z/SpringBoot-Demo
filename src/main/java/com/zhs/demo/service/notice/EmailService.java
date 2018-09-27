package com.zhs.demo.service.notice;

import com.zhs.demo.model.notice.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
    public void sendSimpleEmail(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("www.aiwoo@live.com");
        message.setTo("www.1019263443@qq.com");
        message.setSubject("邮件测试");
        message.setText("这是一条测试邮件");
        message.setSentDate(new Date());

        try {
            mailSender.send(message);
            System.out.println("邮件发送成功");
        }catch (Exception e){
            System.out.println("邮件发送失败");
        }

    }

}
