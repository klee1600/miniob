package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @Test
    public void insertUser(){
        User user = new User();
        user.setRole(Role.GM);
        user.setName("hahaha");
        user.setPassword("123456");
        userDao.insert(user);
    }

    @Test
    public void updateUser(){
        EnrollmentVO en = new EnrollmentVO();
        en.setId(24);
        en.setBirthday(new Date());
        en.setGender("male");
        en.setRole(Role.GM);
        en.setLevel(100);
        UserFullVO vo = userService.update(en);
        System.out.println(vo);
    }

    @Test
    public void addUser(){
        EnrollmentVO en = new EnrollmentVO();
        en.setBirthday(new Date());
        en.setGender("male");
        en.setRole(Role.GM);
        en.setLevel(100);
        en.setPhone("88888888888");
        en.setName("lqffffff");
        UserFullVO vo = userService.enrollment(en);
        System.out.println(vo);
    }

    @Test
    public void deleteUser(){
        userService.del(26);
    }
}
