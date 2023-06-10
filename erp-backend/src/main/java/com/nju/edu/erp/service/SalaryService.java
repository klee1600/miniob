package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.Salary;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.salary.SalaryFullVO;

import java.math.BigDecimal;
import java.util.List;

public interface SalaryService {


    Salary getSalary(Role r);


    void addBonus(String name);

    BigDecimal getMonthlyBonus(User user, int year, int month);

    List<SalaryFullVO> getAllSalary();

    void upd(SalaryFullVO vo);

}
