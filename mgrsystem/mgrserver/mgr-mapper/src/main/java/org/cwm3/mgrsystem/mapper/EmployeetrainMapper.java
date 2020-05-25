package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Employeetrain;

public interface EmployeetrainMapper extends BaseMapper<Employeetrain> {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);
}
