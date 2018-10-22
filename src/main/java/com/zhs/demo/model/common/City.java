package com.zhs.demo.model.common;

import javax.persistence.*;

/**
 * 行政区域信息
 */
@Entity
@Table(name = "t_zhs_city")
public class City{

    private Long id;

    private String code;  //编码

    private String name;  //名称

    private String type;  //类别   （ 省：province   市：city   县（区）：county ）


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)  //主键由数据库自动生成
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
