package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.PaymentPO;
import com.nju.edu.erp.model.po.WagePO;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface WageDao extends BaseMapper<WagePO> {

    WagePO getLatest();

    List<Integer> getUidHaveGZD(Integer year, Integer month);

    Integer checkPayroll(Integer uid, Integer year, Integer month);

    BigDecimal getYearlyTotalSalary(Integer uid, Integer year);

    /**
     * 根据time返回销售单
     */
    List<WagePO> findAllSheetByTime(Date beginTime, Date endTime);
}

