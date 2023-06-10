package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SignupServiceTest {

    @Autowired
    private SignupService signupService;


    @Test
    public void doubleTick() {
        signupService.tick(24);
        Assertions.assertThrows(MyServiceException.class, () -> signupService.tick(24));
    }

    @Test
    public void tickTimes() {
        Assertions.assertEquals(1,signupService.MonthlyTickTimes(24));
    }

    @Test
    public void absentTimes() {
        Assertions.assertEquals(30,signupService.absentTimes(24));
    }

}
