package org.cwm3.mgrsystem.service.impl;

import org.cwm3.mgrsystem.mapper.JobLevelMapper;
import org.cwm3.mgrsystem.model.JobLevel;
import org.cwm3.mgrsystem.service.IJobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 18:00
 */
@Service
public class JobLevelService implements IJobLevelService {
    @Autowired
    JobLevelMapper jobLevelMapper;

    @Override
    public List<JobLevel> getAllJobLevels() {
        return jobLevelMapper.getAllJobLevels();
    }


    @Override
    public Integer addJobLevel(JobLevel jobLevel) {
        jobLevel.setCreateDate(new Date());
        jobLevel.setEnabled(true);
        return jobLevelMapper.insertSelective(jobLevel);
    }

    @Override
    public Integer updateJobLevelById(JobLevel jobLevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(jobLevel);
    }

    @Override
    public Integer deleteJobLevelById(Integer id) {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteJobLevelsByIds(Integer[] ids) {
        return jobLevelMapper.deleteJobLevelsByIds(ids);
    }
}
