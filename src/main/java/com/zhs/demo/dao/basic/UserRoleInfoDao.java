package com.zhs.demo.dao.basic;

import com.zhs.demo.model.basic.UserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRoleInfoDao extends JpaRepository<UserRoleInfo,Long> {


    UserRoleInfo findUserRoleInfoByUserId(Long userId);


    List<UserRoleInfo> findUserRoleInfoByRoleId(Long roleId);






}
