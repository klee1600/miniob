package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.nju.edu.erp.model.po.Budget.LevelDiscountPO;
import com.nju.edu.erp.model.po.Budget.LevelOffsetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LevelOffsetDao extends BaseMapper<LevelOffsetPO> {

}

