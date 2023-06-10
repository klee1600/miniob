package com.nju.edu.erp.model.vo.SaleReturns;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetContentVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleReturnsSheetVO {

    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 供应商id
     */
    private String supplier;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;

    /**
     * 销售人员
     */
    private String salesman;
    /**
     * 单据状态
     */
    private SaleReturnsSheetState state;
    /**
     * 折扣后退款额合计
     */
    private BigDecimal finalAmount;
    /**
     * 折扣前退款额合计
     */
    private BigDecimal rawTotalAmount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 代金券
     */
    private BigDecimal voucher;
    /**
     * 退货单具体内容
     */
    List<SaleReturnsSheetContentVO> contentList;
}
