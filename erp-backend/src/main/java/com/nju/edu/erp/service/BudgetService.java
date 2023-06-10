package com.nju.edu.erp.service;



import com.nju.edu.erp.model.po.Budget.AmountDiscountPO;
import com.nju.edu.erp.model.po.Budget.AmountOffsetPO;
import com.nju.edu.erp.model.po.Budget.LevelDiscountPO;
import com.nju.edu.erp.model.po.Budget.LevelOffsetPO;
import com.nju.edu.erp.model.vo.Budget.AmountDiscountVO;
import com.nju.edu.erp.model.vo.Budget.AmountOffsetVO;
import com.nju.edu.erp.model.vo.Budget.LevelDiscountVO;
import com.nju.edu.erp.model.vo.Budget.LevelOffsetVO;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetService {

    void amountdiscount(AmountDiscountVO vo);

    void amountoffset(AmountOffsetVO vo);

    void leveldiscount(LevelDiscountVO vo);

    void leveloffset(LevelOffsetVO vo);

    List<AmountDiscountPO> getamountdiscount();

    List<AmountOffsetPO> getamountoffset();

    List<LevelDiscountPO> getleveldiscount();

    List<LevelOffsetPO> getleveloffset();
}
