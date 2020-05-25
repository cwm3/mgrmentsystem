package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.EmpSalary;

public interface EmpSalaryMapper extends BaseMapper<EmpSalary> {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpSalary record);

    int insertSelective(EmpSalary record);

    EmpSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpSalary record);

    int updateByPrimaryKey(EmpSalary record);
}
