package com.zhs.demo.service.notice;

import com.github.pagehelper.Page;
import com.zhs.demo.common.exception.SessionExpireException;
import com.zhs.demo.constant.DictionaryConstant;
import com.zhs.demo.dao.JpaRepository.notice.MessageRepository;
import com.zhs.demo.dao.MybatisMapper.basic.DictionaryMapper;
import com.zhs.demo.dao.MybatisMapper.notice.MessageMapper;
import com.zhs.demo.model.basic.Dictionary;
import com.zhs.demo.model.jqGrid.JqGridQueryVo;
import com.zhs.demo.model.jqGrid.JqGridResponse;
import com.zhs.demo.model.notice.Message;
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
public class MessageService {

    @Autowired
    private PageHelperService pageHelperService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
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


    /**
     * 新增及编辑功能
     * @param message
     * @return
     */
    public Json saveOrUpdate(Message message){

        SessionInfo sessionInfo = sessionUtils.getSessionInfo();
        if (sessionInfo == null){
            throw new SessionExpireException("session已过期，请重新登陆");
        }

        if (message.getId() == null){   //新增

            //查询消息类型
            Dictionary dictionary = dictionaryMapper.findByCodeAndType(message.getMessageType(), DictionaryConstant.MESSAGE_TYPE.toString());
            if (dictionary != null){
                message.setMessageTypeName(dictionary.getName());
            }

            dictionary.setCreator(sessionInfo.getUserName());  //发送人

            messageRepository.save(message);

            return Json.ok("新增成功",message.getId());

        }else {   //编辑

            Message m = messageRepository.getOne(message.getId());

            if (m == null){
                return Json.fail("更新失败，未查询到数据信息");
            }

            m.setMessageType(message.getMessageType());
            m.setSendRange(message.getSendRange());
            m.setContent(message.getContent());
            m.setLastUpdateDate(new Date());

            Dictionary dictionary = dictionaryMapper.findByCodeAndType(message.getMessageType(), DictionaryConstant.MESSAGE_TYPE.toString());
            if (dictionary != null){
                message.setMessageTypeName(dictionary.getName());
            }

            messageRepository.save(m);

            return Json.ok("更新成功",m.getId());
        }

    }

}
