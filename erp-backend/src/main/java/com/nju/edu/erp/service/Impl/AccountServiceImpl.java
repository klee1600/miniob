package com.nju.edu.erp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.Initial_AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.Initial_AccountPO;
import com.nju.edu.erp.model.vo.account.AccountVO;
import com.nju.edu.erp.model.vo.account.AddAccountVO;
import com.nju.edu.erp.model.vo.account.UpdateAccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("AccountServiceImpl")
@Primary
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Initial_AccountDao initial_accountDao;

    @Override
    public void addAccount(AddAccountVO vo) {
        if (checkName(vo.getName()))
            throw new MyServiceException("500", "名称重复！");
        AccountPO po = AccountPO.builder().amount(vo.getAmount()).name(vo.getName()).build();
        accountDao.insert(po);
    }

    @Override
    public void addInitial_Account(AddAccountVO vo) {
        Initial_AccountPO po = Initial_AccountPO.builder().amount(vo.getAmount()).name(vo.getName()).build();
        initial_accountDao.insert(po);
    }

    @Override
    public void initialcreate() {
        initial_accountDao.initialcreate();
    }

    @Override
    public void delAccount(Long id) {
        int i = accountDao.deleteById(id);
        if (i == 0)
            throw new MyServiceException("500", "删除失败!");
    }

    @Override
    public void updAccount(UpdateAccountVO vo) {
        if (accountDao.selectById(vo.getId()) == null)
            throw new MyServiceException("500", "没有此id的账户！");

        if (checkName(vo.getName()))
            throw new MyServiceException("500", "名称重复！");

        LambdaUpdateWrapper<AccountPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(AccountPO::getId, vo.getId())
                .set(AccountPO::getName, vo.getName());
        accountDao.update(null, lambdaUpdateWrapper);
    }

    @Override
    public List<AccountVO> getAllAccount() {
        List<AccountPO> accountPOS = accountDao.selectList(null);
        List<AccountVO> res = new ArrayList<>(accountPOS.size());
        for (AccountPO po : accountPOS) {
            AccountVO vo = new AccountVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }


    @Override
    public List<AccountVO> getAllInitialAccount() {
        List<Initial_AccountPO> accountPOS = initial_accountDao.selectList(null);
        List<AccountVO> res = new ArrayList<>(accountPOS.size());
        for (Initial_AccountPO po : accountPOS) {
            AccountVO vo = new AccountVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    /**
     * 给制定账户减少余额
     */
    @Override
    public void reduceAccount(Long bankId, BigDecimal amount) {
        AccountPO po = accountDao.selectById(bankId);
        if (po == null)
            throw new MyServiceException("500", "不存在该账户id！");
        BigDecimal todo = po.getAmount().subtract(amount);
        if (todo.compareTo(BigDecimal.ZERO) < 0)//余额不足
            throw new MyServiceException("500", "操作失败！该账户余额不足！");

        po.setAmount(todo);
        accountDao.updateById(po);
    }

    @Override
    public void raiseAccount(Long bankId, BigDecimal amount) {
        AccountPO po = accountDao.selectById(bankId);
        if (po == null)
            throw new MyServiceException("500", "不存在该账户id！");
        po.setAmount(po.getAmount().add(amount));
        accountDao.updateById(po);
    }

    /**
     * 新建一个账户，返回账户 id
     */
    @Override
    public Long makeAccount() {
        String name  = UUID.randomUUID().toString().replaceAll(" ", "");
        AccountPO po = AccountPO.builder().name(name).amount(BigDecimal.ZERO).build();
        accountDao.insert(po);
        return po.getId();
    }

    /**
     * 检查是否名称重复
     */
    private boolean checkName(String name) {
        return accountDao.selectCount(new QueryWrapper<>(AccountPO.builder().name(name).build())) > 0;
    }


}
