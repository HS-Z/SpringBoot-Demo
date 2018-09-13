package com.zhs.demo.model.jqGrid;

import java.util.List;


public class JqGridResponse {

    private int total; //总页数
    private int page;  //当前页
    private long records; //总记录数
    private List<Object> rows;  //当前页的记录集合
    private Object footer;  //页脚信息
    private int pageRows = 20;  //每页显示记录数


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    public Object getFooter() {
        return footer;
    }

    public void setFooter(Object footer) {
        this.footer = footer;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }
}
