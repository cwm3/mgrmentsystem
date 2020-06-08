package org.cwm3.mgrsystem.service.impl;

import org.cwm3.mgrsystem.mapper.NationMapper;
import org.cwm3.mgrsystem.model.Nation;
import org.cwm3.mgrsystem.service.INationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-11-03 23:20
 */
@Service
public class NationService implements INationService {
    @Autowired
    NationMapper nationMapper;

    @Override
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
