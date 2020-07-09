package org.cwm3.mgrsystem.controller.config;

import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.model.Menu;
import org.cwm3.mgrsystem.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/addMenu")
    @ResponseBody
    public AjaxResult addMenu (Menu menu){
        AjaxResult ajaxResult = new AjaxResult(true);
        if(menuService.insertMenu(menu)==1){
            return ajaxResult;
        }
        ajaxResult.setSuccess(false);
        return ajaxResult;
    }

}