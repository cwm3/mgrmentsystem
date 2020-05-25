package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Employeeec;

public interface EmployeeecMapper  extends BaseMapper<Employeeec> {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);
}
