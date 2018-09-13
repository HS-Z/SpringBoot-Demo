package com.zhs.demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Mybatis的分页
 */
@Service
public class PageHelperService {


    public <T> Page<T> myBatisPageHelper(JqGridQueryVo jqGridQueryVo){

        if (jqGridQueryVo == null){
            jqGridQueryVo = new JqGridQueryVo();
        }

        JqGridRequest jqGridRequest = jqGridQueryVo.getJqGridRequest();

        if (jqGridRequest == null){
            jqGridRequest = new JqGridRequest();
        }

        int page = jqGridRequest.getPage();  //当前页数
        if (page <= 0){
            page = 1;
        }

        int rows = jqGridRequest.getRows();  //每页显示记录数
        if (rows <= 0){
            rows = 15;
        }

        String sord = jqGridRequest.getSord();  //排序的规则
        if (StringUtils.isBlank(sord)){
            sord = "desc";
        }

        String sidx = jqGridRequest.getSidx();   //排序的属性字段
        if (StringUtils.isBlank(sidx)){
            sidx = "id";
        }

        String sortRule = sidx.concat(" ").concat(sord);  //排序的语句

        Page<T> pageHelper = PageHelper.startPage(page,rows,sortRule);

        return pageHelper;

    }


    public JqGridResponse pageHelper(JqGridResponse jqGridResponse, Page<Object> page){


        if (jqGridResponse == null){
            jqGridResponse = new JqGridResponse();
        }

        if (page.size() <= 0){
            throw new RuntimeException("数据分页错误");
        }

        int pageNum = page.getPageNum();  //当前属于哪一页
        int pageSize = page.getPageSize();  //当前页面记录数
        int startRow = page.getStartRow();  //开始行
        int endRow = page.getEndRow();  //结束行
        long total = page.getTotal();   //总记录数
        int pages = page.getPages();  //总页数

        jqGridResponse.setPage(pageNum);
        jqGridResponse.setTotal(pages);
        jqGridResponse.setPageRows(pageSize);
        jqGridResponse.setRecords(total);


        return jqGridResponse;
    }



}
