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
    List<Object> getAllRoleInfo(JqGridQueryVo jqGridQueryVo);


    /**
     *
     */
    RoleInfo findRoleInfoByRoleCode(String roleCode);


}
