package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.model.Menu;

import java.util.List;

/**
 * @Classname IMenuService
 * @Author chengweiming
 * @Date 2020/5/25 11:13
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenusByHrId();

    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenus();

    List<Integer> getMidsByRid(Integer rid);

    boolean updateMenuRole(Integer rid, Integer[] mids);

    Integer insertMenu(Menu menu);

    Object findjobList(QueryRequest request, Menu menu);

    void deleteByParentId(Integer id);
}
