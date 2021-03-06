package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.JobLevel;

import java.util.List;

/**
 * @Classname IJobLevelService
 * @Author chengweiming
 * @Date 2020/5/25 11:12
 */
public interface IJobLevelService extends IService<JobLevel> {

    List<JobLevel> getAllJobLevels();

    Integer addJobLevel(JobLevel jobLevel);

    Integer updateJobLevelById(JobLevel jobLevel);

    Integer deleteJobLevelById(Integer id);

    Integer deleteJobLevelsByIds(Integer[] ids);
}
