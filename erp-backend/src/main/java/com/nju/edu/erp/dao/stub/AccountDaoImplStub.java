package com.nju.edu.erp.dao.stub;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.model.po.AccountPO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class AccountDaoImplStub implements AccountDao {
    @Override
    public int insert(AccountPO entity) {
        System.out.println("DAO新增数据！");
        return 1;
    }

    @Override
    public void initialcreate() {
        System.out.println("期初建账！");
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
    public int delete(Wrapper<AccountPO> wrapper) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO删除数据！");
        return 0;
    }

    @Override
    public int updateById(AccountPO entity) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public int update(AccountPO entity, Wrapper<AccountPO> updateWrapper) {
        System.out.println("DAO修改数据！");
        return 0;
    }

    @Override
    public AccountPO selectById(Serializable id) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<AccountPO> selectBatchIds(Collection<? extends Serializable> idList) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<AccountPO> selectByMap(Map<String, Object> columnMap) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public AccountPO selectOne(Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public Integer selectCount(Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<AccountPO> selectList(Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public List<Object> selectObjs(Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<AccountPO> selectPage(IPage<AccountPO> page, Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }

    @Override
    public IPage<Map<String, Object>> selectMapsPage(IPage<AccountPO> page, Wrapper<AccountPO> queryWrapper) {
        System.out.println("DAO返回数据！");
        return null;
    }
}
