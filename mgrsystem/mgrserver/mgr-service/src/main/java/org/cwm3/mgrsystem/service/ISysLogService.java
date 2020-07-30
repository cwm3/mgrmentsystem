package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.model.SysLog;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 操作日志表(SysLog)表服务接口
 *
 * @author makejava
 * @since 2020-07-08 10:17:56
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLog queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    SysLog insert(SysLog sysLog);

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 实例对象
     */
    SysLog update(SysLog sysLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Page<SysLog> selectPageExt(Integer pageNum, Integer pageSize ,String name);
    /**
     * 异步保存操作日志
     *
     * @param point     切点
     * @param method    Method
     * @param ip   ip
     * @param operation 操作内容
     * @param start     开始时间
     */
    @Async()
    void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation, long start);


}