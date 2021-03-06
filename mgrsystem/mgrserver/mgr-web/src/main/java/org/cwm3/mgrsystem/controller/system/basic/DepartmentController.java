package org.cwm3.mgrsystem.controller.system.basic;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cwm3.mgrsystem.common.annotation.FuncLogEnum;
import org.cwm3.mgrsystem.common.annotation.Log;
import org.cwm3.mgrsystem.common.entity.AjaxResult;
import org.cwm3.mgrsystem.common.system.BaseController;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.service.IDepartmentService;

import org.cwm3.mgrsystem.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

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
    @Log(value="添加部门信息",table="department",type = FuncLogEnum.OPERATE)
    public RespBean addDep(@RequestBody Department dep) {
        departmentService.addDep(dep);
        if (dep.getResult() == 1) {
            return RespBean.ok("添加成功", dep);
        }
        return RespBean.error("添加失败");
    }

    @Log(value="更新部门信息",table="department",type = FuncLogEnum.OPERATE)
    @PostMapping("/upadate")
    public RespBean upadateDep(@RequestBody Department dep) {
        if (dep.getId() != null) {
            departmentService.upadateDep(dep);
            if (dep.getResult() == 1) {
                return RespBean.ok("更新成功", dep);
            }
            return RespBean.error("更新失败");
        } else {
            return RespBean.error("更新id必须传");
        }

    }

    @Log(value="删除部门信息",table="department",type = FuncLogEnum.OPERATE)
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
        if (id == null) {
            ajaxResult.setSuccess(false);
        } else {
            ajaxResult.setData(this.departmentService.queryById(id));
        }
        return ajaxResult;

    }

    @GetMapping("/pageList")
    @ResponseBody
    public AjaxResult pageList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam String name) {
        Department department = new Department();
        AjaxResult ajaxResult = new AjaxResult(true);
        Page<Department> iPage = departmentService.selectPageExt(department, pageNum, pageSize, name);
        ajaxResult.setData(iPage);
        return ajaxResult;
    }

    @GetMapping(value = "/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, Integer[] ids) throws Exception {
        AjaxResult ajaxResult = new AjaxResult(true);
        String[] headers = {"编号", "名称", "父id", "路径", "是否可能", "是否主节点"};
        String fileName = "部门表";
        List<Department> departmentList = new ArrayList<>();
        if (ids.length > 0) {
            List<Integer> list = Arrays.asList(ids);
            for (Integer id : list) {
                Department department = departmentService.queryById(id);
                departmentList.add(department);
            }
        } else {
            departmentList = getAllDepartments();
        }
        Map<String, Object> studentMap = new HashMap();
        studentMap.put("headers", headers);
        studentMap.put("dataList", departmentList);
        studentMap.put("fileName", fileName);
        List<Map> mapList = new ArrayList();
        mapList.add(studentMap);
        try {
            ExcelUtil.exportMultisheetExcel(fileName, mapList, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping (value = "/importExcel")
    @ResponseBody
    public AjaxResult importExcel(MultipartFile file)throws  Exception {
        AjaxResult ajaxResult = new AjaxResult(true);
        InputStream inputStream = file.getInputStream();
        Integer sheetIndex = 0;
        try {
            List<Map<String, String>> maps = ExcelUtil.readExcel( file, sheetIndex);
            maps.forEach(map->{
                Department department = JSON.parseObject(JSON.toJSONString(map), Department.class);
                boolean b = departmentService.saveOrUpdate(department);
                if (b == false){
                    throw  new  RuntimeException(department.getName()+"导入失败");
                }
            });
            return ajaxResult;
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
    }

}
