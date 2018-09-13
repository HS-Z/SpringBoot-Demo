package com.zhs.demo.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户信息
 */
@Entity
@Table(name = "t_zhs_user_info")
public class UserInfo extends BaseModel{


    private String userName;  //用户名称

    private String userType;  //用户类型

    private String sex;  //性别 1男 2女 3保密

    private String account;  //登陆账号

    private String password;  //登陆密码

    private String security;  //加密时生成的key

    private String email;  //邮箱

    private String phone;  //手机号码

    private String address;  //住址

    private String state;  //用户状态

    private Date lastLoginTime;  //最近一次登陆时间（该值改变时，不更新lastUpdate）

    private Boolean isAdmin=Boolean.FALSE;  //是否是管理员

    private Boolean isLock=Boolean.FALSE;  //账号是否锁定，默认为否

    private Date lockTime;  //账号锁定时间

    private Boolean logOut=Boolean.FALSE;  //账号是否已注销，默认为否

    private Date logOutTime;  //账号注销时间


    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "security")
    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "is_admin")
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Column(name = "is_lock")
    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    @Column(name = "lock_time")
    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    @Column(name = "log_out")
    public Boolean getLogOut() {
        return logOut;
    }

    public void setLogOut(Boolean logOut) {
        this.logOut = logOut;
    }

    @Column(name = "log_out_time")
    public Date getLogOutTime() {
        return logOutTime;
    }

    public void setLogOutTime(Date logOutTime) {
        this.logOutTime = logOutTime;
    }
}
