package org.cwm3.mgrsystem.controller.Job;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.model.JobLog;
import org.cwm3.mgrsystem.utils.ExcelUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

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

//    @GetMapping("excel")
//    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
//    public void export(QueryRequest request, Job job, HttpServletResponse response) {
//        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
//        ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
//    }
    @GetMapping("exportExcel")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public AjaxResult exportExcel(HttpServletResponse response, Integer[] ids) throws Exception {
        AjaxResult ajaxResult = new AjaxResult(true);
        String fileName = "任务表";
        String[] headers={"任务ID","bean名称","方法名称","参数","状态","异常信息","耗时（毫秒）","创建时间"};
        List<Job> jobList = new ArrayList<>();
        if ( ids!= null &&ids.length > 0 ) {
            List<Integer> list = Arrays.asList(ids);
            for (Integer id : list) {
                Job job= jobService.getById(id);
                jobList.add(job);
            }
        } else {
            jobList = jobService.list();
        }
        Map<String, Object> studentMap = new HashMap();
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            Object[] datas = new Object[8];
            datas[0] = job.getJobId();
            datas[1] = job.getBeanName();
            datas[2] = job.getMethodName();
            datas[3] = job.getParams();
            datas[4] = job.getCronExpression();
            datas[5] = job.getStatus();
            datas[6] = job.getRemark();
            datas[7] = new SimpleDateFormat("yyyy-MM-dd").format(job.getCreateTime());
            dataList.add(datas);
        }
        studentMap.put("headers", headers);
        studentMap.put("dataList", dataList);
        studentMap.put("fileName", fileName);
        List<Map> mapList = new ArrayList();
        mapList.add(studentMap);
        try {
            ExcelUtil.exportMultisheetExcel(fileName, mapList, response);
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
             ajaxResult.setMessage(e.getMessage());
             ajaxResult.setSuccess(false);
             return ajaxResult;
        }

    }
}
