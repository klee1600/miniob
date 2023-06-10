package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.nju.edu.erp.model.po.Budget.AmountDiscountPO;
import com.nju.edu.erp.model.po.Budget.LevelDiscountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AmountDiscountDao extends BaseMapper<AmountDiscountPO> {

}

