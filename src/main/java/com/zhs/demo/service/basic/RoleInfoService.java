package com.zhs.demo.service.basic;

import com.github.pagehelper.Page;
import com.zhs.demo.dao.basic.RoleInfoDao;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.service.PageHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class RoleInfoService {

    @Autowired
    private RoleInfoDao roleInfoDao;
    @Autowired
    private PageHelperService pageHelperService;


    public JqGridResponse findRoleInfoList(JqGridQueryVo jqGridQueryVo){

        JqGridResponse jqGridResponse = new JqGridResponse();

        Page<Object> pageHelper=pageHelperService.myBatisPageHelper(jqGridQueryVo);
        List<Object> roleInfoList=roleInfoDao.getAllRoleInfo();

        jqGridResponse = pageHelperService.pageHelper(jqGridResponse,pageHelper);

        jqGridResponse.setRows(roleInfoList);

        return jqGridResponse;

    }


}
