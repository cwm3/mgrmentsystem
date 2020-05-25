package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Nation;

import java.util.List;

public interface NationMapper extends BaseMapper<Nation> {
    int deleteByPrimaryKey(Integer id);

    int insert(Nation record);

    int insertSelective(Nation record);

    Nation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Nation record);

    int updateByPrimaryKey(Nation record);

    List<Nation> getAllNations();
}
