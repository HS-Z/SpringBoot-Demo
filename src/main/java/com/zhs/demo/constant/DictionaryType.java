package com.zhs.demo.constant;


public enum DictionaryType {

    USER_TYPE("用户类型"),

    ROLE_TYPE("角色类型"),

    MESSAGE_TYPE("消息类型");


    private String value;

    public String getValue(){
        return value;
    }

    DictionaryType(String value) {
        this.value = value;
    }
}
