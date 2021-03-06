package com.zhs.demo.controller.basic;

import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridRequest;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.basic.RoleInfoService;
import com.zhs.demo.utils.CommonUtils;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.PoiUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;


@Controller
@RequestMapping("roleInfo")
public class RoleInfoController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private PoiUtils poiUtils;
    @Autowired
    private CommonUtils commonUtils;

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
     * 角色的列表查询
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


    /**
     * 跳转到角色新增页面
     * @param model
     * @return
     */
    @RequestMapping(value = "toAddRoleInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String toAddRoleInfo(Model model){
        String roleCode=roleInfoService.generateCode();
        model.addAttribute("roleCode",roleCode);
        return "systemManage/addRoleInfo";
    }


    /**
     * 跳转到角色的编辑页面
     * @param model
     * @return
     */
    @RequestMapping(value = "toEditRoleInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public String toEditRoleInfo(Model model, Long roleId){
        if (roleId != null){
            RoleInfo roleInfo = roleInfoService.findById(roleId);
            model.addAttribute("roleInfo",roleInfo);
        }
        return "systemManage/editRoleInfo";
    }

    /**
     * 角色的删除
     * @param roleId
     * @return
     */
    @RequestMapping(value = "deleteRoleInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Json deleteRoleInfo(Long roleId){
        if (roleId == null){
            return Json.fail("关键参数丢失，删除失败");
        }
        Json json = roleInfoService.deleteRoleInfo(roleId);
        return json;
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


    /**
     * 导出excel
     * @param code
     * @param type
     * @param ids
     * @return
     */
    @RequestMapping(value = "exportExcel", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json exportExcel(String code, String type, String ids){

        if (StringUtils.isBlank(ids)){
            return Json.fail("无数据");
        }

        List<RoleInfo> roleInfoList = roleInfoService.getRoleInfoByIds(ids);  //需要导出的数据

        HashMap<String,String> titleMap=new HashMap<>();  //标题行
        titleMap.put("roleCode","角色编码");
        titleMap.put("roleName","角色名称");
        titleMap.put("roleType","角色类型");
        titleMap.put("createDate","创建时间");
        titleMap.put("description","角色描述");

        List<Map<String, Object>> list = new ArrayList<>();

        //重新封装数据
        if (roleInfoList != null && roleInfoList.size() > 0){
            for (RoleInfo roleInfo:roleInfoList){

                Map<String,Object> hashMap = new HashMap<>();

                hashMap.put("roleCode",roleInfo.getRoleCode());
                hashMap.put("roleName",roleInfo.getRoleName());
                hashMap.put("roleType",roleInfo.getRoleType());
                hashMap.put("description",roleInfo.getDescription());
                hashMap.put("createDate",roleInfo.getCreateDate());

                list.add(hashMap);

            }

        }
        String fileName=commonUtils.getFileName("角色信息",".xlsx");
        poiUtils.exportExcel(fileName,"角色信息","角色信息预览",titleMap,list);
        return null;   //此处必须要返回null，不然会报错
    }


    /**
     * 导入excel
     * @param file  上传的文件信息
     * @param obj  从前台传来的其他参数（预留）
     * @return
     */
    @RequestMapping(value = "uploadExcel", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("obj") String obj){

        try{

            if (file.isEmpty()){
                return Json.fail("导入失败，Excel文件为空");
            }

            String fileName=file.getOriginalFilename();   //上传的文件名称，包括后缀名
            String suffixName=fileName.substring(fileName.lastIndexOf("."));  //文件的后缀名

            if(!(suffixName.equals(".xls") || suffixName.equals(".xlsx"))){
                return Json.fail("导入失败，格式不正确");
            }

            logger.info("开始导入文件，上传的文件名称为[{}]",fileName);

            InputStream inputStream=file.getInputStream();

            List<RoleInfo> roleInfoList = roleInfoService.uploadExcel(inputStream);

            roleInfoService.saveExcelData(roleInfoList);  //保存数据


            return Json.ok("Excel导入成功");

        }catch (Exception e){
            logger.error("Excel导入失败");
            return Json.fail("Excel导入失败");
        }

    }

}
