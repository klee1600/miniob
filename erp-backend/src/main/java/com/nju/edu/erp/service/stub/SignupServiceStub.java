package com.nju.edu.erp.service.stub;


import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.SignupPO;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.service.SignupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("SignupServiceStub")
public class SignupServiceStub implements SignupService {


    @Override
    public void tick(Integer uid) {
        System.out.println("打卡！");
    }


    /**
     * 判断今天是否打卡
     */
    @Override
    public boolean checkDailyTick(Integer uid) {
       return true;
    }

    @Override
    public List<SignupPO> findByuid(Integer uid){
        List<SignupPO> res=new ArrayList<>();
        SignupPO po=SignupPO.builder()
                .uid(uid)
                .id(1L)
                .time(new Date())
                .build();
        res.add(po);
        return res;
    }

    /**
     * 查看该员工每月的打卡次数
     */
    @Override
    public Integer MonthlyTickTimes(Integer uid) {
        return 10;
    }

    @Override
    public Integer absentTimes(Integer uid) {
        return 20;
    }
}

