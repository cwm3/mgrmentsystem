package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.AdjustSalary;

public interface AdjustSalaryMapper extends BaseMapper<AdjustSalary> {
    int deleteByPrimaryKey(Integer id);

    int insert(AdjustSalary record);

    int insertSelective(AdjustSalary record);

    AdjustSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdjustSalary record);

    int updateByPrimaryKey(AdjustSalary record);
}
