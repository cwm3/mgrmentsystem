package org.cwm3.mgrsystem.controller.salary;

import org.cwm3.mgrsystem.common.entily.RespBean;
import org.cwm3.mgrsystem.common.pager.RespPageBean;
import org.cwm3.mgrsystem.model.Salary;
import org.cwm3.mgrsystem.service.IEmployeeService;
import org.cwm3.mgrsystem.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    ISalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeByPageWithSalary(page, size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid, Integer sid) {
        Integer result = employeeService.updateEmployeeSalaryById(eid, sid);
        if (result == 1 || result == 2) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
