package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.PaymentDao;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class PaymentDaoImplStub implements PaymentDao {


    @Override
    public PaymentPO getLatest() {
        System.out.println("获取最新的付款单！");
        return null;
    }

    @Override
    public int insert(PaymentPO entity) {
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
    public int delete(Wrapper<PaymentPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(PaymentPO entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(PaymentPO entity, Wrapper<PaymentPO> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public PaymentPO selectById(Serializable id) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentPO> findAllSheetByTime(Date beginTime, Date endTime){
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentContentPO> findContentBySheetId(String sheetId){
        System.out.println("DAO返回数据！");
        return null;
    }



    @Override
    public List<PaymentPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public PaymentPO selectOne(Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<PaymentPO> selectList(Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<PaymentPO> selectPage(IPage<PaymentPO> page, Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<PaymentPO> page, Wrapper<PaymentPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }
}
