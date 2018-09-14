package com.zhs.demo.controller.basic;

import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.basic.RoleInfoService;
import com.zhs.demo.utils.Json;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("roleInfo")
public class RoleInfoController {

    @Autowired
    private RoleInfoService roleInfoService;

    /**
     * 跳转到角色列表
     * @param model
     * @return
     */
    @RequestMapping(value = "toRoleInfoList", method = {RequestMethod.GET, RequestMethod.POST})
    public String toRoleInfoList(Model model){
        return "systemManage/roleInfoList";
    }


    /**
     * 查询角色列表
     * @param jqGridRequest
     * @param jqGridQueryVo
     * @return
     */
    @RequestMapping(value = "getRoleInfoList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JqGridResponse list(JqGridRequest jqGridRequest, JqGridQueryVo jqGridQueryVo) {
        try {
            jqGridQueryVo.setJqGridRequest(jqGridRequest);

            JqGridResponse jqGridResponse=roleInfoService.findRoleInfoList(jqGridQueryVo);

            return jqGridResponse;
        } catch (Exception e) {
            return new JqGridResponse();
        }
    }



    @RequestMapping(value = "addRoleInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String addRoleInfo(Model model){
        String roleCode="R1809140001";
        model.addAttribute("roleCode",roleCode);
        return "systemManage/addRoleInfo";
    }


    @RequestMapping(value = "saveOrUpdateRoleInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Json saveOrUpdateRoleInfo(RoleInfo roleInfo) {
        Json json = new Json();
        try {

            if (StringUtils.isBlank(roleInfo.getRoleCode()) || StringUtils.isBlank(roleInfo.getRoleName()) || StringUtils.isBlank(roleInfo.getRoleType())){
                json.setSuccess(false);
                json.setMsg("必填字段不能为空");
                return json;
            }

            json = roleInfoService.saveOrUpdate(roleInfo);

        } catch (Exception e) {
            json.setSuccess(false);
            if (roleInfo.getId() != null){
                json.setMsg("角色信息更新失败");
            }else {
                json.setMsg("角色信息新增失败");
            }
        }
        return json;
    }

}
