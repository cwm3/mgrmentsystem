package org.cwm3.mgrsystem.controller.system.basic;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.common.PageData;

import org.cwm3.mgrsystem.common.system.BaseController;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.model.RespBean;
import org.cwm3.mgrsystem.service.IDepartmentService;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-21 8:02
 */
@RestController
@RequestMapping("/mgrsystem/basic/department")
public class DepartmentController extends BaseController {
    @Autowired
    IDepartmentService departmentService;
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.ok("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }
    @PostMapping("/upadate")
    public RespBean upadateDep(@RequestBody Department dep) {
        if(dep.getId()!=null){
            departmentService.upadateDep(dep);
            if (dep.getResult() == 1) {
                return RespBean.ok("更新成功", dep);
            }
            return RespBean.error("更新失败");
        }else {
            return RespBean.error("更新id必须传");
        }

    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult() == -2) {
            return RespBean.error("该部门下有子部门，删除失败");
        } else if (dep.getResult() == -1) {
            return RespBean.error("该部门下有员工，删除失败");
        } else if (dep.getResult() == 1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public AjaxResult selectOne(Integer id) {
        AjaxResult ajaxResult = new AjaxResult(true);
        if(id == null){
            ajaxResult.setSuccess(false);
        }else {
            ajaxResult.setData(this.departmentService.queryById(id));
        }
        return ajaxResult;

    }
    @GetMapping("/pageList")
    @ResponseBody
    public AjaxResult pageList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize ){
        Department department = new Department();
        AjaxResult ajaxResult = new AjaxResult(true);
        Page<Department> iPage = departmentService.selectPageExt(department, pageNum, pageSize);
//        PageHelper.startPage(pageNum,pageSize);
//        List<Department> departmentList = departmentService.selectAll();
//        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        ajaxResult.setData(iPage);
        return ajaxResult;
    }

}
