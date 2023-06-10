package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.TaxDao;
import com.nju.edu.erp.model.po.TaxPO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class TaxDaoImplStub implements TaxDao {


    @Override
    public TaxPO findContentBySheetId(Long sheetId){
        System.out.println("Dao返回数据！");
        return null;
    }

    @Override
    public int insert(TaxPO entity) {
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
    public int delete(Wrapper<TaxPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(TaxPO entity) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public int update(TaxPO entity, Wrapper<TaxPO> updateWrapper) {
        System.out.println("DAO更新数据！");
        return 0;
    }

    @Override
    public TaxPO selectById(Serializable id) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<TaxPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<TaxPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public TaxPO selectOne(Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<TaxPO> selectList(Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<TaxPO> selectPage(IPage<TaxPO> page, Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<TaxPO> page, Wrapper<TaxPO> queryWrapper) {
        System.out.println("DAO查询数据！");
        return null;
    }
}

