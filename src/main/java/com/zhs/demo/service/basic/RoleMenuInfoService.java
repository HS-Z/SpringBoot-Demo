package com.zhs.demo.service.basic;

import com.zhs.demo.dao.basic.RoleMenuInfoDao;
import com.zhs.demo.model.basic.RoleMenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleMenuInfoService {

    @Autowired
    private RoleMenuInfoDao roleMenuInfoDao;


    public List<RoleMenuInfo> findRoleMenuListByRoleId(Long roleId){
        List<RoleMenuInfo> roleMenuInfoList=roleMenuInfoDao.findRoleMenuListByRoleId(roleId);
        return roleMenuInfoList;
    }


}
