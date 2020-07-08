package org.cwm3.mgrsystem.service;

//import org.cwm3.mgrsystem.common.entity.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Department;

import java.util.List;

/**
 * @Classname DepartmentService
 * @Author chengweiming
 * @Date 2020/5/25 11:10
 */
public interface IDepartmentService extends IService<Department> {

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


//    Page<Department> selectPageExt(Department department, Integer pageNum, Integer pageSize);

    Page<Department> selectPageExt(Department department, Integer pageNum, Integer pageSize, String name);

    Integer upadateDep(Department dep);

    List<Department> findAllDepartments();
}
