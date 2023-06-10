package com.nju.edu.erp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao extends BaseMapper<User> {

    User findByUsernameAndPassword(String username, String password);

    int createUser(User user);

    User findByUsername(String username);
}
