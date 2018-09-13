package com.zhs.demo.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色信息
 */
@Entity
@Table(name = "t_zhs_user_role_info")
public class UserRoleInfo extends BaseModel{

    private Long userId;  //用户id

    private Long roleId;  //角色id

    private String description;  //描述

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
