package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cwm3.mgrsystem.mapper.PositionMapper;
import org.cwm3.mgrsystem.model.Position;
import org.cwm3.mgrsystem.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 15:54
 */
@Service
public class PositionService  extends ServiceImpl<PositionMapper, Position> implements IPositionService {
    @Autowired
    PositionMapper positionMapper;

    @Override
    public List<Position> getAllPositions() {
        return positionMapper.getAllPositions();
    }


    @Override
    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    @Override
    public Integer updatePositions(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    @Override
    public Integer deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deletePositionsByIds(Integer[] ids) {
        return positionMapper.deletePositionsByIds(ids);
    }
}
