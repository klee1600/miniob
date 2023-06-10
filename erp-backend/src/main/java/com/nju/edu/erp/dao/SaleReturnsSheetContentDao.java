package com.nju.edu.erp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.po.SaleReturnsSheetContentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SaleReturnsSheetContentDao extends BaseMapper<SaleReturnsSheetContentPO> {

}
