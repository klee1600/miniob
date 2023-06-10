package com.nju.edu.erp.model.entity.salary;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.entity.salary.strategy.GMSalaryStrategy;
import com.nju.edu.erp.model.entity.salary.strategy.NormalSalaryStrategy;
import com.nju.edu.erp.model.entity.salary.strategy.SalaryStrategy;
import com.nju.edu.erp.model.entity.salary.strategy.SalesmanSalaryStrategy;
import com.nju.edu.erp.service.AnnualService;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.service.SignupService;
import com.nju.edu.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工厂类
 */
@Component
public class SalaryFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SignupService signupService;


    public SalaryStrategy SalaryStrategyBuilder(Role r) {
        SalaryStrategy strategy;
        switch (r) {
            case GM:
                strategy = new GMSalaryStrategy(userService);
                break;
            case SALE_MANAGER:
            case SALE_STAFF:
                strategy = new SalesmanSalaryStrategy(userService, salaryService, signupService);
                break;
            case HR:
            case INVENTORY_MANAGER:
            case FINANCIAL_STAFF:
                strategy = new NormalSalaryStrategy(userService, signupService);
                break;
            default:
                throw new MyServiceException("500", "没有该用户类型！");
        }
        return strategy;
    }
}
