package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;

import java.util.List;

public interface PaymentService {

    void makeSheet(PaymentVO vo);

    void makeRedSheet(PaymentVO vo);


    void approval(String FKDId, AccountSheetState state);

    List<PaymentVO> findSheetByState(AccountSheetState state);

    PaymentPO selectBySKDId(String FKDId);

    /**
     * 根据时间筛选销售单
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<PaymentVO> getPaymentSheetByTime(String beginDateStr, String endDateStr);
}
