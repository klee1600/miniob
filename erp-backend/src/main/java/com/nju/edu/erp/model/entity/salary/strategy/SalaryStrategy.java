package com.nju.edu.erp.model.entity.salary.strategy;

import com.nju.edu.erp.dao.WageDao;
import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.PayRollType;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import com.nju.edu.erp.utils.IdGenerator;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * 工资计算策略
 */
public interface SalaryStrategy {

    public abstract WageVO makeSheet(UserAndDateVO vo, WageDao dao, TaxDao taxDao, BigDecimal annual);

    default WagePO makeRawGZDPO(UserFullVO detail, WageDao dao, UserAndDateVO vo, BigDecimal annual) {
        WagePO latest = dao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getWageId(), "GZD");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, vo.getYear());
        c.set(Calendar.MONTH, vo.getMonth() - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.getTime();

        return WagePO.builder().time(c.getTime()).state(AccountSheetState.WAIT).wageId(id)
                .baseSalary(detail.getSalary().getBaseSalary()).rankSalary(detail.getSalary().getRankSalary())
                .annualSalary(vo.getMonth() == 12 ? annual : BigDecimal.ZERO)//是12月就发年终奖
                .type(PayRollType.parse(detail.getSalary().getType()))
                .bankId(detail.getBankId()).uid(detail.getId()).name(detail.getName()).build();
    }

    default TaxPO makeTaxPO(BigDecimal rawSalary) {
        BigDecimal fund = calFund(rawSalary);
        BigDecimal insurance = calInsurance(rawSalary);
        BigDecimal tax = calTax(rawSalary.subtract(fund).subtract(insurance));
        BigDecimal total = tax.add(fund).add(insurance);

        return TaxPO.builder().tax(tax).fund(fund).insurance(insurance).total(total).build();
    }

    /**
     * （工资-扣除的）*使用税率-速算扣除数
     */
    default BigDecimal calTax(BigDecimal rawSalary) {
        BigDecimal[] preSalary = {
                BigDecimal.ZERO,
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(12000),
                BigDecimal.valueOf(25000),
                BigDecimal.valueOf(35000),
                BigDecimal.valueOf(55000),
                BigDecimal.valueOf(80000)
        };

        BigDecimal[] postSalary = {
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(12000),
                BigDecimal.valueOf(25000),
                BigDecimal.valueOf(35000),
                BigDecimal.valueOf(55000),
                BigDecimal.valueOf(80000),
                BigDecimal.valueOf(9999999999999L)
        };

        BigDecimal[] rate = {
                BigDecimal.valueOf(0.03),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(0.2),
                BigDecimal.valueOf(0.25),
                BigDecimal.valueOf(0.3),
                BigDecimal.valueOf(0.35),
                BigDecimal.valueOf(0.45)
        };

        BigDecimal[] rapid = {
                BigDecimal.ZERO,
                BigDecimal.valueOf(2520),
                BigDecimal.valueOf(16920),
                BigDecimal.valueOf(31920),
                BigDecimal.valueOf(52920),
                BigDecimal.valueOf(85920),
                BigDecimal.valueOf(181920)
        };

        for (int i = 0; i < preSalary.length; i++) {
            if (rawSalary.compareTo(preSalary[i]) > 0 && rawSalary.compareTo(postSalary[i]) <= 0)
                return rawSalary.multiply(rate[i]).subtract(rapid[i]);
        }
        return BigDecimal.ZERO;
    }

    default BigDecimal calInsurance(BigDecimal rawSalary) {
        BigDecimal[] preSalary = {
                BigDecimal.ZERO,
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(15000),
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(50000)
        };

        BigDecimal[] postSalary = {
                BigDecimal.valueOf(2000),
                BigDecimal.valueOf(5000),
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(15000),
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(50000),
                BigDecimal.valueOf(9999999999999L)
        };

        BigDecimal[] rate = {
                BigDecimal.valueOf(0.05),
                BigDecimal.valueOf(0.08),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(0.15),
                BigDecimal.valueOf(0.18),
                BigDecimal.valueOf(0.20),
                BigDecimal.valueOf(0.25)
        };
        for (int i = 0; i < preSalary.length; i++) {
            if (rawSalary.compareTo(preSalary[i]) > 0 && rawSalary.compareTo(postSalary[i]) <= 0)
                return rawSalary.multiply(rate[i]);
        }
        return BigDecimal.ZERO;
    }

    default BigDecimal calFund(BigDecimal rawSalary) {
        BigDecimal[] preSalary = {
                BigDecimal.ZERO,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(4000),
                BigDecimal.valueOf(8000),
                BigDecimal.valueOf(16000),
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(50000)
        };

        BigDecimal[] postSalary = {
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(4000),
                BigDecimal.valueOf(8000),
                BigDecimal.valueOf(16000),
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(50000),
                BigDecimal.valueOf(9999999999999L)
        };

        BigDecimal[] rate = {
                BigDecimal.valueOf(0.05),
                BigDecimal.valueOf(0.08),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(0.15),
                BigDecimal.valueOf(0.18),
                BigDecimal.valueOf(0.20),
                BigDecimal.valueOf(0.25)
        };
        for (int i = 0; i < preSalary.length; i++) {
            if (rawSalary.compareTo(preSalary[i]) > 0 && rawSalary.compareTo(postSalary[i]) <= 0)
                return rawSalary.multiply(rate[i]);
        }
        return BigDecimal.ZERO;
    }
}
