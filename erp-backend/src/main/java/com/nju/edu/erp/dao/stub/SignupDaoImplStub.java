package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.SignupDao;
import com.nju.edu.erp.model.po.SignupPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class SignupDaoImplStub implements SignupDao {


    @Override
    public Integer countMonthlyTickTimes(Integer uid, int year, int month) {
        System.out.println("查询该月打卡次数!");
        return 3;
    }

    @Override
    public Integer check(Integer uid, int year, int month, int day) {
        System.out.println("检查有无重复打卡！");
        return 1;
    }

    @Override
    public int insert(SignupPO entity) {
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
    public int delete(Wrapper<SignupPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(SignupPO entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(SignupPO entity, Wrapper<SignupPO> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public SignupPO selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<SignupPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<SignupPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public SignupPO selectOne(Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<SignupPO> selectList(Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<SignupPO> selectPage(IPage<SignupPO> page, Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<SignupPO> page, Wrapper<SignupPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}

