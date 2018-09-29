package com.zhs.demo.model.basic;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表
 */
@Entity
@Table(name = "t_zhs_dictionary")
public class Dictionary extends BaseModel{

    private String code;   //字典表编码

    private String name;   //字典表名称

    private String type;   //字典表类型

    private Boolean state = Boolean.TRUE;  //状态，默认启用

    private String description;  //描述


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Column(length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
