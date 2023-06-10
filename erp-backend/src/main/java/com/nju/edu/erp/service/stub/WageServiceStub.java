package com.nju.edu.erp.service.stub;


import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.PayRollType;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.Wage.DateVO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.TaxVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.model.vo.Wage.YearlyTotalWithAnnualVO;
import com.nju.edu.erp.service.WageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("WageServiceStub")
public class WageServiceStub implements WageService {


    @Override
    public WageVO makeSheet(UserAndDateVO vo) {
        WageVO res = WageVO.builder().wageId("GZD-20220701-00000").type("月薪").rankSalary(BigDecimal.TEN)
                .baseSalary(BigDecimal.ZERO).name("aaa").bankId(10L).uid(vo.getUid()).state(AccountSheetState.WAIT).time(new Date())
                .bonus(BigDecimal.ZERO).finalSalary(BigDecimal.TEN).signup(BigDecimal.ZERO).build();

        TaxVO taxVO = TaxVO.builder().tax(BigDecimal.TEN).fund(BigDecimal.ZERO).insurance(BigDecimal.ZERO).total(BigDecimal.TEN).build();
        res.setTax(taxVO);

        return res;
    }

    @Override
    public void approval(String GZDId, AccountSheetState state) {
        System.out.println("审批！" + GZDId + state);
    }

    @Override
    public List<WageVO> findSheetByState(AccountSheetState state) {
        WagePO po = WagePO.builder().wageId("GZD-20220701-00000").type(PayRollType.ANNUAL).rankSalary(BigDecimal.TEN)
                .baseSalary(BigDecimal.ZERO).name("aaa").bankId(10L).uid(6).state(AccountSheetState.WAIT).time(new Date())
                .bonus(BigDecimal.ZERO).finalSalary(BigDecimal.TEN).signup(BigDecimal.ZERO).id(10L).build();

        TaxVO taxVO = TaxVO.builder().tax(BigDecimal.TEN).fund(BigDecimal.ZERO).insurance(BigDecimal.ZERO).total(BigDecimal.TEN).build();
        WageVO vo = new WageVO();
        BeanUtils.copyProperties(po, vo);
        vo.setTax(taxVO);
        List<WageVO> list = new ArrayList<>();
        list.add(vo);
        list.add(vo);
        return list;
    }

    @Override
    public WagePO selectByGZDId(String GZDId) {
        return WagePO.builder().wageId(GZDId).type(PayRollType.ANNUAL).rankSalary(BigDecimal.TEN)
                .baseSalary(BigDecimal.ZERO).name("aaa").bankId(10L).uid(6).state(AccountSheetState.WAIT).time(new Date())
                .bonus(BigDecimal.ZERO).finalSalary(BigDecimal.TEN).signup(BigDecimal.ZERO).taxId(1L)
                .id(10L).build();
    }

    @Override
    public List<Integer> getUserWithoutPayroll(DateVO vo) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            arr.add(i);
        }
        return arr;
    }

    @Override
    public boolean checkPayroll(UserAndDateVO vo) {
        return false;
    }

    @Override
    public YearlyTotalWithAnnualVO getYearlyTotal(Integer uid, Integer year) {
        return YearlyTotalWithAnnualVO.builder().totalSalary(BigDecimal.TEN).annualSalary(BigDecimal.TEN)
                .name("hhh").uid(uid).build();
    }

    @Override
    public BigDecimal getYearlyTotalSalary(Integer uid, Integer year) {
        return BigDecimal.TEN;
    }


    @Override
    public List<WageVO> getWageSheetByTime(String beginDateStr, String endDateStr){
        WagePO po = WagePO.builder().wageId("GZD-20220701-00000").type(PayRollType.ANNUAL).rankSalary(BigDecimal.TEN)
                .baseSalary(BigDecimal.ZERO).name("aaa").bankId(10L).uid(6).state(AccountSheetState.WAIT).time(new Date())
                .bonus(BigDecimal.ZERO).finalSalary(BigDecimal.TEN).signup(BigDecimal.ZERO).id(10L).build();

        TaxVO taxVO = TaxVO.builder().tax(BigDecimal.TEN).fund(BigDecimal.ZERO).insurance(BigDecimal.ZERO).total(BigDecimal.TEN).build();
        WageVO vo = new WageVO();
        BeanUtils.copyProperties(po, vo);
        vo.setTax(taxVO);
        List<WageVO> list = new ArrayList<>();
        list.add(vo);
        list.add(vo);
        return list;
    }
}

