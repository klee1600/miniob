package com.nju.edu.erp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.BonusDao;
import com.nju.edu.erp.dao.SalaryDao;
import com.nju.edu.erp.enums.PayRollType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.BonusPO;
import com.nju.edu.erp.model.po.Salary;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.salary.SalaryFullVO;
import com.nju.edu.erp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("SalaryServiceImpl")
@Primary
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryDao salaryDao;


    @Autowired
    private BonusDao bonusDao;


    public Salary getSalary(Role r) {
        return salaryDao.selectOne(new QueryWrapper<>(Salary.builder().role(r).build()));
    }


    @Override
    public void addBonus(String name) {
        bonusDao.insert(BonusPO.builder().name(name).time(new Date()).build());
    }

    @Override
    public BigDecimal getMonthlyBonus(User user, int year, int month) {
        if (month < 1 || month > 12)
            throw new MyServiceException("500", "传入时间不合法");
        Integer times = bonusDao.getMonthlyBonus(user.getName(), year, month);
        return getSalary(user.getRole()).getBonusSalary().multiply(BigDecimal.valueOf(times));
    }

    @Override
    public List<SalaryFullVO> getAllSalary() {
        List<Salary> salaries = salaryDao.selectList(null);
        List<SalaryFullVO> res = new ArrayList<>();
        for (Salary salary : salaries) {
            res.add(SalaryFullVO.PO2VO(salary));
        }
        return res;
    }

    @Override
    public void upd(SalaryFullVO vo) {
        Salary salary = salaryDao.selectById(vo.getId());
        if (salary == null)
            throw new MyServiceException("500", "找不到该id对应的薪酬");

        if ((!(salary.getWay().equals(PayRollType.BASE))) && (!vo.getBonusSalary().equals(BigDecimal.ZERO)))//没有提成的人却有提成
            throw new MyServiceException("500", "该类员工不能设置提成！");
        if (vo.getBaseSalary().compareTo(BigDecimal.ZERO) < 0
                || vo.getRankSalary().compareTo(BigDecimal.ZERO) < 0
                || vo.getBonusSalary().compareTo(BigDecimal.ZERO) < 0)
            throw new MyServiceException("500", "工资不能为负数！");

        salary.setBaseSalary(vo.getBaseSalary());
        salary.setRankSalary(vo.getRankSalary());
        salary.setBonusSalary(vo.getBonusSalary());
        salaryDao.updateById(salary);
    }
}
