package com.nju.edu.erp.service;


import com.nju.edu.erp.model.po.SignupPO;

import java.util.Date;
import java.util.List;

public interface SignupService {

    void tick(Integer uid);

    boolean checkDailyTick(Integer uid);

    /**
     * 获取员工本月打卡次数
     */
    Integer MonthlyTickTimes(Integer uid);

    /**
     * 获取员工本月打卡 缺勤 次数
     */
    Integer absentTimes(Integer uid);

    List<SignupPO> findByuid(Integer uid);

}

