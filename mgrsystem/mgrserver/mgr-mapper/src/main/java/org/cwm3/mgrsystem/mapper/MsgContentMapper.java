package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.MsgContent;

public interface MsgContentMapper extends BaseMapper<MsgContent> {
    int deleteByPrimaryKey(Integer id);

    int insert(MsgContent record);

    int insertSelective(MsgContent record);

    MsgContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsgContent record);

    int updateByPrimaryKey(MsgContent record);
}
