package com.zhs.demo.service.notice;

import com.github.pagehelper.Page;
import com.zhs.demo.dao.JpaRepository.notice.MessageRepository;
import com.zhs.demo.dao.MybatisMapper.notice.MessageMapper;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.model.notice.Message;
import com.zhs.demo.service.PageHelperService;
import com.zhs.demo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class MessageService {

    @Autowired
    private PageHelperService pageHelperService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private SessionUtils sessionUtils;




    public JqGridResponse getList(JqGridQueryVo jqGridQueryVo){

        JqGridResponse jqGridResponse = new JqGridResponse();

        Page<Object> pageHelper=pageHelperService.myBatisPageHelper(jqGridQueryVo);
        List<Object> list=messageMapper.getMessageList(jqGridQueryVo);

        jqGridResponse = pageHelperService.pageHelper(jqGridResponse,pageHelper);

        jqGridResponse.setRows(list);

        return jqGridResponse;

    }


    public Message findById(Long id){
        Message message=messageRepository.getOne(id);
        return message;
    }

}
