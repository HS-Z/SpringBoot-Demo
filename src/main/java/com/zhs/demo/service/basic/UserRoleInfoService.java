package com.zhs.demo.service.basic;

import com.zhs.demo.dao.basic.UserRoleInfoDao;
import com.zhs.demo.model.basic.UserRoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRoleInfoService {


    @Autowired
    private UserRoleInfoDao userRoleInfoDao;


    public UserRoleInfo findUserRoleInfoByUserId(Long userId){
        UserRoleInfo userRoleInfo=userRoleInfoDao.findUserRoleInfoByUserId(userId);
        return userRoleInfo;
    }


}
