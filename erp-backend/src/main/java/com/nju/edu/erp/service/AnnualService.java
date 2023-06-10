package com.nju.edu.erp.service;


import com.nju.edu.erp.model.po.AnnualPO;
import com.nju.edu.erp.model.vo.salary.AnnualVO;

import java.math.BigDecimal;
import java.util.List;

public interface AnnualService {
    void makeAnnual(AnnualVO vo);

    BigDecimal getAnnual(Integer uid, Integer year);

    boolean checkAnnual(Integer uid, Integer year);

    List<AnnualPO> getUidWithAnnual(Integer year);


}

