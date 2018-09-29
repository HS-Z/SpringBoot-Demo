package com.zhs.demo.controller.notice;

import com.zhs.demo.constant.NoticeConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Zhang on 2018/9/27.
 */
@Controller
@RequestMapping("email")
public class EmailController {


    /**
     * 跳转到邮箱列表
     * @param model
     * @param type  区分收件箱还是发件箱
     * @return
     */
    @RequestMapping(value = "toEmailList", method = {RequestMethod.GET, RequestMethod.POST})
    public String toEmailList(Model model,String type){
        model.addAttribute("type",type);
        model.addAttribute("typeName", NoticeConstant.valueOf(type).getValue());
        return "notice/emailList";
    }
}
