package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cwm3.mgrsystem.model.MailSendLog;

import java.util.Date;
import java.util.List;

public interface MailSendLogMapper extends BaseMapper<MailSendLog> {
    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    int insert(MailSendLog mailSendLog);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId, @Param("date") Date date);
}
