package org.cwm3.mgrsystem.controller.Job;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.cwm3.mgrsystem.common.annotation.ControllerEndpoint;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.common.entity.FebsResponse;
import org.cwm3.mgrsystem.common.system.BaseController;
import org.cwm3.mgrsystem.model.JobLog;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.service.IJobLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author mrcwm
 */
@Slf4j
@Validated
@RestController
@RequestMapping("jobLog")
public class JobLogController extends BaseController {

    @Resource
    private IJobLogService jobLogService;

    @GetMapping("/jobLogList")
    public AjaxResult jobLogList(QueryRequest request, JobLog log) {
       Page dataTable = (Page) this.jobLogService.findJobLogs(request, log);
        return new AjaxResult(dataTable);
    }

    @GetMapping("delete/{jobIds}")
    @ControllerEndpoint(exceptionMessage = "删除调度日志失败")
    public AjaxResult deleteJobLog(@PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        Integer integer = this.jobLogService.deleteJobLogs(ids);
        System.out.println(integer);
        if (integer!= -1){
            return new AjaxResult();
        }else {
            return new AjaxResult(false);
        }
    }

    @GetMapping("excel")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
    }
}
