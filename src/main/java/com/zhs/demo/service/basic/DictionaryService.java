package com.zhs.demo.service.basic;

import com.github.pagehelper.Page;
import com.zhs.demo.dao.JpaRepository.basic.DictionaryRepository;
import com.zhs.demo.dao.MybatisMapper.basic.DictionaryMapper;
import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.PageHelperService;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class DictionaryService {

    @Autowired
    private PageHelperService pageHelperService;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private DictionaryMapper dictionaryMapper;




    public JqGridResponse getDictionaryList(JqGridQueryVo jqGridQueryVo){

        JqGridResponse jqGridResponse = new JqGridResponse();

        Page<Object> pageHelper=pageHelperService.myBatisPageHelper(jqGridQueryVo);
        List<Object> list=dictionaryMapper.getDictionaryList(jqGridQueryVo);

        jqGridResponse = pageHelperService.pageHelper(jqGridResponse,pageHelper);

        jqGridResponse.setRows(list);

        return jqGridResponse;

    }


    public Dictionary findById(Long id){
        Dictionary dictionary=dictionaryRepository.getOne(id);
        return dictionary;
    }





    public Json deleteById(Long id){

        try {
            dictionaryRepository.deleteById(id);
            return Json.ok("删除成功");
        }catch (Exception e){
            return Json.ok("删除失败");
        }

    }


}
