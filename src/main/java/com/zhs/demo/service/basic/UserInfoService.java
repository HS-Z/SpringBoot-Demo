package com.zhs.demo.service.basic;

import com.zhs.demo.dao.JpaRepository.basic.UserInfoRepository;
import com.zhs.demo.dao.MybatisMapper.basic.UserInfoMapper;
import com.zhs.demo.model.basic.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoMapper userInfoMapper;


    /**
     * 新增
     * @param userInfo
     */
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    /**
     * 更新（必须有主键id）
     * @param userInfo
     */
    public void update(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }


    public UserInfo findByAccount(String account) {
        UserInfo userInfo=userInfoMapper.findByAccount(account);
        return userInfo;
    }


    public UserInfo findById(Long id) {
        UserInfo userInfo=userInfoRepository.getOne(id);
        return userInfo;
    }

    public void deleteById(Long id){
        userInfoRepository.deleteById(id);
    }

}
