package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.WageDao;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.po.WagePO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class WageDaoImplStub implements WageDao {

    @Override
    public List<WagePO> findAllSheetByTime(Date beginTime, Date endTime){
        System.out.println("DAO返回数据！");
        return null;
    }


    @Override
    public WagePO getLatest() {
        System.out.println("获取最新的付款单！");
        return null;
    }

    @Override
    public List<Integer> getUidHaveGZD(Integer year, Integer month) {
        System.out.println("获取该月份已经生成工资单的用户！");
        return null;
    }

    @Override
    public Integer checkPayroll(Integer uid, Integer year, Integer month) {
        return 0;
    }

    @Override
    public BigDecimal getYearlyTotalSalary(Integer uid, Integer year) {
        System.out.println("DAO查询工资和！");
        return BigDecimal.TEN;
    }

    @Override
    public int insert(WagePO entity) {
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
    public int delete(Wrapper<WagePO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(WagePO entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(WagePO entity, Wrapper<WagePO> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public WagePO selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<WagePO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<WagePO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public WagePO selectOne(Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<WagePO> selectList(Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<WagePO> selectPage(IPage<WagePO> page, Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<WagePO> page, Wrapper<WagePO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}

