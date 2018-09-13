package com.zhs.demo.dao.basic;


import com.zhs.demo.model.jqGrid.JqGridQueryVo;

import java.util.List;

public interface RoleInfoDao {


    /**
     * 查询角色列表
     * @param jqGridQueryVo
     * @return
     */
    List<Object> getAllRoleInfo(JqGridQueryVo jqGridQueryVo);






}
