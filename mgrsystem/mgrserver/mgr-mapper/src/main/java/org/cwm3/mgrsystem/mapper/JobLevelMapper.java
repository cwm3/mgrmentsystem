package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cwm3.mgrsystem.model.JobLevel;

import java.util.List;

public interface JobLevelMapper extends BaseMapper<JobLevel> {
    int deleteByPrimaryKey(Integer id);

    int insert(JobLevel record);

    int insertSelective(JobLevel record);

    JobLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobLevel record);

    int updateByPrimaryKey(JobLevel record);

    List<JobLevel> getAllJobLevels();

    Integer deleteJobLevelsByIds(@Param("ids") Integer[] ids);
}
