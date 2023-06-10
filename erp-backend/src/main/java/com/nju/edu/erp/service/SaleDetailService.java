package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.SaleDetailVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SaleDetailService {
    /**
     * 根据以下参数筛选销售明细
     * @param salesman 销售员
     * @param customer 客户名
     * @param pname 商品名
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<SaleDetailVO> getSaleDetailList(String salesman, String customer, String pname, String beginDateStr, String endDateStr);

    /**
     * 销售明细导出
     */
    void download(HttpServletResponse response, String salesman, String customer, String pname, String beginDateStr, String endDateStr);
}
