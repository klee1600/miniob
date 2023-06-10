package com.nju.edu.erp.service.stub;

import com.nju.edu.erp.enums.PayRollType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.Salary;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.salary.SalaryFullVO;
import com.nju.edu.erp.service.SalaryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("SalaryServiceStub")
public class SalaryServiceStub implements SalaryService {


    public Salary getSalary(Role r) {
        return Salary.builder().baseSalary(BigDecimal.TEN).bonusSalary(BigDecimal.ZERO).rankSalary(BigDecimal.TEN).role(Role.HR).id(1).way(PayRollType.MONTHLY).build();
    }


    @Override
    public void addBonus(String name) {
        System.out.println(name+"加钱！");
    }

    @Override
    public BigDecimal getMonthlyBonus(User user, int year, int month) {
        return BigDecimal.TEN;
    }

    @Override
    public List<SalaryFullVO> getAllSalary() {
        Salary salary = Salary.builder().baseSalary(BigDecimal.TEN).bonusSalary(BigDecimal.ZERO).rankSalary(BigDecimal.TEN).role(Role.HR).id(1).way(PayRollType.MONTHLY).build();
        List<SalaryFullVO> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            res.add(SalaryFullVO.PO2VO(salary));
        }
        return res;
    }

    @Override
    public void upd(SalaryFullVO vo) {
        System.out.println(vo);
        System.out.println("更新！");
    }
}
