package org.cwm3.mgrsystem.controller.Job;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.quartz.CronExpression;
import org.cwm3.mgrsystem.common.annotation.ControllerEndpoint;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entity.FebsResponse;
import org.cwm3.mgrsystem.common.system.BaseController;
import org.cwm3.mgrsystem.model.Job;
import org.cwm3.mgrsystem.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author mrcwm
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
public class JobController extends BaseController {

    @Resource
    private IJobService jobService;

    @GetMapping("/jobList")
    public AjaxResult jobList(QueryRequest request,  Job job) {
        AjaxResult ajaxResult = new AjaxResult(true);
        Page dataTable = (Page) this.jobService.findJobs(request, job);
        ajaxResult.setAckCode("200");
        ajaxResult.setData(dataTable);
        return ajaxResult;
    }

     @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/addJob")
    @ControllerEndpoint(operation = "新增定时任务", exceptionMessage = "新增定时任务失败")
    public AjaxResult addJob(@RequestBody Job job) {
        AjaxResult ajaxResult = new AjaxResult(true);
        this.jobService.createJob(job);
        return ajaxResult;

    }

    @GetMapping("delete/{jobIds}")
    @ControllerEndpoint(operation = "删除定时任务", exceptionMessage = "删除定时任务失败")
    public AjaxResult deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        AjaxResult ajaxResult = new AjaxResult(true);
        String[] ids = jobIds.split(StringPool.COMMA);
        this.jobService.deleteJobs(ids);
        return ajaxResult;
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "修改定时任务", exceptionMessage = "修改定时任务失败")
    public AjaxResult updateJob(@Valid Job job) {
        AjaxResult ajaxResult = new AjaxResult(true);
        this.jobService.updateJob(job);
        return ajaxResult;
    }

    @GetMapping("run/{jobIds}")
    @ControllerEndpoint(operation = "执行定时任务", exceptionMessage = "执行定时任务失败")
    public AjaxResult runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        AjaxResult ajaxResult = new AjaxResult(true);
        this.jobService.run(jobIds);
        return ajaxResult;
    }

    @GetMapping("pause/{jobIds}")
    @ControllerEndpoint(operation = "暂停定时任务", exceptionMessage = "暂停定时任务失败")
    public AjaxResult pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.pause(jobIds);
        return new AjaxResult();
    }

    @GetMapping("resume/{jobIds}")
    @ControllerEndpoint(operation = "恢复定时任务", exceptionMessage = "恢复定时任务失败")
    public AjaxResult resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
        return new AjaxResult();
    }

    @GetMapping("excel")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, Job job, HttpServletResponse response) {
        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
        ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
    }
}
