package com.zhs.demo.service.basic;

import com.github.pagehelper.Page;
import com.zhs.demo.common.exception.SessionExpireException;
import com.zhs.demo.dao.JpaRepository.basic.DictionaryRepository;
import com.zhs.demo.dao.MybatisMapper.basic.DictionaryMapper;
import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.model.session.SessionInfo;
import com.zhs.demo.service.PageHelperService;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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
    @Autowired
    private SessionUtils sessionUtils;




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


    /**
     * 启用与禁用
     * @param id
     * @param state
     * @return
     */
    public Json changeState(Long id,Boolean state){

        try {
            Dictionary dictionary = this.findById(id);
            if (dictionary == null){
                return Json.ok("操作失败，关键参数丢失");
            }
            dictionary.setState(state);
            return Json.ok("操作成功");
        }catch (Exception e){
            return Json.ok("操作失败");
        }

    }


    /**
     * 新增及编辑功能
     * @param dictionary
     * @return
     */
    public Json saveOrUpdate(Dictionary dictionary){

        SessionInfo sessionInfo = sessionUtils.getSessionInfo();
        if (sessionInfo == null){
            throw new SessionExpireException("session已过期，请重新登陆");
        }

        if (dictionary.getId() == null){   //新增

            //判断数据是否已存在
            Dictionary dic = dictionaryMapper.findByCodeAndType(dictionary.getCode(), dictionary.getType());
            if (dic != null){
                return Json.fail("新增失败，编码重复");
            }

            dictionary.setCreator(sessionInfo.getUserName());

            dictionaryRepository.save(dictionary);

            return Json.ok("新增成功",dictionary.getId());

        }else {   //编辑

            Dictionary dic = dictionaryRepository.getOne(dictionary.getId());

            if (dic == null){
                return Json.fail("更新失败，未查询到数据信息");
            }

            dic.setCode(dictionary.getCode());
            dic.setName(dictionary.getName());
            dic.setDescription(dictionary.getDescription());
            dic.setLastUpdateDate(new Date());

            dictionaryRepository.save(dictionary);

            return Json.ok("更新成功",dictionary.getId());
        }

    }


    /**
     * 编码生成规则
     * @param type  字典表类型
     * @return
     */
    public String generateCode(String type){
        String maxCode = dictionaryMapper.getMaxCode(type);  //查询当前类型的最大编码
        String firstLetter=type.substring(0,1);  //获得首字母
        if (StringUtils.isBlank(maxCode)){
            maxCode = firstLetter + "000001";
            return maxCode;
        }
        String subCode = maxCode.substring(1);
        int intCode = Integer.parseInt(subCode) + 1;
        maxCode = String.format(firstLetter + "%06d",intCode);

        for (int i=0; i < 10000; i++){   //循环10000次都找不到不重复的编码，我是不信的

            Dictionary dictionary = dictionaryMapper.findByCodeAndType(maxCode,type);   //判断新生成的编码是否已存在
            if (dictionary == null){
                break;
            }else {
                maxCode = String.format(firstLetter + "%06d",intCode + 1);
            }
        }

        return maxCode;
    }


}
