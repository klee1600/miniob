package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.po.Initial_ProductPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Initial_ProductDao extends BaseMapper<Initial_ProductPO> {

    int createProduct(Initial_ProductPO productPO);

    void initialcreate();

}
