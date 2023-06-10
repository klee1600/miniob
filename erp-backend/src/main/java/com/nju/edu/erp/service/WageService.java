package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.Wage.DateVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.model.vo.Wage.YearlyTotalWithAnnualVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface WageService {
    WageVO makeSheet(UserAndDateVO vo);

    void approval(String GZDId, AccountSheetState state);

    List<WageVO> findSheetByState(AccountSheetState state);

    WagePO selectByGZDId(String GZDId);

    List<Integer> getUserWithoutPayroll(DateVO vo);

    boolean checkPayroll(UserAndDateVO vo);

    YearlyTotalWithAnnualVO getYearlyTotal(Integer uid, Integer year);

    BigDecimal getYearlyTotalSalary(Integer uid, Integer year);


    /**
     * 根据时间筛选销售单
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<WageVO> getWageSheetByTime(String beginDateStr, String endDateStr);
}

