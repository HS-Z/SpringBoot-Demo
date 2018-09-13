package com.zhs.demo.model.jqGrid;


public class JqGridRequest {

    private int page = 1;   //当前页数
    private int rows = 20;  //每页显示记录数
    private int totalPage;  //总页数
    private String sord = "desc";  //排序的方式
    private String sidx = "id";  //排序的属性

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }
}
