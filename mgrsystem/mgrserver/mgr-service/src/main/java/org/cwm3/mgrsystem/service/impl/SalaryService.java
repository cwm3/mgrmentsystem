package org.cwm3.mgrsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cwm3.mgrsystem.mapper.SalaryMapper;
import org.cwm3.mgrsystem.model.Salary;
import org.cwm3.mgrsystem.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalaryService  extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    @Override
    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    @Override
    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    @Override
    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
