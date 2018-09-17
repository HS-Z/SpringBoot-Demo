package com.zhs.demo.dao.MybatisMapper.basic;

import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;

import java.util.List;

public interface DictionaryMapper {


    /**
     * 查询角色列表
     * @param jqGridQueryVo
     * @return
     */
    List<Object> getDictionaryList(JqGridQueryVo jqGridQueryVo);


    Dictionary findById(Long id);


}
