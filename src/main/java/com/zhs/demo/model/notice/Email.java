package com.zhs.demo.model.notice;

/**
 * Created by Zhang on 2018/9/27.
 */

import com.zhs.demo.model.basic.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 邮件基本信息
 */
@Entity
@Table(name = "t_zhs_email")
public class Email extends BaseModel{

    private String senderEmail;  //发件人邮箱

    private String receiveEmail;  //接收人邮箱

    private String cc;  //抄送人邮箱

    private String bcc;  //密送人邮箱

    private String subject;  //邮件主题

    private String content;  //邮件内容


    @Column(name = "sender_email")
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Column(name = "receive_email")
    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(length = 10240)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
