package org.cwm3.mgrsystem.feignService;

import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/7/22 16:23
 */
@FeignClient(value = "mailserver")
public interface IMailSendLogFeignService {
    @GetMapping(value = "/pageList" )
    RespBean getPageList(QueryRequest queryRequest);
}
