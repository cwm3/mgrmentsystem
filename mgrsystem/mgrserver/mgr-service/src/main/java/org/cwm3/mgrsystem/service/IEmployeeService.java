package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Employee;
import org.cwm3.mgrsystem.common.pager.RespPageBean;

import java.util.Date;
import java.util.List;

/**
 * @Classname EmployeeService
 * @Author chengweiming
 * @Date 2020/5/25 11:11
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope);

    Integer addEmp(Employee employee);

    Integer maxWorkID();

    Integer deleteEmpByEid(Integer id);

    Integer updateEmp(Employee employee);

    Integer addEmps(List<Employee> list);

    RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size);

    Integer updateEmployeeSalaryById(Integer eid, Integer sid);

    Employee getEmployeeById(Integer empId);

}
