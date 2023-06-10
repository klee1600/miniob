package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.vo.BusinessPositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface BusinessPositionDao extends BaseMapper<BusinessPositionDao> {
    /**
     * 根据以下参数筛选经营情况
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return BusinessPositionVO
     * 要求导出excel
     */
    BigDecimal getsaleincome(Date beginTime, Date endTime);

    BigDecimal getsaleoutcome(Date beginTime, Date endTime);

    BigDecimal getproductincome(Date beginTime, Date endTime);

    BigDecimal getproductoutcome(Date beginTime, Date endTime);

    BigDecimal gethumanoutcome(Date beginTime, Date endTime);
}
