package com.nju.edu.erp.model.entity.salary.strategy;

import com.nju.edu.erp.dao.WageDao;
import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.service.SignupService;
import com.nju.edu.erp.service.UserService;

import java.math.BigDecimal;

public class SalesmanSalaryStrategy implements SalaryStrategy {


    private final UserService userService;


    private final SalaryService salaryService;


    private final SignupService signupService;

    public SalesmanSalaryStrategy(UserService userService, SalaryService salaryService, SignupService signupService) {
        this.userService = userService;
        this.salaryService = salaryService;
        this.signupService = signupService;
    }

    @Override
    public WageVO makeSheet(UserAndDateVO vo, WageDao dao, TaxDao taxDao, BigDecimal annual) {
        WagePO po = makeRawGZDPO(userService.getAllDetail(vo.getUid()), dao, vo, annual);

        User user = userService.getUserById(vo.getUid());
        //提成
        BigDecimal bonus = salaryService.getMonthlyBonus(user, vo.getYear(), vo.getMonth());
        po.setBonus(bonus);
        //打卡
        Integer times = signupService.absentTimes(vo.getUid());
        BigDecimal absent = (BigDecimal.valueOf(times).multiply(po.getBaseSalary())).divide(BigDecimal.valueOf(30),2,BigDecimal.ROUND_DOWN);
        absent = absent.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : absent;
        po.setSignup(BigDecimal.ZERO.subtract(absent));
        po.setRawSalary(po.getBaseSalary().add(po.getRankSalary()).add(po.getBonus().add(po.getSignup())));

        TaxPO tax = makeTaxPO(po.getRawSalary());
        taxDao.insert(tax);
        po.setTaxId(tax.getId());

        //总金额
        po.setFinalSalary(po.getRawSalary().subtract(tax.getTotal()));
        dao.insert(po);

        return WageVO.PO2VO(po, tax);
    }
}
