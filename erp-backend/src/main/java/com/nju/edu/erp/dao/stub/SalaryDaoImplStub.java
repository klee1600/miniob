package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.SalaryDao;
import com.nju.edu.erp.model.po.Salary;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class SalaryDaoImplStub implements SalaryDao {

    @Override
    public int insert(Salary entity) {
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
    public int delete(Wrapper<Salary> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(Salary entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(Salary entity, Wrapper<Salary> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public Salary selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Salary> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Salary> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Salary selectOne(Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Salary> selectList(Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Salary> selectPage(IPage<Salary> page, Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<Salary> page, Wrapper<Salary> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}

