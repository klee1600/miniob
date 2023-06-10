package com.nju.edu.erp.service.Impl;


import com.nju.edu.erp.dao.AmountDiscountDao;
import com.nju.edu.erp.dao.AmountOffsetDao;
import com.nju.edu.erp.dao.LevelDiscountDao;
import com.nju.edu.erp.dao.LevelOffsetDao;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.Budget.AmountDiscountPO;
import com.nju.edu.erp.model.po.Budget.AmountOffsetPO;
import com.nju.edu.erp.model.po.Budget.LevelDiscountPO;
import com.nju.edu.erp.model.po.Budget.LevelOffsetPO;
import com.nju.edu.erp.model.vo.Budget.AmountDiscountVO;
import com.nju.edu.erp.model.vo.Budget.AmountOffsetVO;
import com.nju.edu.erp.model.vo.Budget.LevelDiscountVO;
import com.nju.edu.erp.model.vo.Budget.LevelOffsetVO;
import com.nju.edu.erp.model.vo.account.AccountVO;
import com.nju.edu.erp.service.BudgetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BudgetServiceImpl")
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private LevelDiscountDao leveldiscountDao;

    @Autowired
    private AmountDiscountDao amountdiscountDao;

    @Autowired
    private AmountOffsetDao amountoffsetDao;

    @Autowired
    private LevelOffsetDao leveloffsetDao;

    @Override
    public void amountdiscount(AmountDiscountVO vo) {
        AmountDiscountPO po = AmountDiscountPO.builder().amount(vo.getAmount()).discount(vo.getDiscount()).build();
        amountdiscountDao.insert(po);
    }

    @Override
    public void amountoffset(AmountOffsetVO vo) {
        AmountOffsetPO po = AmountOffsetPO.builder().amount(vo.getAmount()).offset(vo.getOffset()).build();
        amountoffsetDao.insert(po);
    }

    @Override
    public void leveldiscount(LevelDiscountVO vo) {
        LevelDiscountPO po = LevelDiscountPO.builder().level(vo.getLevel()).discount(vo.getDiscount()).build();
        leveldiscountDao.insert(po);
    }

    @Override
    public void leveloffset(LevelOffsetVO vo) {
        LevelOffsetPO po = LevelOffsetPO.builder().level(vo.getLevel()).offset(vo.getOffset()).build();
        leveloffsetDao.insert(po);
    }

    @Override
    public List<AmountDiscountPO> getamountdiscount() {
        List<AmountDiscountPO> res = amountdiscountDao.selectList(null);
        return res;
    }

    @Override
    public List<AmountOffsetPO> getamountoffset() {
        List<AmountOffsetPO> res = amountoffsetDao.selectList(null);
        return res;
    }

    @Override
    public List<LevelDiscountPO> getleveldiscount() {
        List<LevelDiscountPO> res = leveldiscountDao.selectList(null);
        return res;
    }

    @Override
    public List<LevelOffsetPO> getleveloffset() {
        List<LevelOffsetPO> res = leveloffsetDao.selectList(null);
        return res;
    }
}

