package com.nju.edu.erp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao extends BaseMapper<SaleReturnsSheetPO> {

    SaleReturnsSheetPO getLatest();

    /**
     * 查找所有销售单
     */
    List<SaleReturnsSheetPO> findAllSheet();

    /**
     * 根据state返回销售单
     */
    List<SaleReturnsSheetPO> findAllSheetByState(SaleSheetState state);

    /**
     * 根据time返回销售单
     */
    List<SaleReturnsSheetPO> findAllSheetByTime(Date beginTime, Date endTime);

    /**
     * 查找指定id的销售单
     * @param id
     * @return
     */
    SaleReturnsSheetPO findSheetById(String id);

    /**
     * 查找指定销售单下具体的商品内容
     * @param sheetId
     */
    List<SaleReturnsSheetContentPO> findContentBySheetId(String sheetId);
}
