package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Nation;

import java.util.List;

/**
 * @Classname INationService
 * @Author chengweiming
 * @Date 2020/5/25 11:14
 */
public interface INationService extends IService<Nation> {

    List<Nation> getAllNations();
}
