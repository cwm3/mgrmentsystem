package org.cwm3.mailserver.receiver.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.model.MailSendLog;
import org.cwm3.mgrsystem.service.IMailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;


/**
 * @description:
 * @author: chengweiming
 * @time: 2020/7/22 14:56
 */
@RequestMapping("/mailSendLog")
@RestController
@Api(value = "mail_send_log", description = "邮件发送日志")
public class MailsendLogController {

     @Autowired
     IMailSendLogService mailSendLogService;

    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("根据条件查询邮件发送日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", required = true, dataType = "Int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少行", required = true, dataType = "Int", paramType = "query", example = "1"),
    })
    public RespBean getPageList(@ApiIgnore QueryRequest queryRequest){
        try {
            IPage<MailSendLog> mailSendLogIPage = this.mailSendLogService.getPageList(queryRequest);
            return RespBean.ok("查询成功",mailSendLogIPage);
        }catch (Exception e){
            return RespBean.error(e.getMessage());
        }
    }


}
