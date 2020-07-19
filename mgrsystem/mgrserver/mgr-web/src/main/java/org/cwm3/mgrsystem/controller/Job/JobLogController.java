package org.cwm3.mgrsystem.controller.Job;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.common.entity.FebsResponse;
import org.cwm3.mgrsystem.common.system.BaseController;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.model.JobLog;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.service.IJobLogService;
import org.cwm3.mgrsystem.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public RespBean jobLogList(QueryRequest request, JobLog log) {
       Page dataTable = (Page) this.jobLogService.findJobLogs(request, log);
        return RespBean.ok(dataTable);
    }

    @GetMapping("delete/{jobIds}")
    public RespBean deleteJobLog(@PathVariable String jobIds) {
        String[] ids = jobIds.split(StringPool.COMMA);
        Integer integer = this.jobLogService.deleteJobLogs(ids);
        System.out.println(integer);
        if (integer!= -1){
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除成功");
        }
    }

//    @GetMapping("excel")
//    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
//        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
//        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
//    }

    @GetMapping(value = "/exportExcel")
    @ResponseBody
    public RespBean exportExcel(HttpServletResponse response, Integer[] ids) throws Exception {
        String fileName = "任务日志表";
        String[] headers={"任务ID","bean名称","方法名称","参数","状态","异常信息","耗时（毫秒）","创建时间"};
        List<JobLog> jobLogList = new ArrayList<>();
        if ( ids!= null &&ids.length > 0 ) {
            List<Integer> list = Arrays.asList(ids);
            for (Integer id : list) {
                JobLog jobLog = jobLogService.getById(id);
                jobLogList.add(jobLog);
            }
        } else {
            jobLogList = jobLogService.list();
        }
        Map<String, Object> studentMap = new HashMap();
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (int i = 0; i < jobLogList.size(); i++) {
            JobLog jobLog = jobLogList.get(i);
            Object[] datas = new Object[8];
            datas[0] = jobLog.getJobId();
            datas[1] = jobLog.getBeanName();
            datas[2] = jobLog.getMethodName();
            datas[3] = jobLog.getParams();
            datas[4] = jobLog.getStatus();
            datas[5] = jobLog.getError();
            datas[6] = jobLog.getTimes();
            datas[7] = new SimpleDateFormat("yyyy-MM-dd").format(jobLog.getCreateTime());
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
