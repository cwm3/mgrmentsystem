package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Politicsstatus;

import java.util.List;

public interface PoliticsstatusMapper extends BaseMapper<Politicsstatus> {
    int deleteByPrimaryKey(Integer id);

    int insert(Politicsstatus record);

    int insertSelective(Politicsstatus record);

    Politicsstatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Politicsstatus record);

    int updateByPrimaryKey(Politicsstatus record);

    List<Politicsstatus> getAllPoliticsstatus();
}
