package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Appraise;

public interface AppraiseMapper extends BaseMapper<Appraise> {
    int deleteByPrimaryKey(Integer id);

    int insert(Appraise record);

    int insertSelective(Appraise record);

    Appraise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appraise record);

    int updateByPrimaryKey(Appraise record);
}
