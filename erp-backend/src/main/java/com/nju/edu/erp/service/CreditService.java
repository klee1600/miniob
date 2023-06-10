package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.CreditPO;
import com.nju.edu.erp.model.vo.Credit.CreditVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;

import java.util.List;

public interface CreditService {

    void makeSheet(CreditVO vo);

    void approval(String SKDId, AccountSheetState state);

    List<CreditVO> findSheetByState(AccountSheetState state);

    CreditPO selectBySKDId(String SKDId);

    /**
     * 根据时间筛选销售单
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<CreditVO> getCreditSheetByTime(String beginDateStr, String endDateStr);
}
