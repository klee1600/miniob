package com.nju.edu.erp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.CreditContentDao;
import com.nju.edu.erp.dao.CreditDao;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CreditContentPO;
import com.nju.edu.erp.model.po.CreditPO;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.vo.Credit.CreditContentVO;
import com.nju.edu.erp.model.vo.Credit.CreditVO;
import com.nju.edu.erp.model.vo.Payment.PaymentContentVO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.CreditService;
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
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditDao Creditdao;

    @Autowired
    private CreditContentDao SKDcontentdao;

    @Autowired
    private CustomerService customerService;


    @Override
    @Transactional
    public void makeSheet(CreditVO vo) {
        CreditPO latest = Creditdao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getCreditId(), "SKD");
        CreditPO po = CreditPO.builder().cid(vo.getCid()).operator(vo.getOperator()).build();
        po.setCreditId(id);
        BigDecimal amount = BigDecimal.ZERO;
        for (CreditContentVO cvo : vo.getList()) {
            CreditContentPO cpo = new CreditContentPO();
            BeanUtils.copyProperties(cvo, cpo);
            cpo.setCreditId(id);
            amount = amount.add(cpo.getAmount());
            SKDcontentdao.insert(cpo);
        }
        po.setAmount(amount);
        po.setState(AccountSheetState.WAIT);//待审批
        Creditdao.insert(po);
    }

    @Override
    @Transactional
    public void approval(String SKDId, AccountSheetState state) {
        //更新状态+增加客户应付
        CreditPO po = selectBySKDId(SKDId);
        if (po == null)
            throw new MyServiceException("400", "收款单id出错！");
        po.setState(state);
        Creditdao.updateById(po);
        if (AccountSheetState.SUCCESS.equals(state)) {
            //增加客户应付数据
            //详细逻辑让这个接口去做了
            customerService.customerAddPayable(po.getCid(), po.getAmount());
        }
    }

    @Override
    public List<CreditVO> findSheetByState(AccountSheetState state) {
        List<CreditPO> pos;
        if (state == null)
            pos = Creditdao.selectList(null);
        else
            pos = Creditdao.selectList(new QueryWrapper<>(CreditPO.builder().state(state).build()));

        List<CreditVO> res = new ArrayList<>();
        for (CreditPO po : pos) {
            CreditVO vo = new CreditVO();
            BeanUtils.copyProperties(po,vo);
            List<CreditContentPO> cPOs = SKDcontentdao.selectList(new QueryWrapper<>(CreditContentPO.builder().creditId(po.getCreditId()).build()));
            List<CreditContentVO> cVOs = new ArrayList<>();
            for (CreditContentPO cPO : cPOs) {
                CreditContentVO cVO = new CreditContentVO();
                BeanUtils.copyProperties(cPO,cVO);
                cVOs.add(cVO);
            }
            vo.setList(cVOs);
            res.add(vo);
        }
        return res;
    }

    @Override
    public CreditPO selectBySKDId(String SKDId) {
        return Creditdao.selectOne(new QueryWrapper<>(CreditPO.builder().creditId(SKDId).build()));
    }


    @Override
    @Transactional
    public List<CreditVO> getCreditSheetByTime(String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<CreditVO> res= new ArrayList<>();
        List<CreditPO> all;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                all = Creditdao.findAllSheetByTime(beginTime, endTime);
                for (CreditPO po : all) {
                    CreditVO vo = new CreditVO();
                    BeanUtils.copyProperties(po, vo);
                    List<CreditContentPO> allContent = Creditdao.findContentBySheetId(po.getCreditId());
                    List<CreditContentVO> vos = new ArrayList<>();
                    for (CreditContentPO p : allContent) {
                        CreditContentVO v = new CreditContentVO();
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
