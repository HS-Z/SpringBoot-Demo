package com.zhs.demo.dao.MybatisMapper.basic;

import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;

import java.util.List;

public interface RoleInfoMapper {


    /**
     * 查询角色列表
     * @param jqGridQueryVo
     * @return
     */
    List<Object> getRoleInfoList(JqGridQueryVo jqGridQueryVo);


    /**
     * 根据角色编码查询角色信息
     */
    RoleInfo findRoleInfoByRoleCode(String roleCode);


    RoleInfo findById(Long id);


}
