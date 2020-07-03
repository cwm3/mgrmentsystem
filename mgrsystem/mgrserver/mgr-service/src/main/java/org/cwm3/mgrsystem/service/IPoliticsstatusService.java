package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Politicsstatus;

import java.util.List;

/**
 * @Classname IPoliticsstatusService
 * @Author chengweiming
 * @Date 2020/5/25 11:14
 */
public interface IPoliticsstatusService extends IService<Politicsstatus> {

    List<Politicsstatus> getAllPoliticsstatus();
}
