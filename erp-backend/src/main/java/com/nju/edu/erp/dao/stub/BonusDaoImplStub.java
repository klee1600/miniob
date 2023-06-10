package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.BonusDao;
import com.nju.edu.erp.model.po.BonusPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class BonusDaoImplStub implements BonusDao {


    @Override
    public Integer getMonthlyBonus(String name, int year, int month) {
        System.out.println("DAO返回数据！");
        return 10;
    }

    @Override
    public int insert(BonusPO entity) {
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
    public int delete(Wrapper<BonusPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(BonusPO entity) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public int update(BonusPO entity, Wrapper<BonusPO> updateWrapper) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public BonusPO selectById(Serializable id) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<BonusPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<BonusPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public BonusPO selectOne(Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<BonusPO> selectList(Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<BonusPO> selectPage(IPage<BonusPO> page, Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<BonusPO> page, Wrapper<BonusPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }
}

