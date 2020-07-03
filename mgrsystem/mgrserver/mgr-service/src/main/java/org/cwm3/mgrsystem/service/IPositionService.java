package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Position;

import java.util.List;

/**
 * @Classname IPositionService
 * @Author chengweiming
 * @Date 2020/5/25 11:15
 */
public interface IPositionService extends IService<Position> {

    List<Position> getAllPositions();

    Integer addPosition(Position position);

    Integer updatePositions(Position position);

    Integer deletePositionById(Integer id);

    public Integer deletePositionsByIds(Integer[] ids);

}
