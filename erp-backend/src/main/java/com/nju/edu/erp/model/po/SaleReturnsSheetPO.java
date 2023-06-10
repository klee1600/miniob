package com.nju.edu.erp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sale_returns_sheet")
public class SaleReturnsSheetPO {
    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 供应商id
     */
    private String saleSheetId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 客户
     */
    private Integer customer;
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
     * 创建时间
     */
    private Date createTime;
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
}
