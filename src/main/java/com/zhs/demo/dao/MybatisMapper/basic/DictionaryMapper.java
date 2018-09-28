package com.zhs.demo.dao.MybatisMapper.basic;

import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DictionaryMapper {


    /**
     * 查询角色列表
     * @param jqGridQueryVo
     * @return
     */
    List<Object> getDictionaryList(JqGridQueryVo jqGridQueryVo);


    Dictionary findById(Long id);


    /**
     * 根据编码和类型编码确定唯一
     * @param code
     * @param type
     * @return
     */
    Dictionary findByCodeAndType(@Param("code") String code, @Param("type") String type);


    /**
     * 根据字典表类型查询当前的最大编码
     * @param type
     * @return
     */
    @Select("SELECT MAX(code) as code FROM t_zhs_dictionary where type = #{type}")
    String getMaxCode(String type);


}
