package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cwm3.mgrsystem.model.Position;

import java.util.List;

public interface PositionMapper  extends BaseMapper<Position> {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();

    Integer deletePositionsByIds(@Param("ids") Integer[] ids);
}
