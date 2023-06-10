package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.SignupPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface SignupDao extends BaseMapper<SignupPO> {

    Integer countMonthlyTickTimes(Integer uid, int year, int month);

    Integer check(Integer uid, int year, int month, int day);
}

