package com.zhs.demo.model.notice;


import com.zhs.demo.model.basic.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 消息信息表
 */
@Entity
@Table(name = "t_zhs_message")
public class Message extends BaseModel{

    private String title;   //消息标题

    private String content;  //消息内容

    private String messageType;  //消息类型

    private String messageTypeName;   //消息类型名称

    private String sendRange;  //发送范围

    private String sendRangeName;  //发送范围名称

    private String userIds;   //用户的id


    private String readState;  //阅读状态（临时字段）

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 4096)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "message_type")
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Column(name = "message_type_name")
    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    @Column(name = "send_range")
    public String getSendRange() {
        return sendRange;
    }

    public void setSendRange(String sendRange) {
        this.sendRange = sendRange;
    }

    @Column(name = "send_range_name")
    public String getSendRangeName() {
        return sendRangeName;
    }

    public void setSendRangeName(String sendRangeName) {
        this.sendRangeName = sendRangeName;
    }

    @Transient
    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    @Column(name = "user_ids")
    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

}
