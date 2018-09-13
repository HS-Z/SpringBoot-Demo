package com.zhs.demo.model.jqGrid;


/**
 * 基本查询参数
 */
public class JqGridQueryVo {

    private JqGridRequest jqGridRequest;    // jqGrid 参数封装对象

    private Long id;  //主键id
    private Long userId;    //用户Id
    private String startDate;   //开始日期
    private String endDate;  //结束日期
    private String status;  //状态
    private String criteriaText;  //查询文本
    private String code; //编码
    private String name; //名称
    private String type; //类型
    private String ids;  //多条数据id拼接成的ids


    public JqGridRequest getJqGridRequest() {
        return jqGridRequest;
    }

    public void setJqGridRequest(JqGridRequest jqGridRequest) {
        this.jqGridRequest = jqGridRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCriteriaText() {
        return criteriaText;
    }

    public void setCriteriaText(String criteriaText) {
        this.criteriaText = criteriaText;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
