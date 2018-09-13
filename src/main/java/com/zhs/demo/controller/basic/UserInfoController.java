package com.zhs.demo.controller.basic;

import com.zhs.demo.model.basic.UserInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.redis.RedisUtils;
import com.zhs.demo.service.basic.RoleInfoService;
import com.zhs.demo.service.basic.UserInfoService;
import com.zhs.demo.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CommonUtils commonUtils;


    @RequestMapping(value = "/aaa",method = RequestMethod.GET)
    public String aaa(){

        String UUID=commonUtils.getUUID();

        String userName="zhs";
        String password="111111";

        String newPassword=commonUtils.encryptDataMD5(password,UUID);
        UserInfo userInfo=new UserInfo();
        userInfo.setAccount(userName);
        userInfo.setPassword(newPassword);
        userInfo.setSecurity(UUID);
//        userInfoService.save(userInfo);

        return "login";
    }

    @RequestMapping(value = "toUserInfoList", method = {RequestMethod.GET, RequestMethod.POST})
    public String toUserInfoList(Model model){

        return "systemManage/userInfoList";
    }



    @RequestMapping(value = "getUserInfoList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JqGridResponse list(JqGridRequest jqGridRequest, JqGridQueryVo jqGridQueryVo) {
        try {
            jqGridQueryVo.setJqGridRequest(jqGridRequest);
            JqGridResponse jqGridResponse = new JqGridResponse();

            jqGridResponse=roleInfoService.findRoleInfoList(jqGridQueryVo);


            return jqGridResponse;
        } catch (Exception e) {
            return new JqGridResponse();
        }
    }




}
