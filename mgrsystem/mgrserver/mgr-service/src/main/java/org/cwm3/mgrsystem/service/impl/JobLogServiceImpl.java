package org.cwm3.mgrsystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cwm3.mgrsystem.mapper.JobLogMapper;
import org.cwm3.mgrsystem.model.JobLog;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.service.IJobLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author mrcwm
 */
@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

    @Override
    public IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog) {
        LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(jobLog.getBeanName())) {
            queryWrapper.like(JobLog::getBeanName, jobLog.getBeanName());
        }
        if (StringUtils.isNotBlank(jobLog.getMethodName())) {
            queryWrapper.like(JobLog::getMethodName, jobLog.getMethodName());
        }
        if (StringUtils.isNotBlank(jobLog.getStatus())) {
            queryWrapper.eq(JobLog::getStatus, jobLog.getStatus());
        }
        Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
//        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);

        queryWrapper.orderByDesc(JobLog::getCreateTime);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    @Override
    @Transactional
    public Integer deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        int i = this.baseMapper.deleteBatchIds(list);
        return i;
    }

}
