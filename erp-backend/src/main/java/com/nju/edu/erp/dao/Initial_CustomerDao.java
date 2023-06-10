package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.Initial_CustomerPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Initial_CustomerDao extends BaseMapper<Initial_CustomerPO> {
    void initial_create();

    List<Initial_CustomerPO> findAllByType(CustomerType customerType);


}
