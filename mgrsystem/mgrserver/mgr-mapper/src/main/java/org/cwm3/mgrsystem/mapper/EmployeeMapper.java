package org.cwm3.mgrsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.cwm3.mgrsystem.model.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee> {
    int deleteByPrimaryKey(Integer id);

//    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("emp") Employee employee,@Param("beginDateScope") Date[] beginDateScope);

    Integer maxWorkID();

    Integer addEmps(@Param("list") List<Employee> list);

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);
}
