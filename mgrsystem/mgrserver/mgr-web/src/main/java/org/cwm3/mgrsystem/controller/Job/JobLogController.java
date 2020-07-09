//package org.cwm3.mgrsystem.controller.Job;
//
//
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.wuwenze.poi.ExcelKit;
//import lombok.extern.slf4j.Slf4j;
//import org.cwm3.mgrsystem.common.annotation.ControllerEndpoint;
//import org.cwm3.mgrsystem.common.entity.FebsResponse;
//import org.cwm3.mgrsystem.common.system.BaseController;
//import org.cwm3.mgrsystem.model.JobLog;
//import org.cwm3.mgrsystem.common.entily.QueryRequest;
//import org.cwm3.mgrsystem.common.entily.RespBean;
//import org.cwm3.mgrsystem.service.IJobLogService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.constraints.NotBlank;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author mrcwm
// */
//@Slf4j
//@Validated
//@RestController
//@RequestMapping("jobLog")
//public class JobLogController extends BaseController {
//
//    @Autowired
//    private IJobLogService jobLogService;
//
//    @GetMapping
//    public RespBean jobLogList(QueryRequest request, JobLog log) {
//        Map<String, Object> dataTable = getDataTable(this.jobLogService.findJobLogs(request, log));
//        return new RespBean(200,"ok",dataTable);
//    }
//
//    @GetMapping("delete/{jobIds}")
//    @ControllerEndpoint(exceptionMessage = "删除调度日志失败")
//    public FebsResponse deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) {
//        String[] ids = jobIds.split(StringPool.COMMA);
//        this.jobLogService.deleteJobLogs(ids);
//        return new FebsResponse().success();
//    }
//
//    @GetMapping("excel")
//    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
//    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
//        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
//        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
//    }
//}
