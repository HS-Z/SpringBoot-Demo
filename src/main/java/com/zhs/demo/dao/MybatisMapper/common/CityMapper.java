package com.zhs.demo.dao.MybatisMapper.common;


import com.zhs.demo.model.common.City;

import java.util.List;

public interface CityMapper {


    /**
     * 查询所有的省份信息
     * @return
     */
    List<City> getAllProvince();


    /**
     * 根据省份编码查询该省所有的行政市信息
     * @param provinceCode
     * @return
     */
    List<City> getCityByProvince(String provinceCode);


    /**
     * 根据行政市编码查询该市所有的行政县信息
     * @param cityCode
     * @return
     */
    List<City> getCountyByCity(String cityCode);




}
