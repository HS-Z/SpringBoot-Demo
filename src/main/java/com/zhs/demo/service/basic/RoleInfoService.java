package com.zhs.demo.service.basic;

import com.github.pagehelper.Page;
import com.zhs.demo.dao.JpaRepository.basic.RoleInfoRepository;
import com.zhs.demo.dao.MybatisMapper.basic.RoleInfoMapper;
import com.zhs.demo.exception.CustomRuntimeException;
import com.zhs.demo.model.basic.RoleInfo;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.model.session.SessionInfo;
import com.zhs.demo.service.PageHelperService;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        List<Object> roleInfoList=roleInfoMapper.getAllRoleInfo(jqGridQueryVo);

        jqGridResponse = pageHelperService.pageHelper(jqGridResponse,pageHelper);

        jqGridResponse.setRows(roleInfoList);

        return jqGridResponse;

    }


    public RoleInfo findRoleInfoById(Long id){
        RoleInfo roleInfo=roleInfoMapper.findById(id);
        return roleInfo;
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


    public Json deleteRoleInfo(Long roleId){

        try {
            roleInfoRepository.deleteById(roleId);
            return Json.ok("删除角色信息成功");
        }catch (Exception e){
            return Json.ok("删除角色信息失败");
        }

    }


}
