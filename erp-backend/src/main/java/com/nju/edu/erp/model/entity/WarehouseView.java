package com.nju.edu.erp.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseCountingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseView {

    @Excel(name = "库存id")
    private Integer id;

    @Excel(name = "数量")
    private Integer quantity;

    @Excel(name = "进价")
    private BigDecimal purchasePrice;

    @Excel(name = "批次")
    private Integer batchId;

    @Excel(name = "出厂日期", width = 20, format = "yyyy-MM-dd")
    private Date productionDate;

    //以下为商品名

    @Excel(name = "商品id")
    private String pid;

    @Excel(name = "商品名")
    private String name;

    @Excel(name = "分类id")
    private Integer categoryId;

    @Excel(name = "商品型号")
    private String type;

    @Excel(name = "商品数量")
    private Integer p_quantity;

    @Excel(name = "进价")
    private BigDecimal p_purchasePrice;

    @Excel(name = "零售价")
    private BigDecimal retailPrice;

    @Excel(name = "最近进价")
    private BigDecimal recentPp;

    @Excel(name = "最近零售价")
    private BigDecimal recentRp;

    public static WarehouseView VO2View(WarehouseCountingVO vo) {
        WarehouseView view = WarehouseView.builder()
                .id(vo.getId())
                .quantity(vo.getQuantity())
                .purchasePrice(vo.getPurchasePrice())
                .batchId(vo.getBatchId())
                .productionDate(vo.getProductionDate())
                .build();
        return pro(vo.getProduct(), view);
    }

    private static WarehouseView pro(ProductInfoVO vo, WarehouseView view) {
        view.setPid(vo.getId());
        view.setName(vo.getName());
        view.setCategoryId(vo.getCategoryId());
        view.setType(vo.getType());
        view.setP_quantity(vo.getQuantity());
        view.setP_purchasePrice(vo.getPurchasePrice());
        view.setRecentPp(vo.getRecentPp());
        view.setRecentRp(vo.getRecentRp());
        view.setRetailPrice(vo.getRetailPrice());
        return view;
    }
}
