package com.zhs.demo.dao.basic;

import com.zhs.demo.model.basic.RoleMenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoleMenuInfoDao extends JpaRepository<RoleMenuInfo,Long> {

    List<RoleMenuInfo> findRoleMenuListByRoleId(Long roleId);






}
