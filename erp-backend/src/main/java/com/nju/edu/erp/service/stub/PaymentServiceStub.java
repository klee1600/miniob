package com.nju.edu.erp.service.stub;

import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("PaymentServiceStub")
public class PaymentServiceStub implements PaymentService {


    @Override
    public void makeSheet(PaymentVO vo) {
        System.out.println(vo);
        System.out.println("生成！");
    }

    @Override
    public void makeRedSheet(PaymentVO vo) {
        System.out.println(vo);
        System.out.println("生成！");
    }

    @Override
    public void approval(String FKDId, AccountSheetState state) {
        System.out.println("审批！" + FKDId + state);
    }

    @Override
    public List<PaymentVO> findSheetByState(AccountSheetState state) {

        List<PaymentVO> res = new ArrayList<>(2);
        PaymentContentVO contentVO = PaymentContentVO.builder().paymentId("XJSKD-20220701-00000").id(1L)
                .id(1L).remark("haihaiiha").amount(BigDecimal.TEN).build();
        List<PaymentContentVO> list = new ArrayList<>();
        list.add(contentVO);
        list.add(contentVO);

        res.add(PaymentVO.builder().paymentId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .bankId(10L).operator("llll").list(list).build());

        res.add(PaymentVO.builder().paymentId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .bankId(10L).operator("llll").list(list).build());
        return res;
    }

    @Override
    public PaymentPO selectBySKDId(String CreditId) {
        return PaymentPO.builder().paymentId(CreditId).bankId(10L).operator("hhh").amount(BigDecimal.TEN).state(AccountSheetState.WAIT).build();
    }

    @Override
    public List<PaymentVO> getPaymentSheetByTime(String beginDateStr, String endDateStr){
        List<PaymentVO> res = new ArrayList<>(2);
        PaymentContentVO contentVO = PaymentContentVO.builder().paymentId("XJSKD-20220701-00000").id(1L)
                .id(1L).remark("haihaiiha").amount(BigDecimal.TEN).build();
        List<PaymentContentVO> list = new ArrayList<>();
        list.add(contentVO);
        list.add(contentVO);

        res.add(PaymentVO.builder().paymentId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .bankId(10L).operator("llll").list(list).build());

        res.add(PaymentVO.builder().paymentId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .bankId(10L).operator("llll").list(list).build());
        return res;
    }

}
