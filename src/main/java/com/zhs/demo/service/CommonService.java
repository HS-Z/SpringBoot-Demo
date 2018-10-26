package com.zhs.demo.service;

import com.zhs.demo.dao.JpaRepository.common.CityRepository;
import com.zhs.demo.dao.MybatisMapper.common.CityMapper;
import com.zhs.demo.model.common.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class CommonService {


    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityMapper cityMapper;


    /**
     * 删除所有的行政区域信息
     */
    public void deleteAllCity(){
        cityRepository.deleteAll();
    }


    /**
     * 批量保存行政区域信息
     * @param cityList
     */
    public void saveCity(List<City> cityList){
        cityRepository.saveAll(cityList);
    }


    /**
     * 查询行政信息集合
     * @param code
     * @param type
     * @return
     */
    public List<City> getCityList(String code, String type){
        List<City> list ;
        if ("province".equals(type)){   //省份信息
            list = cityMapper.getAllProvince();
        }else if ("city".equals(type)){   //行政市信息
            code = code.substring(0,1);
            list = cityMapper.getCityByProvince(code);
        }else {
            code = code.substring(0,3);
            list = cityMapper.getCountyByCity(code);
        }
        return list;
    }






}
