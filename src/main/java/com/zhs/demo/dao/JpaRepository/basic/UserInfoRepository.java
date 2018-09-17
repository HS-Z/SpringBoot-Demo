package com.zhs.demo.dao.JpaRepository.basic;

import com.zhs.demo.model.basic.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {


}
