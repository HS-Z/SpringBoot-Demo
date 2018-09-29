package com.zhs.demo.controller.basic;

import com.zhs.demo.constant.DictionaryType;
import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.basic.DictionaryService;
import com.zhs.demo.utils.Json;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 跳转到通用设置列表
     * @param model
     * @return
     */
    @RequestMapping(value = "toDictionaryList", method = {RequestMethod.GET, RequestMethod.POST})
    public String toDictionaryList(Model model, String type){
        model.addAttribute("type",type);
        model.addAttribute("typeName", DictionaryType.valueOf(type).getValue());
        return "systemManage/dictionaryList";
    }


    /**
     * 通用设置的列表查询
     * @param jqGridRequest
     * @param jqGridQueryVo
     * @return
     */
    @RequestMapping(value = "getDictionaryList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JqGridResponse list(JqGridRequest jqGridRequest, JqGridQueryVo jqGridQueryVo) {
        try {
            jqGridQueryVo.setJqGridRequest(jqGridRequest);

            JqGridResponse jqGridResponse=dictionaryService.getDictionaryList(jqGridQueryVo);

            return jqGridResponse;
        } catch (Exception e) {
            return new JqGridResponse();
        }
    }


    /**
     * 跳转到新增页面
     * @param model
     * @param type
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model, String type){
        String code = dictionaryService.generateCode(type);
        model.addAttribute("code",code);
        model.addAttribute("type",type);
        model.addAttribute("typeName",DictionaryType.valueOf(type).getValue());
        return "systemManage/addDictionary";

    }


    /**
     * 新增及编辑功能
     * @param dictionary
     * @return
     */
    @RequestMapping(value = "saveOrEdit",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Json saveOrEdit(Dictionary dictionary){
        Json json = new Json();

        try{
            if (StringUtils.isBlank(dictionary.getCode()) || StringUtils.isBlank(dictionary.getName()) || StringUtils.isBlank(dictionary.getType())){
                json.setSuccess(false);
                json.setMsg("必填字段不能为空");
                return json;
            }

            json = dictionaryService.saveOrUpdate(dictionary);

        }catch (Exception e){
            json.setSuccess(false);
            json.setMsg("操作失败");
        }

        return json;
    }


    /**
     * 跳转到编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "toEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public String toEdit(Model model, Long id){
        if (id != null){
            Dictionary dictionary = dictionaryService.findById(id);
            model.addAttribute("dictionary",dictionary);
            model.addAttribute("typeName",DictionaryType.valueOf(dictionary.getType()).getValue());
        }
        return "systemManage/editDictionary";
    }


    @RequestMapping(value = "deleteById", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Json deleteById(Long id){
        if (id == null){
            return Json.fail("关键参数丢失，删除失败");
        }
        Json json = dictionaryService.deleteById(id);
        return json;
    }


    @RequestMapping(value = "changeState", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Json changeState(Long id, Boolean state){
        if (id == null){
            return Json.fail("关键参数丢失，删除失败");
        }
        Json json = dictionaryService.changeState(id,state);
        return json;
    }






}
