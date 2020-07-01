package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.cwm3.mgrsystem.common.PageData;
//import org.cwm3.mgrsystem.common.entity.PageData;
import org.cwm3.mgrsystem.model.Department;

import java.util.List;

/**
 * @Classname DepartmentService
 * @Author chengweiming
 * @Date 2020/5/25 11:10
 */
public interface IDepartmentService {

    List<Department> getAllDepartments();

    void addDep(Department dep);

    void deleteDepById(Department dep);

    List<Department> getAllDepartmentsWithOutChildren();

    Department queryById(Integer id);
    /**
     * @description: 分页查询部门
     * @Param:
     * @return:
     * @author: chengweiming
     * @time: 2020/6/29 14:57
     */

//    List<Department> selectAll();


    IPage<Department> selectPageExt(Department department, Integer pageNum, Integer pageSize);
}
