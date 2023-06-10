package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.TaxPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface TaxDao extends BaseMapper<TaxPO> {



    /**
     * 查找指定销售单下具体的商品内容
     * @param sheetId
     */
    TaxPO findContentBySheetId(Long sheetId);
}
