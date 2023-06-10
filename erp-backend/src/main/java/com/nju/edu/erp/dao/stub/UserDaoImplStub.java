package com.nju.edu.erp.dao.stub;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.model.po.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class UserDaoImplStub implements UserDao {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        System.out.println("dao根据用户密码查询！");
        return null;
    }

    @Override
    public int createUser(User user) {
        System.out.println("dao创建新用户！");
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        System.out.println("dao根据用户名查User！");
        return null;
    }

    @Override
    public int insert(User entity) {
        System.out.println("DAO新增数据！");
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> columnMap) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int delete(Wrapper<User> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(User entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(User entity, Wrapper<User> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public User selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<User> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<User> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public User selectOne(Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<User> selectList(Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<User> selectPage(IPage<User> page, Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<User> page, Wrapper<User> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}
