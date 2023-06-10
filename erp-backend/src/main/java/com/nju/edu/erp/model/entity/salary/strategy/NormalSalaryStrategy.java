package com.nju.edu.erp.model.entity.salary.strategy;

import com.nju.edu.erp.dao.WageDao;
import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.service.SignupService;
import com.nju.edu.erp.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NormalSalaryStrategy implements SalaryStrategy {

    private final UserService userService;

    private final SignupService signupService;


    public NormalSalaryStrategy(UserService userService, SignupService signupService) {
        this.userService = userService;
        this.signupService = signupService;
    }


    @Override
    public WageVO makeSheet(UserAndDateVO vo, WageDao dao, TaxDao taxDao, BigDecimal annual) {
        WagePO po = makeRawGZDPO(userService.getAllDetail(vo.getUid()), dao, vo, annual);

        po.setBonus(BigDecimal.ZERO);
        //打卡
        Integer times = signupService.absentTimes(vo.getUid());
        BigDecimal absent = (BigDecimal.valueOf(times).multiply(po.getBaseSalary())).divide(BigDecimal.valueOf(30),2,BigDecimal.ROUND_DOWN);
        absent = absent.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : absent;
        po.setSignup(BigDecimal.ZERO.subtract(absent));

        po.setRawSalary(po.getBaseSalary().add(po.getRankSalary()).add(po.getSignup()));

        TaxPO tax = makeTaxPO(po.getRawSalary());
        taxDao.insert(tax);
        po.setTaxId(tax.getId());


        //总金额
        po.setFinalSalary(po.getRawSalary().subtract(tax.getTotal()));
        dao.insert(po);

        return WageVO.PO2VO(po, tax);
    }
}
