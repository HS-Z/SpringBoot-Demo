package com.zhs.demo.model.notice;

/**
 * Created by Zhang on 2018/9/27.
 */

/**
 * 邮件基本信息
 */
public class Email {

    private String host;  //邮件服务器

    private String port;  //端口号

    private String name;  //发件人名称

    private String password;   //邮箱密码

    private String from;  //发件人邮箱

    private String to;  //接收人邮箱

    private String cc;  //抄送人邮箱

    private String bcc;  //密送人邮箱

    private String subject;  //邮件主题

    private String content;  //邮件内容


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
