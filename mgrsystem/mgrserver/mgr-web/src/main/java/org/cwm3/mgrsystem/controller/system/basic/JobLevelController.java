package org.cwm3.mgrsystem.controller.system.basic;

import org.apache.ibatis.annotations.Delete;
import org.cwm3.mgrsystem.model.JobLevel;
import org.cwm3.mgrsystem.model.RespBean;
import org.cwm3.mgrsystem.service.IJobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 17:59
 */
@RestController
@RequestMapping("/mgrsystem/basic/joblevel")
public class JobLevelController {
    @Autowired
    IJobLevelService jobLevelService;
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.addJobLevel(jobLevel) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.updateJobLevelById(jobLevel) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id) {
        if (jobLevelService.deleteJobLevelById(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids) {
        if (jobLevelService.deleteJobLevelsByIds(ids) == ids.length) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
