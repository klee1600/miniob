package com.nju.edu.erp.service.Impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.edu.erp.config.JwtConfig;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.Salary;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.salary.SalaryVO;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service("UserServiceImpl")
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserDao userDao;

    @Qualifier("SalaryServiceImpl")
    @Autowired
    private  SalaryService salaryService;

    @Qualifier("AccountServiceImpl")
    @Autowired
    private  AccountService accountService;

    @Autowired
    private  JwtConfig jwtConfig;


    @Override
    public Map<String, String> login(UserVO userVO) {
        User user = userDao.findByUsernameAndPassword(userVO.getName(), userVO.getPassword());
        if (null == user) {
            throw new MyServiceException("A0000", "用户名或密码错误");
        }
        Map<String, String> authToken = new HashMap<>();
        String token = jwtConfig.createJWT(user);
        authToken.put("token", token);
        return authToken;
    }

    @Override
    public void register(UserVO userVO) {
        User user = userDao.findByUsername(userVO.getName());
        if (user != null) {
            throw new MyServiceException("A0001", "用户名已存在");
        }
        User userSave = new User();
        BeanUtils.copyProperties(userVO, userSave);
        userDao.createUser(userSave);
    }

    @Override
    public UserVO auth(String token) {
        Map<String, Claim> claims = jwtConfig.parseJwt(token);
        UserVO userVO = UserVO.builder()
                .name(claims.get("name").as(String.class))
                .role(Role.valueOf(claims.get("role").as(String.class)))
                .build();
        return userVO;
    }

    /**
     * 1.先注册一个账号
     * 2.再注册一个银行账户
     * 3.两个绑定
     * <p>
     * 返回新的账户所有信息
     */
    @Override
    @Transactional
    public UserFullVO enrollment(EnrollmentVO userVO) {
        if (checkName(userVO.getName()))
            throw new MyServiceException("500", "名称已被使用!");

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setPassword("123456");
        Long bankId = accountService.makeAccount();
        user.setBankId(bankId);
        userDao.insert(user);

        return getAllDetail(user.getId());
    }


    @Override
    public UserFullVO getAllDetail(Integer id) {
        User user = userDao.selectById(id);
        Salary salary = salaryService.getSalary(user.getRole());
        SalaryVO salaryVO = SalaryVO.builder()
                .baseSalary(salary.getBaseSalary())
                .rankSalary(salary.getRankSalary().multiply(BigDecimal.valueOf(user.getLevel())))
                .type(salary.getWay().toString())
                .build();

        return UserFullVO.builder()
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .bankId(user.getBankId())
                .level(user.getLevel())
                .phone(user.getPhone())
                .name(user.getName())
                .role(user.getRole())
                .id(user.getId())
                .salary(salaryVO).build();
    }

    @Override
    public UserFullVO update(EnrollmentVO userVO) {
        User u = userDao.selectOne(new QueryWrapper<>(User.builder().name(userVO.getName()).build()));
        if (u != null && !Objects.equals(u.getId(), userVO.getId()))
            throw new MyServiceException("500", "名称已被使用!");

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        userDao.updateById(user);

        return getAllDetail(user.getId());
    }

    @Override
    @Transactional
    public void del(Integer id) {
        User user = userDao.selectById(id);
        accountService.delAccount(user.getBankId());
        userDao.deleteById(id);
    }

    @Override
    public List<EnrollmentVO> getAll() {
        List<User> users = userDao.selectList(null);
        List<EnrollmentVO> res = new ArrayList<>();
        for (User user : users) {
            EnrollmentVO vo = EnrollmentVO.builder()
                    .birthday(user.getBirthday())
                    .id(user.getId())
                    .gender(user.getGender())
                    .name(user.getName())
                    .level(user.getLevel())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .build();
            res.add(vo);
        }
        return res;
    }

    @Override
    public Integer getIdByName(String name) {
        return userDao.selectOne(new QueryWrapper<>(User.builder().name(name).build())).getId();
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public List<Integer> getAllUserNotIn(List<Integer> ids) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        if (ids.size() != 0)
            wrapper.notIn("id", ids);
        List<Integer> res = new ArrayList<>();
        for (User user : userDao.selectList(wrapper))
            res.add(user.getId());
        return res;
    }

    private boolean checkName(String name) {
        User user = new User();
        user.setName(name);
        return userDao.selectCount(new QueryWrapper<>(user)) > 0;
    }


}
