package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.CreditContentDao;
import com.nju.edu.erp.model.po.CreditContentPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CreditContentDaoImplStub implements CreditContentDao {
    @Override
    public int insert(CreditContentPO entity) {
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
    public int delete(Wrapper<CreditContentPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(CreditContentPO entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(CreditContentPO entity, Wrapper<CreditContentPO> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public CreditContentPO selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditContentPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditContentPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public CreditContentPO selectOne(Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditContentPO> selectList(Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<CreditContentPO> selectPage(IPage<CreditContentPO> page, Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<CreditContentPO> page, Wrapper<CreditContentPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}
