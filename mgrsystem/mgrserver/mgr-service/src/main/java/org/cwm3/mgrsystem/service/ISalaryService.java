package org.cwm3.mgrsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cwm3.mgrsystem.model.Salary;

import java.util.List;

/**
 * @Classname ISalaryService
 * @Author chengweiming
 * @Date 2020/5/25 11:15
 */
public interface ISalaryService extends IService<Salary> {

    List<Salary> getAllSalaries();

    Integer addSalary(Salary salary);

    Integer deleteSalaryById(Integer id);

    Integer updateSalaryById(Salary salary);

}
