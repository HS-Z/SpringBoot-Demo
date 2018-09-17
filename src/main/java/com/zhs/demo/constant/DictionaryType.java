package com.zhs.demo.constant;


public enum DictionaryType {


    CODE_RULE("编码生成规则");


    private String value;

    public String getValue(){
        return value;
    }

    DictionaryType(String value) {
        this.value = value;
    }
}
