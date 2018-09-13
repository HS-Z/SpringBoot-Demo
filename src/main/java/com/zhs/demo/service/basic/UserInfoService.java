package com.zhs.demo.service.basic;

import com.zhs.demo.dao.basic.UserInfoDao;
import com.zhs.demo.model.basic.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 新增
     * @param userInfo
     */
    public void save(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

    /**
     * 更新（必须有主键id）
     * @param userInfo
     */
    public void update(UserInfo userInfo){
        userInfoDao.save(userInfo);
    }


    public UserInfo findByAccount(String account) {
        UserInfo userInfo=userInfoDao.findByAccount(account);
        return userInfo;
    }


    public UserInfo findById(Long id) {
        UserInfo userInfo=userInfoDao.getOne(id);
        return userInfo;
    }

    public void deleteById(Long id){
        userInfoDao.deleteById(id);
    }

}
