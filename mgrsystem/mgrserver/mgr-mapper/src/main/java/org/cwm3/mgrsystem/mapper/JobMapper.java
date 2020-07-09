package org.cwm3.mgrsystem.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cwm3.mgrsystem.model.Job;

import java.util.List;

/**
 * @author mrcwm
 */
public interface JobMapper extends BaseMapper<Job> {
	
	List<Job> queryList();
}