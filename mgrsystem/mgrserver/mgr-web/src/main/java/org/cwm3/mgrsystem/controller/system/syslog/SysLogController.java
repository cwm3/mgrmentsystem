package org.cwm3.mgrsystem.controller.system.syslog;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.model.SysLog;
import org.cwm3.mgrsystem.service.ISysLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志表(TLog)表控制层
 *
 * @author makejava
 * @since 2020-07-08 10:13:14
 */
@RestController
@RequestMapping("/mgrsystem/syslog")
public class SysLogController {
    /**
     * 服务对象
     */
    @Resource
    private ISysLogService sysLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysLog selectOne(Long id) {
        return this.sysLogService.queryById(id);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param
     * @return 所有数据
     */
    @GetMapping("/")
    public AjaxResult getAllSysLog(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize ,String name){
        Department department = new Department();
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Page<SysLog> iPage = sysLogService.selectPageExt(pageNum,pageSize,department ,name);
            ajaxResult.setSuccess(true);
            ajaxResult.setData(iPage);
        }catch (Exception e){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage(e.getMessage());
        }
        return  ajaxResult;
    }

}