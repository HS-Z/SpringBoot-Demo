package com.zhs.demo.dao.JpaRepository.notice;

import com.zhs.demo.model.notice.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {


}
