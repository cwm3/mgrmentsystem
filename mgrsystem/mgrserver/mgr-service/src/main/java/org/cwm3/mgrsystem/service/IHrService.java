package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Hr;

import java.util.List;

/**
 * @Classname IHrService
 * @Author chengweiming
 * @Date 2020/5/25 11:11
 */
public interface IHrService extends IService<Hr> {

    List<Hr> getAllHrs(String keywords);

    Integer updateHr(Hr hr);

    boolean updateHrRole(Integer hrid, Integer[] rids);

    Integer deleteHrById(Integer id);

    List<Hr> getAllHrsExceptCurrentHr();

    Integer updateHyById(Hr hr);

    boolean updateHrPasswd(String oldpass, String pass, Integer hrid);

    Integer updateUserface(String url, Integer id);

}
