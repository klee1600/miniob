package com.nju.edu.erp.model.entity.salary.strategy;

import com.nju.edu.erp.dao.WageDao;
import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GMSalaryStrategy implements SalaryStrategy {


    private final UserService userService;

    public GMSalaryStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public WageVO makeSheet(UserAndDateVO vo, WageDao dao, TaxDao taxDao, BigDecimal annual) {
        if (vo.getMonth() != 12)
            throw new MyServiceException("500", "总经理只有12月才能发放工资");

        WagePO po = makeRawGZDPO(userService.getAllDetail(vo.getUid()), dao, vo,annual);

        po.setBaseSalary(po.getBaseSalary().multiply(BigDecimal.valueOf(12)));
        po.setRankSalary(po.getRankSalary().multiply(BigDecimal.valueOf(12)));

        po.setBonus(BigDecimal.ZERO);
        po.setSignup(BigDecimal.ZERO);
        po.setRawSalary(po.getBaseSalary().add(po.getRankSalary()));

        TaxPO tax = makeTaxPO(po.getRawSalary());
        taxDao.insert(tax);
        po.setTaxId(tax.getId());

        //总金额
        po.setFinalSalary(po.getRawSalary().subtract(tax.getTotal()));
        dao.insert(po);

        return WageVO.PO2VO(po, tax);
    }

}
