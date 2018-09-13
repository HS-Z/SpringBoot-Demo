package com.zhs.demo.dao.basic;

import com.zhs.demo.model.basic.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoDao extends JpaRepository<UserInfo,Long> {


    /**
     * 根据账号查询用户对象
     * @param account
     * @return
     */
    UserInfo findByAccount(String account);





}
