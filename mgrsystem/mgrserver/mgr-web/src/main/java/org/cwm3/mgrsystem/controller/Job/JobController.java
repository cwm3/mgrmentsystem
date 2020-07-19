package org.cwm3.mgrsystem.controller.Job;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.cwm3.mgrsystem.common.annotation.FuncLogEnum;
import org.cwm3.mgrsystem.common.annotation.Log;
import org.cwm3.mgrsystem.common.entily.RespBean;

import org.cwm3.mgrsystem.utils.ExcelUtil;
import org.quartz.CronExpression;
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
    public RespBean jobList(QueryRequest request, Job job) {
        Page dataTable = (Page) this.jobService.findJobs(request, job);
        return RespBean.ok(dataTable);
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
    public RespBean addJob(@RequestBody Job job) {
        this.jobService.createJob(job);
        return RespBean.ok("添加成功");

    }

    @Log(value="删除定时任务",table="t_job",type = FuncLogEnum.SYS_FUNCTION)
    @GetMapping("delete/{jobIds}")
    public RespBean deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        this.jobService.deleteJobs(ids);
        return RespBean.ok("删除成功");
    }

    @Log(value="更新定时任务",table="t_job",type = FuncLogEnum.SYS_FUNCTION)
    @PostMapping("/update")
    public RespBean updateJob(@RequestBody Job job) {
        this.jobService.updateJob(job);
        return RespBean.ok("修改成功");
    }
    @Log(value="运行定时任务",table="t_job",type = FuncLogEnum.SYS_FUNCTION)
    @GetMapping("run/{jobIds}")

    public RespBean runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.run(jobIds);
        return RespBean.ok("执行成功");
    }

    @Log(value="暂停定时任务",table="t_job",type = FuncLogEnum.SYS_FUNCTION)
    @GetMapping("pause/{jobIds}")
    public RespBean pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.pause(jobIds);
        return RespBean.ok("暂停定时任务");
    }

    @Log(value="恢复定时任务",table="t_job",type = FuncLogEnum.SYS_FUNCTION)
    @GetMapping("resume/{jobIds}")
    public RespBean resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
        return RespBean.ok("恢复定时任务");
    }

//    @GetMapping("excel")
//    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
//    public void export(QueryRequest request, Job job, HttpServletResponse response) {
//        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
//        ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
//    }
    @GetMapping("exportExcel")
    public RespBean exportExcel(HttpServletResponse response, Integer[] ids) throws Exception {
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
            return RespBean.ok("导出成功");
        } catch (Exception e) {
            e.printStackTrace();
             return RespBean.error("导出失败");
        }

    }
}
