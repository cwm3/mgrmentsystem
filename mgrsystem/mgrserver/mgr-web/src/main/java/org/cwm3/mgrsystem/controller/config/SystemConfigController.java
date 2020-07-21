package org.cwm3.mgrsystem.controller.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cwm3.mgrsystem.common.annotation.FuncLogEnum;
import org.cwm3.mgrsystem.common.annotation.Log;
import org.cwm3.mgrsystem.common.entily.QueryRequest;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.model.Menu;
import org.cwm3.mgrsystem.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.awt.SystemColor.menu;

/**

 * @作者 cwm3

 * @时间 2019-09-27 7:10
 */
@RestController
@RequestMapping("/mgrsystem/config")
public class SystemConfigController {
    @Autowired
    IMenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId() {
        return menuService.getMenusByHrId();
    }

    @GetMapping("/menuList")
    public RespBean jobList(QueryRequest request, Menu menu) {
        Page dataTable = (Page) this.menuService.findjobList(request, menu);
        return RespBean.ok(dataTable);
    }

    @Log(value="添加菜单",table="menu",type = FuncLogEnum.SYS_FUNCTION)
    @RequestMapping("/addMenu")
    @ResponseBody
    public RespBean addMenu (Menu menu){
        if(menuService.insertMenu(menu)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @Log(value="更新菜单",table="menu",type = FuncLogEnum.SYS_FUNCTION)
    @RequestMapping("/updateMenu")
    @ResponseBody
    public RespBean updateMenu (Menu menu){
        if (menu.getId() != null) {
            if (menuService.insertMenu(menu) == 1) {
                return RespBean.ok("更新成功");
            }
            return RespBean.error("更新失败");
        }else{
            return RespBean.error("id必须传");
        }
    }
    @GetMapping("/getMenuByParentId")
    public RespBean getMenuByParentId(@RequestParam Integer id) {
        Menu menu = this.menuService.getById(id);
        return RespBean.ok(menu);
    }
    @Log(value="删除菜单",table="menu",type = FuncLogEnum.SYS_FUNCTION)
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam Integer id) {
        boolean b = this.menuService.removeById(id);
        this.menuService.deleteByParentId(id);
        if (b == true) {
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }

}
