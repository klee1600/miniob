package com.nju.edu.erp.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.dao.SignupDao;

import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.SignupPO;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.vo.Wage.TaxVO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.service.SignupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Primary
public class SignupServiceImpl implements SignupService {

    @Autowired
    private SignupDao signupDao;

    @Override
    public void tick(Integer uid) {
        if (checkDailyTick(uid))
            throw new MyServiceException("500", "今天已打卡！");

        SignupPO po = SignupPO.builder().time(new Date()).uid(uid).build();
        signupDao.insert(po);
    }


    /**
     * 判断今天是否打卡
     */
    @Override
    public boolean checkDailyTick(Integer uid) {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int year = now.get(Calendar.YEAR);
        int month = (now.get(Calendar.MONTH) + 1);
        int day = now.get(Calendar.DATE);

        return signupDao.check(uid, year, month ,day) > 0;
    }

    /**
     * 查看该员工每月的打卡次数
     */
    @Override
    public Integer MonthlyTickTimes(Integer uid) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = (now.get(Calendar.MONTH) + 1);

        return signupDao.countMonthlyTickTimes(uid, year, month);
    }

    @Override
    public Integer absentTimes(Integer uid) {
        Calendar now = Calendar.getInstance();
        int total = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        return total - MonthlyTickTimes(uid);
    }

    @Override
    public List<SignupPO> findByuid(Integer uid){
        List<SignupPO> pos;
        if (uid == null)
            pos = signupDao.selectList(null);
        else
            pos = signupDao.selectList(new QueryWrapper<>(SignupPO.builder().uid(uid).build()));
        return pos;
    }

}

