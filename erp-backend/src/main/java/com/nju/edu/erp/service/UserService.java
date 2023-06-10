package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserFullVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    /**
     * 用户登录
     * @param userVO
     * @return
     */
    Map<String, String> login(UserVO userVO);

    /**
     * 用户注册
     * @param userVO
     */
    void register(UserVO userVO);

    /**
     * 用户认证
     * @param token
     */
    UserVO auth(String token);

    /**
     * 用户入职 新增一个账号
     */
    UserFullVO enrollment(EnrollmentVO userVO);

    UserFullVO getAllDetail(Integer id);

    UserFullVO update(EnrollmentVO userVO);

    void del(Integer id);

    List<EnrollmentVO> getAll();

    Integer getIdByName(String name);

    User getUserById(Integer id);

    List<Integer> getAllUserNotIn(List<Integer> ids);
}
