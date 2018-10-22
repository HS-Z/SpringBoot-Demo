package com.zhs.demo.dao.JpaRepository.common;

import com.zhs.demo.model.common.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {


}
