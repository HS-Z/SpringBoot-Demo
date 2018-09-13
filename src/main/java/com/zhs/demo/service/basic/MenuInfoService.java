package com.zhs.demo.service.basic;

import com.zhs.demo.dao.basic.MenuInfoDao;
import com.zhs.demo.model.basic.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuInfoService {

    @Autowired
    private MenuInfoDao menuInfoDao;


    public MenuInfo findMenuInfoById(Long id){
        MenuInfo menuInfo=menuInfoDao.getOne(id);
        return menuInfo;
    }


}
