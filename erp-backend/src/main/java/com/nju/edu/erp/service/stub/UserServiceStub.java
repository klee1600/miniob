package com.nju.edu.erp.service.stub;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.salary.SalaryVO;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("UserServiceStub")
public class UserServiceStub implements UserService {


    @Override
    public Map<String, String> login(UserVO userVO) {
        return null;
    }

    @Override
    public void register(UserVO userVO) {
    }

    @Override
    public UserVO auth(String token) {
        return null;
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
        UserFullVO vo = new UserFullVO();
        BeanUtils.copyProperties(userVO, vo);
        SalaryVO salaryVO = SalaryVO.builder().type("月薪").baseSalary(BigDecimal.TEN).rankSalary(BigDecimal.TEN).build();
        vo.setSalary(salaryVO);
        return vo;
    }


    @Override
    public UserFullVO getAllDetail(Integer id) {
        UserFullVO vo = UserFullVO.builder().id(id).role(Role.GM).name("lll").bankId(10L).phone("1888888").gender("male").level(5).birthday(new Date()).build();
        SalaryVO salaryVO = SalaryVO.builder().type("月薪").baseSalary(BigDecimal.TEN).rankSalary(BigDecimal.TEN).build();
        vo.setSalary(salaryVO);
        return vo;
    }

    @Override
    public UserFullVO update(EnrollmentVO userVO) {
        UserFullVO vo = new UserFullVO();
        BeanUtils.copyProperties(userVO, vo);
        SalaryVO salaryVO = SalaryVO.builder().type("月薪").baseSalary(BigDecimal.TEN).rankSalary(BigDecimal.TEN).build();
        vo.setSalary(salaryVO);
        return vo;
    }

    @Override
    public void del(Integer id) {
    }

    @Override
    public List<EnrollmentVO> getAll() {
        List<EnrollmentVO> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EnrollmentVO vo = EnrollmentVO.builder()
                    .birthday(new Date())
                    .id(i)
                    .gender("男")
                    .name("aaa")
                    .level(i)
                    .role(Role.HR)
                    .phone("18888888888")
                    .build();
            res.add(vo);
        }
        return res;
    }

    @Override
    public Integer getIdByName(String name) {
        return 1;
    }

    @Override
    public User getUserById(Integer id) {
        return User.builder().id(id).role(Role.GM).name("lll").bankId(10L).phone("1888888").gender("male").level(5).birthday(new Date()).build();
    }

    @Override
    public List<Integer> getAllUserNotIn(List<Integer> ids) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            arr.add(i);
        return arr;
    }

}
