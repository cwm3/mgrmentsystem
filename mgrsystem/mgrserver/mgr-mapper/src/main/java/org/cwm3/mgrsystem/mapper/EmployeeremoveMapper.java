package org.cwm3.mgrsystem.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Employeeremove;

public interface EmployeeremoveMapper extends BaseMapper<Employeeremove> {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeremove record);

    int insertSelective(Employeeremove record);

    Employeeremove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeremove record);

    int updateByPrimaryKey(Employeeremove record);
}
