package org.cwm3.mgrsystem.service;

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
}
