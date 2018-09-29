package com.zhs.demo.dao.MybatisMapper.notice;

import com.zhs.demo.model.jqGrid.JqGridQueryVo;

import java.util.List;

public interface MessageMapper {


    /**
     * 查询角色列表
     * @param jqGridQueryVo
     * @return
     */
    List<Object> getMessageList(JqGridQueryVo jqGridQueryVo);


}
