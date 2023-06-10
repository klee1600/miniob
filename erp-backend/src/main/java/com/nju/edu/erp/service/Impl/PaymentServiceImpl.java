package com.nju.edu.erp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.PaymentContentDao;
import com.nju.edu.erp.dao.PaymentDao;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Primary
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao Paymentdao;

    @Autowired
    private PaymentContentDao Paymentcontentdao;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public void makeSheet(PaymentVO vo) {
        PaymentPO latest = Paymentdao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getPaymentId(), "XJSKD");
        PaymentPO po = PaymentPO.builder().bankId(vo.getBankId()).operator(vo.getOperator()).build();
        po.setPaymentId(id);
        BigDecimal amount = BigDecimal.ZERO;
        for (PaymentContentVO cvo : vo.getList()) {
            PaymentContentPO cpo = new PaymentContentPO();
            BeanUtils.copyProperties(cvo, cpo);
            cpo.setPaymentId(id);
            amount = amount.add(cpo.getAmount());
            Paymentcontentdao.insert(cpo);
        }
        po.setAmount(amount);
        po.setState(AccountSheetState.WAIT);//待审批
        Paymentdao.insert(po);
    }

    @Override
    @Transactional
    public void makeRedSheet(PaymentVO vo) {
        PaymentPO latest = Paymentdao.getLatest();
        PaymentPO po = PaymentPO.builder().paymentId(vo.getPaymentId()).bankId(vo.getBankId()).operator(vo.getOperator()).state(vo.getState()).build();
        BigDecimal amount = BigDecimal.ZERO;
        for (PaymentContentVO cvo : vo.getList()) {
            PaymentContentPO cpo = new PaymentContentPO();
            BeanUtils.copyProperties(cvo, cpo);
            cpo.setPaymentId(po.getPaymentId());
            amount = amount.add(cpo.getAmount());
            Paymentcontentdao.insert(cpo);
        }
        po.setAmount(amount);
        Paymentdao.insert(po);
    }

    @Override
    @Transactional
    public void approval(String FKDId, AccountSheetState state) {
        //更新状态+增加所有客户应收
        //每一个客户都要加
        PaymentPO po = selectBySKDId(FKDId);
        if (po == null)
            throw new MyServiceException("400", "收款单id出错！");
        po.setState(state);
        Paymentdao.updateById(po);
        if (AccountSheetState.SUCCESS.equals(state)) {
            List<PaymentContentPO> contents = Paymentcontentdao.selectList(new QueryWrapper<>(PaymentContentPO.builder().paymentId(FKDId).build()));
            //增加客户应收数据
            //详细逻辑让这个接口去做了
            for (PaymentContentPO content : contents) {
                customerService.customerAddReceivable(content.getCid(), content.getAmount());
            }
        }
    }

    @Override
    public List<PaymentVO> findSheetByState(AccountSheetState state) {
        List<PaymentPO> pos;
        if (state == null)
            pos = Paymentdao.selectList(null);
        else
            pos = Paymentdao.selectList(new QueryWrapper<>(PaymentPO.builder().state(state).build()));

        List<PaymentVO> res = new ArrayList<>();
        for (PaymentPO po : pos) {
            PaymentVO vo = new PaymentVO();
            BeanUtils.copyProperties(po, vo);
            List<PaymentContentPO> cPOs = Paymentcontentdao.selectList(new QueryWrapper<>(PaymentContentPO.builder().paymentId(po.getPaymentId()).build()));
            List<PaymentContentVO> cVOs = new ArrayList<>();
            for (PaymentContentPO cPO : cPOs) {
                PaymentContentVO cVO = new PaymentContentVO();
                BeanUtils.copyProperties(cPO, cVO);
                cVOs.add(cVO);
            }
            vo.setList(cVOs);
            res.add(vo);
        }
        return res;
    }

    @Override
    public PaymentPO selectBySKDId(String FKDId) {
        return Paymentdao.selectOne(new QueryWrapper<>(PaymentPO.builder().paymentId(FKDId).build()));
    }


    @Override
    @Transactional
    public List<PaymentVO> getPaymentSheetByTime(String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<PaymentVO> res= new ArrayList<>();
        List<PaymentPO> all;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                all = Paymentdao.findAllSheetByTime(beginTime, endTime);
                for (PaymentPO po : all) {
                    PaymentVO vo = new PaymentVO();
                    BeanUtils.copyProperties(po, vo);
                    List<PaymentContentPO> allContent = Paymentdao.findContentBySheetId(po.getPaymentId());
                    List<PaymentContentVO> vos = new ArrayList<>();
                    for (PaymentContentPO p : allContent) {
                        PaymentContentVO v = new PaymentContentVO();
                        BeanUtils.copyProperties(p, v);
                        vos.add(v);
                    }
                    vo.setList(vos);
                    res.add(vo);
                }
                return res;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
