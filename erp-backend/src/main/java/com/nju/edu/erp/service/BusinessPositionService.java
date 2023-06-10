package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.BusinessPositionVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BusinessPositionService {
    /**
     * 根据以下参数筛选经营情况表
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return BusinessPositionVO
     * 要求导出excel
     */
    BusinessPositionVO getBusinessPosition(String beginDateStr, String endDateStr);


}
