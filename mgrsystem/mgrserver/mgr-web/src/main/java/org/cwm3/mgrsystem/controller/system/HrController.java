package org.cwm3.mgrsystem.controller.system;

import org.cwm3.mgrsystem.model.Hr;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.model.Role;
import org.cwm3.mgrsystem.service.IHrService;
import org.cwm3.mgrsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

///**
//
// * @作者 cwm3
//
// * @时间 2019-10-24 8:09
// */
//@RestController
//@RequestMapping("/mgrsystem/hr")
//public class HrController {
//    @Autowired
//    IHrService hrService;
//    @Autowired
//    IRoleService roleService;
//    @GetMapping("/")
//    public List<Hr> getAllHrs(String keywords) {
//        return hrService.getAllHrs(keywords);
//    }
//
//    @PutMapping("/")
//    public RespBean updateHr(@RequestBody Hr hr) {
//        if (hrService.updateHr(hr) == 1) {
//            return RespBean.ok("更新成功!");
//        }
//        return RespBean.error("更新失败!");
//    }
//    @GetMapping("/roles")
//    public List<Role> getAllRoles() {
//        return roleService.getAllRoles();
//    }
//
//    @PutMapping("/role")
//    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
//        if (hrService.updateHrRole(hrid, rids)) {
//            return RespBean.ok("更新成功!");
//        }
//        return RespBean.error("更新失败!");
//    }
//
//    @DeleteMapping("/{id}")
//    public RespBean deleteHrById(@PathVariable Integer id) {
//        if (hrService.deleteHrById(id) == 1) {
//            return RespBean.ok("删除成功!");
//        }
//        return RespBean.error("删除失败!");
//    }
//}
