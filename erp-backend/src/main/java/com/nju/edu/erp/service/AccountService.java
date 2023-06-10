package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.account.AccountVO;
import com.nju.edu.erp.model.vo.account.AddAccountVO;
import com.nju.edu.erp.model.vo.account.UpdateAccountVO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void addAccount(AddAccountVO vo);

    void addInitial_Account(AddAccountVO vo);

    void delAccount(Long id);

    void updAccount(UpdateAccountVO vo);

    List<AccountVO> getAllAccount();

    List<AccountVO> getAllInitialAccount();


    void reduceAccount(Long bankId, BigDecimal amount);

    void raiseAccount(Long bankId, BigDecimal amount);

    Long makeAccount();

    void initialcreate();
}
