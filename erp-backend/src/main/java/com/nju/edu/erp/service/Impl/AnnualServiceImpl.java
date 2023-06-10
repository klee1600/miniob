package com.nju.edu.erp.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.AnnualDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AnnualPO;
import com.nju.edu.erp.model.vo.salary.AnnualVO;
import com.nju.edu.erp.service.AnnualService;
import com.nju.edu.erp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("AnnualServiceImpl")
public class AnnualServiceImpl implements AnnualService {

    @Autowired
    private AnnualDao annualDao;

    @Autowired
    private UserService userService;


    @Override
    public void makeAnnual(AnnualVO vo) {
        if (vo.getAnnualSalary().compareTo(BigDecimal.ZERO) < 0)
            throw new MyServiceException("500","年终奖不能为负数！");
        AnnualPO po = annualDao.selectOne(new QueryWrapper<>(AnnualPO.builder().uid(vo.getUid()).year(vo.getYear()).build()));
        if (po == null)
            po = new AnnualPO();

        BeanUtils.copyProperties(vo, po);
        annualDao.insert(po);
    }

    @Override
    public BigDecimal getAnnual(Integer uid, Integer year) {
        AnnualPO po = annualDao.selectOne(new QueryWrapper<>(AnnualPO.builder().uid(uid).year(year).build()));
        if (po == null)
            return BigDecimal.ZERO;
        return po.getAnnualSalary();
    }

    @Override
    public boolean checkAnnual(Integer uid, Integer year) {
        return annualDao.selectCount(new QueryWrapper<>(AnnualPO.builder().uid(uid).year(year).build())) > 0;
    }

    @Override
    public List<AnnualPO> getUidWithAnnual(Integer year) {
        List<AnnualPO> ids = annualDao.getUidWithAnnual(year);
        return ids;
    }


}

