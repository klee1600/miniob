package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.CreditDao;
import com.nju.edu.erp.model.po.CreditContentPO;
import com.nju.edu.erp.model.po.CreditPO;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CreditDaoImplStub implements CreditDao {


    @Override
    public List<CreditPO> findAllSheetByTime(Date beginTime, Date endTime){
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<CreditContentPO> findContentBySheetId(String sheetId){
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public CreditPO getLatest() {
        System.out.println("获得最新的付款单！");
        return null;
    }

    @Override
    public int insert(CreditPO entity) {
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
    public int delete(Wrapper<CreditPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(CreditPO entity) {
        System.out.println("DAO新增数据！");
        return 0;
    }

    @Override
    public int update(CreditPO entity, Wrapper<CreditPO> updateWrapper) {
        System.out.println("DAO新增数据！");
        return 0;
    }

    @Override
    public CreditPO selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public CreditPO selectOne(Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<CreditPO> selectList(Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<CreditPO> selectPage(IPage<CreditPO> page, Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<CreditPO> page, Wrapper<CreditPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}
