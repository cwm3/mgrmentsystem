package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.model.MailSendLog;

import java.util.Date;
import java.util.List;

/**
 * @Classname IMailSendLogService
 * @Author chengweiming
 * @Date 2020/5/25 11:12
 */
public interface IMailSendLogService extends IService<MailSendLog> {

    Integer insert(MailSendLog mailSendLog);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(String msgId, Date date);

    Integer updateMailSendLogStatus(String msgId, Integer status);

    IPage<MailSendLog> getPageList(QueryRequest queryRequest);
}
