package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.mapper.MailSendLogMapper;
import org.cwm3.mgrsystem.model.MailSendLog;
import org.cwm3.mgrsystem.service.IMailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MailSendLogService  extends ServiceImpl<MailSendLogMapper, MailSendLog> implements IMailSendLogService {
    @Resource
    MailSendLogMapper mailSendLogMapper;

    @Override
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    @Override
    public IPage<MailSendLog> getPageList(QueryRequest queryRequest) {
        Page<MailSendLog> page = new Page<>(queryRequest.getPageNum(),queryRequest.getPageSize());
        return this.page(page);
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
