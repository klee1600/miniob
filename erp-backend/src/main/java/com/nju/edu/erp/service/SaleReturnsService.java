package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.user.UserVO;

import java.util.List;

// 制定进货退货单 + 销售经理审批/总经理二级审批 + 更新客户表 + 更新库存
public interface SaleReturnsService {
    /**
     * 制定销售退货单
     */
    void makePurchaseReturnsSheet(UserVO userVO, SaleReturnsSheetVO vo);

    /**
     * 根据状态获取进货退货单(state == null 则获取所有进货退货单)
     *
     * @param state 进货退货单状态
     * @return 进货退货单
     */
    List<SaleReturnsSheetVO> getSalesReturnsSheetByState(SaleReturnsSheetState state);

    /**
     * 根据时间筛选销售单
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    List<SaleReturnsSheetVO> getSaleReturnsSheetByTime(String beginDateStr, String endDateStr);

    /**
     * 根据进货退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param SaleReturnsSheetId 进货退货单id
     * @param state              进货退货单修改后的状态
     */
    void approval(String SaleReturnsSheetId, SaleReturnsSheetState state);

}