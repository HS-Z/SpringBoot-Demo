package com.zhs.demo.controller.notice;

import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.notice.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Zhang on 2018/9/27.
 */
@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "toMessageList", method = {RequestMethod.GET,RequestMethod.POST})
    public String toMessageList(Model model){
        return "notice/messageList";
    }


    @RequestMapping(value = "getMessageList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JqGridResponse list(JqGridRequest jqGridRequest, JqGridQueryVo jqGridQueryVo) {
        try {
            jqGridQueryVo.setJqGridRequest(jqGridRequest);

            JqGridResponse jqGridResponse=messageService.getList(jqGridQueryVo);

            return jqGridResponse;
        } catch (Exception e) {
            return new JqGridResponse();
        }
    }
}
