package org.cwm3.mgrsystem.service.impl;

import org.cwm3.mgrsystem.mapper.MailSendLogMapper;
import org.cwm3.mgrsystem.model.MailSendLog;
import org.cwm3.mgrsystem.service.IMailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MailSendLogService implements IMailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;

    @Override
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    @Override
    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    @Override
    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    @Override
    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }
}
