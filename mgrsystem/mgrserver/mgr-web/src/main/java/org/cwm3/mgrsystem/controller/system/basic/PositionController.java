package org.cwm3.mgrsystem.controller.system.basic;

import org.cwm3.mgrsystem.common.annotation.FuncLogEnum;
import org.cwm3.mgrsystem.common.annotation.Log;
import org.cwm3.mgrsystem.model.Position;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 15:52
 */
@RestController
@RequestMapping("/mgrsystem/basic/pos")
public class PositionController {
    @Autowired
    IPositionService positionService;
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @Log(value="添加职位信息",table="role",type = FuncLogEnum.OPERATE)
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        if (positionService.addPosition(position) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @Log(value="更新职位信息",table="role",type = FuncLogEnum.OPERATE)
    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position) {
        if (positionService.updatePositions(position) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @Log(value="删除职位信息",table="role",type = FuncLogEnum.OPERATE)
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id) {
        if (positionService.deletePositionById(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @Log(value="删除职位信息",table="role",type = FuncLogEnum.OPERATE)
    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids) {
        if (positionService.deletePositionsByIds(ids) == ids.length) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
