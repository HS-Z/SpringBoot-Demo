package com.zhs.demo.model.session;

import java.io.Serializable;

public class SessionInfo implements Serializable {

    private Long userId;   //当前登陆用户id

    private String userName;   //当前登陆用户名称

    private String userType;   //当前登陆用户类型

    private String account;   //当前登陆用户账号

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
