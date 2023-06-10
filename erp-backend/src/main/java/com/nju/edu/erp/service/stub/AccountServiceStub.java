package com.nju.edu.erp.service.stub;

import com.nju.edu.erp.model.vo.account.AccountVO;
import com.nju.edu.erp.model.vo.account.AddAccountVO;
import com.nju.edu.erp.model.vo.account.UpdateAccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("AccountServiceStub")
public class AccountServiceStub implements AccountService {


    @Override
    public void addAccount(AddAccountVO vo) {
        System.out.println(vo);
        System.out.println("加账户！");
    }

    @Override
    public void addInitial_Account(AddAccountVO vo) {
        System.out.println(vo);
        System.out.println("加期初账户！");
    }

    @Override
    public void initialcreate() {
        System.out.println("期初账户导入！");
    }

    @Override
    public void delAccount(Long id) {
        System.out.println("删除！" + id);
    }

    @Override
    public void updAccount(UpdateAccountVO vo) {
        System.out.println(vo);
        System.out.println("更新！");
    }

    @Override
    public List<AccountVO> getAllAccount() {
        List<AccountVO> res = new ArrayList<>(3);
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(10L).name("ahaha").build());
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(100L).name("lll").build());
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(1L).name("aaa").build());
        return res;
    }

    @Override
    public List<AccountVO> getAllInitialAccount() {
        List<AccountVO> res = new ArrayList<>(3);
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(10L).name("ahaha").build());
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(100L).name("lll").build());
        res.add(AccountVO.builder().amount(BigDecimal.TEN).id(1L).name("aaa").build());
        return res;
    }

    /**
     * 给制定账户减少余额
     */
    @Override
    public void reduceAccount(Long bankId, BigDecimal amount) {
        System.out.println("减钱！" + bankId + amount);
    }

    /**
     * 给制定账户增加余额
     */
    @Override
    public void raiseAccount(Long bankId, BigDecimal amount) {
        System.out.println("加钱！" + bankId + amount);
    }

    /**
     * 新建一个账户，返回账户 id
     */
    @Override
    public Long makeAccount() {
        return 10L;
    }

}
