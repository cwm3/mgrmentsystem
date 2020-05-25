package org.cwm3.mgrsystem.controller.system.basic;

import org.cwm3.mgrsystem.model.Menu;
import org.cwm3.mgrsystem.model.RespBean;
import org.cwm3.mgrsystem.model.Role;
import org.cwm3.mgrsystem.service.IMenuService;
import org.cwm3.mgrsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-01 19:41
 */
@RestController
@RequestMapping("/mgrsystem/basic/permiss")
public class PermissController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IMenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid) {
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        if (menuService.updateMenuRole(rid, mids)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role) {
        if (roleService.addRole(role) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
