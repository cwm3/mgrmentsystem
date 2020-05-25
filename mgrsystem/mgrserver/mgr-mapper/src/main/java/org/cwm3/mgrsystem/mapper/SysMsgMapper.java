package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.SysMsg;

public interface SysMsgMapper extends BaseMapper<SysMsg> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMsg record);

    int insertSelective(SysMsg record);

    SysMsg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMsg record);

    int updateByPrimaryKey(SysMsg record);
}
