package com.zhs.demo.service;

import com.zhs.demo.dao.JpaRepository.common.CityRepository;
import com.zhs.demo.dao.MybatisMapper.common.CityMapper;
import com.zhs.demo.model.common.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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






}
