package com.zhs.demo.service.basic;

import com.github.pagehelper.Page;
import com.zhs.demo.dao.JpaRepository.basic.RoleInfoRepository;
import com.zhs.demo.dao.MybatisMapper.basic.RoleInfoMapper;
import com.zhs.demo.common.exception.CustomRuntimeException;
import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.model.session.SessionInfo;
import com.zhs.demo.service.PageHelperService;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class RoleInfoService {

    @Autowired
    private PageHelperService pageHelperService;
    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Autowired
    private RoleInfoRepository roleInfoRepository;
    @Autowired
    private SessionUtils sessionUtils;



    public JqGridResponse findRoleInfoList(JqGridQueryVo jqGridQueryVo){

        JqGridResponse jqGridResponse = new JqGridResponse();

        Page<Object> pageHelper=pageHelperService.myBatisPageHelper(jqGridQueryVo);
        List<Object> list=roleInfoMapper.getRoleInfoList(jqGridQueryVo);

        jqGridResponse = pageHelperService.pageHelper(jqGridResponse,pageHelper);

        jqGridResponse.setRows(list);

        return jqGridResponse;

    }


    public RoleInfo findById(Long id){
        RoleInfo roleInfo=roleInfoMapper.findById(id);
        return roleInfo;
    }


    /**
     * 查询所有的角色信息，包括已被删除的
     * @return
     */
    public List<RoleInfo> getAllRoleInfo(){
        List<RoleInfo> roleInfoList=roleInfoRepository.findAll();
        return roleInfoList;
    }


    /**
     * 根据多个id查询信息
     * @param ids
     * @return
     */
    public List<RoleInfo> getRoleInfoByIds(String ids){
        List<RoleInfo> roleInfoList=roleInfoMapper.getRoleInfoByIds(ids);
        return roleInfoList;
    }


    /**
     * 查询所有的角色信息，不包括已被删除的
     * @return
     */
    public List<RoleInfo> getAllRoleInfoNotDelete(){
        List<RoleInfo> roleInfoList=roleInfoMapper.getAllRoleInfoNotDelete();
        return roleInfoList;
    }


    public Json saveOrUpdate(RoleInfo roleInfo){

        SessionInfo sessionInfo = sessionUtils.getSessionInfo();
        if (sessionInfo == null){
            throw new CustomRuntimeException("session已过期，请重新登陆");
        }

        if (roleInfo.getId() == null){   //角色新增

            //判断角色编码是否已重复
            RoleInfo role = roleInfoMapper.findRoleInfoByRoleCode(roleInfo.getRoleCode());
            if (role != null){
                return Json.fail("新增角色失败，角色编码重复");
            }

            roleInfo.setCreator(sessionInfo.getUserName());

            roleInfoRepository.save(roleInfo);

            return Json.ok("新增角色成功",roleInfo.getId());

        }else {   //角色更新

            RoleInfo role = roleInfoRepository.getOne(roleInfo.getId());

            if (role == null){
                return Json.fail("更新角色失败，未查询到角色信息");
            }

            role.setRoleName(roleInfo.getRoleName());
            role.setRoleType(roleInfo.getRoleType());
            role.setDescription(roleInfo.getDescription());
            role.setLastUpdateDate(new Date());

            roleInfoRepository.save(role);

            return Json.ok("更新角色成功",role.getId());
        }

    }


    /**
     * 批量保存
     * @param roleInfoList
     * @return
     */
    public Json saveAll(List<RoleInfo> roleInfoList){
        try {
            roleInfoRepository.saveAll(roleInfoList);
            return Json.ok("批量保存角色信息成功");
        }catch (Exception e){
            return Json.fail("批量保存角色信息失败");
        }
    }


    /**
     * 保存excel的导入数据
     * @param roleInfoList
     * @return
     */
    public Json saveExcelData(List<RoleInfo> roleInfoList){
        for (RoleInfo roleInfo:roleInfoList){
            if (roleInfo.getCreateDate() == null){  //创建时间
                roleInfo.setCreateDate(new Date());
            }
            if (roleInfo.getLastUpdateDate() == null){  //最近更改时间
                roleInfo.setLastUpdateDate(new Date());
            }
            if (StringUtils.isBlank(roleInfo.getRoleCode())){  //角色编码
                String roleCode=this.generateCode();
                roleInfo.setRoleCode(roleCode);
            }
            if (roleInfo.getDeleted() == null){
                roleInfo.setDeleted(Boolean.FALSE);
            }
        }

        this.saveAll(roleInfoList);

        return Json.ok("成功");
    }


    public Json deleteRoleInfo(Long roleId){

        try {
            roleInfoRepository.deleteById(roleId);
            return Json.ok("删除角色信息成功");
        }catch (Exception e){
            return Json.ok("删除角色信息失败");
        }

    }


    /**
     * 生成角色编码
     * @return
     */
    public String generateCode(){
        String maxRoleCode = roleInfoMapper.getMaxRoleCode();
        if (StringUtils.isBlank(maxRoleCode)){
            maxRoleCode = "R000001";
            return maxRoleCode;
        }
        String subRoleCode = maxRoleCode.substring(1);
        int intRoleCode = Integer.parseInt(subRoleCode) + 1;
        maxRoleCode = String.format("R" + "%06d",intRoleCode);

        for (int i=0; i < 10000; i++){   //循环10000次都找不到不重复的编码，我是不信的

            RoleInfo roleInfo = roleInfoMapper.findRoleInfoByRoleCode(maxRoleCode);   //判断新生成的角色编码是否已存在
            if (roleInfo == null){
                break;
            }else {
                maxRoleCode = String.format("R" + "%06d",intRoleCode + 1);
            }
        }

        return maxRoleCode;
    }


    /**
     * 导入excel
     * @param inputStream
     * @return
     */
    public List<RoleInfo> uploadExcel(InputStream inputStream){

        List<RoleInfo> roleInfoList=new ArrayList<>();

        try {
            Workbook workbook = WorkbookFactory.create(inputStream);

            if (workbook == null){
                throw new RuntimeException("创建excel工作簿失败");
            }

            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            //遍历excel中的所有sheet，但一般只会有一个
            for (int i=0; i<workbook.getNumberOfSheets(); i++){
                sheet = workbook.getSheetAt(i);
                if (sheet == null){
                    continue;
                }

                //判断excel格式是否符合标准
                row = sheet.getRow(0);
                if (row.getPhysicalNumberOfCells() != 2){  //标准应该为两列，角色名称，角色描述
                    throw new RuntimeException("创建excel工作簿失败");
                }
                //遍历当前sheet中的除标题以外的行
                for (int j=1; j<=sheet.getLastRowNum(); j++){
                    row = sheet.getRow(j);
                    if (row == null){
                        continue;
                    }

                    RoleInfo roleInfo = new RoleInfo();
                    if (row.getCell(0) != null){
                        roleInfo.setRoleName(row.getCell(0).toString());
                    }
                    if (row.getCell(1) != null){
                        roleInfo.setDescription(row.getCell(1).toString());
                    }

                    roleInfoList.add(roleInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return roleInfoList;

    }


}
