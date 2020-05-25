package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.OpLog;

public interface OpLogMapper extends BaseMapper<OpLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    OpLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKey(OpLog record);
}
