package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cwm3.mgrsystem.mapper.PoliticsstatusMapper;
import org.cwm3.mgrsystem.model.Politicsstatus;
import org.cwm3.mgrsystem.service.IPoliticsstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-11-03 23:20
 */
@Service
public class PoliticsstatusService  extends ServiceImpl<PoliticsstatusMapper, Politicsstatus> implements IPoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    @Override
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
