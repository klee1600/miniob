package com.nju.edu.erp.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.WageDao;

import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.entity.salary.SalaryFactory;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.Credit.CreditContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.Wage.*;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.AnnualService;
import com.nju.edu.erp.service.WageService;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("WageServiceImpl")
@Primary
public class WageServiceImpl implements WageService {

    @Autowired
    private WageDao Wagedao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @Qualifier("AccountServiceImpl")
    @Autowired
    private AccountService accountService;

    @Autowired
    private SalaryFactory factory;

    @Autowired
    private AnnualService annualService;

    @Override
    @Transactional
    public WageVO makeSheet(UserAndDateVO vo) {
        checkTime(vo.getMonth());
        if (checkPayroll(vo))
            throw new MyServiceException("500", "该用户该月份已创建工资单！");
        User user = userService.getUserById(vo.getUid());

        BigDecimal annual = vo.getMonth() == 12 ?
                annualService.getAnnual(vo.getUid(), vo.getYear()) :
                BigDecimal.ZERO;

        return factory.SalaryStrategyBuilder(user.getRole()).makeSheet(vo, Wagedao, taxDao, annual);
    }

    @Override
    @Transactional
    public void approval(String GZDId, AccountSheetState state) {
        //todo 更新状态的同时发钱
        WagePO po = selectByGZDId(GZDId);
        if (po == null)
            throw new MyServiceException("400", "收款单id出错！");
        po.setState(state);
        Wagedao.updateById(po);
        accountService.raiseAccount(po.getBankId(), po.getFinalSalary());
    }

    @Override
    public List<WageVO> findSheetByState(AccountSheetState state) {
        List<WagePO> pos;
        if (state == null)
            pos = Wagedao.selectList(null);
        else
            pos = Wagedao.selectList(new QueryWrapper<>(WagePO.builder().state(state).build()));

        List<WageVO> res = new ArrayList<>();
        for (WagePO po : pos) {
            WageVO vo = new WageVO();
            BeanUtils.copyProperties(po,vo);
            TaxPO taxPO = taxDao.selectById(po.getTaxId());
            TaxVO taxVO = new TaxVO();
            BeanUtils.copyProperties(taxPO,taxVO);
            
            vo.setTax(taxVO);
            res.add(vo);
        }
        return res;
    }

    @Override
    public WagePO selectByGZDId(String GZDId) {
        return Wagedao.selectOne(new QueryWrapper<>(WagePO.builder().wageId(GZDId).build()));
    }

    @Override
    public List<Integer> getUserWithoutPayroll(DateVO vo) {
        checkTime(vo.getMonth());

        List<Integer> uid = Wagedao.getUidHaveGZD(vo.getYear(), vo.getMonth());
        return userService.getAllUserNotIn(uid);
    }

    @Override
    public boolean checkPayroll(UserAndDateVO vo) {
        return Wagedao.checkPayroll(vo.getUid(), vo.getYear(), vo.getMonth()) > 0;
    }

    @Override
    public YearlyTotalWithAnnualVO getYearlyTotal(Integer uid, Integer year) {
        User user = userService.getUserById(uid);
        if (user.getRole().equals(Role.GM))
            throw new MyServiceException("500", "总经理是年薪制！");
        if (user.getRole().equals(Role.ADMIN))
            throw new MyServiceException("500", "该用户是超级管理员!");

        BigDecimal annual = annualService.getAnnual(uid, year);
        BigDecimal total = getYearlyTotalSalary(uid, year);
        return YearlyTotalWithAnnualVO
                .builder().uid(uid).name(user.getName())
                .annualSalary(annual).totalSalary(total).build();
    }

    @Override
    public BigDecimal getYearlyTotalSalary(Integer uid, Integer year) {
        return Wagedao.getYearlyTotalSalary(uid, year);
    }

    private void checkTime(Integer month) {
        if (month < 1 || month > 12)
            throw new MyServiceException("500", "传入月份不合法");
    }


    @Override
    @Transactional
    public List<WageVO> getWageSheetByTime(String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<WageVO> res= new ArrayList<>();
        List<WagePO> all;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                all = Wagedao.findAllSheetByTime(beginTime, endTime);
                for (WagePO po : all) {
                    WageVO vo = new WageVO();
                    BeanUtils.copyProperties(po, vo);
                    TaxPO allContent = taxDao.findContentBySheetId(po.getTaxId());
                    TaxVO vos=new TaxVO();
                    BeanUtils.copyProperties(allContent,vos);
                    vo.setTax(vos);
                    res.add(vo);
                }
                return res;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class Taxdao {
    }
}

