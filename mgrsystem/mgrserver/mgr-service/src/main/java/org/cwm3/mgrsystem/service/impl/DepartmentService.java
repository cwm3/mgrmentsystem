package org.cwm3.mgrsystem.service.impl;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import org.cwm3.mgrsystem.mapper.DepartmentMapper;
import org.cwm3.mgrsystem.model.Department;
import org.cwm3.mgrsystem.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-21 8:04
 */
@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService  {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Override
    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    @Override
    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }

    @Override
    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }

    @Override
    public Department queryById(Integer id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

//    @Override
//    public  List<Department> selectAll() {
//        return departmentMapper.selectAll();
//    }

    @Override
    public Page<Department> selectPageExt(Department department, Integer pageNum, Integer pageSize ,String name) {
        try {
            Page<Department> p = new Page<>(pageNum, pageSize);
            if (StringUtil.isNotEmpty(name)&& name.trim().toString().length()>0){
               QueryWrapper queryWrapper = new QueryWrapper<Department>();
                queryWrapper.like("name",name);
//                p.setOptimizeCountSql(false);
//                p.setSearchCount(false);
                IPage<Department> departmentIPage = departmentMapper.selectPage(p, queryWrapper);
                p.setRecords(departmentIPage.getRecords());
            }else{
                p.setRecords(departmentMapper.selectPageExt(p,department));
            }
            return p;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Integer upadateDep(Department department) {
        return departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public List<Department> findAllDepartments() {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return departmentMapper.selectList(lambdaQueryWrapper);
    }

}
