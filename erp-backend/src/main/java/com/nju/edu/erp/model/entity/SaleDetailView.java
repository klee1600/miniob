package com.nju.edu.erp.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.nju.edu.erp.model.vo.SaleDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDetailView {
    @Excel(name = "时间")
    private String createDate;

    @Excel(name = "商品名")
    private String pname;

    @Excel(name = "型号")
    private String type;

    @Excel(name = "数量")
    private int quantity;

    @Excel(name = "单价")
    private BigDecimal unitPrice;

    @Excel(name = "总额")
    private BigDecimal totalPrice;

    @Excel(name = "明细类型")
    private String detailType;

    public static SaleDetailView VO2View(SaleDetailVO vo) {
        SaleDetailView view = SaleDetailView.builder()
                .createDate(vo.getCreateDate())
                .pname(vo.getPname())
                .type(vo.getType())
                .quantity(vo.getQuantity())
                .unitPrice(vo.getUnitPrice())
                .totalPrice(vo.getTotalPrice())
                .detailType(vo.getDetailType())
                .build();
        return view;
    }
}
