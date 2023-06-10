package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.PaymentContentDao;
import com.nju.edu.erp.model.po.PaymentContentPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class PaymentContentDaoImplStub implements PaymentContentDao {
    @Override
    public int insert(PaymentContentPO entity) {
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
    public int delete(Wrapper<PaymentContentPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(PaymentContentPO entity) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public int update(PaymentContentPO entity, Wrapper<PaymentContentPO> updateWrapper) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public PaymentContentPO selectById(Serializable id) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentContentPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentContentPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public PaymentContentPO selectOne(Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentContentPO> selectList(Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<PaymentContentPO> selectPage(IPage<PaymentContentPO> page, Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<PaymentContentPO> page, Wrapper<PaymentContentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }
}
