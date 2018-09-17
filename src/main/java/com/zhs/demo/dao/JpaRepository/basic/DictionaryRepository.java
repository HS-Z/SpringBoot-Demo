package com.zhs.demo.dao.JpaRepository.basic;

import com.zhs.demo.model.basic.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {


}
