package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cwm3.mgrsystem.mapper.MenuMapper;
import org.cwm3.mgrsystem.mapper.MenuRoleMapper;
import org.cwm3.mgrsystem.model.Hr;
import org.cwm3.mgrsystem.model.Menu;
import org.cwm3.mgrsystem.model.MenuRole;
import org.cwm3.mgrsystem.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-09-27 7:13
 */
@Service
@CacheConfig(cacheNames = "menus_cache")
public class MenuService  extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    @Override
    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    @Cacheable
    @Override
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @Override
    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    @Override
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.length == 0) {
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result==mids.length;
    }

    @Override
    public Integer insertMenu(Menu menu) {
       return menuMapper.insertOne(menu);
    }
}
