package com.zhs.demo.dao.MybatisMapper.basic;

import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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


    @Select("SELECT MAX(role_code) as roleCode FROM t_zhs_role_info")
    String getMaxRoleCode();


    /**
     * 查询所有的角色信息，不包括已被删除的
     * @return
     */
    List<RoleInfo> getAllRoleInfoNotDelete();


    /**
     * 根据多个id同时查询角色信息
     * @param ids
     * @return
     */
    List<RoleInfo> getRoleInfoByIds(@Param("ids") String ids);


}
