package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import org.cwm3.mgrsystem.mapper.SysLogMapper;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.model.SysLog;
import org.cwm3.mgrsystem.service.ISysLogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志表(SysLog)表服务实现类
 *
 * @author makejava
 * @since 2020-07-08 10:17:57
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysLog queryById(Long id) {
        return this.sysLogMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysLog> queryAllByLimit(int offset, int limit) {
        return this.sysLogMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysLog insert(SysLog sysLog) {
        this.sysLogMapper.insert(sysLog);
        return sysLog;
    }

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    @Override
    public SysLog update(SysLog sysLog) {
        this.sysLogMapper.update(sysLog);
        return this.queryById(sysLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysLogMapper.deleteById(id) > 0;
    }

    @Override
    public Page<SysLog> selectPageExt(Integer pageNum, Integer pageSize, Department department,String name) {
        Page<SysLog> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<SysLog> lambdaQueryWrapper =new LambdaQueryWrapper();
        if (StringUtil.isNotEmpty(name)) {
            lambdaQueryWrapper.like(SysLog::getUsername, name);
            Page iPage = (Page) sysLogMapper.selectPage(page, lambdaQueryWrapper);
            return iPage;
        }
        Page iPage = (Page) sysLogMapper.selectPage(page, lambdaQueryWrapper);
        return iPage;
    }
}