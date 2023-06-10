package com.nju.edu.erp.service.stub;

import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.CreditPO;
import com.nju.edu.erp.model.vo.Credit.CreditContentVO;
import com.nju.edu.erp.model.vo.Credit.CreditVO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.service.CreditService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("CreditServiceStub")
public class CreditServiceStub implements CreditService {


    @Override
    @Transactional
    public void makeSheet(CreditVO vo) {
        System.out.println(vo);
        System.out.println("生成！");
    }

    @Override
    @Transactional
    public void approval(String SKDId, AccountSheetState state) {
        System.out.println(SKDId + state);
        System.out.println("审批！");
    }

    @Override
    public List<CreditVO> findSheetByState(AccountSheetState state) {
        CreditPO po = CreditPO.builder().cid(10).operator("aaa").state(state).creditId("SKD-20220701-00000").amount(BigDecimal.TEN).id(10L).build();

        List<CreditVO> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CreditVO vo = new CreditVO();
            BeanUtils.copyProperties(po, vo);
            List<CreditContentVO> cVOs = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                CreditContentVO cVO = CreditContentVO.builder().creditId("SKD-20220701-00000")
                        .amount(BigDecimal.TEN).id((long) j).bankId(10L).remark("remake").build();
                cVOs.add(cVO);
            }
            vo.setList(cVOs);
            res.add(vo);
        }
        return res;
    }

    @Override
    public CreditPO selectBySKDId(String SKDId) {
        return CreditPO.builder().cid(10).operator("aaa").state(AccountSheetState.WAIT).creditId(SKDId).amount(BigDecimal.TEN).id(10L).build();
    }

    @Override
    public List<CreditVO> getCreditSheetByTime(String beginDateStr, String endDateStr){
        List<CreditVO> res = new ArrayList<>(2);
        CreditContentVO contentVO = CreditContentVO.builder().creditId("XJSKD-20220701-00000").id(1L)
                .id(1L).remark("haihaiiha").amount(BigDecimal.TEN).build();
        List<CreditContentVO> list = new ArrayList<>();
        list.add(contentVO);
        list.add(contentVO);

        res.add(CreditVO.builder().creditId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .operator("llll").list(list).build());

        res.add(CreditVO.builder().creditId("XJSKD-20220701-00000").amount(BigDecimal.TEN.add(BigDecimal.TEN))
                .id(1L).state(AccountSheetState.WAIT)
                .operator("llll").list(list).build());
        return res;
    }
}
