package com.zhs.demo.constant;


public enum NoticeConstant {

    INBOX("收件箱"),

    OUTBOX("发件箱");


    private String value;

    public String getValue(){
        return value;
    }

    NoticeConstant(String value) {
        this.value = value;
    }
}
