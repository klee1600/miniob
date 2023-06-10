package com.nju.edu.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nju.edu.erp.model.vo.SaleDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleDetailDao extends BaseMapper<SaleDetailDao> {
    /**
     * 根据以下参数筛选销售明细
     * @param salesman 销售员
     * @param customer 客户名
     * @param pname 商品名
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<SaleDetailVO> getSaleDetailList(String salesman, String customer, String pname, Date beginTime, Date endTime);

    /**
     * 相似的，筛选销售退货明细
     */
    List<SaleDetailVO> getSaleReturnsDetailList(String salesman, String customer, String pname, Date beginTime, Date endTime);
}
