package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.po.Initial_AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface Initial_AccountDao extends BaseMapper<Initial_AccountPO> {

    void initialcreate();

}
