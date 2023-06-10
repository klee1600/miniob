package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.po.CreditContentPO;
import com.nju.edu.erp.model.po.CreditPO;
import com.nju.edu.erp.model.po.PaymentContentPO;
import com.nju.edu.erp.model.po.PaymentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CreditDao extends BaseMapper<CreditPO> {

    CreditPO getLatest();


    /**
     * 根据time返回销售单
     */
    List<CreditPO> findAllSheetByTime(Date beginTime, Date endTime);

    /**
     * 查找指定销售单下具体的商品内容
     * @param sheetId
     */
    List<CreditContentPO> findContentBySheetId(String sheetId);
}
